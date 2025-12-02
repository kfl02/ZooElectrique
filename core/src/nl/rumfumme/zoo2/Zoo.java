package nl.rumfumme.zoo2;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Cursor.SystemCursor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Timer.Task;

import nl.rumfumme.zoo2.cages.bee.Bee;
import nl.rumfumme.zoo2.cages.birb.Birb;
import nl.rumfumme.zoo2.cages.brezelbub.Brezelbub;
import nl.rumfumme.zoo2.cages.oneeye.OneEye;
import nl.rumfumme.zoo2.cages.peacock.Peacock;
import nl.rumfumme.zoo2.cages.red.Red;
import nl.rumfumme.zoo2.cages.test.Test;
import nl.rumfumme.zoo2.cages.threepiece.ThreePiece;
import nl.rumfumme.zoo2.cages.tobor.Tobor;
import nl.rumfumme.zoo2.cages.xray.XRay;
import nl.rumfumme.zoo2.cages.skeleton.Skeleton;

public class Zoo extends Game {
    private InputMultiplexer multiplexer = new InputMultiplexer();
    private List<ICage> cages;
    private int currentCage;
    private CageTimer timer;

    public SpriteBatch batch;

    @Override
    public void create() {
        Gdx.graphics.setSystemCursor(SystemCursor.None);

        batch = new SpriteBatch();
        timer = new CageTimer(new Task() {
            
            @Override
            public void run() {
                nextCage();
            }
        }, 90.0f);
        cages = new ArrayList<ICage>();
        
        // add screens here
        cages.add(new ThreePiece(this));
        cages.add(new Tobor(this));
//        cages.add(new Test(this));
//        cages.add(new Red(this));
        cages.add(new TimedCage(new XRay(this), timer));
        cages.add(new TimedCage(new Skeleton(this), timer));
        cages.add(new TimedCage(new Birb(this), timer));
        cages.add(new TimedCage(new Peacock(this), timer));
        cages.add(new TimedCage(new OneEye(this), timer));
        cages.add(new TimedCage(new Bee(this), timer));
        cages.add(new TimedCage(new Red(this), timer));
        cages.add(new TimedCage(new Brezelbub(this), timer));
        
        multiplexer.addProcessor(new InputAdapter() {
            // main InputProcessor
            // intercept ',' and '.' and escape to move to previous and next cage and exit the zoo
            @Override
            public boolean keyDown(int key) {
                switch(key) {
                    case Keys.COMMA:
                        previousCage();
                        return true;
                        
                    case Keys.PERIOD:
                        nextCage();
                        return true;
                        
                    case Keys.ESCAPE:
                        Gdx.app.exit();
                        return true;
                }
                
                return false;
            }
        });
        
        Gdx.input.setInputProcessor(multiplexer);
        
        if(!cages.isEmpty()) {
            currentCage = 0;
            setScreen(cages.get(0));
            multiplexer.addProcessor((ICage)screen);
        }
    }
    
    private void nextCage() {
        if(cages.size() != 0) {
            multiplexer.removeProcessor((ICage)screen);

            currentCage++;
            
            if(currentCage == cages.size()) {
                currentCage = 0;
            }
            
            setScreen(cages.get(currentCage));
            multiplexer.addProcessor((ICage)screen);
        }
    }
    
    private void previousCage() {
        if(cages.size() != 0) {
            multiplexer.removeProcessor((ICage)screen);

            currentCage--;
            
            if(currentCage == -1) {
                currentCage = cages.size() - 1;
            }
            
            setScreen(cages.get(currentCage));
            multiplexer.addProcessor((ICage)screen);
        }
    }

    @Override
    public void render() {
        super.render();
    }
}
