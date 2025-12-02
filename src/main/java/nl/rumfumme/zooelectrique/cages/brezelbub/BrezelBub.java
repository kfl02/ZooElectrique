package nl.rumfumme.zooelectrique.cages.brezelbub;

import org.je3gl.renderer.Camera2DRenderer;
import org.je3gl.renderer.effect.GLXEffect;

public class BrezelBub extends Camera2DRenderer {
    protected BrezelBub(GLRendererType rendererType) {
        super(rendererType);
    }

    public BrezelBub(GLRendererType rendererType, GLXEffect... effects) {
        super(rendererType, effects);
    }

    public BrezelBub(GLRendererType rendererType, float cameraDistanceFrustum, float followInterpolationAmount) {
        super(rendererType, cameraDistanceFrustum, followInterpolationAmount);
    }
}
