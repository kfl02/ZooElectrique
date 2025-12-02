package nl.rumfumme.verlet;

import com.badlogic.gdx.math.Vector2;

public class Point {
    public Vector2 pos;
    public Vector2 oldPos;
    public Vector2 gravity = new Vector2(0.0f, 0.9f);
    public float radius = 0.0f;
    public float friction = 0.999f;
    public boolean fixed = false;

    public Point(Vector2 pos) {
        this.pos = pos.cpy();
        oldPos = pos.cpy();
    }
    
    public Point(Vector2 pos, Vector2 v) {
        this.pos = pos.cpy();
        oldPos = new Vector2(pos.x - v.x, pos.y - v.y);
    }

    public Point(float x, float y) {
        pos = new Vector2(x, y);
        oldPos = new Vector2(x, y);
    }
    
    public Point(float x, float y, float vx, float vy) {
        pos = new Vector2(x, y);
        oldPos = new Vector2(x - vx, y - vy);
    }

    public void move() {
        if (fixed) {
            return;
        }

        Vector2 v = pos.cpy().sub(oldPos).scl(friction);

        oldPos.set(pos);
        Vector2 np = pos.cpy().add(v).add(gravity);

        pos = np;
    }

}
