package nl.rumfumme.zoo2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;

public abstract class Cage implements ICage {
    protected Zoo zoo;
    protected OrthographicCamera camera;
    protected FitViewport viewport;
    protected float secondsToExpire;

    public Cage(Zoo zoo) {
        this.zoo = zoo;
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera = (OrthographicCamera) viewport.getCamera();
    }
    
    public void setWorldSize(float width, float height) {
        viewport.setWorldSize(width, height);
        camera.setToOrtho(true, width, height);
    }

    public void setWorldSize(boolean yDown, float width, float height) {
        viewport.setWorldSize(width, height);
        camera.setToOrtho(yDown, width, height);
    }

    @Override
    public boolean keyDown(int keycode) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub

    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub

    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub

    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub

    }
}
