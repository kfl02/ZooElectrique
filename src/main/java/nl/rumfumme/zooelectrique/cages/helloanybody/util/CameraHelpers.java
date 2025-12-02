package nl.rumfumme.zooelectrique.cages.helloanybody.util;

import com.jme3.bounding.BoundingBox;
import com.jme3.math.Transform;
import com.jme3.renderer.Camera;

public class CameraHelpers {
    /*--- Public static methods ---------------------------------------------*/

    public static void setCameraFrustum(Camera camera, Frustum frustum) {
        camera.setFrustum(frustum.near, frustum.far, frustum.left, frustum.right, frustum.top, frustum.bottom);
        camera.update();
    }

    public static void setCameraBoundingBox(Camera camera, BoundingBox boundingBox) {
        setCameraFrustum(camera, new Frustum(boundingBox));
    }

    public static BoundingBox scaleBoundingBoxToCamera(BoundingBox boundingBox, Camera camera, float aspectRatio) {
        float cameraWidth = camera.getWidth();
        float cameraHeight = camera.getHeight();
        float cameraAspectRatio = cameraWidth / cameraHeight;

        float scaleX = 1.0f;
        float scaleY = 1.0f;

        if(cameraAspectRatio < aspectRatio) {
            scaleY = aspectRatio / cameraAspectRatio;
        } else {
            scaleX = cameraAspectRatio / aspectRatio;
        }

        return (BoundingBox) boundingBox.transform(new Transform().setScale(scaleX, scaleY, 0.0f));
    }

}
