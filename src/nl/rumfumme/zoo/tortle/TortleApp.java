package nl.rumfumme.zoo.tortle;

import nl.rumfumme.pt.ProcessingApp;
import nl.rumfumme.zoo.ZooApp;
import processing.core.PImage;

public class TortleApp extends ZooApp {
    PImage img_body;
    PImage img_back;
    PImage[] img_neck = new PImage[12];
    PImage[] img_tail = new PImage[3];

    int x_body = 243;
    int y_body = 271;
    int[][] x_necks = {
            { 243, 329, 336, 340, 346, 355, 362, 368, 375, 378, 379, 367 },
            { 181, 279, 313, 331, 346, 355, 362, 367, 374, 378, 379, 367 },
            { 236, 304, 331, 339, 347, 356, 363, 368, 375, 378, 379, 367 },
            { 125, 217, 258, 274, 298, 331, 354, 364, 372, 378, 388, 372 } };
    int[][] y_necks = {
            { 663, 661, 639, 620, 600, 579, 559, 539, 522, 501, 474, 448 },
            { 663, 654, 632, 610, 590, 573, 555, 536, 522, 500, 474, 448 },
            { 794, 712, 669, 635, 604, 579, 558, 538, 522, 501, 474, 448 },
            { 594, 627, 621, 605, 585, 562, 538, 524, 510, 494, 466, 444 } };
    float[][] rad_necks = {
            { 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f },
            { 0.0f, 0.13999999f, 0.31999996f, 0.33999994f, 0.31999996f, 0.16f, 0.04f, 0.03f, 0.01f, 0.01f, 0.0f, 0.0f },
            { -0.7399996f, -0.4899998f, -0.08999999f, -0.02f, -0.03f, -0.02f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f },
            {
                    0.3899999f,
                    0.56999975f,
                    0.87999946f,
                    0.7299996f,
                    0.6199997f,
                    0.5999997f,
                    0.46999982f,
                    0.23000003f,
                    0.11999998f,
                    0.03f,
                    0.08999999f,
                    0.03f } };

    int[] x_neck = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
    int[] y_neck = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
    float[] rad_neck = { 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f };

    int[] x_tail = { 741, 814, 802 };
    int[] y_tail = { 379, 508, 556 };

    boolean switch_pressed = false;
    boolean in_switch_animation = false;
    float neck_animate_phase = 0.0f;
    int neck_animate_target = 0;
    float neck_x_drift = 0.0f;
    float neck_y_drift = 0.0f;

    float tail_animate_phase = 0.0f;

    @Override
    public void load() {
        img_body = loadImage("nl/rumfumme/zoo/tortle/img/body.png");
        img_back = loadImage("nl/rumfumme/zoo/tortle/img/background.png");

        for (int i = 0; i < 12; i++) {
            img_neck[i] = loadImage("nl/rumfumme/zoo/tortle/img/neck_" + Integer.toString(i) + ".png");
            x_neck[i] = x_necks[0][i];
            y_neck[i] = y_necks[0][i];
            rad_neck[i] = rad_necks[0][i];
        }

        for (int i = 0; i < 3; i++) {
            img_tail[i] = loadImage("nl/rumfumme/zoo/tortle/img/tail_" + Integer.toString(i + 1) + ".png");
        }
    }

    @Override
    public void init() {
        super.init();

        switch_pressed = false;
        in_switch_animation = false;

        for (int i = 0; i < 12; i++) {
            x_neck[i] = x_necks[0][i];
            y_neck[i] = y_necks[0][i];
            rad_neck[i] = rad_necks[0][i];
        }

        neck_animate_phase = 0.0f;
        neck_animate_target = 0;
        neck_x_drift = 0.0f;
        neck_y_drift = 0.0f;

        tail_animate_phase = 0.0f;
    }

    @Override
    public void terminate() {
        // TODO Auto-generated method stub

    }

    @Override
    public void animate() {
        fitToScreen(img_back.width, img_back.height);
        background(224, 215, 204);
        image(img_back, 0, 0);

        translate(50, 80);

        image(img_body, x_body, y_body);

        push();
        {
            float rad_target = sin(tail_animate_phase) / 10.0f;
            int x_target = (int)map(rad_target, -0.1f, 0.1f, -12.0f, 7.0f);
            int y_target = (int)map(rad_target, -0.1f, 0.1f, -2.0f, 2.0f);

            translate(x_tail[0] + x_target, y_tail[0] + y_target);
            rotate(rad_target);
            image(img_tail[0], 0, 0);

            translate(x_tail[1] - x_tail[0], y_tail[1] - y_tail[0]);
            image(img_tail[1], 0, 0);
            translate(x_tail[2] - x_tail[1], y_tail[2] - y_tail[1]);
            image(img_tail[2], 0, 0);

            tail_animate_phase += (in_switch_animation ? random(0.1f, 0.5f) : random(0.01f, 0.05f)) % TWO_PI;
        }
        pop();

        for (int i = 11; i >= 0; i--) {
            int x_target = animate ? x_necks[neck_animate_target][i] : x_neck[i];
            int y_target = animate ? y_necks[neck_animate_target][i] : y_neck[i];
            float rad_target = animate ? rad_necks[neck_animate_target][i] : rad_neck[i];

            push();
            float x_drift = sin(neck_x_drift) * (11 - i) * 1.01f;
            float y_drift = sin(neck_y_drift) * (11 - i) * 1.08f;

            translate(lerp(x_neck[i], x_target, neck_animate_phase) + x_drift,
                    lerp(y_neck[i], y_target, neck_animate_phase) + y_drift);
            rotate(lerp(rad_neck[i], rad_target, neck_animate_phase));

            image(img_neck[i], 0, 0);

            pop();
        }

        boolean new_target = in_switch_animation ? false
                : switch_pressed ? true : random(0.0f, 1.0f) < 0.02f ? true : false;

        if (new_target) {
            for (int i = 0; i < 12; i++) {
                x_neck[i] = (int) lerp(x_neck[i], x_necks[neck_animate_target][i], neck_animate_phase);
                y_neck[i] = (int) lerp(y_neck[i], y_necks[neck_animate_target][i], neck_animate_phase);
                rad_neck[i] = lerp(rad_neck[i], rad_necks[neck_animate_target][i], neck_animate_phase);
            }

            neck_animate_phase = 0.0f;

            if (!switch_pressed) {
                neck_animate_target = (int) random(0, x_necks.length);
            } else {
                neck_animate_target = 3;
                switch_pressed = false;
                in_switch_animation = true;
            }
        } else {
            neck_animate_phase = constrain(neck_animate_phase + random(0.01f, 0.05f), 0.0f, 1.0f);

            if (in_switch_animation && neck_animate_phase == 1.0f) {
                in_switch_animation = false;
            }
        }

        neck_x_drift += random(0.0f, 0.05f);
        neck_y_drift += random(0.0f, 0.08f);
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
