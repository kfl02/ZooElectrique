package nl.rumfumme.zoo2.cages.test;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.ScreenUtils;

import nl.rumfumme.zoo2.Cage;
import nl.rumfumme.zoo2.Zoo;

public class Test extends Cage {
    private SpriteBatch batch;

    public Test(Zoo zoo) {
        super(zoo);
    }

    @Override
    public void render(float delta) {
        camera.update();
        ScreenUtils.clear(1.0f, 1.0f, 1.0f, 1.0f);
        
        Matrix4 cam = camera.combined;
       
        batch.setProjectionMatrix(cam);
        batch.begin();
        
        batch.end();
    }
}
