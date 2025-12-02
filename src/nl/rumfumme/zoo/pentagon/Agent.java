package nl.rumfumme.zoo.pentagon;

import nl.rumfumme.pt.ProcessingApp;
import nl.rumfumme.verlet.Point;
import nl.rumfumme.zoo.ZooApp;
import processing.core.PVector;
import static nl.rumfumme.zoo.pentagon.AgentImageLoader.*;

public class Agent extends ZooApp {
    private AgentImageLoader imageLoader;
    private Point pnt_glass;
    private FoamPoint pnt_foam[];

    class FoamPoint extends Point {
        public float rot;
        public float rot_inc;
        
        public FoamPoint(float x, float y) {
            super(x, y);
        }

        public FoamPoint(float x, float y, float vx, float vy) {
            super(x, y, vx, vy);
        }
}

    @Override
    public void load() {
        imageLoader = new AgentImageLoader(this);
    }

    @Override
    public void init() {
        super.init();

        pnt_glass = new Point(imageLoader.x_offs[IDX_GLASS_WITHOUT_FOAM], imageLoader.y_offs[IDX_GLASS_WITHOUT_FOAM]);
        pnt_glass.gravity = new PVector(0.0f, 2.5f);
        pnt_glass.friction = 0.0f;
        
        pnt_foam = new FoamPoint[8];
        
        for(int i = 0; i < 8; i++) {
            pnt_foam[i] = new FoamPoint(imageLoader.x_offs[IDX_FOAM_8 + i], imageLoader.y_offs[IDX_FOAM_8 + i], -5 * (i - 4), -50);
            pnt_foam[i].gravity = new PVector(0.0f, 6.0f);
            pnt_foam[i].rot = 0.0f;
            pnt_foam[i].rot_inc = random(-PI / 20.0f, PI / 20.0f);;
        }
    }

    @Override
    public void terminate() {
        // TODO Auto-generated method stub

    }

    @Override
    public void animate() {

        fitToScreen(3968, 2976);
        background(255);
        
        image(imageLoader.img_full_withouth_hand, imageLoader.x_offs[IDX_FULL_WITHOUTH_HAND], imageLoader.y_offs[IDX_FULL_WITHOUTH_HAND]);
        image(imageLoader.img_hand_without_glass, imageLoader.x_offs[IDX_HAND_WITHOUT_GLASS], imageLoader.y_offs[IDX_HAND_WITHOUT_GLASS]);
        
        image(imageLoader.img_glass_without_foam, pnt_glass.pos.x, pnt_glass.pos.y);
        image(imageLoader.img_fingers, imageLoader.x_offs[IDX_FINGERS], imageLoader.y_offs[IDX_FINGERS]);
        
        for(int i = 0; i < 8; i++) {
            push();
            translate(pnt_foam[i].pos.x - imageLoader.images[IDX_FOAM_8 + i].width / 2.0f, pnt_foam[i].pos.y - imageLoader.images[IDX_FOAM_8 + i].height / 2.0f);
            rotate(pnt_foam[i].rot);
            image(imageLoader.images[IDX_FOAM_8 + i], imageLoader.images[IDX_FOAM_8 + i].width / 2.0f, imageLoader.images[IDX_FOAM_8 + i].height / 2.0f);
            pop();
        }

        if(millis() - start_time < 4000) {
            return;
        }

        if(pnt_glass.pos.y < 2976) {
            pnt_glass.move();
            
            if(pnt_glass.pos.y < imageLoader.y_offs[IDX_FINGERS]) {
                pnt_glass.friction += 0.01f; 
            }
        } else {
            for(int i = 0; i < 8; i++) {
                if(pnt_foam[i].pos.y < 4000) {
                    pnt_foam[i].move();
                    pnt_foam[i].rot += pnt_foam[i].rot_inc;
                }
            }
        }
    }

}
