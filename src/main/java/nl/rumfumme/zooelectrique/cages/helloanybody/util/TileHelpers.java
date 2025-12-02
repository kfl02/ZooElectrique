package nl.rumfumme.zooelectrique.cages.helloanybody.util;

import com.jme3.bounding.BoundingBox;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import nl.rumfumme.zooelectrique.cages.helloanybody.Constants;

import static nl.rumfumme.zooelectrique.cages.helloanybody.Constants.*;

public class TileHelpers {
    /*--- Public static methods ---------------------------------------------*/

    public static Vector2f getTilePosition(int index) {
        // Calculate the center position of a tile.

        int x = index % numDivisions;
        int y = index / numDivisions;

        return new Vector2f((float) (x * videoWithBorderWidth) - totalWidth / 2.0f,
                            (totalHeight - (float) (y * videoWithBorderHeight)) - totalHeight / 2.0f)
                    .addLocal(videoWidth / 2.0f, -videoHeight / 2.0f);
    }

    public static BoundingBox getBoundingBoxForTiles(int x, int y, int extentX, int extentY) {
        int xAdjusted = Math.min(x, numDivisions - extentX);
        int yAdjusted = Math.min(y, numDivisions - extentY);
        int index = yAdjusted * numDivisions + xAdjusted;
        Vector2f size = new Vector2f(videoWidth + (extentX - 1) * videoWithBorderWidth,
                                     videoHeight + (extentY - 1) * videoWithBorderHeight);
        Vector2f center = getTilePosition(index)
                            .addLocal(-videoWidth / 2.0f, videoHeight / 2.0f)
                            .addLocal(size.x / 2.0f, -size.y / 2.0f);

        // Attention! BoundingBox(Vector3f, Vector3f) is different from BoundingBox(Vector3f, float, float, float).
        return new BoundingBox(new Vector3f(center.x, center.y, 0.0f),
                              size.x / 2.0f, size.y / 2.0f, 0.0f);
    }

    public static BoundingBox getBoundingBoxForTileSquare(int index, int extent) {
        return getBoundingBoxForTiles(index % numDivisions, index / numDivisions, extent, extent);
    }

    public static BoundingBox getBoundingBoxForTileSquare(int x, int y, int extent) {
        return getBoundingBoxForTileSquare(y * numDivisions + x, extent);
    }
}
