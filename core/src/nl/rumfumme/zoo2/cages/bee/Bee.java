package nl.rumfumme.zoo2.cages.bee;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

import nl.rumfumme.verlet.CircleConstraint;
import nl.rumfumme.verlet.Entity;
import nl.rumfumme.verlet.Line;
import nl.rumfumme.verlet.Point;
import nl.rumfumme.verlet.World;
import nl.rumfumme.zoo2.Cage;
import nl.rumfumme.zoo2.Zoo;

import static nl.rumfumme.util.Math.*;
import static nl.rumfumme.util.Random.*;

public class Bee extends Cage {
    private TextureAtlas atlas;

    private AtlasRegion img_back; 
    private AtlasRegion img_body;
    private AtlasRegion img_sting;

    private static final int x_body = 433;
    private static final int y_body = 250;
    private static final int BODY_TILE_SIZE = 16;
    private static final int STING_TILE_SIZE = 12;
    private static final int STING_STEP_SIZE = 8;

    private int BODY_NUM_TILES_X;
    private int BODY_NUM_TILES_Y;
    private int STING_NUM_TILES;
    private int STING_WIDTH;
    
    private class BPoint extends Point {
        public TextureRegion r;

        public BPoint(Vector2 pos) {
            super(pos);
        }
        
        public BPoint(Vector2 pos, Vector2 v) {
            super(pos, v);
        }

        public BPoint(float x, float y) {
            super(x, y);
        }
        
        public BPoint(float x, float y, float vx, float vy) {
            super(x, y, vx, vy);
        }
    }
    
    boolean[][] alpha_body;
    BPoint[][] pnt_body;
    BPoint[] pnt_sting;
    Entity ent_body;
    Entity ent_sting;
    World world;
    
    boolean suicide = false;
    Vector2 sting_gravity;
    float sting_gravity_rad = 0.0f;

    public Bee(Zoo zoo) {
        super(zoo);

        atlas = new TextureAtlas(Gdx.files.internal("bee/bee.atlas"));

        img_back = atlas.findRegion("background");
        img_body = atlas.findRegion("body");
        img_sting = atlas.findRegion("sting");

        TextureData textureData = img_body.getTexture().getTextureData();
        
        if(!textureData.isPrepared()) {
            textureData.prepare();
        }
        
        Pixmap pm_body = new Pixmap(img_body.getRegionWidth(), img_body.getRegionHeight(), textureData.getFormat());
        
        pm_body.drawPixmap(textureData.consumePixmap(), 
                0, 0, 
                img_body.getRegionX(), img_body.getRegionY(),
                img_body.getRegionWidth(), img_body.getRegionHeight());

        initBody(pm_body);
        pm_body.dispose();
        
        STING_NUM_TILES = img_sting.getRegionHeight() / STING_STEP_SIZE;

        setWorldSize(1288, 1276);
    }
    
    private boolean isTransparent(Pixmap p, int x, int y, int w, int h) {
        int pw = p.getWidth();
        int ph = p.getHeight();

        for(int i = x; i < min(x + w, pw); i++) {
            for(int j = y; j < min(y + h, ph); j++) {
                int px = p.getPixel(i, j);

                if((px & 0xff) != 0) {
                    return false;
                }
            }
        }
        
        return true;
    }
    
    private void initBody(Pixmap body) {
        BODY_NUM_TILES_X = body.getWidth() / BODY_TILE_SIZE;
        BODY_NUM_TILES_Y = body.getHeight() / BODY_TILE_SIZE;
        
        alpha_body = new boolean[BODY_NUM_TILES_X + 2][BODY_NUM_TILES_Y + 2];
        pnt_body = new BPoint[BODY_NUM_TILES_X + 1][BODY_NUM_TILES_Y + 1];
        
        for (int y = 0; y < BODY_NUM_TILES_Y; y++) {
            for (int x = 0; x < BODY_NUM_TILES_X; x++) {
                alpha_body[x + 1][y + 1] = isTransparent(body, x * BODY_TILE_SIZE, y * BODY_TILE_SIZE, BODY_TILE_SIZE, BODY_TILE_SIZE);
            }
        }

        for (int x = 0; x < BODY_NUM_TILES_X + 2; x++) {
            alpha_body[x][0] = true;
        }

        for (int y = 0; y < BODY_NUM_TILES_Y + 1; y++) {
            alpha_body[0][y] = true;
        }
    }
    
    @Override
    public void show() {
        sting_gravity_rad = 0.0f;
        suicide = false;

        int size_x = img_back.getRegionWidth();
        int size_y = img_back.getRegionHeight();

        ent_body = new Entity(new CircleConstraint(new Vector2(size_x / 2 - 18, size_y / 2 - 22), (size_y - 30) / 2));
        ent_body.iterations = 10;
        
        Vector2 gravity = new Vector2(random(-0.001f, 0.001f), random(-0.005f, 0.005f));

        // initialize points

        for (int x = 0; x < BODY_NUM_TILES_X + 1; x++) {
            for (int y = 0; y < BODY_NUM_TILES_Y + 1; y++) {
                BPoint p = new BPoint(x_body + x * BODY_TILE_SIZE, y_body + y * BODY_TILE_SIZE);
                p.gravity = gravity;
                p.radius = 1;

                pnt_body[x][y] = p;
            }
        }

        // initialize lines for non-empty squares

        for (int x = 1; x < BODY_NUM_TILES_X + 1; x++) {
            for (int y = 1; y < BODY_NUM_TILES_Y + 1; y++) {
                int rx = x - 1;
                int ry = y - 1;

                if (alpha_body[x][y]) {
                    // nothing to draw
                    continue;
                }
                if (alpha_body[x - 1][y] && alpha_body[x + 1][y] && alpha_body[x][y + 1] && alpha_body[x][y - 1]) {
                    // isolated square
                    continue;
                }
                if (alpha_body[x - 1][y]) {
                    // nothing on left -> draw left line
                    ent_body.addLine(pnt_body[rx][ry], pnt_body[rx][ry + 1]);
                }
                if (alpha_body[x][y - 1]) {
                    // nothing on top -> draw top line
                    ent_body.addLine(pnt_body[rx][ry], pnt_body[rx + 1][ry]);
                }
                // right line
                ent_body.addLine(pnt_body[rx + 1][ry], pnt_body[rx + 1][ry + 1]);

                // bottom line
                ent_body.addLine(pnt_body[rx][ry + 1], pnt_body[rx + 1][ry + 1]);

                // diagonal upper left, bottom right
                ent_body.addLine(pnt_body[rx][ry], pnt_body[rx + 1][ry + 1]);

                // diagonal bottom left, upper right
                ent_body.addLine(pnt_body[rx][ry + 1], pnt_body[rx + 1][ry]);
            }
        }
        
        for(Line l : ent_body.lines) {
            l.stiffness = 0.9f;
        }

        // remove points from empty squares

        for (int x = 0; x < BODY_NUM_TILES_X + 1; x++) {
            for (int y = 0; y < BODY_NUM_TILES_Y + 1; y++) {
                if (!ent_body.points.contains(pnt_body[x][y])) {
                    pnt_body[x][y] = null;
                } else {
                    if(x < BODY_NUM_TILES_X && y < BODY_NUM_TILES_Y) {
                        pnt_body[x][y].r = new TextureRegion(img_body, x * BODY_TILE_SIZE, (y + 1) * BODY_TILE_SIZE, BODY_TILE_SIZE, -BODY_TILE_SIZE);
                    }
                }
            }
        }
        
        ent_sting = new Entity(new CircleConstraint(new Vector2(size_x / 2 + 25, size_y / 2 - 22), (size_y - 20) / 2));
        ent_sting.iterations = STING_NUM_TILES / 2;

        world = new World();

        world.addEntity(ent_body);
        world.addEntity(ent_sting);

        Point anchor_sting = new Point(pnt_body[12][16].pos.x, pnt_body[12][16].pos.y);
        anchor_sting.fixed = true;
        anchor_sting.friction = 0.0f;
        sting_gravity = new Vector2(0.0f, 0.9f);

        for (int y = 0; y < STING_NUM_TILES; y++) {
            
        }
}

    @Override
    public void render(float delta) {
        camera.update();
        ScreenUtils.clear(0.91f, 0.91f, 0.91f, 1.0f);
        zoo.batch.setProjectionMatrix(camera.combined);
        zoo.batch.begin();

        zoo.batch.draw(img_back, 0.0f, 0.0f);
        
        for (int x = 0; x < BODY_NUM_TILES_X; x++) {
            for (int y = 0; y < BODY_NUM_TILES_Y; y++) {
                if (pnt_body[x][y] != null && pnt_body[x + 1][y] != null) {
                    float mx = pnt_body[x + 1][y].pos.x - pnt_body[x][y].pos.x;
                    float my = pnt_body[x + 1][y].pos.y - pnt_body[x][y].pos.y;
                    float at = atan2(my, mx);
                    
                    zoo.batch.draw(pnt_body[x][y].r,
                            pnt_body[x][y].pos.x, pnt_body[x][y].pos.y,
                            0.0f, 0.0f,
                            BODY_TILE_SIZE, BODY_TILE_SIZE,
                            1.0f, 1.0f,
                            degrees(at));
                }
            }
        }

        if (suicide) {
            for (int x = 0; x < BODY_NUM_TILES_X + 1; x++) {
                for (int y = 0; y < BODY_NUM_TILES_Y; y++) {
                    if (pnt_body[x][y] != null) {
                        pnt_body[x][y].gravity.x = sin(sting_gravity_rad) * .7f;
                        pnt_body[x][y].gravity.y = cos(sting_gravity_rad) * .05f;
                    }
                }
            }
        }

        sting_gravity_rad += random(0.0f, 0.1f);

        world.update();

        zoo.batch.end();
    }

    @Override
    public boolean keyDown(int keycode) {
        switch(keycode) {
            case Keys.B:
                suicide = true;
                return true;
        }
        
        return false;
    }
}
