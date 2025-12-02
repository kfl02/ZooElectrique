package nl.rumfumme.zooelectrique.cages.helloanybody.util;

import nl.rumfumme.zooelectrique.cages.helloanybody.Constants;

import static java.lang.Math.*;
import static nl.rumfumme.zooelectrique.cages.helloanybody.Constants.numDivisions;

public class Square {
    private int x = Integer.MIN_VALUE;
    private int y = Integer.MIN_VALUE;
    private int a = 0;
    private int index = Integer.MIN_VALUE;

    public static int getOuterIndex(Square square, int index) {
        int inX = index % square.getA();
        int inY = index / square.getA();

        if(inY >= square.getA()) {
            inY = square.getA() - 1;
        }

        return ((square.getY() + inY) % numDivisions) * numDivisions + ((square.getX() + inX) % numDivisions);
    }

    private void adjust() {
        if (a > Constants.numDivisions) {
            a = Constants.numDivisions;
        }

        if (a < 1) {
            a = 1;
        }

        if (x > Constants.numDivisions - a) {
            x = Constants.numDivisions - a;
        }

        if (y > Constants.numDivisions - a) {
            y = Constants.numDivisions - a;
        }

        index = y * Constants.numDivisions + x;
    }

    public Square() {
    }

    public Square(int x, int y, int a) {
        this.x = x;
        this.y = y;
        this.a = a;

        adjust();
    }

    public Square(Square square) {
        this(square.x, square.y, square.a);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getA() {
        return a;
    }

    public int getIndex() {
        return index;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setA(int a) {
        this.a = a;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isActive() {
        return x != Integer.MIN_VALUE && y != Integer.MIN_VALUE;
    }

    public boolean isReserved() {
        return x != Integer.MIN_VALUE && y == Integer.MIN_VALUE;
    }

    public boolean isEmpty() {
        return x == Integer.MIN_VALUE;
    }

    public boolean isFull() {
        return x != Integer.MIN_VALUE && y != Integer.MIN_VALUE && a == Constants.numDivisions;
    }

    public boolean isInside(int x, int y) {
        return x >= this.x && x < this.x + this.a && y >= this.y && y < this.y + this.a;
    }

    public boolean isInside(Square square) {
        return isInside(square.x, square.y);
    }

    public void reset() {
        x = Integer.MIN_VALUE;
        y = Integer.MIN_VALUE;
        a = 0;
        index = Integer.MIN_VALUE;
    }

    public void set(int x, int y, int a) {
        this.x = x;
        this.y = y;
        this.a = a;

        adjust();
    }

    public void set(int index, int a) {
        int x = index % Constants.numDivisions;
        int y = index / Constants.numDivisions;

        set(x, y, a);
    }

    public void set(Square square) {
        set(square.x, square.y, square.a);
    }

    public void add(int x, int y) {
        int dx = 1;
        int dy = 1;

        if (this.x == Integer.MIN_VALUE) {
            this.x = x;
        } else if (x != this.x) {
            this.x = min(this.x, x);
            dx = abs(this.x - x) + 1;
        }

        if (this.y == Integer.MIN_VALUE) {
            this.y = y;
        } else if (y != this.y) {
            this.y = min(this.y, y);
            dy = abs(this.y - y) + 1;
        }

        this.a = max(dx, dy);

        adjust();
    }

    public void add(int index) {
        add(index % Constants.numDivisions, index / Constants.numDivisions);
    }
}
