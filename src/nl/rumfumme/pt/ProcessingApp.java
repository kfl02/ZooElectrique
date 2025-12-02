package nl.rumfumme.pt;

import processing.core.PApplet;
import processing.core.PVector;

public abstract class ProcessingApp extends PApplet {
    protected boolean animate = true;
    protected ImageLoader img;

    @Override
    public void settings() {
        // do settings in ProcessingAppContainer
    }

    @Override
    public void setup() {
        // do settings in ProcessingAppContainer
    }

    public final void setup(PApplet parent) {
        this.g = parent.g;
        this.sketchPath(parent.sketchPath());
        this.width = parent.width;
        this.height = parent.height;
    }

    public final void fitToScreen(int w, int h) {
        float ratioWidth = (float) width / (float) w;
        float ratioHeight = (float) height / (float) h;

        if (ratioWidth <= ratioHeight) {
            scale(ratioWidth);

            float t = ((height / ratioWidth) - h) / 2.0f;

            translate(0.0f, t);
        } else {
            scale(ratioHeight);

            float t = ((width / ratioHeight) - w) / 2.0f;

            translate(t, 0.0f);
        }
    }

    // load images etc.
    public abstract void load();

    // initialize for consecutive running
    public abstract void init();

    // clean up after running
    public abstract void terminate();

    // return true as long as you're fading in
    public boolean fadeIn() {
        return false;
    }

    @Override
    public void draw() {
        if (animate) {
            animate();
        }
    }

    public abstract boolean finished();

    // return true as long as you have something to draw
    public abstract void animate();

    // return true as long as you're fading out
    public boolean fadeOut() {
        return false;
    }

    @Override
    public void keyPressed() {
        switch (key) {
            case 'm':
                animate = !animate;
                break;

            case ' ':
                if (!animate) {
                    animate();
                }
                break;
        }
    }
}
