package nl.rumfumme.zoo2.cages.birb;

import nl.rumfumme.zoo2.Cage;
import nl.rumfumme.zoo2.Zoo;

import static nl.rumfumme.util.Constants.*;
import static nl.rumfumme.util.Graphics.*;
import static nl.rumfumme.util.Math.*;
import static nl.rumfumme.util.Random.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Affine2;
import com.badlogic.gdx.utils.ScreenUtils;

public class Birb extends Cage {
    private TextureAtlas atlas;

    private AtlasRegion img_body;
    private AtlasRegion img_eye_right;
    private AtlasRegion img_eye_left;
    private AtlasRegion img_wing_1;
    private AtlasRegion img_wing_2;
    private AtlasRegion img_tail;
    private AtlasRegion[] img_antenna = new AtlasRegion[25];

    private static final int x_body = 0;
    private static final int y_body = 484;
    private static final int x_eye_right = 560;
    private static final int y_eye_right = 669;
    private static final int x_eye_left = 0;
    private static final int y_eye_left = 638;
    private static final int x_wing_1 = 638;
    private static final int y_wing_1 = 959;
    private static final int x_wing_2 = 656;
    private static final int y_wing_2 = 871;
    private static final int x_tail = 830;
    private static final int y_tail = 1043;
    private static final int[] x_antenna = {
            248,
            291,
            300,
            312,
            327,
            337,
            340,
            355,
            355,
            355,
            355,
            355,
            355,
            355,
            354,
            354,
            355,
            355,
            355,
            355,
            357,
            356,
            356,
            357,
            359 // 25
    };
    private static final int[] y_antenna = {
            470,
            456,
            441,
            425,
            408,
            395,
            368,
            348,
            328,
            308,
            288,
            268,
            248,
            228,
            208,
            188,
            168,
            148,
            128,
            108,
            88,
            68,
            48,
            28,
            0// 25
    };

    private float eye_right_phase = 0.0f;
    private     float eye_left_phase = 0.0f;

    private float antenna_phase = 0.0f;
    private float antenna_phase_last = 0.0f;
    private float antenna_amplitude = 0.1f;
    private float antenna_amplitude_max = 0.05f;
    private float antenna_amplitude_min = 0.001f;

    private float stretch_antenna_phase = 0.0f;

    private boolean buzz_antenna = false;
    private boolean buzz_pressed = false;
    private float buzz_antenna_phase = 0.0f;
    private float buzz_antenna_amplitude = 0.0f;

    private boolean flutter_wing = false;
    private int flutter_wing_times = 0;
    private float flutter_wing_radius = 0.0f;
    private float flutter_wing_phase = 0.0f;

    public Birb(Zoo zoo) {
        super(zoo);

        atlas = new TextureAtlas(Gdx.files.internal("birb/birb.atlas"));

        img_body = findAtlasRegionFlipped(atlas, "body");
        img_eye_right = findAtlasRegionFlipped(atlas, "eye_right");
        img_eye_left = findAtlasRegionFlipped(atlas, "eye_left");
        img_wing_1 = findAtlasRegionFlipped(atlas, "wing", 1);
        img_wing_2 = findAtlasRegionFlipped(atlas, "wing", 2);
        img_tail = findAtlasRegionFlipped(atlas, "tail");

        for (int i = 0; i < 25; i++) {
            img_antenna[i] = findAtlasRegionFlipped(atlas, "antenna", i + 1);
        }
        
        setWorldSize(1010 + 100, 1446 + 100);
    }

    @Override
    public void show() {
        eye_right_phase = 0.0f;
        eye_left_phase = 0.0f;

        antenna_phase = 0.0f;
        antenna_phase_last = 0.0f;
        antenna_amplitude = 0.1f;
        antenna_amplitude_max = 0.05f;
        antenna_amplitude_min = 0.001f;

        stretch_antenna_phase = 0.0f;

        buzz_antenna = false;
        buzz_antenna_phase = 0.0f;
        buzz_antenna_amplitude = 0.0f;

        flutter_wing = false;
        flutter_wing_times = 0;
        flutter_wing_radius = 0.0f;
        flutter_wing_phase = 0.0f;
    }

    @Override
    public void render(float delta) {
        camera.update();
        ScreenUtils.clear(0.0f, 0.0f, 0.0f, 0.0f);
        zoo.batch.setProjectionMatrix(camera.combined);
        zoo.batch.begin();
        
        final float T_X = 50.0f;
        final float T_Y = 50.0f;
        
        zoo.batch.draw(img_body, T_X + x_body, T_Y + y_body);
        zoo.batch.draw(img_tail, T_X + x_tail, T_Y + y_tail);

        if (!flutter_wing) {
            flutter_wing = random(0.0f, 1.0f) < 0.001f ? true : false;
            flutter_wing_times = (int) random(1.0f, 10.0f);
            flutter_wing_radius = random(0.05f, 0.15f);
            flutter_wing_phase = 0.0f;
        }

        drawRotated(zoo.batch, img_wing_1, T_X + x_wing_1, T_Y + y_wing_1, cos(flutter_wing_phase) * flutter_wing_radius * sin(flutter_wing_phase / PI));
        drawRotated(zoo.batch, img_wing_2, T_X + x_wing_2, T_Y + y_wing_2, sin(flutter_wing_phase) * flutter_wing_radius * sin(flutter_wing_phase / PI));

        if (flutter_wing) {
            flutter_wing_phase += random(0.2f, 0.8f);

            if (flutter_wing_phase >= TWO_PI * flutter_wing_times) {
                flutter_wing = false;
            }
        }

        drawRotatedCentered(zoo.batch, img_eye_right, T_X + x_eye_right, T_Y + y_eye_right, sin(eye_right_phase) * 0.4f + 0.3f);
        drawRotatedCentered(zoo.batch, img_eye_left, T_X + x_eye_left, T_Y + y_eye_left, sin(eye_left_phase + TWO_PI) * 0.4f + 0.3f);

        eye_right_phase = (eye_right_phase + random(0.01f, 0.02f)) % TWO_PI;
        eye_left_phase = (eye_left_phase + random(0.01f, 0.02f)) % TWO_PI;

        zoo.batch.draw(img_antenna[0], T_X + x_antenna[0], T_Y + y_antenna[0]);

        Affine2 m = new Affine2();
        
        m.translate(T_X + x_antenna[0], T_Y + y_antenna[0]);

        for (int i = 1; i < 25; i++) {
            float x_offs = (i > 7)
                    ? sin((i - 7) / 18.0f * TWO_PI) * buzz_antenna_amplitude * sin(buzz_antenna_phase)
                    : 0.0f;
            float y_offs = sin(stretch_antenna_phase) * 2.0f * (antenna_amplitude / antenna_amplitude_max);
            
            m.translate(x_antenna[i] - x_antenna[i - 1], y_antenna[i] - y_antenna[i - 1] + y_offs);
            m.rotateRad(sin(antenna_phase) * antenna_amplitude);
            
            Affine2 m2 = new Affine2(m);
            m2.translate(x_offs, 0.0f);

            zoo.batch.draw(img_antenna[i], img_antenna[i].getRegionWidth(), img_antenna[i].getRegionHeight(), m2);
        }

        antenna_phase_last = antenna_phase;
        antenna_phase = (antenna_phase + random(0.005f, 0.05f)) % 360.0f;

        if (Math.signum(sin(antenna_phase)) != Math.signum(sin(antenna_phase_last))) {
            antenna_amplitude -= 0.01f * (antenna_amplitude / antenna_amplitude_max);

            if (!buzz_antenna) {
                buzz_antenna = random(0.0f, 1.0f) < 0.5f ? true : false;
                buzz_antenna_phase = 0;

                if (buzz_antenna) {
                    buzz_antenna_amplitude = random(5.0f, 30.0f);
                    antenna_amplitude += buzz_antenna_amplitude / 1000.0f;
                } else {
                    buzz_antenna_amplitude = 0.0f;
                }
            }
        }

        if (buzz_pressed) {
            buzz_antenna_amplitude = random(10.0f, 100.0f);
            antenna_amplitude += buzz_antenna_amplitude / 1000.0f;
            flutter_wing = true;
            buzz_antenna = true;
            buzz_pressed = false;
        }

        if (buzz_antenna) {
            buzz_antenna_phase += random(0.1f, 0.2f);
            buzz_antenna_amplitude -= 1.0f;

            if (buzz_antenna_amplitude <= 0.0f) {
                buzz_antenna_phase = 0.0f;
                buzz_antenna_amplitude = 0.0f;
                buzz_antenna = false;
            }
        }

        antenna_amplitude = constrain(antenna_amplitude, antenna_amplitude_min, antenna_amplitude_max);

        stretch_antenna_phase = (stretch_antenna_phase + 0.1f) % 360.0f;
        
        zoo.batch.end();
    }

    @Override
    public boolean keyDown(int keycode) {
        switch(keycode) {
            case Keys.B:
                buzz_pressed = true;
                return true;
        }
        
        return false;
    }

}
