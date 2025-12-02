package nl.rumfumme.zoo.bee;

import nl.rumfumme.verlet.*;
import nl.rumfumme.zoo.ZooApp;
import nl.rumfumme.pt.*;
import static nl.rumfumme.pt.util.LinAlg.squareToQuad;
import processing.core.PImage;
import processing.core.PMatrix3D;
import processing.core.PVector;

public class BeeApp extends ZooApp {
    static final int BODY_NUM_TILES_X = 32;
    static final int BODY_NUM_TILES_Y = 57;
    static final int BODY_TILE_SIZE = 16;

    static final int STING_TILE_SIZE = 12;
    static final int STING_STEP_SIZE = 8;
    static final int STING_WIDTH = 55;
    static final int STING_HEIGHT = 627;
    static final int STING_NUM_TILES = STING_HEIGHT / STING_STEP_SIZE;

    static final int PNT_LEFT_UP = 0;
    static final int PNT_RIGHT_UP = 1;
    static final int PNT_LEFT_DOWN = 2;
    static final int PNT_RIGHT_DOWN = 3;
    static final int PNT_MID_UP = 4;
    static final int PNT_MID_DOWN = 5;

    PImage img_back;
    PImage[][] img_body = new PImage[BODY_NUM_TILES_X][BODY_NUM_TILES_Y];
    boolean[][] alpha_body = new boolean[BODY_NUM_TILES_X + 2][BODY_NUM_TILES_Y + 2];
    PImage[] img_sting = new PImage[STING_NUM_TILES];
    int[][] alpha_range_sting = new int[STING_NUM_TILES][PNT_MID_DOWN + 1];

    Point[][] pnt_body = new Point[BODY_NUM_TILES_X + 1][BODY_NUM_TILES_Y + 1];
    Point[][] pnt_sting = new Point[STING_NUM_TILES + 1][PNT_MID_DOWN + 1];
    Entity ent_body;
    Entity ent_sting;
    World world;

    int x_body = 433;
    int y_body = 250;

    PVector sting_gravity;
    float sting_gravity_rad = 0.0f;
    boolean suicide = false;
    int due_time_stamp;
    boolean show_lines = false;

    public boolean checkAlpha(PImage img) {
        img.loadPixels();

        for (int i = 0; i < img.pixels.length; i++) {
            // since transparency is often at corners, hopefully this
            // will find a non-transparent pixel quickly and exit
            if (img.pixels[i] != 0) {
                return false;
            }
        }

        return true;
    }

    public int[] checkAlphaRange(PImage img) {
        int[] ret = { -1, -1 };

        img.loadPixels();

        for (int i = 0; i < img.pixels.length / img.height; i++) {
            if (img.pixels[i] != 0) {
                ret[0] = i;
                break;
            }
        }

        if (ret[0] == -1) {
            return ret;
        }

        for (int i = img.pixels.length / img.height - 1; i > 0; i--) {
            if (img.pixels[i] != 0) {
                ret[1] = i;
                break;
            }
        }

        return ret;
    }

    @Override
    public void load() {
        img_back = loadImage("nl/rumfumme/zoo/bee/img/background.png");

        /***
         * Body
         ***/

        for (int y = 0; y < BODY_NUM_TILES_Y; y++) {
            for (int x = 0; x < BODY_NUM_TILES_X; x++) {
                img_body[x][y] = loadImage(
                        "nl/rumfumme/zoo/bee/img/body_" + Integer.toString(x) + "_" + Integer.toString(y) + ".png");
                alpha_body[x + 1][y + 1] = checkAlpha(img_body[x][y]);
            }
        }

        for (int x = 0; x < BODY_NUM_TILES_X + 2; x++) {
            alpha_body[x][0] = true;
        }

        for (int y = 0; y < BODY_NUM_TILES_Y + 1; y++) {
            alpha_body[0][y] = true;
        }

        /***
         * Sting
         ***/

        for (int i = 0; i < STING_NUM_TILES; i++) {
            img_sting[i] = loadImage("nl/rumfumme/zoo/bee/img/sting_" + STING_TILE_SIZE + "_" + STING_STEP_SIZE + "_"
                    + Integer.toString(i) + ".png");
            int[] alpha_range = checkAlphaRange(img_sting[i]);

            alpha_range_sting[i][0] = alpha_range[0];
            alpha_range_sting[i][1] = alpha_range[1];
        }

    }

    int size_x = 1288;
    int size_y = 1276;

    @Override
    public void init() {
        super.init();
        show_lines = false;

        ent_body = new Entity(new CircleConstraint(new PVector(size_x / 2 - 18, size_y / 2 - 22), (size_y - 30) / 2));
        ent_body.iterations = 10;

        PVector gravity = new PVector(random(-0.001f, 0.001f), random(-0.005f, 0.005f));

        // initialize points

        for (int x = 0; x < BODY_NUM_TILES_X + 1; x++) {
            for (int y = 0; y < BODY_NUM_TILES_Y + 1; y++) {
                Point p = new Point(x_body + x * BODY_TILE_SIZE, y_body + y * BODY_TILE_SIZE);
                p.gravity = gravity;
                p.radius = 1;

                pnt_body[x][y] = p;
            }
        }

        // initialize lines for non-empty squares

        for (int x = 1; x < BODY_NUM_TILES_X + 1; x++) {
            for (int y = 1; y < BODY_NUM_TILES_Y + 1; y++) {
                int rx = x - 1;
                int ry = y - 1;
                Line l;

                if (alpha_body[x][y]) {
                    // nothing to draw
                    continue;
                }
                if (alpha_body[x - 1][y] && alpha_body[x + 1][y] && alpha_body[x][y + 1] && alpha_body[x][y - 1]) {
                    // isolated square
                    continue;
                }
                if (alpha_body[x - 1][y]) {
                    // nothing on left -> draw left line
                    l = ent_body.addLine(pnt_body[rx][ry], pnt_body[rx][ry + 1]);
                }
                if (alpha_body[x][y - 1]) {
                    // nothing on top -> draw top line
                    l = ent_body.addLine(pnt_body[rx][ry], pnt_body[rx + 1][ry]);
                }
                // right line
                l = ent_body.addLine(pnt_body[rx + 1][ry], pnt_body[rx + 1][ry + 1]);

                // bottom line
                l = ent_body.addLine(pnt_body[rx][ry + 1], pnt_body[rx + 1][ry + 1]);

                // diagonal upper left, bottom right
                l = ent_body.addLine(pnt_body[rx][ry], pnt_body[rx + 1][ry + 1]);

                // diagonal bottom left, upper right
                l = ent_body.addLine(pnt_body[rx][ry + 1], pnt_body[rx + 1][ry]);
            }
        }

        for(Line l : ent_body.lines) {
            l.stiffness = 0.9f;
        }

        // remove points from empty squares

        for (int x = 0; x < BODY_NUM_TILES_X + 1; x++) {
            for (int y = 0; y < BODY_NUM_TILES_Y + 1; y++) {
                if (!ent_body.points.contains(pnt_body[x][y])) {
                    pnt_body[x][y] = null;
                }
            }
        }

        ent_sting = new Entity(new CircleConstraint(new PVector(size_x / 2 + 25, size_y / 2 - 22), (size_y - 20) / 2));
        ent_sting.iterations = STING_NUM_TILES / 2;

        // initialize points

        world = new World();

        world.addEntity(ent_body);
        world.addEntity(ent_sting);

        Point anchor_sting = new Point(pnt_body[12][16].pos.x, pnt_body[12][16].pos.y);
        anchor_sting.fixed = true;
        anchor_sting.friction = 0.0f;
        sting_gravity = new PVector(0.0f, 0.9f);

        for (int y = 0; y < STING_NUM_TILES; y++) {
            Point p_l_u = y == 0
                    ? new Point(anchor_sting.pos.x - STING_WIDTH / 2.0f, anchor_sting.pos.y + y * STING_STEP_SIZE)
                    : pnt_sting[y - 1][PNT_LEFT_DOWN];
            Point p_r_u = y == 0
                    ? new Point(anchor_sting.pos.x + STING_WIDTH / 2.0f, anchor_sting.pos.y + y * STING_STEP_SIZE)
                    : pnt_sting[y - 1][PNT_RIGHT_DOWN];
            Point p_l_d = y == STING_NUM_TILES - 1
                    ? new Point(anchor_sting.pos.x - STING_WIDTH / 2.0f, anchor_sting.pos.y + (y + 1) * STING_STEP_SIZE)
                    : new Point(anchor_sting.pos.x - STING_WIDTH / 2.0f,
                            anchor_sting.pos.y + (y + 1) * STING_STEP_SIZE);
            Point p_r_d = y == STING_NUM_TILES - 1
                    ? new Point(anchor_sting.pos.x + STING_WIDTH / 2.0f, anchor_sting.pos.y + (y + 1) * STING_STEP_SIZE)
                    : new Point(anchor_sting.pos.x + STING_WIDTH / 2.0f,
                            anchor_sting.pos.y + (y + 1) * STING_STEP_SIZE);
            Point p_m_u = y == 0 ? new Point(PVector.add(p_l_u.pos, p_r_u.pos).mult(0.5f))
                    : pnt_sting[y - 1][PNT_MID_DOWN];
            Point p_m_d = new Point(PVector.add(p_l_d.pos, p_r_d.pos).mult(0.5f));

            for (Point p : new Point[] { p_l_u, p_r_u, p_l_d, p_r_d, p_m_u, p_m_d }) {
                if (p != null) {
                    p.gravity = sting_gravity.copy();
                    p.friction = anchor_sting.friction;
                    p.radius = 2;
                }
            }

            pnt_sting[y][PNT_LEFT_UP] = p_l_u;
            pnt_sting[y][PNT_RIGHT_UP] = p_r_u;
            pnt_sting[y][PNT_LEFT_DOWN] = p_l_d;
            pnt_sting[y][PNT_RIGHT_DOWN] = p_r_d;
            pnt_sting[y][PNT_MID_UP] = p_m_u;
            pnt_sting[y][PNT_MID_DOWN] = p_m_d;

            Line l;

            l = new Line(p_m_u, p_m_d);
            l.stiffness = 1.0f;
            ent_sting.addLine(l);
        }

        pnt_sting[0][PNT_MID_UP].fixed = true;

        for (int y = 0; y < STING_NUM_TILES; y++) {
            pnt_sting[y][PNT_MID_DOWN].pos.y = pnt_sting[0][PNT_MID_DOWN].pos.y + y;
        }

        set_due_time_stamp();
        suicide = false;
    }

    @Override
    public void terminate() {
        // TODO Auto-generated method stub

    }

    @Override
    public void animate() {
        if(millis() - due_time_stamp > 0) {
            if(!suicide) {
                PVector gravity = new PVector(random(-0.005f, 0.005f), random(-0.005f, 0.005f));

                for (int x = 0; x < BODY_NUM_TILES_X + 1; x++) {
                    for (int y = 0; y < BODY_NUM_TILES_Y; y++) {
                        if (pnt_body[x][y] != null) {
                            pnt_body[x][y].gravity = gravity;
                        }
                    }
                }
            }

            set_due_time_stamp();
        }

        fitToScreen(size_x, size_y);
        background(232);

        image(img_back, 0, 0);

        for (int x = 0; x < BODY_NUM_TILES_X; x++) {
            for (int y = 0; y < BODY_NUM_TILES_Y; y++) {
                if (pnt_body[x][y] != null && pnt_body[x + 1][y] != null) {
                    float mx = pnt_body[x + 1][y].pos.x - pnt_body[x][y].pos.x;
                    float my = pnt_body[x + 1][y].pos.y - pnt_body[x][y].pos.y;
                    float at = atan2(my, mx);

                    push();
                    translate(pnt_body[x][y].pos.x, pnt_body[x][y].pos.y);
                    rotate(at);
                    image(img_body[x][y], 0, 0);
                    pop();
                }
            }
        }

        draw_sting_3();

        world.draw();

        pnt_sting[0][PNT_MID_UP].pos = pnt_body[12][16].pos;

        if (suicide) {
            for (int y = 0; y < STING_NUM_TILES; y++) {
                pnt_sting[y][PNT_MID_UP].gravity.y = cos(sting_gravity_rad) * .5f + 0.9f;
                pnt_sting[y][PNT_MID_DOWN].gravity.y = cos(sting_gravity_rad) * .5f + 0.9f;
            }

            for (int x = 0; x < BODY_NUM_TILES_X + 1; x++) {
                for (int y = 0; y < BODY_NUM_TILES_Y; y++) {
                    if (pnt_body[x][y] != null) {
                        pnt_body[x][y].gravity.x = sin(sting_gravity_rad) * .7f;
                        pnt_body[x][y].gravity.y = cos(sting_gravity_rad) * .05f;
                    }
                }
            }
        }

        sting_gravity_rad += random(0.0f, 0.1f);
        
        if(show_lines) {
            push();
            stroke(0);

            for(Line l : ent_body.lines) {
                line(l.p1.pos.x, l.p1.pos.y, l.p2.pos.x, l.p2.pos.y);
            }

            for(Line l : ent_sting.lines) {
                line(l.p1.pos.x, l.p1.pos.y, l.p2.pos.x, l.p2.pos.y);
            }
            
            pop();
        }

        world.update();
    }

    void draw_sting_3() {
        for (int y = 0; y < STING_NUM_TILES; y++) {
            PVector[] pv = calc_rect(y);

            PVector p_l_u = pv[0];
            PVector p_r_u = pv[1];
            PVector p_l_d = pv[2];
            PVector p_r_d = pv[3];
            PVector p_m = pv[4];

            PMatrix3D m = new PMatrix3D();

            boolean r = squareToQuad(p_l_u, p_r_u, p_r_d, p_l_d, m);

            if (r) {
                push();

                applyMatrix(m);
                scale(1.0f / STING_WIDTH, 1.0f / STING_STEP_SIZE, 0.0f);

                noTint();
                image(img_sting[y], 0, 0);

                pop();
            }
        }
    }

    PVector[] calc_rect(int y) {
        PVector p_m = PVector.sub(pnt_sting[y][PNT_MID_DOWN].pos, pnt_sting[y][PNT_MID_UP].pos);
        PVector p_m2 = null;

        PVector p_l_u = new PVector(-p_m.y, p_m.x).normalize().mult(STING_WIDTH / 2.0f)
                .add(pnt_sting[y][PNT_MID_UP].pos);
        PVector p_r_u = new PVector(p_m.y, -p_m.x).normalize().mult(STING_WIDTH / 2.0f)
                .add(pnt_sting[y][PNT_MID_UP].pos);
        PVector p_l_d;
        PVector p_r_d;
        PVector p_l_d_o = null;
        PVector p_r_d_o = null;
        float a = 0.0f;

        float A_MAX = 30.0f;

        if (y == STING_NUM_TILES - 1) {
            p_l_d = new PVector(-p_m.y, p_m.x).normalize().mult(STING_WIDTH / 2.0f).add(pnt_sting[y][PNT_MID_DOWN].pos);
            p_r_d = new PVector(p_m.y, -p_m.x).normalize().mult(STING_WIDTH / 2.0f).add(pnt_sting[y][PNT_MID_DOWN].pos);
        } else {
            p_m2 = PVector.sub(pnt_sting[y + 1][PNT_MID_DOWN].pos, pnt_sting[y + 1][PNT_MID_UP].pos);

            a = degrees(PVector.angleBetween(p_m, p_m2));

            p_l_d_o = new PVector(-p_m2.y, p_m2.x).normalize().mult(STING_WIDTH / 2.0f)
                    .add(pnt_sting[y][PNT_MID_DOWN].pos);

            if (PVector.dist(p_l_u, p_l_d_o) < STING_TILE_SIZE || a > A_MAX) {
                p_l_d = PVector.add(p_l_u, p_m.copy().normalize().mult(STING_TILE_SIZE));
            } else {
                p_l_d = p_l_d_o;
            }

            p_r_d_o = new PVector(p_m2.y, -p_m2.x).normalize().mult(STING_WIDTH / 2.0f)
                    .add(pnt_sting[y][PNT_MID_DOWN].pos);

            if (PVector.dist(p_r_u, p_r_d_o) < STING_TILE_SIZE || a > A_MAX) {
                p_r_d = PVector.add(p_r_u, p_m.copy().normalize().mult(STING_TILE_SIZE));
            } else {
                p_r_d = p_r_d_o;
            }
        }

        return new PVector[] { p_l_u, p_r_u, p_l_d, p_r_d, p_m, p_m2, p_l_d_o, p_r_d_o };
    }

    void set_due_time_stamp() {
        due_time_stamp = millis() + (int)random(2000, 5000);
    }

    @Override
    public void keyPressed() {
        super.keyPressed();

        switch(key) {
            case 'b':
                start_time = millis();
                suicide = true;
                break;
                
            case '-':
                show_lines = true;
                break;
        }
    }
}
