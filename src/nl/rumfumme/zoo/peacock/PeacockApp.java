package nl.rumfumme.zoo.peacock;

import nl.rumfumme.pt.ProcessingApp;
import nl.rumfumme.zoo.ZooApp;
import processing.core.PImage;

public class PeacockApp extends ZooApp {
    PImage img_body;
    PImage[] img_collar = new PImage[12];
    PImage[] img_tentacle = new PImage[5];
    PImage[] img_tail = new PImage[5];
    PImage[] img_tail_f = new PImage[5];
    PImage[] img_tail_f_o = new PImage[5];
    PImage img_tail_0;

    int x_body = 273;
    int y_body = 415;
    int[] x_collar = { 357, 357, 359, 364, 362, 369, 372, 378, 380, 373, 374, 370 };
    int[] y_collar = { 533, 551, 564, 583, 607, 616, 626, 635, 648, 652, 661, 683 };
    int[] x_tentacle = { 434, 416, 389, 362, 354 };
    int[] y_tentacle = { 867, 863, 838, 703, 710 };
    int[] x_tail = { 611, 615, 606, 605, 605 };
    int[] y_tail = { 550, 486, 424, 387, 387 };
    int x_tail_0 = 605;
    int y_tail_0 = 574;

    int[] x_collar_offs = { 0, 0, 0, 0, 0, 0, -7, -18, -18, -20, -20, -20 };
    int[] y_collar_offs = { 0, 0, -15, -16, -22, -22, -30, -35, -47, -47, -54, -76 };
    float[] collar_rot = { 0.0f, -0.13f, -0.13f, -0.22f, -0.36f, -0.64f, -0.83f, -1.0f, -1.2f, -1.3f, -1.45f, -1.5f };

    boolean collar_switch = false;
    boolean switch_pressed = false;
    float collar_phase = 1.0f;
    float collar_phase_offs = 0.0f;
    float collar_drift_phase = 0.0f;
    float collar_drift_radius = 0.1f;
    float collar_drift_max = 0.1f;
    float collar_drift_min = 0.0f;
    int collar_color_phase = 255;

    float[] tentacle_phase = { 0.0f, 0.0f, 0.0f, 0.0f, 0.0f };

    PImage[] tail_images = {
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
    PImage[] tail_f_images = {
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
    int[] x_tail_offs = {
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
    int[] y_tail_offs = {
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
    float[] tail_rot = {
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

    boolean tail_switch = false;
    float tail_phase = 0.0f;
    float tail_phase_offs = 0.0f;
    float[] tail_drift_phase = { 0.0f, 0.0f, 0.0f, 0.0f };
    float[] tail_drift_radius = { 0.1f, 0.1f, 0.1f, 0.1f };
    float[] tail_drift_max = { 0.1f, 0.1f, 0.1f, 0.1f };
    float[] tail_drift_min = { 0.0f, 0.0f, 0.0f, 0.0f };
    int tail_color_phase = 255;

    @Override
    public void load() {
        img_body = loadImage("nl/rumfumme/zoo/peacock/img/body.png");

        for (int i = 0; i < img_collar.length; i++) {
            img_collar[i] = loadImage("nl/rumfumme/zoo/peacock/img/collar_" + Integer.toString(i + 1) + ".png");
        }

        for (int i = 0; i < img_tentacle.length; i++) {
            img_tentacle[i] = loadImage("nl/rumfumme/zoo/peacock/img/tentacle_" + Integer.toString(i + 1) + ".png");
        }

        for (int i = 0; i < img_tail.length; i++) {
            img_tail[i] = loadImage("nl/rumfumme/zoo/peacock/img/tail_" + Integer.toString(i + 1) + ".png");
            img_tail_f[i] = loadImage("nl/rumfumme/zoo/peacock/img/tail_f_" + Integer.toString(i + 1) + ".png");
            img_tail_f_o[i] = loadImage("nl/rumfumme/zoo/peacock/img/tail_f_" + Integer.toString(i + 1) + "_o.png");
        }

        for (int i = 0; i < tail_images.length; i++) {
            tail_images[i] = img_tail[3 - i / 4];
            tail_f_images[i] = img_tail_f[3 - i / 4];
        }

        img_tail_0 = loadImage("nl/rumfumme/zoo/peacock/img/tail_0.png");
    }

    @Override
    public void init() {
        super.init();

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

    @Override
    public void terminate() {
        // TODO Auto-generated method stub

    }

    @Override
    public void animate() {
        fitToScreen(740, 840);
        background(0);

        translate(-x_body + 50, -y_tail[3] + 10);

        image(img_body, x_body, y_body);

        colorMode(HSB);

        for (int i = 0; i < 12; i++) {
            push();
            tint((collar_color_phase + 8 * i) % 256, (1.0f - collar_phase) * 128, 255, 255);
            translate(x_collar[i] + x_collar_offs[i] * collar_phase * collar_phase,
                    y_collar[i] + y_collar_offs[i] * collar_phase * collar_phase);
            rotate(collar_rot[i] * collar_phase * collar_phase + sin(collar_drift_phase) * collar_drift_radius);
            image(img_collar[i], 0, 0);
            pop();
        }

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

        push();
        tint(0, 0, 255, 255);
        translate(x_tail[4], y_tail[4]);
        image(img_tail[4], 0, -0);
        tint(tail_color_phase, (tail_phase / 4.0f) * 255, 255, (tail_phase / 4.0f) * 128);
        image(img_tail_f[4], 0, -0);
        pop();

        for (int i = 0; i < tail_images.length; i++) {
            float tail_part_phase = constrain(tail_phase - i / 4, 0.0f, 1.0f);

            push();
            translate(x_tail_offs[i], y_tail_offs[i] + tail_images[i].height);
            rotate(tail_rot[i] * tail_part_phase + +sin(tail_drift_phase[i / 4]) * tail_drift_radius[i / 4]);
            tint(0, 0, 255, 255);
            image(tail_images[i], 0, -tail_images[i].height);
            tint((tail_color_phase + (i & 0xc0) * 16 + (i & 0x03) * 16) & 0xff, (tail_phase / 4.0f) * 255, 255,
                    (tail_phase / 4.0f) * 192);
            image(tail_f_images[i], 0, -tail_f_images[i].height);
            pop();
        }

        tint(0, 0, 255, 255);

        push();
        translate(x_tail_0, y_tail_0);
        image(img_tail_0, 0, -0);
        pop();

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

        colorMode(RGB, 255, 255, 255);

        tint(255, 255);

        for (int i = 0; i < img_tentacle.length; i++) {
            push();
            translate(x_tentacle[i], y_tentacle[i]);
            rotate(sin(tentacle_phase[i]) * 0.05f);
            image(img_tentacle[i], 0, 0);
            pop();

            tentacle_phase[i] += random(0.0f, 0.05f);
        }

        switch_pressed = false;
    }

    @Override
    public void keyPressed() {
        super.keyPressed();

        if (key == 'b') {
            start_time = millis();
            switch_pressed = true;
        }
    }
}
