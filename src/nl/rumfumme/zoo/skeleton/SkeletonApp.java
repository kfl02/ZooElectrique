package nl.rumfumme.zoo.skeleton;

import nl.rumfumme.verlet.CircleConstraint;
import nl.rumfumme.verlet.Entity;
import nl.rumfumme.verlet.Line;
import nl.rumfumme.verlet.Point;
import nl.rumfumme.zoo.ZooApp;
import processing.core.PVector;

import static nl.rumfumme.zoo.skeleton.SkeletonImageLoader.*;

import java.util.ArrayList;
import java.util.List;

public class SkeletonApp extends ZooApp {
    private SkeletonImageLoader img;
    private int[][] line_defs = {
            { IDX_1, IDX_2 },
            { IDX_1, IDX_3 },
            { IDX_1, IDX_4 },
            { IDX_1, IDX_5 },
            { IDX_2, IDX_3 },
            { IDX_2, IDX_4 },
            { IDX_3, IDX_4 },
            { IDX_3, IDX_5 },
            { IDX_4, IDX_5 },
            { IDX_4, IDX_8 },
            { IDX_4, IDX_9 },
            { IDX_4, IDX_10 },
            { IDX_4, IDX_11 },
            { IDX_5, IDX_12 },
            { IDX_5, IDX_13 },
            { IDX_5, IDX_25 },
            { IDX_5, IDX_26 },
            { IDX_5, IDX_7 },
            { IDX_5, IDX_6 },
            { IDX_5, IDX_22 },
            { IDX_5, IDX_23 },
            { IDX_5, IDX_24 },
            { IDX_6, IDX_7 },
            { IDX_6, IDX_23 },
            { IDX_6, IDX_25 },
            { IDX_7, IDX_23 },
            { IDX_7, IDX_25 },
            { IDX_8, IDX_4 },
            { IDX_8, IDX_9 },
            { IDX_8, IDX_10 },
            { IDX_8, IDX_11 },
            { IDX_8, IDX_13 },
            { IDX_9, IDX_4 },
            { IDX_9, IDX_10 },
            { IDX_9, IDX_11 },
            { IDX_9, IDX_13 },
            { IDX_10, IDX_4 },
            { IDX_10, IDX_11 },
            { IDX_10, IDX_12 },
            { IDX_10, IDX_13 },
            { IDX_10, IDX_14 },
            { IDX_11, IDX_5 },
            { IDX_11, IDX_12 },
            { IDX_11, IDX_13 },
            { IDX_11, IDX_14 },
            { IDX_12, IDX_5 },
            { IDX_13, IDX_14 },
            { IDX_13, IDX_15 },
            { IDX_13, IDX_16 },
            { IDX_13, IDX_25 },
            { IDX_14, IDX_15 },
            { IDX_14, IDX_16 },
            { IDX_14, IDX_25 },
            { IDX_15, IDX_16 },
            { IDX_15, IDX_17 },
            { IDX_15, IDX_22 },
            { IDX_15, IDX_25 },
            { IDX_16, IDX_17 },
            { IDX_16, IDX_18 },
            { IDX_16, IDX_22 },
            { IDX_17, IDX_18 },
            { IDX_17, IDX_19 },
            { IDX_17, IDX_22 },
            { IDX_18, IDX_19 },
            { IDX_18, IDX_20 },
            { IDX_18, IDX_22 },
            { IDX_19, IDX_20 },
            { IDX_19, IDX_21 },
            { IDX_19, IDX_22 },
            { IDX_19, IDX_24 },
            { IDX_20, IDX_21 },
            { IDX_20, IDX_22 },
            { IDX_20, IDX_24 },
            { IDX_21, IDX_22 },
            { IDX_21, IDX_24 },
            { IDX_21, IDX_26 },
            { IDX_22, IDX_23 },
            { IDX_22, IDX_24 },
            { IDX_22, IDX_26 },
            { IDX_23, IDX_24 },
            { IDX_23, IDX_26 },
            { IDX_24, IDX_26 },
            { IDX_25, IDX_26 },
};

    
    private List<Point> points;
    private List<Line> lines;
    private Entity ent;
    private Point mid_point;
    private float stiffness = 1.0f;
    private float stiffness_dec = 0.008f;
    private float[] heading;
    boolean dance = false;

    int due_time_stamp;
    boolean show_lines = false;
    
    @Override
    public void load() {
        img = new SkeletonImageLoader(this);
    }

    @Override
    public void init() {
        super.init();

        due_time_stamp = 0;

        show_lines = false;
        dance = false;

        points = new ArrayList(img.num_images);
        lines = new ArrayList(line_defs.length);
        heading = new float[img.num_images];
        stiffness = 1.0f;
        
        float min_x = Float.MAX_VALUE;
        float min_y = Float.MAX_VALUE;
        float max_x = Float.MIN_VALUE;
        float max_y = Float.MIN_VALUE;
        
        for(int i = 0; i < img.num_images; i++) {
            Point p = new Point(img.x_offs[i] + img.images[i].width / 2, img.y_offs[i] + img.images[i].height / 2);
            
            min_x = min(min_x, p.pos.x);
            min_y = min(min_y, p.pos.y);
            max_x = max(max_x, p.pos.x);
            max_y = max(max_y, p.pos.y);
            
            points.add(p);
        }
        
        mid_point = new Point(min_x + (max_x - min_x) / 2.0f, min_y + (max_y - min_y) / 2.0f);
        mid_point.fixed = true;
        
//        for(int i = 0; i < line_defs.length; i++) {
//            Line l = new Line(points.get(line_defs[i][0]), points.get(line_defs[i][1]));
//            l.stiffness = stiffness;
//            
//            lines.add(l);
//        }
//        
        for(int i = 0; i < img.num_images; i++) {
            Point p = points.get(i);
            Line l = new Line(p, mid_point);
            
            lines.add(l);
            
            heading[i] = new PVector(p.pos.x - mid_point.pos.x, p.pos.y - mid_point.pos.y).heading();
            PVector g = PVector.fromAngle(heading[i]).mult(10.0f);

            p.gravity = g;
        }
        
        ent = new Entity(new CircleConstraint(new PVector(1181 / 2.0f, 1772 / 2.0f), 1181 / 2.0f));
        ent.addLines(lines);
    }

    @Override
    public void terminate() {
        // TODO Auto-generated method stub

    }

    @Override
    public void animate() {
        fitToScreen(1181, 1772);
        background(0);
        stroke(255);

        for(int i = 0; i < img.num_images; i++) {
            image(img.images[i], points.get(i).pos.x - img.images[i].width / 2, points.get(i).pos.y - img.images[i].height / 2);
        }
        
        if(dance) {
            float r = random(0.0f, PI);

            for(int i = 0; i < img.num_images; i++) {
                Point p = points.get(i);
                PVector g = PVector.fromAngle(heading[i] + r + stiffness).mult(stiffness * 2.0f - 1.0f);
          
                p.gravity = g;
            }
        } else {
            for(Line l : ent.lines) {
                l.stiffness = stiffness * 0.9f;

                if(show_lines) {
                    line(l.p1.pos.x, l.p1.pos.y, l.p2.pos.x, l.p2.pos.y);
                }
            }
        }
        
        ent.update();
        
        stiffness -= stiffness_dec;
        
        if(stiffness < 0.1f) {
            stiffness = 1.0f;
        }

        if(lines.size() == 0 && millis() - due_time_stamp > 0) {
            dance = true;
        }
    }

    @Override
    public void keyPressed() {
        switch(key) {
            case 'b':
                start_time = millis();

                if(lines.size() > 0) {
                    Line l = lines.get( (int)random(0.0f, lines.size()));
                    lines.remove(l);
                    ent.lines.remove(l);
                }
                
                if(lines.size() == 0) {
                    due_time_stamp = millis() + 2000;
                }
                break;
                
            case '-':
                show_lines = true;
                break;
        }
    }
}
