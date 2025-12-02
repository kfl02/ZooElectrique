package nl.rumfumme.zoo.title;

import nl.rumfumme.pt.ProcessingApp;
import processing.core.PImage;

public class TitleApp extends ProcessingApp {
    PImage img_title;
    PImage img_text;
    PImage img_instr;
    
    int start_time;

    @Override
    public void load() {
        img_title = loadImage("nl/rumfumme/zoo/title/img/zoo_title.png");
        img_text = loadImage("nl/rumfumme/zoo/title/img/zoo_text.png");
        img_instr = loadImage("nl/rumfumme/zoo/title/img/zoo_instr.png");
    }

    @Override
    public void init() {
        start_time = millis();

    }

    @Override
    public void terminate() {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean finished() {
        if(millis() - start_time > (15 + 20 + 15) * 1000) {
            return true;
        }
        return false;
    }

    @Override
    public void animate() {
        fitToScreen(img_title.width, img_title.height);
        background(0);

        if(millis() - start_time > (15 + 20) * 1000) {
            image(img_instr, 0, 0);
        } else if(millis() - start_time > 15 * 1000) {
            image(img_text, 0, 0);
        } else {
            image(img_title, 0, 0);
        }
    }

}
