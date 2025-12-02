package nl.rumfumme.zoo.pred;

import nl.rumfumme.pt.ImageLoader;

import processing.core.PImage;
import processing.core.PApplet;

public class PredImageLoader extends ImageLoader {
    public static final int IDX_DENDRITE_BRUSH_1 = 0;   // 0
    public static final int IDX_DENDRITE_BRUSH_2 = IDX_DENDRITE_BRUSH_1 + 1;    // 1
    public static final int IDX_DENDRITE_BRUSH_3 = IDX_DENDRITE_BRUSH_2 + 1;    // 2
    public static final int IDX_HEAD = IDX_DENDRITE_BRUSH_3 + 1;    // 3
    public static final int IDX_FULL_WITHOUT_TAIL_AND_HEAD = IDX_HEAD + 1;  // 4
    public static final int IDX_TAIL_6 = IDX_FULL_WITHOUT_TAIL_AND_HEAD + 1;    // 5
    public static final int IDX_TAIL_5 = IDX_TAIL_6 + 1;    // 6
    public static final int IDX_TAIL_4 = IDX_TAIL_5 + 1;    // 7
    public static final int IDX_TAIL_3 = IDX_TAIL_4 + 1;    // 8
    public static final int IDX_TAIL_2 = IDX_TAIL_3 + 1;    // 9
    public static final int IDX_TAIL_1 = IDX_TAIL_2 + 1;    // 10
    public PImage img_dendrite_brush_1;
    public PImage img_dendrite_brush_2;
    public PImage img_dendrite_brush_3;
    public PImage img_head;
    public PImage img_full_without_tail_and_head;
    public PImage img_tail_6;
    public PImage img_tail_5;
    public PImage img_tail_4;
    public PImage img_tail_3;
    public PImage img_tail_2;
    public PImage img_tail_1;

    public PredImageLoader(PApplet app) {
        super(app, 11, 0);
        image_names = new String[] {
            "nl/rumfumme/zoo/pred/img/dendrite_brush_1.png",  // 0
            "nl/rumfumme/zoo/pred/img/dendrite_brush_2.png",  // 1
            "nl/rumfumme/zoo/pred/img/dendrite_brush_3.png",  // 2
            "nl/rumfumme/zoo/pred/img/head.png",  // 3
            "nl/rumfumme/zoo/pred/img/full_without_tail_and_head.png",    // 4
            "nl/rumfumme/zoo/pred/img/tail_6.png",    // 5
            "nl/rumfumme/zoo/pred/img/tail_5.png",    // 6
            "nl/rumfumme/zoo/pred/img/tail_4.png",    // 7
            "nl/rumfumme/zoo/pred/img/tail_3.png",    // 8
            "nl/rumfumme/zoo/pred/img/tail_2.png",    // 9
            "nl/rumfumme/zoo/pred/img/tail_1.png" // 10
        };
        x_offs = new int[] {
            979,    // 0
            979,    // 1
            977,    // 2
            945,    // 3
            0,  // 4
            705,    // 5
            694,    // 6
            679,    // 7
            669,    // 8
            642,    // 9
            637 // 10
        };
        y_offs = new int[] {
            656,    // 0
            656,    // 1
            728,    // 2
            371,    // 3
            0,  // 4
            1203,   // 5
            1075,   // 6
            838,    // 7
            742,    // 8
            645,    // 9
            580 // 10
        };

        for(int i = 0; i < 11; i++) {
            images[i] = app.loadImage(image_names[i]);
        }
        img_dendrite_brush_1 = images[IDX_DENDRITE_BRUSH_1];
        img_dendrite_brush_2 = images[IDX_DENDRITE_BRUSH_2];
        img_dendrite_brush_3 = images[IDX_DENDRITE_BRUSH_3];
        img_head = images[IDX_HEAD];
        img_full_without_tail_and_head = images[IDX_FULL_WITHOUT_TAIL_AND_HEAD];
        img_tail_6 = images[IDX_TAIL_6];
        img_tail_5 = images[IDX_TAIL_5];
        img_tail_4 = images[IDX_TAIL_4];
        img_tail_3 = images[IDX_TAIL_3];
        img_tail_2 = images[IDX_TAIL_2];
        img_tail_1 = images[IDX_TAIL_1];
    }
}
