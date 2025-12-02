package nl.rumfumme.zoo2.cages.oneeye;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.ScreenUtils;

import nl.rumfumme.zoo2.Cage;
import nl.rumfumme.zoo2.Zoo;

import static nl.rumfumme.util.Constants.*;
import static nl.rumfumme.util.Graphics.*;
import static nl.rumfumme.util.Math.*;
import static nl.rumfumme.util.Random.*;

public class OneEye extends Cage {
    private TextureAtlas atlas;
    
    private AtlasRegion img_full;
    private AtlasRegion img_head;
    private AtlasRegion img_iris;
    private AtlasRegion img_eyeball;
    private AtlasRegion[] img_tooth = new AtlasRegion[11];

    private static final int x_head = 377;
    private static final int y_head = 259;
    private static final int x_iris = 411;
    private static final int y_iris = 363;
    private static final int x_eyeball = 398;
    private static final int y_eyeball = 351;
    private static final int[] x_tooth = { 408, 419, 433, 445, 454, 460, 466, 472, 476, 480, 483 };
    private static final int[] y_tooth = { 435, 436, 441, 445, 445, 448, 450, 452, 454, 455, 457 };

    private boolean switch_pressed = false;
    private float switch_phase = 0.0f;
    private boolean switch_pressed2 = false;
    private float switch_phase2 = 0.0f;
    private boolean switch_pressed3 = false;
    private boolean switch_pressed4 = false;
    private float[] switch_tooth_phase = new float[11];
    private float[] rad_tooth = new float[11];
    private float rad_iris_x;
    private float rad_iris_xy;
    float eye_hue = 40.0f;
    float eye_sat = 128f;

    private boolean no_head = false;


    public OneEye(Zoo zoo) {
        super(zoo);
        
        atlas = new TextureAtlas(Gdx.files.internal("oneeye/oneeye.atlas"));
        
        img_full = findAtlasRegionFlipped(atlas, "full");
        img_head = findAtlasRegionFlipped(atlas, "head_upper");
        img_iris = findAtlasRegionFlipped(atlas, "eye_iris");
        img_eyeball = findAtlasRegionFlipped(atlas, "eye_ball");

        for(int i = 0; i < 11; i++) {
            img_tooth[i] = findAtlasRegionFlipped(atlas, "tooth", i + 1);
        }

        setWorldSize(1000, 1000);
    }
    
    @Override
    public void show() {
        no_head = false;

        switch_pressed = false;
        switch_phase = 0.0f;
        switch_tooth_phase = new float[12];
        switch_pressed2 = false;
        switch_phase2 = 0.0f;
        switch_pressed3 = false;
        switch_pressed4 = false;
        rad_tooth = new float[11];
        rad_iris_x = 0.0f;
        rad_iris_xy = 0.0f;
        eye_hue = 40.0f;
        eye_sat = 128f;
    }

    @Override
    public void render(float delta) {
        camera.update();
        ScreenUtils.clear(0.91f, 0.91f, 0.91f, 1.0f);
        zoo.batch.setProjectionMatrix(camera.combined);
        zoo.batch.begin();
        zoo.batch.draw(img_full, 0, 0);
        
        if(no_head) {
            zoo.batch.draw(img_eyeball, x_eyeball, y_eyeball);
        }

        if (!switch_pressed) {
            for (int i = 0; i < 11; i++) {
                zoo.batch.draw(img_tooth[i], x_tooth[i], y_tooth[i] - (sin(rad_tooth[i]) / 2 + 0.5f) * img_tooth[i].getRegionHeight());
                rad_tooth[i] += random(0.0f, 0.1f);
            }
        } else {
            if(!switch_pressed3) {
                if (switch_tooth_phase[10] < 1.0f) {
                    for (int i = 0; i < 11; i++) {
                        float y_offs = 650.0f * switch_tooth_phase[i] * switch_tooth_phase[i] * switch_tooth_phase[i]
                                * switch_tooth_phase[i];

                        zoo.batch.draw(img_tooth[i], x_tooth[i],
                                y_tooth[i] - (sin(rad_tooth[i]) / 2 + 0.5f) * img_tooth[i].getRegionHeight() + y_offs);

                        if (switch_phase * 26.0f > i) {
                            switch_tooth_phase[i] = min(switch_tooth_phase[i] + 0.02f, 1.0f);
                        }
                    }

                    switch_phase += 0.005f;
                } else {
                    switch_phase = 63999957f;
                }
            } else {
                if (switch_tooth_phase[0] > 0.0f) {
                    for (int i = 10; i >= 0; i--) {
                        float y_offs = 650.0f * switch_tooth_phase[i] * switch_tooth_phase[i] * switch_tooth_phase[i]
                                * switch_tooth_phase[i];

                        zoo.batch.draw(img_tooth[i], x_tooth[i],
                                y_tooth[i] - (sin(rad_tooth[i]) / 2 + 0.5f) * img_tooth[i].getRegionHeight() + y_offs);

                        if (switch_phase * 26.0f > i) {
                            switch_tooth_phase[i] = max(switch_tooth_phase[i] - 0.02f, 0.0f);
                        }
                    }

                    switch_phase -= 0.005f;
                } else {
                    switch_pressed = false;
                    switch_pressed3 = false;

                    for (int i = 0; i < 11; i++) {
                        switch_tooth_phase[i] = 0.0f;
                        switch_phase = 0.0f;
                    }
                }
            }
        }

        if(!no_head) {
            zoo.batch.draw(img_eyeball, x_eyeball, y_eyeball);
        }
        
        zoo.batch.setColor(fromHSV(eye_hue / 255.0f, eye_sat / 255.0f, 1.0f));
        
        if (!switch_pressed2) {
            zoo.batch.draw(img_iris, x_iris + cos(rad_iris_x) * 30 + 11, y_iris - sin(rad_iris_xy) * 30 - 10);

            rad_iris_x = (rad_iris_x + random(0.0f, 0.1f)) % TWO_PI;
            rad_iris_xy = (rad_iris_xy + random(0.0f, 0.05f)) % TWO_PI;
            eye_hue += random(-1.3f, 1.0f);
            eye_sat += random(-1.0f, 1.0f);
            eye_hue = constrain(eye_hue, 40, 128);
            eye_sat = constrain(eye_sat, 0, 255);
        } else {
            float y_offs = 700.0f * switch_phase2 * switch_phase2;

            zoo.batch.draw(img_iris, x_iris + cos(rad_iris_x) * 30 + 11, y_iris - sin(rad_iris_xy) * 30 - 10 + y_offs);

            if(!switch_pressed4) {
                if (switch_phase2 < 1.0f) {
                    switch_phase2 += 0.05f;
                } else {
                    switch_phase2 = 1.0f;
                }
            } else {
                if (switch_phase2 > 0.0f) {
                    switch_phase2 -= 0.05f;
                } else {
                    switch_phase2 = 0.0f;
                    switch_pressed2 = false;
                    switch_pressed4 = false;
                }
            }
        }

        zoo.batch.setColor(Color.WHITE);

        if(!no_head) {
            zoo.batch.draw(img_head, x_head, y_head);
        }

        zoo.batch.end();
    }
    
    @Override
    public boolean keyDown(int keycode) {
        switch(keycode) {
            case Keys.SPACE:
                if (!switch_pressed) {
                    switch_pressed = true;
                } else if(!switch_pressed2) {
                    switch_pressed2 = true;
                } else if(!switch_pressed3) {
                    switch_pressed3 = true;
                } else if(!switch_pressed4) {
                    switch_pressed4 = true;
                }
                return true;
        }
        
        return false;
    }
}
