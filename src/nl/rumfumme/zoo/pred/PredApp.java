package nl.rumfumme.zoo.pred;

import nl.rumfumme.pt.ProcessingApp;
import nl.rumfumme.verlet.Entity;
import nl.rumfumme.verlet.Line;
import nl.rumfumme.verlet.Point;
import nl.rumfumme.verlet.RectangleConstraint;
import nl.rumfumme.zoo.ZooApp;
import processing.core.PImage;
import processing.core.PMatrix;
import processing.core.PVector;

import static nl.rumfumme.zoo.pred.PredImageLoader.*;

import java.util.ArrayList;
import java.util.List;

public class PredApp extends ZooApp {
    private PredImageLoader img;
    private boolean move_head = false;
    private float head_deg = 0.0f;
    private float head_deg_inc = 0.0f;
    private int head_deg_frame_cnt = 0;
    private float head_x_rotated = -230.0f;
    private float head_y_rotated = -20.0f;

    private final PVector dendrite1 = new PVector(984, 660);
    private final PVector dendrite2 = new PVector(1002, 724);
    private final PVector dendrite3 = new PVector(979, 804);
    
    private List<Line> lines;
    private Entity ent_tail;
    
    @Override
    public void load() {
        img = new PredImageLoader(this);
    }

    @Override
    public void init() {
        super.init();

        head_deg = 0.0f;
        head_deg_inc = 0.0f;
        head_deg_frame_cnt = 0;
        
        lines = new ArrayList<Line>(7);
        
        Point p1 = new Point(0, img.y_offs[IDX_TAIL_1]);
        Point p2 = new Point(0, img.y_offs[IDX_TAIL_2]);
        Point p3 = new Point(0, img.y_offs[IDX_TAIL_3]);
        Point p4 = new Point(0, img.y_offs[IDX_TAIL_4]);
        Point p5 = new Point(0, img.y_offs[IDX_TAIL_5]);
        Point p6 = new Point(0, img.y_offs[IDX_TAIL_6]);
        Point p7 = new Point(0, img.y_offs[IDX_TAIL_6] + img.img_tail_6.height, 2.0f, 0.0f);

        p1.fixed = true;
        p1.friction = 0.99f;
        p2.friction = 0.99f;
        p3.friction = 0.99f;
        p4.friction = 0.99f;
        p5.friction = 0.99f;
        p6.friction = 0.99f;
        p7.friction = 0.99f;
        
        lines.add(new Line(p1, p2));
        lines.add(new Line(p2, p3));
        lines.add(new Line(p3, p4));
        lines.add(new Line(p4, p5));
        lines.add(new Line(p5, p6));
        lines.add(new Line(p6, p7));
        
        ent_tail = new Entity(new RectangleConstraint(new PVector(-200.0f, -2000.0f), new PVector(200.0f, 2000.0f)));
        ent_tail.addLines(lines);
    }

    @Override
    public void terminate() {
        // TODO Auto-generated method stub

    }

    PMatrix pm;
    PMatrix pmi;
    
    @Override
    public void animate() {
        fitToScreen(1760, 1772);

        if(pm == null) {
            pm = getMatrix();
            pmi = pm.get();
            pmi.invert();
        }
        
        background(250, 245, 239);

        if(!move_head) {
            move_head = random(0.0f, 1.0f) < 0.005f ? true : false;
            head_deg_inc = 0.005f;
            head_deg_frame_cnt = (int) random(0.0f, 2.0f * frameRate);
        }

        float mx = lines.get(0).p2.pos.x - lines.get(0).p1.pos.x;
        float my = lines.get(0).p2.pos.y - lines.get(0).p1.pos.y;
        float at = atan2(mx, my);
        
        push();
        translate(img.x_offs[IDX_TAIL_1] + lines.get(0).p1.pos.x, img.y_offs[IDX_TAIL_1]);
        rotate(-at);
        image(img.img_tail_1, 0, 0);
        pop();

        mx = lines.get(1).p2.pos.x - lines.get(1).p1.pos.x;
        my = lines.get(1).p2.pos.y - lines.get(1).p1.pos.y;
        at = atan2(mx, my);

        push();
        translate(img.x_offs[IDX_TAIL_2] + lines.get(1).p1.pos.x, img.y_offs[IDX_TAIL_2]);
        rotate(-at);
        image(img.img_tail_2, 0, 0);
        pop();

        mx = lines.get(2).p2.pos.x - lines.get(2).p1.pos.x;
        my = lines.get(2).p2.pos.y - lines.get(2).p1.pos.y;
        at = atan2(mx, my);

        push();
        translate(img.x_offs[IDX_TAIL_3] + lines.get(2).p1.pos.x, img.y_offs[IDX_TAIL_3]);
        rotate(-at);
        image(img.img_tail_3, 0, 0);
        pop();

        mx = lines.get(3).p2.pos.x - lines.get(3).p1.pos.x;
        my = lines.get(3).p2.pos.y - lines.get(3).p1.pos.y;
        at = atan2(mx, my);

        push();
        translate(img.x_offs[IDX_TAIL_4] + lines.get(3).p1.pos.x, img.y_offs[IDX_TAIL_4]);
        rotate(-at);
        image(img.img_tail_4, 0, 0);
        pop();

        mx = lines.get(4).p2.pos.x - lines.get(4).p1.pos.x;
        my = lines.get(4).p2.pos.y - lines.get(4).p1.pos.y;
        at = atan2(mx, my);

        push();
        translate(img.x_offs[IDX_TAIL_5] + lines.get(4).p1.pos.x, img.y_offs[IDX_TAIL_5]);
        rotate(-at);
        image(img.img_tail_5, 0, 0);
        pop();

        mx = lines.get(5).p2.pos.x - lines.get(5).p1.pos.x;
        my = lines.get(5).p2.pos.y - lines.get(5).p1.pos.y;
        at = atan2(mx, my);

        push();
        translate(img.x_offs[IDX_TAIL_6] + lines.get(5).p1.pos.x, img.y_offs[IDX_TAIL_6]);
        rotate(-at);
        image(img.img_tail_6, 0, 0);
        pop();
        
        ent_tail.update();
        
        if(abs(lines.get(5).p2.pos.x - lines.get(5).p2.oldPos.x) < 0.1f) {
            lines.get(5).p2.oldPos.x += random(-1.0f, 1.0f);
        }
        
        push();
        
        float phase = (cos(head_deg + PI) + 1.0f) / 2.0f; // normalize between 0 and 1
        float deg = phase * -PI / 2.0f;
        float x_offs = phase * head_x_rotated;
        float y_offs = phase * head_y_rotated;

        translate(img.x_offs[IDX_HEAD], img.y_offs[IDX_HEAD]);
        rotate(deg);
        image(img.img_head, x_offs, y_offs);
        
        PMatrix m = getMatrix();

        PVector d1 = pmi.mult(m.mult(new PVector(dendrite1.x - img.x_offs[IDX_HEAD] + x_offs, dendrite1.y - img.y_offs[IDX_HEAD] + y_offs), new PVector()), new PVector());
        PVector d2 = pmi.mult(m.mult(new PVector(dendrite2.x - img.x_offs[IDX_HEAD] + x_offs, dendrite2.y - img.y_offs[IDX_HEAD] + y_offs), new PVector()), new PVector());
        PVector d3 = pmi.mult(m.mult(new PVector(dendrite3.x - img.x_offs[IDX_HEAD] + x_offs, dendrite3.y - img.y_offs[IDX_HEAD] + y_offs), new PVector()), new PVector());
        
        pop();

        drawLine(dendrite1.x, dendrite1.y, d1.x, d1.y, img.img_dendrite_brush_1);
        drawLine(dendrite2.x, dendrite2.y, d2.x, d2.y, img.img_dendrite_brush_2);
        drawLine(dendrite3.x, dendrite3.y, d3.x, d3.y, img.img_dendrite_brush_3);

        image(img.img_full_without_tail_and_head, img.x_offs[IDX_FULL_WITHOUT_TAIL_AND_HEAD], img.y_offs[IDX_FULL_WITHOUT_TAIL_AND_HEAD]);
        
        if(move_head) {
            head_deg = head_deg + head_deg_inc;
            
            if(head_deg > TWO_PI) {
                head_deg = 0.0f;
                move_head = false;
            } else if(head_deg > PI) {
                if(head_deg_frame_cnt > 0) {
                    head_deg_inc = 0.0f;
                    head_deg_frame_cnt--;
                } else {
                    head_deg_inc = 0.005f;
                }
            }
        }
    }
    
    private void drawLine(float x1, float y1, float x2, float y2, PImage brush) {
        float d = dist(x1, y1, x2, y2);
        int n = (int)(d / (brush.width / 2.0f));

        for(int i = 0; i <= n; i++) {
            float a = (float)i / (float) n;
            float x = lerp(x1, x2, a);
            float y = y1 + (y2 - y1) * a * a;
            
            image(brush, x - brush.width / 2.0f, y - brush.height / 2.0f);
        }
    }
    
    @Override
    public void keyPressed() {
        switch(key) {
            case 'b':
                start_time = millis();
                move_head = true;
                head_deg_inc = 0.1f;
                head_deg_frame_cnt = (int) random(0.0f, 2.0f * frameRate);
                lines.get(5).p2.oldPos.x += 20.0f * Math.signum(random(-1.0f, 1.0f));

                if(head_deg > PI) {
                    head_deg = -(head_deg - PI) + PI;
                }
                break;
        }
    }

}
