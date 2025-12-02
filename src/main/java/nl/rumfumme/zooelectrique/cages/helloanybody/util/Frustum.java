package nl.rumfumme.zooelectrique.cages.helloanybody.util;

import com.jme3.bounding.BoundingBox;
import com.jme3.math.FastMath;

import java.util.Objects;

public class Frustum {
    /*--- Private constants -------------------------------------------------*/

    static private final float defaultNear = 1.0f;
    static private final float defaultFar = 2.0f;
    static private final float defaultLeft = -0.5f;
    static private final float defaultRight = 0.5f;
    static private final float defaultTop = 0.5f;
    static private final float defaultBottom = -0.5f;

    /*--- Public variables --------------------------------------------------*/

    public float near = defaultNear;
    public float far = defaultFar;
    public float left = defaultLeft;
    public float right = defaultRight;
    public float top = defaultTop;
    public float bottom = defaultBottom;

    /*--- Constructors ------------------------------------------------------*/

    public Frustum() {
        this(defaultNear, defaultFar, defaultLeft, defaultRight, defaultTop, defaultBottom);
    }

    public Frustum(float near, float far, float left, float right, float top, float bottom) {
        this.near = near;
        this.far = far;
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bottom;
    }

    public Frustum(float left, float right, float top, float bottom) {
        this(-1000.0f, 1000.0f, left, right, top, bottom);
    }

    public Frustum(Frustum frustum) {
        this(frustum.near, frustum.far, frustum.left, frustum.right, frustum.top, frustum.bottom);
    }

    public Frustum(BoundingBox boundingBox) {
        this(boundingBox.getCenter().x - boundingBox.getXExtent(),
             boundingBox.getCenter().x + boundingBox.getXExtent(),
             boundingBox.getCenter().y + boundingBox.getYExtent(),
             boundingBox.getCenter().y - boundingBox.getYExtent());
    }

    /*--- Public methods ----------------------------------------------------*/

    public void set(float near, float far, float left, float right, float top, float bottom) {
        this.near = near;
        this.far = far;
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bottom;
    }

    public void set(float left, float right, float top, float bottom) {
        set(-1000.0f, 1000.0f, left, right, top, bottom);
    }

    public void set(Frustum frustum) {
        this.set(frustum.near, frustum.far, frustum.left, frustum.right, frustum.top, frustum.bottom);
    }

    public void set(BoundingBox boundingBox) {
        this.set(boundingBox.getCenter().x - boundingBox.getXExtent(),
                 boundingBox.getCenter().x + boundingBox.getXExtent(),
                 boundingBox.getCenter().y + boundingBox.getYExtent(),
                 boundingBox.getCenter().y - boundingBox.getYExtent());
    }

    public void interpolateLinear(Frustum origin, Frustum target, float scale) {
        this.near = FastMath.interpolateLinear(scale, origin.near, target.near);
        this.far = FastMath.interpolateLinear(scale, origin.far, target.far);
        this.left = FastMath.interpolateLinear(scale, origin.left, target.left);
        this.right = FastMath.interpolateLinear(scale, origin.right, target.right);
        this.top = FastMath.interpolateLinear(scale, origin.top, target.top);
        this.bottom = FastMath.interpolateLinear(scale, origin.bottom, target.bottom);
    }

    /*--- Overridden methods ------------------------------------------------*/

    @Override
    public String toString() {
        return "Frustum(near=" + near + ", far=" + far + ", left=" + left + ", right=" + right + ", top=" + top + ", bottom=" + bottom + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Frustum frustum)) {
            return false;
        }

        return Float.compare(near, frustum.near) == 0
                && Float.compare(far, frustum.far) == 0
                && Float.compare(left, frustum.left) == 0
                && Float.compare(right, frustum.right) == 0
                && Float.compare(top, frustum.top) == 0
                && Float.compare(bottom, frustum.bottom) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(near, far, left, right, top, bottom);
    }
}
