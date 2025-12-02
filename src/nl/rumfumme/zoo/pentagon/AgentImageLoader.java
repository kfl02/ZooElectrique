package nl.rumfumme.zoo.pentagon;

import nl.rumfumme.pt.ImageLoader;
import processing.core.PApplet;
import processing.core.PImage;

public class AgentImageLoader extends ImageLoader {
    public static final int IDX_FULL_WITHOUTH_HAND = 0; // 0
    public static final int IDX_HAND_WITHOUT_GLASS = IDX_FULL_WITHOUTH_HAND + 1;    // 1
    public static final int IDX_GLASS_WITHOUT_FOAM = IDX_HAND_WITHOUT_GLASS + 1;    // 2
    public static final int IDX_FOAM_8 = IDX_GLASS_WITHOUT_FOAM + 1;    // 3
    public static final int IDX_FOAM_7 = IDX_FOAM_8 + 1;    // 4
    public static final int IDX_FOAM_6 = IDX_FOAM_7 + 1;    // 5
    public static final int IDX_FOAM_5 = IDX_FOAM_6 + 1;    // 6
    public static final int IDX_FOAM_4 = IDX_FOAM_5 + 1;    // 7
    public static final int IDX_FOAM_3 = IDX_FOAM_4 + 1;    // 8
    public static final int IDX_FOAM_2 = IDX_FOAM_3 + 1;    // 9
    public static final int IDX_FOAM_1 = IDX_FOAM_2 + 1;    // 10
    public static final int IDX_FINGERS = IDX_FOAM_1 + 1;   // 11
    public PImage img_full_withouth_hand;
    public PImage img_hand_without_glass;
    public PImage img_glass_without_foam;
    public PImage img_foam_8;
    public PImage img_foam_7;
    public PImage img_foam_6;
    public PImage img_foam_5;
    public PImage img_foam_4;
    public PImage img_foam_3;
    public PImage img_foam_2;
    public PImage img_foam_1;
    public PImage img_fingers;

    public AgentImageLoader(PApplet app) {
        super(app, 12, 0);
        image_names = new String[] {
            "nl/rumfumme/zoo/pentagon/img/full_withouth_hand.png",  // 0
            "nl/rumfumme/zoo/pentagon/img/hand_without_glass.png",  // 1
            "nl/rumfumme/zoo/pentagon/img/glass_without_foam.png",  // 2
            "nl/rumfumme/zoo/pentagon/img/foam_8.png",  // 3
            "nl/rumfumme/zoo/pentagon/img/foam_7.png",  // 4
            "nl/rumfumme/zoo/pentagon/img/foam_6.png",  // 5
            "nl/rumfumme/zoo/pentagon/img/foam_5.png",  // 6
            "nl/rumfumme/zoo/pentagon/img/foam_4.png",  // 7
            "nl/rumfumme/zoo/pentagon/img/foam_3.png",  // 8
            "nl/rumfumme/zoo/pentagon/img/foam_2.png",  // 9
            "nl/rumfumme/zoo/pentagon/img/foam_1.png",  // 10
            "nl/rumfumme/zoo/pentagon/img/fingers.png"  // 11
        };
        x_offs = new int[] {
            72, // 0
            74, // 1
            471,    // 2
            771,    // 3
            1077,   // 4
            1174,   // 5
            1132,   // 6
            967,    // 7
            834,    // 8
            718,    // 9
            549,    // 10
            407 // 11
        };
        y_offs = new int[] {
            88, // 0
            1120,   // 1
            88, // 2
            442,    // 3
            588,    // 4
            664,    // 5
            485,    // 6
            338,    // 7
            100,    // 8
            348,    // 9
            473,    // 10
            1126    // 11
        };

        for(int i = 0; i < 12; i++) {
            images[i] = app.loadImage(image_names[i]);
        }
        img_full_withouth_hand = images[IDX_FULL_WITHOUTH_HAND];
        img_hand_without_glass = images[IDX_HAND_WITHOUT_GLASS];
        img_glass_without_foam = images[IDX_GLASS_WITHOUT_FOAM];
        img_foam_8 = images[IDX_FOAM_8];
        img_foam_7 = images[IDX_FOAM_7];
        img_foam_6 = images[IDX_FOAM_6];
        img_foam_5 = images[IDX_FOAM_5];
        img_foam_4 = images[IDX_FOAM_4];
        img_foam_3 = images[IDX_FOAM_3];
        img_foam_2 = images[IDX_FOAM_2];
        img_foam_1 = images[IDX_FOAM_1];
        img_fingers = images[IDX_FINGERS];
    }
}
