package nl.rumfumme.verlet;

import com.badlogic.gdx.math.Vector2;

public class CircleConstraint extends Constraint {
    private Vector2 center;
    private float radius;

    public CircleConstraint(Vector2 center, float radius) {
        this.center = center;
        this.radius = radius;
    }

    public CircleConstraint(float x, float y, float radius) {
        center = new Vector2(x, y);
        this.radius = radius;
    }

    /**
     * Constrain a point to the given circle.
     * 
     * @param p The point to be constrained.
     */
    public void constrain(Point p) {
        Vector2 v = p.pos.cpy().sub(p.oldPos).scl(p.friction);
        float vm = v.len();
        Vector2 po = p.pos.cpy().sub(center);
        float len = po.len();

        if(len > radius - p.radius) {
            Vector2 np = po.cpy().scl((radius - p.radius) / len).add(center);

            p.pos.set(np);

            Vector2 nv = po.cpy().scl(vm / len * surfaceFriction);
            Vector2 onp = np.cpy().add(nv);

            p.oldPos.set(onp);
        }
    }
}
