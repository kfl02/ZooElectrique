package nl.rumfumme.zoo2.cages.brezelbub;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Affine2;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

import nl.rumfumme.verlet.Point;
import nl.rumfumme.zoo2.Cage;
import nl.rumfumme.zoo2.Zoo;
import space.earlygrey.shapedrawer.ShapeDrawer;

import static nl.rumfumme.util.Constants.*;
import static nl.rumfumme.util.Graphics.*;
import static nl.rumfumme.util.Math.*;
import static nl.rumfumme.util.Random.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Brezelbub extends Cage {
    // technical stuff for drawing
    private TextureAtlas atlas;
    private ShapeDrawer shape;

    private Stack<Affine2> matrixStack = new Stack<Affine2>();

    private AtlasRegion img_back;
    private AtlasRegion img_body;
    private AtlasRegion img_tail;
    private AtlasRegion img_tail1;
    private AtlasRegion img_tail2;
    private AtlasRegion img_tail3;
    private AtlasRegion img_tentacles;
    private AtlasRegion img_skull;
    private AtlasRegion img_brains[] = new AtlasRegion[4];
    private AtlasRegion img_brains_s[] = new AtlasRegion[4];
    private AtlasRegion img_mouth;

    private final int x_tail = 139;
    private final int y_tail = 466;
    private final int x_tail1 = 167;
    private final int y_tail1 = 582;
    private final int x_tail2 = 244;
    private final int y_tail2 = 583;
    private final int x_tail3 = 288;
    private final int y_tail3 = 632;
    private final int x_tentacles = 24;
    private final int y_tentacles = 401;
    private final int x_skull = 57;
    private final int y_skull = 9;
    private final int x_brain = 27;
    private final int y_brain = -1;
    private final int x_nut = 83;
    private final int y_nut = 25;
    private final int x_duck = 89;
    private final int y_duck = 26;
    private final int x_ball = 89;
    private final int y_ball = 26;
    private final int x_mouth = 7;
    private final int y_mouth = 180;
    private final int x_brains[] = { x_brain, x_nut, x_duck, x_ball };
    private final int y_brains[] = { y_brain, y_nut, y_duck, y_ball };
    private final int MAX_POOPS = 10;

    class Pooint extends Point {
        public int image_no;
        public float rot;
        public float rot_inc;
        
        public Pooint(float x, float y) {
            super(x, y);
        }
    }
    
    private float deg = 0;
    private boolean blink_eye = false;
    private boolean blink_brain = false;
    private boolean move_mouth = false;
    private boolean wiggle = false;
    private boolean poop = false;
    private int eye_phase = 0;
    private int brain_phase = 0;
    private int brain_image = 0;
    private int wiggle_times = 0;
    private float lift = 0.0f;
    private float tail_rad = 0.0f;
    private float tail1_rad1 = 0.0f;
    private float tail1_rad2 = 0.0f;
    private float tail3_rad = 0.0f;
    private float tentacle_rad1 = 0.0f;
    private float tentacle_rad2 = 0.0f;
    private float mouth_rad = 0.0f;
    private float mouth_rad_offs = 0.0f;
    private List<Pooint> poops = new ArrayList<Pooint>();

    private Affine2 push(Affine2 m) {
        matrixStack.push(m);
        return new Affine2(m);
    }
    
    private Affine2 pop() {
        return matrixStack.pop();
    }

    public Brezelbub(Zoo zoo) {
        super(zoo);

        atlas = new TextureAtlas(Gdx.files.internal("brezelbub/brezelbub.atlas"));
        img_back = findAtlasRegionFlipped(atlas, "background");
        img_body = findAtlasRegionFlipped(atlas, "body_wo_mouzh_skull_tentacles_tail");
        img_tail = findAtlasRegionFlipped(atlas, "tail");
        img_tail1 = findAtlasRegionFlipped(atlas, "tail_part1");
        img_tail2 = findAtlasRegionFlipped(atlas, "tail_part2");
        img_tail3 = findAtlasRegionFlipped(atlas, "tail_part3");
        img_tentacles = findAtlasRegionFlipped(atlas, "tentacles");
        img_skull = findAtlasRegionFlipped(atlas, "skull");
        img_brains[0] = findAtlasRegionFlipped(atlas, "brain-lateral");
        img_brains[1] = findAtlasRegionFlipped(atlas, "nut");
        img_brains[2] = findAtlasRegionFlipped(atlas, "rubberduck");
        img_brains[3] = findAtlasRegionFlipped(atlas, "football");
        img_brains_s[0] = findAtlasRegionFlipped(atlas, "brain-lateral_s");
        img_brains_s[1] = findAtlasRegionFlipped(atlas, "nut_s");
        img_brains_s[2] = findAtlasRegionFlipped(atlas, "rubberduck_s");
        img_brains_s[3] = findAtlasRegionFlipped(atlas, "football_s");
        img_mouth = findAtlasRegionFlipped(atlas, "mouth");
        shape = createShapeDrawer(zoo.batch);

        setWorldSize(1920, 1200);
    }
    
    @Override
    public void show() {
        deg = 0;
        blink_eye = false;
        blink_brain = false;
        move_mouth = false;
        wiggle = false;
        poop = false;
        eye_phase = 0;
        brain_phase = 0;
        brain_image = 0;
        wiggle_times = 0;
        lift = 0.0f;
        tail_rad = 0.0f;
        tail1_rad1 = 0.0f;
        tail1_rad2 = 0.0f;
        tail3_rad = 0.0f;
        tentacle_rad1 = 0.0f;
        tentacle_rad2 = 0.0f;
        mouth_rad = 0.0f;
        mouth_rad_offs = 0.0f;
        poops = new ArrayList<Pooint>();
    }
    
    @Override
    public void render(float delta) {
        Affine2 m = new Affine2();

        camera.update();
        ScreenUtils.clear(0, 0, 0, 1);
        zoo.batch.setProjectionMatrix(camera.combined);
        zoo.batch.begin();
        
        zoo.batch.draw(img_back, 0, 0);

        float y_offs = sin(deg) * 25;

        if (!blink_eye) {
            blink_eye = random(0.0f, 1.0f) < 0.01f ? true : false;
            eye_phase = 0;
        }

        if (!blink_brain) {
            blink_brain = random(0.0f, 1.0f) < 0.005f ? true : false;
            brain_phase = 0;
            brain_image = (int) random(0.0f, (float) img_brains.length);
        }

        if (!move_mouth) {
            move_mouth = random(0.0f, 1.0f) < 0.005f ? true : false;
            mouth_rad = 0.0f;
            mouth_rad_offs = random(0.0f, 1.0f) < 0.5f ? 0.0f : PI;
        }

        if (!wiggle) {
            wiggle = random(0.0f, 1.0f) < 0.005f ? true : false;
            tail3_rad = 0.0f;
            wiggle_times = (int) random(1.0f, 7.0f);
        }
        
        if(poop) {
            if(poops.size() < MAX_POOPS) {
                Pooint p = new Pooint(830 + x_tail + cos(tail_rad) * 5 + 35, 50 + y_tail + sin(tail_rad) * 3 + 35+ y_offs);
                p.gravity = new Vector2(random(-0.01f, 0.01f), random(0.9f, 1.0f));
                p.image_no = (int) random(0.0f, img_brains_s.length);
                p.rot = 0.0f;
                p.rot_inc = random(-PI / 20.0f, PI / 20.0f);
                
                poops.add(p);
            }
            
            poop = false;
        }

        // draw poop
        
        poops.removeIf(p -> p.pos.y > 1200);
        
        for(Pooint p : poops) {
            m = push(m);
            m.translate(p.pos.x + 50, p.pos.y + 50);
            m.rotateRad(p.rot);
            drawAffineXY(zoo.batch, img_brains_s[p.image_no], -50, -50, m);
            p.move();
            m = pop();
            
            p.rot += p.rot_inc;
        }

        m.translate(830, 50 + y_offs);

        // draw skull/brain

        // draw skull
        if (blink_brain) {
            drawAffineXY(zoo.batch, img_skull, x_skull, y_skull, m);
            
            drawAffineXY(zoo.batch, img_brains[brain_image], x_brains[brain_image], y_brains[brain_image], m);

            if (brain_phase % 3 == 0) {
                drawAffineXY(zoo.batch, img_skull, x_skull, y_skull, m);
            }

            brain_phase = brain_phase + 1;

            if (brain_phase == 17) {
                blink_brain = false;
            }
        } else {
            drawAffineXY(zoo.batch, img_skull, x_skull, y_skull, m);
        }
        
        drawAffine(zoo.batch, img_body, m);
        
        // draw and move tail
        m = push(m);
        {
            m.translate(x_tail + cos(tail_rad) * 5, y_tail + sin(tail_rad) * 3);
            drawAffine(zoo.batch, img_tail, m);
            
            // tail part
            m = push(m);
            {
                m.translate(x_tail1 - x_tail, y_tail1 - y_tail);
                m.rotateRad(sin(tail1_rad1) * 0.15f * (0.5f + 0.5f * sin(tail1_rad2)));
                drawAffine(zoo.batch, img_tail1, m);
            }
            m = pop();

            // tail part
            m = push(m);
            {
                m.translate(x_tail2 - x_tail, y_tail2 - y_tail);
                m.rotateRad(lift);
                drawAffine(zoo.batch, img_tail2, m);

                m = push(m);
                {
                    m.translate(x_tail3 - x_tail2, y_tail3 - y_tail2);

                    float wiggle_radius = wiggle ? sin(tail3_rad / (TWO_PI * wiggle_times) * PI) : 0.0f;

                    m.rotateRad(wiggle_radius * 0.25f * sin(tail3_rad));
                    drawAffine(zoo.batch, img_tail3, m);
                }
                m = pop();
            }
            m = pop();
        }
        m = pop();

        tail_rad = (tail_rad + random(0.0f, 0.05f));
        tail1_rad1 = (tail1_rad1 + random(0.0f, 0.05f));
        tail1_rad2 = (tail1_rad2 + random(0.0f, 0.008f));

        if (wiggle) {
            tail3_rad = (tail3_rad + random(0.2f, 0.1f));

            if (tail3_rad > TWO_PI * wiggle_times) {
                wiggle = false;
                tail3_rad = 0.0f;
            }
        }

        lift = (lift + random(-0.008f, 0.008f));

        if (lift < -0.5) {
            lift = -0.5f;
        }

        if (lift > 0.0) {
            lift = 0.0f;
        }

        // draw, move and scale tentacles
        m = push(m);

        float scale_factor = 1.0f + 1.0f / 20.0f + cos(tentacle_rad1) / 20.0f;

        m.translate(x_tentacles, y_tentacles);
        m.rotateRad(sin(tentacle_rad2) * 0.02f * (0.5f + 0.5f * sin(tentacle_rad1)));
        m.scale(1.0f, scale_factor);
        drawAffine(zoo.batch, img_tentacles, m);

        m = pop();

        tentacle_rad1 = (tentacle_rad1 + random(0.0f, 0.05f));
        tentacle_rad2 = (tentacle_rad2 + random(0.0f, 0.005f));

        // draw mouth
        m = push(m);
        {
            m.translate(x_mouth, y_mouth);

            if (move_mouth) {
                scale_factor = 1.0f + sin(mouth_rad + mouth_rad_offs) / 10.0f;

                m = push(m);
                {
                    m.scale(1.0f, scale_factor);
                    drawAffine(zoo.batch, img_mouth, m);
                }
                pop();

                mouth_rad = (mouth_rad + random(0.0f, 0.15f));

                if (mouth_rad > TWO_PI) {
                    move_mouth = false;
                }
            } else {
                drawAffine(zoo.batch, img_mouth, m);
            }
        }
        m = pop();

        // blink eyes
        if (blink_eye) {
            int weight = eye_phase > 15 ? 30 - eye_phase : eye_phase;
            
            shape.setColor(Color.BLACK);
            shape.setDefaultLineWidth(weight);

            shape.ellipse(830 + 28, 50 + y_offs + 99, (30 - weight) / 2.0f, (40 - weight) / 2.0f);
            shape.ellipse(830 + 63, 50 + y_offs + 142, (30 - weight) / 2.0f, (40 - weight) / 2.0f);

            eye_phase = eye_phase + 2;

            if (eye_phase > 30) {
                blink_eye = false;
            }
        }

        zoo.batch.end();
    }

    @Override
    public void dispose() {
        shape.getRegion().getTexture().dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        switch(keycode) {
            case Keys.SPACE:
                poop = true;
                return true;
        }
        
        return false;
    }
}
