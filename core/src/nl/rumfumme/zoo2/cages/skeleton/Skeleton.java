package nl.rumfumme.zoo2.cages.skeleton;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

import nl.rumfumme.verlet.CircleConstraint;
import nl.rumfumme.verlet.Entity;
import nl.rumfumme.verlet.Line;
import nl.rumfumme.verlet.Point;
import nl.rumfumme.zoo2.Cage;
import nl.rumfumme.zoo2.Zoo;
import space.earlygrey.shapedrawer.ShapeDrawer;

import static nl.rumfumme.util.Constants.*;
import static nl.rumfumme.util.Graphics.*;
import static nl.rumfumme.util.Math.*;
import static nl.rumfumme.util.Random.*;

public class Skeleton extends Cage {
    private TextureAtlas atlas;
    private ShapeDrawer shape;
    private Texture texture;
    private TextureRegion region;

    private AtlasRegion[] imgs;

    private int[] x_offs = {
            269,    // 0
            272,    // 1
            293,    // 2
            310,    // 3
            508,    // 4
            736,    // 5
            674,    // 6
            347,    // 7
            362,    // 8
            384,    // 9
            416,    // 10
            435,    // 11
            402,    // 12
            412,    // 13
            422,    // 14
            480,    // 15
            512,    // 16
            557,    // 17
            577,    // 18
            626,    // 19
            681,    // 20
            625,    // 21
            795,    // 22
            733,    // 23
            465,    // 24
            612     // 25
        };
        private int[] y_offs = {
            444,    // 0
            624,    // 1
            606,    // 2
            744,    // 3
            742,    // 4
            793,    // 5
            779,    // 6
            858,    // 7
            878,    // 8
            909,    // 9
            919,    // 10
            904,    // 11
            930,    // 12
            961,    // 13
            1005,   // 14
            1036,   // 15
            1068,   // 16
            1097,   // 17
            1116,   // 18
            1160,   // 19
            1243,   // 20
            941,    // 21
            877,    // 22
            1071,   // 23
            902,    // 24
            837     // 25
        };

    private List<Point> points;
    private List<Line> lines;
    private Entity ent;
    private Point mid_point;
    private float stiffness = 1.0f;
    private float stiffness_dec = 0.008f;
    private float[] heading;
    private boolean dance = false;
    boolean show_lines = false;
    float due_time = 0.0f;

    public Skeleton(Zoo zoo) {
        super(zoo);

        atlas = new TextureAtlas(Gdx.files.internal("skeleton/skeleton.atlas"));
        imgs = new AtlasRegion[26];

        for(int i = 0; i < 26; i++) {
            imgs[i] = findAtlasRegionFlipped(atlas, Integer.toString(i + 1));
        }

        Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
        
        pixmap.setColor(Color.WHITE);
        pixmap.drawPixel(0, 0);

        texture = new Texture(pixmap); //remember to dispose of later
        
        pixmap.dispose();
        
        region = new TextureRegion(texture, 0, 0, 1, 1);
        shape = new ShapeDrawer(zoo.batch, region);
        
        setWorldSize(1772, 1772);
    }

    @Override
    public void show() {
        dance = false;

        points = new ArrayList<Point>();
        lines = new ArrayList<Line>();
        heading = new float[26];
        stiffness = 1.0f;
        due_time = 0.0f;

        float min_x = Float.MAX_VALUE;
        float min_y = Float.MAX_VALUE;
        float max_x = Float.MIN_VALUE;
        float max_y = Float.MIN_VALUE;

        for(int i = 0; i < 26; i++) {
            Point p = new Point(x_offs[i] + imgs[i].getRegionWidth() / 2, y_offs[i] + imgs[i].getRegionHeight() / 2);
            
            min_x = min(min_x, p.pos.x);
            min_y = min(min_y, p.pos.y);
            max_x = max(max_x, p.pos.x);
            max_y = max(max_y, p.pos.y);
            
            points.add(p);
        }
        
        float mid_x = (max_x - min_x) / 2.0f;
        float mid_y = (max_y - min_y) / 2.0f;

        mid_point = new Point(mid_x, mid_y);
        mid_point.fixed = true;

        for(int i = 0; i < 26; i++) {
            Point p = points.get(i);
            p.pos.x = p.pos.x - min_x;
            p.pos.y = p.pos.y - min_y;
            Line l = new Line(p, mid_point);
            
            lines.add(l);
            
            heading[i] = new Vector2(p.pos.x - mid_point.pos.x, p.pos.y - mid_point.pos.y).angleRad();
            Vector2 g = new Vector2(1.0f, 0.0f).setAngleRad(heading[i]).scl(10.0f);
            p.gravity = g;

            ent = new Entity(new CircleConstraint(mid_point.pos, 1181.0f / 2.0f));
            ent.addLines(lines);
        }
    }

    @Override
    public void render(float delta) {
        camera.update();
        ScreenUtils.clear(0.0f, 0.0f, 0.0f, 0.0f);
        zoo.batch.setProjectionMatrix(camera.combined);
        zoo.batch.begin();

        for(int i = 0; i < 26; i++) {
            zoo.batch.draw(imgs[i],
                    points.get(i).pos.x - imgs[i].getRegionWidth() / 2.0f + 1181.0f / 2.0f,
                    points.get(i).pos.y - imgs[i].getRegionHeight() / 2.0f + 1181.0f / 2.0f);
        }
        
        if(dance) {
            float r = random(0.0f, PI);

            for(int i = 0; i < 26; i++) {
                Point p = points.get(i);
                Vector2 g = new Vector2(1.0f, 0.0f).setAngleRad(heading[i] + r + stiffness).scl(stiffness * 2.0f - 1.0f);

                p.gravity = g;
            }
        } else {
            for(Line l : ent.lines) {
                l.stiffness = stiffness * 0.9f;
                if(show_lines) {
                    shape.line(l.p1.pos.x, l.p1.pos.y, l.p2.pos.x, l.p2.pos.y);
                }
            }
        }

        ent.update();
        
        stiffness -= stiffness_dec;
        
        if(stiffness < 0.1f) {
            stiffness = 1.0f;
        }

        if(lines.size() == 0 && !dance) {
            due_time += Gdx.graphics.getDeltaTime();
            
            if(due_time > 2.0f) {
                dance = true;
            }
        }

        zoo.batch.end();
    }

    @Override
    public boolean keyDown(int keycode) {
        switch(keycode) {
            case Keys.SPACE:
                if(lines.size() > 0) {
                    Line l = lines.get((int)random(0.0f, lines.size()));
                    lines.remove(l);
                    ent.lines.remove(l);
                }
                return true;
        }
        
        return false;
    }
}
