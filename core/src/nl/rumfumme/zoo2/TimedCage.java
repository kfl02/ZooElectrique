package nl.rumfumme.zoo2;

public class TimedCage implements ICage {
    protected Cage cage;
    protected CageTimer timer;

    public TimedCage(Cage cage, CageTimer timer) {
        this.cage = cage;
        this.timer = timer;
    }

    @Override
    public boolean keyDown(int keycode) {
        boolean triggered = cage.keyDown(keycode);
        
        if(triggered) {
            timer.restart();
        }
        
        return triggered;
    }

    @Override
    public boolean keyUp(int keycode) {
        boolean triggered = cage.keyUp(keycode);
        
        if(triggered) {
            timer.restart();
        }
        
        return triggered;
    }

    @Override
    public boolean keyTyped(char character) {
        boolean triggered = cage.keyTyped(character);
        
        if(triggered) {
            timer.restart();
        }
        
        return triggered;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        boolean triggered = cage.touchDown(screenX, screenY, pointer, button);
        
        if(triggered) {
            timer.restart();
        }
        
        return triggered;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        boolean triggered = cage.touchUp(screenX, screenY, pointer, button);
        
        if(triggered) {
            timer.restart();
        }
        
        return triggered;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        boolean triggered = cage.touchCancelled(screenX, screenY, pointer, button);
        
        if(triggered) {
            timer.restart();
        }
        
        return triggered;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        boolean triggered = cage.touchDragged(screenX, screenY, pointer);
        
        if(triggered) {
            timer.restart();
        }
        
        return triggered;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        boolean triggered = cage.mouseMoved(screenX, screenY);
        
        if(triggered) {
            timer.restart();
        }
        
        return triggered;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        boolean triggered = cage.scrolled(amountX, amountY);
        
        if(triggered) {
            timer.restart();
        }
        
        return triggered;
    }

    @Override
    public void show() {
        timer.restart();
        cage.show();
    }

    @Override
    public void render(float delta) {
        cage.render(delta);
    }

    @Override
    public void resize(int width, int height) {
        cage.resize(width, height);
    }

    @Override
    public void pause() {
        cage.pause();
    }

    @Override
    public void resume() {
        cage.resume();
    }

    @Override
    public void hide() {
        timer.stop();
        cage.hide();
    }

    @Override
    public void dispose() {
        cage.dispose();
    }
}
