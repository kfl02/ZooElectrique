package nl.rumfumme.zoo.red;

import java.util.ArrayList;
import java.util.List;

import nl.rumfumme.pt.ProcessingApp;
import static nl.rumfumme.pt.util.LinAlg.squareToQuad;
import nl.rumfumme.verlet.*;
import nl.rumfumme.zoo.ZooApp;
import processing.core.PImage;
import processing.core.PMatrix3D;
import processing.core.PVector;

public class RedApp extends ZooApp {
    static final int NUM_X = 80;
    static final int NUM_Y = 55;
    static final int PIX = 16;
    static final int MAX_BLOOD = 500;
    PImage[][] imgs = new PImage[NUM_X][NUM_Y];
    PImage img_tree;
    PImage img_body;
    PImage img_back;

    boolean do_verlet = false;
    boolean do_random = true;
    boolean gotcha = false;

    int x_tree = 490;
    int y_tree = 364;
    int x_body = 495;
    int y_body = 94;

    float x_tree1 = 0.0f;
    float y_tree1 = 0.0f;
    float x_tree2 = 0.0f;
    float y_tree2 = 0.0f;
    float rad_tree = 0.0f;
    float rad_tree2 = 0.0f;
    float rad_tree3 = 0.0f;
    float rad_body = 0.0f;

    World world;
    Entity ent_back;
    List<Point> points = new ArrayList<Point>();
    List<Point> blood = new ArrayList<Point>();
    float[][][] background_point_offsets = new float[NUM_X + 1][NUM_Y + 1][2];

    @Override
    public void load() {
        img_body = loadImage("nl/rumfumme/zoo/red/img/body.png");
        img_tree = loadImage("nl/rumfumme/zoo/red/img/tree.png");
        img_back = loadImage("nl/rumfumme/zoo/red/img/back.png");

        if (do_verlet || do_random) {
            for (int x = 0; x < NUM_X; x++) {
                for (int y = 0; y < NUM_Y; y++) {
                    imgs[x][y] = loadImage("nl/rumfumme/zoo/red/img/b" + PIX + "_" + x + "_" + y + ".png");
                }
            }
        }
    }

    @Override
    public void init() {
        super.init();

        gotcha = false;
        x_tree1 = x_tree;
        y_tree1 = y_tree;
        x_tree2 = x_tree + img_tree.width;
        y_tree2 = y_tree;
        rad_tree = 0.0f;
        rad_tree2 = 0.0f;
        rad_tree3 = 0.0f;
        rad_body = 0.0f;
        blood = new ArrayList<Point>();

        if (do_verlet) {
            world = new World();
            ent_back = new Entity(
                    new RectangleConstraint(new PVector(0.0f, 0.0f), new PVector(NUM_X * PIX, NUM_Y * PIX)));

            world.addEntity(ent_back);
            points.clear();

            for (int x = 0; x < NUM_X + 1; x++) {
                for (int y = 0; y < NUM_Y + 1; y++) {
                    Point p = new Point(x * PIX, y * PIX, 0.0f, 0.0f);

                    p.friction = 0.9999f;
                    p.gravity = new PVector(0.0f, 0.0f);

                    points.add(p);

                    if (y == 0 || x == 0 || y == NUM_Y || x == NUM_X) {
                        p.fixed = true;
                    }
                }
            }

            for (int x = 0; x < NUM_X + 1; x++) {
                for (int y = 0; y < NUM_Y + 1; y++) {
                    if (x < NUM_X) {
                        Line l = ent_back.addLine(points.get(x * (NUM_Y + 1) + y),
                                points.get((x + 1) * (NUM_Y + 1) + y));
                    }
                    if (y < NUM_Y) {
                        Line l = ent_back.addLine(points.get(x * (NUM_Y + 1) + y),
                                points.get(x * (NUM_Y + 1) + y + 1));
                    }
                }
            }
        } else if(do_random) {
            for (int x = 0; x < NUM_X + 1; x++) {
                for (int y = 0; y < NUM_Y + 1; y++) {
                    background_point_offsets[x][y][0] = 0.0f;
                    background_point_offsets[x][y][1] = 0.0f;
                }
            }
        }
    }

    @Override
    public void terminate() {
        // TODO Auto-generated method stub

    }

    static final float EPS = .2f;

    @Override
    public void animate() {
        fitToScreen(NUM_X * PIX, NUM_Y * PIX);
        background(105, 43, 43);

        if (do_verlet) {
            for (int x = 0; x < NUM_X; x++) {
                for (int y = 0; y < NUM_Y; y++) {
                    PVector q1 = new PVector(points.get(x * (NUM_Y + 1) + y).pos.x,
                            points.get(x * (NUM_Y + 1) + y).pos.y);
                    PVector q2 = new PVector(points.get((x + 1) * (NUM_Y + 1) + y).pos.x,
                            points.get((x + 1) * (NUM_Y + 1) + y).pos.y);
                    PVector q3 = new PVector(points.get((x + 1) * (NUM_Y + 1) + y + 1).pos.x,
                            points.get((x + 1) * (NUM_Y + 1) + y + 1).pos.y);
                    PVector q4 = new PVector(points.get(x * (NUM_Y + 1) + y + 1).pos.x,
                            points.get(x * (NUM_Y + 1) + y + 1).pos.y);

                    PMatrix3D m = new PMatrix3D();

                    boolean r = squareToQuad(q1, q2, q3, q4, m);

                    float[] mf = m.get(null);

                    if (1.0f - abs(mf[12]) < EPS || 1.0f - abs(mf[13]) < EPS) {
                        r = false;
                    }

                    if (r) {
                        push();

                        applyMatrix(m);
                        scale(1.0f / PIX, 1.0f / PIX, 0.0f);
                        image(imgs[x][y], 0.0f, 0.0f);

                        pop();
                    }
                }
            }
        } else if(do_random) {
            for (int x = 0; x < NUM_X; x++) {
                for (int y = 0; y < NUM_Y; y++) {
                    PVector q1 = new PVector(x * PIX + background_point_offsets[x][y][0], y * PIX + background_point_offsets[x][y][1]);
                    PVector q2 = new PVector((x + 1) * PIX + background_point_offsets[x + 1][y][0], y * PIX + background_point_offsets[x + 1][y][1]);
                    PVector q3 = new PVector((x + 1) * PIX + background_point_offsets[x + 1][y + 1][0], (y + 1) * PIX + background_point_offsets[x + 1][y + 1][1]);
                    PVector q4 = new PVector(x * PIX + background_point_offsets[x][y + 1][0], (y + 1) * PIX + background_point_offsets[x][y + 1][1]);

                    PMatrix3D m = new PMatrix3D();

                    boolean r = squareToQuad(q1, q2, q3, q4, m);

                    float[] mf = m.get(null);

                    if (r) {
                        push();

                        applyMatrix(m);
                        scale(1.0f / PIX, 1.0f / PIX, 0.0f);
                        image(imgs[x][y], 0.0f, 0.0f);

                        pop();
                    }
                    
                    if(x > 0 && x < NUM_X - 1 && y > 0 && y < NUM_Y - 1) {
                        background_point_offsets[x][y][0] = constrain(background_point_offsets[x][y][0] + random(-.5f, .5f), -4.0f, 4.0f);
                        background_point_offsets[x][y][1] = constrain(background_point_offsets[x][y][1] + random(-.5f, .5f), -4.0f, 4.0f);
                    }
                }
            }
        } else {
            image(img_back, 0, 0);
        }

        push();

        if (!gotcha) {
            translate(x_body, y_body + sin(rad_body) * 40.0f + sin(rad_tree3) * 10.0f, 1.0f);
            image(img_body, 0, 0);

            rad_body = (rad_body + random(0.0f, 0.05f)) % TWO_PI;
        } else {
            translate(x_body, y_body + sin(rad_body) * 40.0f + sin(rad_tree3) * 10.0f + y_tree1 - 260, 1.0f);
            image(img_body, 0, 0);

            // remove blood that ran out of screen

            blood.removeIf(p -> p.pos.y > NUM_Y * PIX);

            // spawn new blood
            if (blood.size() < MAX_BLOOD) {
                int r = (int) min(random(0, 10), MAX_BLOOD - blood.size());

                for (int i = 0; i < r; i++) {
                    Point p = new Point(img_body.width / 4.0f + random(0.0f, img_body.width / 2.0f),
                            img_body.height / 4.0f * 3.0f + random(0.0f, img_body.height / 4.0f) - 5.0f);
                    p.radius = random(2.0f, 5.0f);
                    p.gravity = new PVector(random(-0.001f, 0.001f), random(0.9f, 1.0f));

                    blood.add(p);
                }
            }

            push();

            noStroke();
            fill(255, 43, 43);

            // draw and move blood
            for (Point p : blood) {
                triangle(p.oldPos.x, p.oldPos.y, p.pos.x + p.radius, p.pos.y, p.pos.x - p.radius, p.pos.y);
                circle(p.pos.x, p.pos.y, p.radius * 2.0f);
                p.move();
            }

            pop();
        }

        pop();

        push();

        PVector q1 = new PVector(x_tree1, y_tree1);
        PVector q2 = new PVector(x_tree2, y_tree2);
        PVector q3 = new PVector(x_tree + img_tree.width, y_tree + img_tree.height);
        PVector q4 = new PVector(x_tree, y_tree + img_tree.height);

        PMatrix3D m = new PMatrix3D();

        boolean b = squareToQuad(q1, q2, q3, q4, m);

        if (b) {
            applyMatrix(m);
            translate(0.49f + cos(rad_tree2) * 0.01f, 1.0f + sin(rad_tree2) * 0.02f, 1.0f);
            scale(1.0f / img_tree.width, 1.0f / img_tree.height);
            rotateZ(sin(rad_tree) * 0.05f);

            image(img_tree, -img_tree.width / 2.0f, -img_tree.height);

            float rx = gotcha ? 0.0f : random(-1.0f * sin(rad_tree3 + PI / 2.0f), 1.0f * sin(rad_tree3));
            float ry = random(-1.0f * sin(rad_tree3), 1.0f * sin(rad_tree3 + PI / 2.0f));
            x_tree1 += rx;
            x_tree2 -= rx;
            y_tree1 += ry;
            x_tree1 = constrain(x_tree1, x_tree - 90.0f, x_tree + 120.0f);
            x_tree2 = constrain(x_tree2, x_tree + img_tree.width - 120.0f, x_tree + img_tree.width + 90.0f);
            y_tree1 = constrain(y_tree1, y_tree - 100.0f, y_tree + 30.0f);
            y_tree2 = y_tree1;

            rad_tree = (rad_tree + random(0.0f, 0.05f)) % TWO_PI;
            rad_tree2 = (rad_tree2 + random(0.0f, 0.05f)) % TWO_PI;
            rad_tree3 = (rad_tree3 + random(0.0f, 0.025f)) % TWO_PI;
        }

        pop();

        if (do_verlet) {
            world.update();

            for (Point p : points) {
                PVector g = p.gravity;
                g.x += random(-0.01f, 0.01f);
                g.x = constrain(g.x, -0.05f, 0.05f);
                g.y += random(-0.01f, 0.01f);
                g.y = constrain(g.y, -0.05f, 0.05f);
            }
        }
    }

    @Override
    public void keyPressed() {
        super.keyPressed();

        switch (key) {
            case 'b':
                start_time = millis();
                if(!gotcha) {
                    x_tree1 = x_tree + 120.0f;
                    x_tree2 = x_tree + img_tree.width - 120.0f;
                    y_tree1 = y_tree - 100.0f;
                    y_tree2 = y_tree - 100.0f;

                    float body_pos = y_body + sin(rad_body) * 40.0f + sin(rad_tree3) * 10.0f;

                    if (body_pos > y_tree - 250) {
                        gotcha = true;
                    }
                }
                break;
        }
    }
}
