package nl.rumfumme.zooelectrique.cages.helloanybody;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.bounding.BoundingBox;
import com.jme3.material.Material;
import com.jme3.material.Materials;
import com.jme3.material.RenderState;
import com.jme3.math.*;
import com.jme3.renderer.Camera;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.instancing.InstancedNode;
import com.jme3.scene.shape.CenterQuad;
import com.jme3.texture.Image;
import com.jme3.texture.Texture2D;
import com.jme3.texture.image.ColorSpace;
import com.jme3.util.BufferUtils;
import nl.rumfumme.zooelectrique.cages.helloanybody.util.Frustum;
import nl.rumfumme.zooelectrique.cages.helloanybody.control.ViewControl;
import nl.rumfumme.zooelectrique.cages.helloanybody.control.TileControl;
import nl.rumfumme.zooelectrique.cages.helloanybody.util.Square;

import static com.jme3.math.FastMath.*;
import static java.lang.Math.ceil;
import static java.lang.Math.max;
import static java.lang.Math.random;
import static nl.rumfumme.zooelectrique.cages.helloanybody.Constants.*;
import static nl.rumfumme.zooelectrique.cages.helloanybody.util.TileHelpers.getBoundingBoxForTileSquare;
import static nl.rumfumme.zooelectrique.cages.helloanybody.util.TileHelpers.getTilePosition;
import static nl.rumfumme.zooelectrique.cages.helloanybody.util.CameraHelpers.scaleBoundingBoxToCamera;

public class HelloAnybodyApp extends BaseAppState {
    /*--- Private variables -------------------------------------------------*/

    private final TileControl[] tiles = new TileControl[numVideoElements];
    private ViewControl viewControl;

    private boolean tileStatesCached;
    private int activeTileCount;
    private int activeOrReservedTileCount;
    private final boolean[] isTileActive = new boolean[numVideoElements];
    private final Square activeOrReservedTileSquare = new Square();

    private float anxiety = 0.0f;
    private float anxietyStepDirection = 1.0f;
    private int maxAnxietyTiles = 1;
    private final Square anxietyBoundingSquare = new Square();

    float updateDelayCounter = updateDelay;

    /*--- Constructors ------------------------------------------------------*/

    public HelloAnybodyApp() {
        super();
    }

    /*--- Private methods ---------------------------------------------------*/

    private Frustum calculateFrustumForTileSquare(int index, int extent) {
        Camera guiCamera = getApplication().getGuiViewPort().getCamera();
        BoundingBox boundingBox = getBoundingBoxForTileSquare(index, extent);
        boundingBox = scaleBoundingBoxToCamera(boundingBox, guiCamera, aspectRatio);

        return new Frustum(boundingBox);
    }

    private Frustum calculateFrustumForTileSquare(Square square) {
        return calculateFrustumForTileSquare(square.getIndex(), square.getA());
    }

    private void updateTileCounters() {
        int activeTileCount = 0;
        int activeOrReservedTileCount = 0;

        activeOrReservedTileSquare.reset();

        for(int i = 0; i < numVideoElements; i++) {
            if(tiles[i].isActive()) {
                if(!isTileActive[i]) {
                    isTileActive[i] = true;
                }

                activeTileCount++;
                activeOrReservedTileCount++;
                activeOrReservedTileSquare.add(i);
            } else {
                if(isTileActive[i]) {
                    isTileActive[i] = false;
                }
            }

            if(tiles[i].isReserved()) {
                activeOrReservedTileCount++;
                activeOrReservedTileSquare.add(i);
            }
        }

        if(activeOrReservedTileCount == 0) {
            // Nothing active or reserved, so choose a random tile to begin with.
            activeOrReservedTileSquare.set((int)(random() * numVideoElements), 1);
        }

        this.activeTileCount = activeTileCount;
        this.activeOrReservedTileCount = activeOrReservedTileCount;

        tileStatesCached = true;
    }

    private int getActiveTileCount() {
        if(!tileStatesCached) {
            updateTileCounters();
        }

        return activeTileCount;
    }

    private int getActiveOrReservedTileCount() {
        if(!tileStatesCached) {
            updateTileCounters();
        }

        return activeOrReservedTileCount;
    }

    private Square getActiveOrReservedTileSquare() {
        if(!tileStatesCached) {
            updateTileCounters();
        }

        return activeOrReservedTileSquare;
    }

    /*
     * Calculate the bounding square which contains all active tiles and where new tiles can be placed.
     */
    private void updateAnxietyBoundingSquare() {
        /*
         * Determine the maximum number of active tiles according to anxiety level.
         * Even at an anxiety level of 0.0, we will have at least one possible active tile.
         * The number of possible active tiles is a quadratic function of anxiety level.
         */
        maxAnxietyTiles = (int) max(ceil(anxiety * anxiety * numDivisions * numDivisions), 1);
        int a = (int) ceil(sqrt(maxAnxietyTiles));

        anxietyBoundingSquare.set(getActiveOrReservedTileSquare());

        if(getActiveOrReservedTileCount() >= maxAnxietyTiles || anxietyBoundingSquare.getA() >= a) {
            // Already has enough active or reserved tiles, so just reuse those.
            return;
        }

        // Grow the bounding square iteratively, because I couldn't think of anything better.
        while(anxietyBoundingSquare.getA() != a) {
            int x1 = anxietyBoundingSquare.getX();
            int y1 = anxietyBoundingSquare.getY();
            int x2 = x1 + anxietyBoundingSquare.getA() - 1;
            int y2 = y1 + anxietyBoundingSquare.getA() - 1;
            int dx1 = x1;
            int dy1 = y1;
            int dx2 = (numDivisions - 1) - x2;
            int dy2 = (numDivisions - 1) - y2;

            if((dx1 == 0 && dx2 == 0) || (dy1 == 0 && dy2 == 0)) {
                // Cannot happen. (But it did.)
                throw new RuntimeException("(dx1 == 0 && dx2 == 0) || (dy1 == 0 && dy2 == 0)");
            }

            // If there's no space in the positive or negative X direction, there must be space in the other.
            // If there's space in both directions, then choose the direction to grow randomly.
            if(dx1 == 0) {
                x2++;
            } else if(dx2 == 0) {
                x1--;
            } else {
                if(random() < 0.5) {
                    x2++;
                } else {
                    x1--;
                }
            }

            // The same for the Y axis.
            if(dy1 == 0) {
                y2++;
            } else if(dy2 == 0) {
                y1--;
            } else {
                if(random() < 0.5) {
                    y2++;
                } else {
                    y1--;
                }
            }

            anxietyBoundingSquare.set(x1, y1, max(x2 - x1 + 1, y2 -y1 + 1));
        }
    }

    private void spawnAnxietyVideos() {
        // There are already more than enough tiles active.
        if(maxAnxietyTiles <= getActiveTileCount()) {
            return;
        }

        // Anxiety of 0.0 should not mean that we don't ever get a movie, 1.0 should not always give one.
        float adjustedAnxiety = interpolateLinear(anxiety, minAnxiety, maxAnxiety);
        int maxInnerIndex = anxietyBoundingSquare.getA() * anxietyBoundingSquare.getA();
        // Number of tries is the maximum number of tiles minus the number of active tiles.
        int maxTries = maxAnxietyTiles - getActiveTileCount();

        // Have the maximum number of tries.
        for(int i = 0; i < maxTries; i++) {
            // Choose a random tile inside the anxiety square.
            int tile = (int)(random() * maxInnerIndex);
            int innerIndex = tile;
            int outerIndex = Square.getOuterIndex(anxietyBoundingSquare, innerIndex);

            // If the random tile is already active, loop to the next inactive tile.
            for(int j = 0; j < maxInnerIndex; j++) {
                innerIndex = (tile + j) % maxInnerIndex;
                outerIndex = Square.getOuterIndex(anxietyBoundingSquare, innerIndex);

                if(!isTileActive[outerIndex]) {
                    break;
                }
            }

            // Activate the tile, weighted by anxiety.
            float chance = (float) random();

            if(chance < adjustedAnxiety) {
                tiles[outerIndex].activate();
                updateTileCounters();
            }
        }
    }

    /*--- Public methods ----------------------------------------------------*/

    public void setAnxiety(float anxiety) {
        this.anxiety = anxiety;
    }

    public void reshape(int w, int h) {

    }

    /*--- Overridden methods ------------------------------------------------*/

    @Override
    protected void initialize(Application app) {
        SimpleApplication simpleApplication = (SimpleApplication) app;

        simpleApplication.getFlyByCamera().setEnabled(false);
        simpleApplication.getFlyByCamera().unregisterInput();
        simpleApplication.getCamera().setParallelProjection(true);

        Node rootNode = simpleApplication.getRootNode();
        Node groupNode = new InstancedNode("VideoGroup");

        rootNode.attachChild(groupNode);

        Geometry containerQuad = new Geometry("Background", new CenterQuad(totalWidth, totalHeight));
        Material containerMaterial = new Material(app.getAssetManager(), Materials.UNSHADED);

        containerMaterial.setColor("Color", ColorRGBA.White);
        containerQuad.setMaterial(containerMaterial);
        groupNode.attachChild(containerQuad);

        for(int i = 0; i < numVideoElements; i++) {
            Texture2D texture = new Texture2D(
                    new Image(Image.Format.BGRA8,
                            videoWidth, videoHeight,
                            BufferUtils.createByteBuffer(videoSize * 4),
                            ColorSpace.Linear
                    )
            );
            Geometry videoQuad = new Geometry("Video " + i, new CenterQuad(videoWidth, videoHeight));
            Material material = new Material(app.getAssetManager(), Materials.UNSHADED);
            TileControl control = new TileControl();
            tiles[i] = control;

            videoQuad.setCullHint(Spatial.CullHint.Never);

            material.setTexture("ColorMap", texture);
            material.getAdditionalRenderState().setFaceCullMode(RenderState.FaceCullMode.Front);
            videoQuad.setMaterial(material);
            videoQuad.rotate(0.0f, FastMath.PI, FastMath.PI);   // mind the roration when positioning!!!

            Vector2f pos = getTilePosition(i);
            Vector3f translation = new Vector3f(pos.x, pos.y, 0.0f);

            videoQuad.setLocalTranslation(translation);
            videoQuad.addControl(control);
            groupNode.attachChild(videoQuad);
        }

        Frustum frustum = calculateFrustumForTileSquare(0, 1);
        viewControl = new ViewControl(frustum);

        groupNode.addControl(viewControl);
    }

    @Override
    protected void cleanup(Application app) {

    }

    @Override
    public void update(float tpf) {
        updateDelayCounter -= tpf;

        if(updateDelayCounter > 0.0f)
        {
            return;
        }

        updateDelayCounter = updateDelay;
        tileStatesCached = false;

        anxiety += anxietyStep * anxietyStepDirection;

        if(anxiety > 1.0f) {
            anxietyStepDirection = -1.0f;
            anxiety = 1.0f;
        } else if(anxiety < 0.0f) {
            anxietyStepDirection = 1.0f;
            anxiety = 0.0f;

//            System.exit(0);
        }

        updateTileCounters();
        updateAnxietyBoundingSquare();
        spawnAnxietyVideos();

        if(activeTileCount > 0) {
            Frustum frustum = calculateFrustumForTileSquare(getActiveOrReservedTileSquare());

            if(!viewControl.getTargetFrustum().equals(frustum)) {
                viewControl.setTargetFrustum(frustum, 2.0f + anxiety);
            }
        }

    }

    @Override
    protected void onEnable() {

    }

    @Override
    protected void onDisable() {

    }
}
