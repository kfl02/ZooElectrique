package nl.rumfumme.zoo.brezelbub;

import java.util.ArrayList;
import java.util.List;

import nl.rumfumme.pt.ProcessingApp;
import nl.rumfumme.verlet.Point;
import nl.rumfumme.zoo.ZooApp;
import processing.core.PImage;
import processing.core.PVector;

public class BrezelbubApp extends ZooApp {
    PImage img_back;
    PImage img_body;
    PImage img_tail;
    PImage img_tail1;
    PImage img_tail2;
    PImage img_tail3;
    PImage img_tentacles;
    PImage img_skull;
    PImage img_brains[] = new PImage[4];
    PImage img_brains_s[] = new PImage[4];
    PImage img_mouth;
    PImage img_mouth2;

    final int x_tail = 139;
    final int y_tail = 466;
    final int x_tail1 = 167;
    final int y_tail1 = 582;
    final int x_tail2 = 244;
    final int y_tail2 = 583;
    final int x_tail3 = 288;
    final int y_tail3 = 632;
    final int x_tentacles = 24;
    final int y_tentacles = 401;
    final int x_skull = 57;
    final int y_skull = 9;
    final int x_brain = 27;
    final int y_brain = -1;
    final int x_nut = 83;
    final int y_nut = 25;
    final int x_duck = 89;
    final int y_duck = 26;
    final int x_ball = 89;
    final int y_ball = 26;
    final int x_mouth = 7;
    final int y_mouth = 180;
    final int x_mouth2 = 69;
    final int y_mouth2 = 237;
    final int x_brains[] = { x_brain, x_nut, x_duck, x_ball };
    final int y_brains[] = { y_brain, y_nut, y_duck, y_ball };
    final int MAX_POOPS = 10;

    class Pooint extends Point {
        public int image_no;
        public float rot;
        public float rot_inc;
        
        public Pooint(float x, float y) {
            super(x, y);
        }
    }
    
    float deg = 0;
    boolean blink_eye = false;
    boolean blink_brain = false;
    boolean move_mouth = false;
    boolean burp = false;
    boolean wiggle = false;
    boolean poop = false;
    int eye_phase = 0;
    int brain_phase = 0;
    int brain_image = 0;
    int wiggle_times = 0;
    float lift = 0.0f;
    float tail_rad = 0.0f;
    float tail1_rad1 = 0.0f;
    float tail1_rad2 = 0.0f;
    float tail3_rad = 0.0f;
    float tentacle_rad1 = 0.0f;
    float tentacle_rad2 = 0.0f;
    float mouth_rad = 0.0f;
    float mouth_rad_offs = 0.0f;
    List<Pooint> poops = new ArrayList<Pooint>();

    float frame_rate_ratio = 1.0f;

    @Override
    public void load() {
        img_back = loadImage("nl/rumfumme/zoo/brezelbub/img/background.png");
        img_body = loadImage("nl/rumfumme/zoo/brezelbub/img/body_wo_mouzh_skull_tentacles_tail.png");
        img_tail = loadImage("nl/rumfumme/zoo/brezelbub/img/tail.png");
        img_tail1 = loadImage("nl/rumfumme/zoo/brezelbub/img/tail_part1.png");
        img_tail2 = loadImage("nl/rumfumme/zoo/brezelbub/img/tail_part2.png");
        img_tail3 = loadImage("nl/rumfumme/zoo/brezelbub/img/tail_part3.png");
        img_tentacles = loadImage("nl/rumfumme/zoo/brezelbub/img/tentacles.png");
        img_skull = loadImage("nl/rumfumme/zoo/brezelbub/img/skull.png");
        img_brains[0] = loadImage("nl/rumfumme/zoo/brezelbub/img/brain-lateral.png");
        img_brains[1] = loadImage("nl/rumfumme/zoo/brezelbub/img/nut.png");
        img_brains[2] = loadImage("nl/rumfumme/zoo/brezelbub/img/rubberduck.png");
        img_brains[3] = loadImage("nl/rumfumme/zoo/brezelbub/img/football.png");
        img_brains_s[0] = loadImage("nl/rumfumme/zoo/brezelbub/img/brain-lateral_s.png");
        img_brains_s[1] = loadImage("nl/rumfumme/zoo/brezelbub/img/nut_s.png");
        img_brains_s[2] = loadImage("nl/rumfumme/zoo/brezelbub/img/rubberduck_s.png");
        img_brains_s[3] = loadImage("nl/rumfumme/zoo/brezelbub/img/football_s.png");
        img_mouth = loadImage("nl/rumfumme/zoo/brezelbub/img/mouth.png");
        img_mouth2 = loadImage("nl/rumfumme/zoo/brezelbub/img/mouth2.png");

        frame_rate_ratio = frameRate / 60.0f;
    }

    @Override
    public void init() {
        super.init();

        deg = 0;
        blink_eye = false;
        blink_brain = false;
        move_mouth = false;
        burp = false;
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
    public boolean fadeIn() {
        return false;
    }

    @Override
    public void terminate() {
    }

    @Override
    public void animate() {
        fitToScreen(1920, 1200);
        background(0);
        image(img_back, 0, 0);

        float y_offs = sin(deg) * 25 * frame_rate_ratio;

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
                p.gravity = new PVector(random(-0.01f, 0.01f), random(0.9f, 1.0f));
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
            push();
            translate(p.pos.x + 50, p.pos.y + 50);
            rotate(p.rot);
            image(img_brains_s[p.image_no], -50, -50);
            p.move();
            pop();
            
            p.rot += p.rot_inc;
        }

        translate(830, 50 + y_offs);

        // draw skull/brain

        // draw skull
        if (blink_brain) {
            image(img_skull, x_skull, y_skull);
            
            image(img_brains[brain_image], x_brains[brain_image], y_brains[brain_image]);

            if (brain_phase % 3 == 0) {
                image(img_skull, x_skull, y_skull);
            }

            brain_phase = brain_phase + 1;

            if (brain_phase == 17) {
                blink_brain = false;
            }
        } else {
            image(img_skull, x_skull, y_skull);
        }

        // draw body
        image(img_body, 0, 0);
        
        // draw and move tail
        push();
        {
            translate(x_tail + cos(tail_rad) * 5, y_tail + sin(tail_rad) * 3);
            image(img_tail, 0, 0);
            
            // tail part
            push();
            {
                translate(x_tail1 - x_tail, y_tail1 - y_tail);
                rotate(sin(tail1_rad1) * 0.15f * (0.5f + 0.5f * sin(tail1_rad2)));
                image(img_tail1, 0, 0);
            }
            pop();

            // tail part
            push();
            {
                translate(x_tail2 - x_tail, y_tail2 - y_tail);
                rotate(lift);
                image(img_tail2, 0, 0);

                push();
                {
                    translate(x_tail3 - x_tail2, y_tail3 - y_tail2);

                    float wiggle_radius = wiggle ? sin(tail3_rad / (TWO_PI * wiggle_times) * PI) : 0.0f;

                    rotate(wiggle_radius * 0.25f * sin(tail3_rad));
                    image(img_tail3, 0, 0);
                }
                pop();
            }
            pop();
        }
        pop();

        tail_rad = (tail_rad + random(0.0f, 0.05f)) * frame_rate_ratio;
        tail1_rad1 = (tail1_rad1 + random(0.0f, 0.05f)) * frame_rate_ratio;
        tail1_rad2 = (tail1_rad2 + random(0.0f, 0.008f)) * frame_rate_ratio;

        if (wiggle) {
            tail3_rad = (tail3_rad + random(0.2f, 0.1f)) * frame_rate_ratio;

            if (tail3_rad > TWO_PI * wiggle_times) {
                wiggle = false;
                tail3_rad = 0.0f;
            }
        }

        lift = (lift + random(-0.008f, 0.008f)) * frame_rate_ratio;

        if (lift < -0.5) {
            lift = -0.5f;
        }

        if (lift > 0.0) {
            lift = 0.0f;
        }

        // draw, move and scale tentacles
        push();

        float scale_factor = 1.0f + 1.0f / 20.0f + cos(tentacle_rad1) / 20.0f;

        translate(x_tentacles, y_tentacles);
        rotate(sin(tentacle_rad2) * 0.02f * (0.5f + 0.5f * sin(tentacle_rad1)));
        scale(1.0f, scale_factor);
        image(img_tentacles, 0, 0);

        pop();

        tentacle_rad1 = (tentacle_rad1 + random(0.0f, 0.05f)) * frame_rate_ratio;
        tentacle_rad2 = (tentacle_rad2 + random(0.0f, 0.005f)) * frame_rate_ratio;

        // draw mouth
        push();
        {
            translate(x_mouth, y_mouth);

            if (move_mouth) {

                scale_factor = 1.0f + sin(mouth_rad + mouth_rad_offs) / 10.0f;

                push();
                {
                    scale(1.0f, scale_factor);
                    image(img_mouth, 0, 0);
                }
                pop();

                push();
                {
                    scale(1.0f, scale_factor);
                    image(img_mouth2, x_mouth2 - x_mouth, y_mouth2 - y_mouth);
                }
                pop();

                mouth_rad = (mouth_rad + random(0.0f, 0.15f)) * frame_rate_ratio;

                if (mouth_rad > TWO_PI) {
                    move_mouth = false;
                }
            } else {
                image(img_mouth, 0, 0);
                image(img_mouth2, x_mouth2 - x_mouth, y_mouth2 - y_mouth);
            }
        }
        pop();

        // blink eyes
        if (blink_eye) {
            push();
            fill(0, 0, 0, 0);
            stroke(0);
            ellipseMode(CENTER);

            int weight = eye_phase > 15 ? 30 - eye_phase : eye_phase;

            strokeWeight(weight);

            ellipse(28, 99, 30 - weight, 40 - weight);
            ellipse(63, 142, 30 - weight, 40 - weight);

            eye_phase = eye_phase + 2;

            if (eye_phase > 30) {
                blink_eye = false;
            }
            pop();
        }

        deg = (deg + PI / 180.0f * frame_rate_ratio) % TWO_PI;
    }

    @Override
    public void keyPressed() {
        super.keyPressed();

        switch (key) {
            case 'b':
                start_time = millis();
                poop = true;
                break;
        }
    }
}
