package nl.rumfumme.verlet;

import com.badlogic.gdx.math.Vector2;

public class Line {
    public Point p1;
    public Point p2;
    public boolean draw;
    public float length;
    public float stiffness = 1.0f;
    
    protected Line() {}

    public Line(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
        length = p1.pos.cpy().sub(p2.pos).len();
    }

    public Line(Point p1, Point p2, float length) {
        this.p1 = p1;
        this.p2 = p2;
        this.length = length;
    }

    public void constrain() {
        Vector2 d = p2.pos.cpy().sub(p1.pos);

        float dist = d.len();
        float diff = (length - dist) / dist / 2.0f * stiffness;

        d.scl(diff);

        if(p2.fixed) {
            if(!p1.fixed) {
                p1.pos.sub(d.scl(2.0f));
            }
        } else if(p1.fixed) {
            if(!p2.fixed) {
                p2.pos.add(d.scl(2.0f));
            }
        } else {
            p1.pos.sub(d);
            p2.pos.add(d);
        }
    }
}
