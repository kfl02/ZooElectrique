package nl.rumfumme.zoo.birb;

import nl.rumfumme.pt.ProcessingApp;
import nl.rumfumme.zoo.ZooApp;
import processing.core.PImage;

public class BirbApp extends ZooApp {
    PImage img_body;
    PImage img_eye_right;
    PImage img_eye_left;
    PImage img_wing_1;
    PImage img_wing_2;
    PImage img_tail;
    PImage[] img_antenna = new PImage[25];

    int x_body = 0;
    int y_body = 484;
    int x_eye_right = 560;
    int y_eye_right = 669;
    int x_eye_left = 0;
    int y_eye_left = 638;
    int x_wing_1 = 638;
    int y_wing_1 = 959;
    int x_wing_2 = 656;
    int y_wing_2 = 871;
    int x_tail = 830;
    int y_tail = 1043;
    int[] x_antenna = {
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
    int[] y_antenna = {
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

    float eye_right_phase = 0.0f;
    float eye_left_phase = 0.0f;

    float antenna_phase = 0.0f;
    float antenna_phase_last = 0.0f;
    float antenna_amplitude = 0.1f;
    float antenna_amplitude_max = 0.05f;
    float antenna_amplitude_min = 0.001f;

    float stretch_antenna_phase = 0.0f;

    boolean buzz_antenna = false;
    boolean buzz_pressed = false;
    float buzz_antenna_phase = 0.0f;
    float buzz_antenna_amplitude = 0.0f;

    boolean flutter_wing = false;
    int flutter_wing_times = 0;
    float flutter_wing_radius = 0.0f;
    float flutter_wing_phase = 0.0f;

    @Override
    public void load() {
        img_body = loadImage("nl/rumfumme/zoo/birb/img/body.png");
        img_eye_right = loadImage("nl/rumfumme/zoo/birb/img/eye_right.png");
        img_eye_left = loadImage("nl/rumfumme/zoo/birb/img/eye_left.png");
        img_wing_1 = loadImage("nl/rumfumme/zoo/birb/img/wing_1.png");
        img_wing_2 = loadImage("nl/rumfumme/zoo/birb/img/wing_2.png");
        img_tail = loadImage("nl/rumfumme/zoo/birb/img/tail.png");

        for (int i = 0; i < 25; i++) {
            img_antenna[i] = loadImage("nl/rumfumme/zoo/birb/img/antenna_" + Integer.toString(i + 1) + ".png");
        }
    }

    @Override
    public void init() {
        super.init();

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
    public void terminate() {
    }

    @Override
    public void animate() {
        fitToScreen(650, 800);
        background(0);

        scale(0.5f);
        translate(100, 100);

        image(img_body, x_body, y_body);

        push();
        {
            translate(x_tail, y_tail);
            image(img_tail, 0, 0);
        }
        pop();

        if (!flutter_wing) {
            flutter_wing = random(0.0f, 1.0f) < 0.001f ? true : false;
            flutter_wing_times = (int) random(1.0f, 10.0f);
            flutter_wing_radius = random(0.05f, 0.15f);
            flutter_wing_phase = 0.0f;
        }

        push();
        {
            translate(x_wing_1, y_wing_1);
            rotate(cos(flutter_wing_phase) * flutter_wing_radius * sin(flutter_wing_phase / PI));
            image(img_wing_1, 0, 0);

        }
        pop();

        push();
        {
            translate(x_wing_2, y_wing_2);
            rotate(sin(flutter_wing_phase) * flutter_wing_radius * sin(flutter_wing_phase / PI));
            image(img_wing_2, 0, 0);

        }
        pop();

        if (flutter_wing) {
            flutter_wing_phase += random(0.2f, 0.8f);

            if (flutter_wing_phase >= TWO_PI * flutter_wing_times) {
                flutter_wing = false;
            }
        }

        push();
        {
            translate(x_eye_right + img_eye_right.width / 2, y_eye_right + img_eye_right.height / 2);
            rotate(sin(eye_right_phase) * 0.4f + 0.3f);
            image(img_eye_right, -img_eye_right.width / 2, -img_eye_right.height / 2);
        }
        pop();

        push();
        {
            translate(x_eye_left + img_eye_left.width / 2, y_eye_left + img_eye_left.height / 2);
            rotate(sin(eye_left_phase + TWO_PI) * 0.4f + 0.3f);
            image(img_eye_left, -img_eye_left.width / 2, -img_eye_left.height / 2);
        }
        pop();

        eye_right_phase = (eye_right_phase + random(0.01f, 0.02f)) % TWO_PI;
        eye_left_phase = (eye_left_phase + random(0.01f, 0.02f)) % TWO_PI;

        push();
        {
            translate(x_antenna[0], y_antenna[0]);
            image(img_antenna[0], 0, 0);

            for (int i = 1; i < 25; i++) {
                float x_offs = (i > 7)
                        ? sin((i - 7) / 18.0f * TWO_PI) * buzz_antenna_amplitude * sin(buzz_antenna_phase)
                        : 0.0f;
                float y_offs = sin(stretch_antenna_phase) * 2.0f * (antenna_amplitude / antenna_amplitude_max);

                translate(x_antenna[i] - x_antenna[i - 1], y_antenna[i] - y_antenna[i - 1] + y_offs);
                rotate(sin(antenna_phase) * antenna_amplitude);
                image(img_antenna[i], x_offs, 0);
            }
        }
        pop();

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
    }

    @Override
    public void keyPressed() {
        super.keyPressed();

        switch (key) {
            case 'b':
                start_time = millis();
                buzz_pressed = true;
                eye_left_phase = 0.0f;
                eye_right_phase = PI;
                break;
        }
    }
}
