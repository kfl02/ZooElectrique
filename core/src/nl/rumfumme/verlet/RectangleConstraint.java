package nl.rumfumme.verlet;

import com.badlogic.gdx.math.Vector2;

public class RectangleConstraint extends Constraint {
    private float x1;
    private float y1;
    private float x2;
    private float y2;

    public RectangleConstraint(float x1, float y1, float x2, float y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    @Override
    void constrain(Point p) {
        Vector2 v = p.pos.cpy().sub(p.oldPos).scl(p.friction);

        if(p.pos.y <= y1 + p.radius) {
            p.pos.y = y1 + p.radius;
            p.oldPos.y = p.pos.y + v.y * surfaceFriction;
        }

        if(p.pos.x <= x1 + p.radius) {
            p.pos.x = x1 + p.radius;
            p.oldPos.x = p.pos.x + v.x * surfaceFriction;
        }

        if(p.pos.y >= y2 - p.radius) {
            p.pos.y = y2 - p.radius;
            p.oldPos.y = p.pos.y + v.y * surfaceFriction;
        }

        if(p.pos.x >= x2 - p.radius) {
            p.pos.x = x2 - p.radius;
            p.oldPos.x = p.pos.x + v.x * surfaceFriction;
        }
    }
}
