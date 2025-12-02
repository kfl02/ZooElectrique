package nl.rumfumme.zoo2.cages.peacock;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Affine2;
import com.badlogic.gdx.utils.ScreenUtils;

import nl.rumfumme.zoo2.Cage;
import nl.rumfumme.zoo2.Zoo;

import static nl.rumfumme.util.Graphics.*;
import static nl.rumfumme.util.Math.*;
import static nl.rumfumme.util.Random.*;

import java.util.Stack;

public class Peacock extends Cage {
    private TextureAtlas atlas;
    
    private Stack<Affine2> matrixStack = new Stack<Affine2>();

    private AtlasRegion img_body;
    private AtlasRegion[] img_collar = new AtlasRegion[12];
    private AtlasRegion[] img_tentacle = new AtlasRegion[5];
    private AtlasRegion[] img_tail = new AtlasRegion[5];
    private AtlasRegion[] img_tail_f = new AtlasRegion[5];
    private AtlasRegion img_tail_0;

    private int x_body = 273;
    private int y_body = 415;
    private int[] x_collar = { 357, 357, 359, 364, 362, 369, 372, 378, 380, 373, 374, 370 };
    private int[] y_collar = { 533, 551, 564, 583, 607, 616, 626, 635, 648, 652, 661, 683 };
    private int[] x_tentacle = { 434, 416, 389, 362, 354 };
    private int[] y_tentacle = { 867, 863, 838, 703, 710 };
    private int[] x_tail = { 611, 615, 606, 605, 605 };
    private int[] y_tail = { 550, 486, 424, 387, 387 };
    private int x_tail_0 = 605;
    private int y_tail_0 = 574;

    private int[] x_collar_offs = { 0, 0, 0, 0, 0, 0, -7, -18, -18, -20, -20, -20 };
    private int[] y_collar_offs = { 0, 0, -15, -16, -22, -22, -30, -35, -47, -47, -54, -76 };
    private float[] collar_rot = { 0.0f, -0.13f, -0.13f, -0.22f, -0.36f, -0.64f, -0.83f, -1.0f, -1.2f, -1.3f, -1.45f, -1.5f };

    private boolean collar_switch = false;
    private boolean switch_pressed = false;
    private float collar_phase = 1.0f;
    private float collar_phase_offs = 0.0f;
    private float collar_drift_phase = 0.0f;
    private float collar_drift_radius = 0.1f;
    private float collar_drift_max = 0.1f;
    private float collar_drift_min = 0.0f;
    private int collar_color_phase = 255;

    private float[] tentacle_phase = { 0.0f, 0.0f, 0.0f, 0.0f, 0.0f };

    private AtlasRegion[] tail_images = {
            img_tail[3],
            img_tail[3],
            img_tail[3],
            img_tail[3],
            img_tail[2],
            img_tail[2],
            img_tail[2],
            img_tail[2],
            img_tail[1],
            img_tail[1],
            img_tail[1],
            img_tail[1],
            img_tail[0],
            img_tail[0],
            img_tail[0],
            img_tail[0] };
    private AtlasRegion[] tail_f_images = {
            img_tail_f[3],
            img_tail_f[3],
            img_tail_f[3],
            img_tail_f[3],
            img_tail_f[2],
            img_tail_f[2],
            img_tail_f[2],
            img_tail_f[2],
            img_tail_f[1],
            img_tail_f[1],
            img_tail_f[1],
            img_tail_f[1],
            img_tail_f[0],
            img_tail_f[0],
            img_tail_f[0],
            img_tail_f[0] };
    private int[] x_tail_offs = {
            x_tail[3],
            x_tail[3],
            x_tail[3],
            x_tail[3],
            x_tail[2],
            x_tail[2],
            x_tail[2],
            x_tail[2],
            x_tail[1],
            x_tail[1],
            x_tail[1],
            x_tail[1],
            x_tail[0],
            x_tail[0],
            x_tail[0],
            x_tail[0] };
    private int[] y_tail_offs = {
            y_tail[3],
            y_tail[3],
            y_tail[3],
            y_tail[3],
            y_tail[2],
            y_tail[2],
            y_tail[2],
            y_tail[2],
            y_tail[1],
            y_tail[1],
            y_tail[1],
            y_tail[1],
            y_tail[0],
            y_tail[0],
            y_tail[0],
            y_tail[0] };
    private float[] tail_rot = {
            0.0f,
            0.05f,
            0.10f,
            0.15f,
            0.0f,
            0.1f,
            0.2f,
            0.3f,
            0.1f,
            0.2f,
            0.3f,
            0.4f,
            0.25f,
            0.45f,
            0.65f,
            0.85f };

    private boolean tail_switch = false;
    private float tail_phase = 0.0f;
    private float tail_phase_offs = 0.0f;
    private float[] tail_drift_phase = { 0.0f, 0.0f, 0.0f, 0.0f };
    private float[] tail_drift_radius = { 0.1f, 0.1f, 0.1f, 0.1f };
    private float[] tail_drift_max = { 0.1f, 0.1f, 0.1f, 0.1f };
    private float[] tail_drift_min = { 0.0f, 0.0f, 0.0f, 0.0f };
    private int tail_color_phase = 255;

    public Peacock(Zoo zoo) {
        super(zoo);

        atlas = new TextureAtlas(Gdx.files.internal("peacock/peacock.atlas"));
        
        img_body = findAtlasRegionFlipped(atlas, "body");

        for (int i = 0; i < img_collar.length; i++) {
            img_collar[i] = findAtlasRegionFlipped(atlas, "collar", i + 1);
        }

        for (int i = 0; i < img_tentacle.length; i++) {
            img_tentacle[i] = findAtlasRegionFlipped(atlas, "tentacle", i + 1);
        }

        for (int i = 0; i < img_tail.length; i++) {
            img_tail[i] = findAtlasRegionFlipped(atlas, "tail", i + 1);
            img_tail_f[i] = findAtlasRegionFlipped(atlas, "tail_f", i + 1);
        }

        for (int i = 0; i < tail_images.length; i++) {
            tail_images[i] = img_tail[3 - i / 4];
            tail_f_images[i] = img_tail_f[3 - i / 4];
        }

        img_tail_0 = findAtlasRegionFlipped(atlas, "tail", 0);

        setWorldSize(740 + 100, 840 + 100);
    }

    @Override
    public void show() {
        collar_switch = false;
        collar_phase = 1.0f;
        collar_phase_offs = 0.0f;
        collar_drift_phase = 0.0f;
        collar_drift_radius = 0.1f;
        collar_color_phase = 255;

        for (int i = 0; i < tentacle_phase.length; i++) {
            tentacle_phase[i] = 0.0f;
        }

        tail_switch = false;
        tail_phase = 0.0f;
        tail_phase_offs = 0.0f;
        tail_color_phase = 255;

        for (int i = 0; i < tail_drift_phase.length; i++) {
            tail_drift_phase[i] = 0.0f;
            tail_drift_radius[i] = 0.1f;
        }
    }
    
    private Affine2 push(Affine2 m) {
        matrixStack.push(m);
        return new Affine2(m);
    }
    
    private Affine2 pop() {
        return matrixStack.pop();
    }

    @Override
    public void render(float delta) {
        camera.update();
        ScreenUtils.clear(0.0f, 0.0f, 0.0f, 0.0f);
        zoo.batch.setProjectionMatrix(camera.combined);
        zoo.batch.begin();

        Affine2 m = new Affine2();

        m.translate(-x_body + 50 + 50, -y_tail[3] + 10 + 50);
        drawAffineXY(zoo.batch, img_body, x_body, y_body, m);

        for (int i = 0; i < 12; i++) {
            zoo.batch.setColor(fromHSV(((collar_color_phase + 8 * i) % 256) / 255.0f, ((1.0f - collar_phase) * 128) / 255.0f, 1.0f));

            m = push(m);
            
            m.translate(x_collar[i] + x_collar_offs[i] * collar_phase * collar_phase,
                    y_collar[i] + y_collar_offs[i] * collar_phase * collar_phase);
            m.rotateRad(collar_rot[i] * collar_phase * collar_phase + sin(collar_drift_phase) * collar_drift_radius);
            drawAffine(zoo.batch, img_collar[i], m);
            
            m = pop();
        }
        
        zoo.batch.setColor(Color.WHITE);

        collar_drift_phase += random(0.005f, 0.02f);
        collar_drift_radius = constrain(collar_drift_radius + random(-0.001f, 0.002f), collar_drift_min,
                collar_drift_max);
        collar_color_phase = (collar_color_phase - 1) & 0xff;
        collar_phase = constrain(collar_phase + collar_phase_offs, 0.0f, 1.0f);

        if (collar_phase == 1.0f || collar_phase == 0.0f) {
            collar_phase_offs = 0.0f;
        }

        collar_switch = switch_pressed ? true : random(0.0f, 1.0f) < 0.01f ? true : false;

        if (collar_switch) {
            if (collar_phase_offs == 0.0f) {
                collar_phase_offs = random(0.01f, 0.04f);

                if (collar_phase == 1.0f) {
                    collar_phase_offs *= -1.0f;
                }
            } else {
                collar_phase_offs *= -1.0f;
            }
        }

        zoo.batch.setColor(Color.WHITE);
        drawAffineXY(zoo.batch, img_tail[4], x_tail[4], y_tail[4], m);
        zoo.batch.setColor(fromHSV(tail_color_phase / 255.0f, (tail_phase / 4.0f), 1.0f, (tail_phase / 4.0f) * 0.5f));
        drawAffineXY(zoo.batch, img_tail_f[4], x_tail[4], y_tail[4], m);
        
        for (int i = 0; i < tail_images.length; i++) {
            float tail_part_phase = constrain(tail_phase - i / 4, 0.0f, 1.0f);
                m = push(m);
                
                m.translate(x_tail_offs[i], y_tail_offs[i] + tail_images[i].getRegionHeight());
                m.rotateRad(tail_rot[i] * tail_part_phase + +sin(tail_drift_phase[i / 4]) * tail_drift_radius[i / 4]);
                zoo.batch.setColor(Color.WHITE);
                drawAffineXY(zoo.batch, tail_images[i], 0, -tail_images[i].getRegionHeight(), m);
                zoo.batch.setColor(fromHSV(((tail_color_phase + (i & 0xc0) * 16 + (i & 0x03) * 16) & 0xff) / 255.0f,
                        (tail_phase / 4.0f), 1.0f, (tail_phase / 4.0f) * 192.0f / 255.0f));
                drawAffineXY(zoo.batch, tail_f_images[i], 0, -tail_f_images[i].getRegionHeight(), m);

                m = pop();
        }

        zoo.batch.setColor(Color.WHITE);

        drawAffineXY(zoo.batch, img_tail_0, x_tail_0, y_tail_0, m);

        for (int i = 0; i < 4; i++) {
            tail_drift_phase[i] += random(0.005f, 0.02f);
            tail_drift_radius[i] = constrain(tail_drift_radius[i] + random(-0.001f, 0.002f), tail_drift_min[i],
                    tail_drift_max[i]);
        }

        tail_color_phase = (tail_color_phase + 3) % 256;
        tail_phase = constrain(tail_phase + tail_phase_offs, 0.0f, 4.0f);

        if (tail_phase == 1.0f || tail_phase == 0.0f) {
            tail_phase_offs = 0.0f;
        }

        tail_switch = switch_pressed ? true : random(0.0f, 1.0f) < 0.01f ? true : false;

        if (tail_switch) {
            if (tail_phase_offs == 0.0f) {
                tail_phase_offs = random(0.1f, 0.4f);

                if (tail_phase == 1.0f) {
                    tail_phase_offs *= -1.0f;
                }
            } else {
                tail_phase_offs *= -1.0f;
            }
        }

        for (int i = 0; i < img_tentacle.length; i++) {
            m = push(m);
            m.translate(x_tentacle[i], y_tentacle[i]);
            m.rotateRad(sin(tentacle_phase[i]) * 0.05f);
            drawAffine(zoo.batch, img_tentacle[i], m);
            m = pop();

            tentacle_phase[i] += random(0.0f, 0.05f);
        }

        switch_pressed = false;

        zoo.batch.end();
    }
    
    @Override
    public boolean keyDown(int keycode) {
        switch(keycode) {
            case Keys.SPACE:
                switch_pressed = true;
                return true;
        }
        
        return false;
    }
}
