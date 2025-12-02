package nl.rumfumme.zoo.oneeye;

import nl.rumfumme.pt.ProcessingApp;
import nl.rumfumme.zoo.ZooApp;
import processing.core.PImage;

public class OneEyeApp extends ZooApp {
    PImage img_full;
    PImage img_head;
    PImage img_iris;
    PImage img_eyeball;
    PImage[] img_tooth = new PImage[11];

    int x_head = 377;
    int y_head = 259;
    int x_iris = 411;
    int y_iris = 363;
    int x_eyeball = 398;
    int y_eyeball = 351;
    int[] x_tooth = { 408, 419, 433, 445, 454, 460, 466, 472, 476, 480, 483 };
    int[] y_tooth = { 435, 436, 441, 445, 445, 448, 450, 452, 454, 455, 457 };

    boolean switch_pressed = false;
    float switch_phase = 0.0f;
    boolean switch_pressed2 = false;
    float switch_phase2 = 0.0f;
    boolean switch_pressed3 = false;
    boolean switch_pressed4 = false;
    float[] switch_tooth_phase = new float[11];
    float[] rad_tooth = new float[11];
    float rad_iris_x;
    float rad_iris_xy;
    float eye_hue = 40.0f;
    float eye_sat = 128f;

    boolean no_head = false;

    @Override
    public void load() {
        img_full = loadImage("nl/rumfumme/zoo/oneeye/img/full.png");
        img_head = loadImage("nl/rumfumme/zoo/oneeye/img/head_upper.png");
        img_iris = loadImage("nl/rumfumme/zoo/oneeye/img/eye_iris.png");
        img_eyeball = loadImage("nl/rumfumme/zoo/oneeye/img/eye_ball.png");

        for (int i = 0; i < 11; i++) {
            img_tooth[i] = loadImage("nl/rumfumme/zoo/oneeye/img/tooth_" + Integer.toString(i + 1) + ".png");
        }
    }

    @Override
    public void init() {
        start_time = millis();
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
    public void terminate() {
    }

    @Override
    public boolean finished() {
        if(switch_pressed2) {
            if(millis() - start_time > 30 * 1000) {
                return true;
            }
        } else if(millis() - start_time > 2 * 60 * 1000) {
            return true;
        }
        return false;
    }

    @Override
    public void animate() {
        fitToScreen(1000, 1000);
        background(232);
        image(img_full, 0, 0);

        if(no_head) {
            image(img_eyeball, x_eyeball, y_eyeball);
        }

        if (!switch_pressed) {
            for (int i = 0; i < 11; i++) {
                image(img_tooth[i], x_tooth[i], y_tooth[i] - (sin(rad_tooth[i]) / 2 + 0.5f) * img_tooth[i].height);
                rad_tooth[i] += random(0.0f, 0.1f);
            }
        } else {
            if(!switch_pressed3) {
                if (switch_tooth_phase[10] < 1.0f) {
                    for (int i = 0; i < 11; i++) {
                        float y_offs = 650.0f * switch_tooth_phase[i] * switch_tooth_phase[i] * switch_tooth_phase[i]
                                * switch_tooth_phase[i];

                        image(img_tooth[i], x_tooth[i],
                                y_tooth[i] - (sin(rad_tooth[i]) / 2 + 0.5f) * img_tooth[i].height + y_offs);

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

                        image(img_tooth[i], x_tooth[i],
                                y_tooth[i] - (sin(rad_tooth[i]) / 2 + 0.5f) * img_tooth[i].height + y_offs);

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
            image(img_eyeball, x_eyeball, y_eyeball);
        }

        push();
        colorMode(HSB);
        tint(eye_hue, eye_sat, 255);

        if (!switch_pressed2) {
            image(img_iris, x_iris + cos(rad_iris_x) * 30 + 11, y_iris - sin(rad_iris_xy) * 30 - 10);

            rad_iris_x = (rad_iris_x + random(0.0f, 0.1f)) % TWO_PI;
            rad_iris_xy = (rad_iris_xy + random(0.0f, 0.05f)) % TWO_PI;
            eye_hue += random(-1.3f, 1.0f);
            eye_sat += random(-1.0f, 1.0f);
            eye_hue = constrain(eye_hue, 40, 128);
            eye_sat = constrain(eye_sat, 0, 255);
        } else {
            float y_offs = 700.0f * switch_phase2 * switch_phase2;

            image(img_iris, x_iris + cos(rad_iris_x) * 30 + 11, y_iris - sin(rad_iris_xy) * 30 - 10 + y_offs);

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

        pop();

        if(!no_head) {
            image(img_head, x_head, y_head);
        }
    }

    @Override
    public void keyPressed() {
        super.keyPressed();

        if (key == 'b') {
            start_time = millis();
            if (!switch_pressed) {
                switch_pressed = true;
            } else if(!switch_pressed2) {
                switch_pressed2 = true;
            } else if(!switch_pressed3) {
                switch_pressed3 = true;
            } else if(!switch_pressed4) {
                switch_pressed4 = true;
            }
        } else if(key == '-') {
            no_head = true;
        }
    }
}
