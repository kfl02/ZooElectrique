package nl.rumfumme.zoo2.cages.threepiece;

import static nl.rumfumme.zoo2.cages.threepiece.ThreePieceAtlas.*;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import nl.rumfumme.zoo2.Cage;
import nl.rumfumme.zoo2.Zoo;

public class ThreePiece extends Cage {
    private ThreePieceAtlas atlas;
    private SpriteBatch batch;

    public ThreePiece(Zoo zoo) {
        super(zoo);

        batch = zoo.batch;
        atlas = new ThreePieceAtlas();

        setWorldSize(false, CANVAS_WIDTH, CANVAS_HEIGHT);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.961f, 0.961f, 0.961f, 1.0f);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        
        for(int i = 0; i < ThreePieceAtlas.NUM_IMAGES; i++) {
            batch.draw(atlas.images[i], x_offs[i], y_offs[i]);
        }
        
        batch.end();
    }

}
