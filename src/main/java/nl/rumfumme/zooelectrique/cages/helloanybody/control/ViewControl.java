package nl.rumfumme.zooelectrique.cages.helloanybody.control;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;
import nl.rumfumme.zooelectrique.cages.helloanybody.util.CameraHelpers;
import nl.rumfumme.zooelectrique.cages.helloanybody.util.Frustum;

public class ViewControl extends AbstractControl {
    /*--- Private variables -------------------------------------------------*/

    private final Frustum originFrustum = new Frustum();
    private final Frustum targetFrustum = new Frustum();
    private final Frustum currentFrustum = new Frustum();

    private boolean isMorphing;
    private float targetTime;
    private float currentTime;

    /*--- Constructors ------------------------------------------------------*/

    public ViewControl(Frustum frustum) {
        originFrustum.set(frustum);
        targetFrustum.set(frustum);
        currentFrustum.set(frustum);

        targetTime = 1.0f;
        currentTime = 1.0f;
        isMorphing = false;
    }

    /*--- Getter/Setter -----------------------------------------------------*/

    public Frustum getTargetFrustum() {
        return targetFrustum;
    }

    public void setTargetFrustum(Frustum frustum, float duration) {
        if(frustum.equals(targetFrustum)) {
            return;
        }

        originFrustum.set(currentFrustum);
        targetFrustum.set(frustum);

        targetTime = duration;
        currentTime = 0.0f;
        isMorphing = true;
    }

    public void setTargetFrustum(Frustum frustum) {
        setTargetFrustum(frustum, 1.0f);
    }

    public void setFrustum(Frustum frustum) {
        currentFrustum.set(frustum);
        originFrustum.set(frustum);
        targetFrustum.set(frustum);

        currentTime = targetTime = 1.0f;
    }

    /*--- Overridden methods ------------------------------------------------*/

    @Override
    protected void controlUpdate(float tpf) {
        // Interpolate between origin and target frustum until target time is reached.

        if(!isMorphing) {
            return;
        }

        float scale = (float) Math.sqrt(currentTime / targetTime);

        currentFrustum.interpolateLinear(originFrustum, targetFrustum, scale);

        currentTime += tpf;

        if(currentTime > targetTime) {
            currentTime = targetTime;

            originFrustum.set(targetFrustum);
            currentFrustum.set(targetFrustum);

            isMorphing = false;
        }
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        CameraHelpers.setCameraFrustum(vp.getCamera(), currentFrustum);
    }
}
