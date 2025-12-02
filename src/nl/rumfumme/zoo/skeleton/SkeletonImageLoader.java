package nl.rumfumme.zoo.skeleton;

import processing.core.PImage;
import nl.rumfumme.pt.ImageLoader;
import processing.core.PApplet;

public class SkeletonImageLoader extends ImageLoader {
    public static final int IDX_1 = 0;  // 0
    public static final int IDX_2 = IDX_1 + 1;  // 1
    public static final int IDX_3 = IDX_2 + 1;  // 2
    public static final int IDX_4 = IDX_3 + 1;  // 3
    public static final int IDX_5 = IDX_4 + 1;  // 4
    public static final int IDX_6 = IDX_5 + 1;  // 5
    public static final int IDX_7 = IDX_6 + 1;  // 6
    public static final int IDX_8 = IDX_7 + 1;  // 7
    public static final int IDX_9 = IDX_8 + 1;  // 8
    public static final int IDX_10 = IDX_9 + 1; // 9
    public static final int IDX_11 = IDX_10 + 1;    // 10
    public static final int IDX_12 = IDX_11 + 1;    // 11
    public static final int IDX_13 = IDX_12 + 1;    // 12
    public static final int IDX_14 = IDX_13 + 1;    // 13
    public static final int IDX_15 = IDX_14 + 1;    // 14
    public static final int IDX_16 = IDX_15 + 1;    // 15
    public static final int IDX_17 = IDX_16 + 1;    // 16
    public static final int IDX_18 = IDX_17 + 1;    // 17
    public static final int IDX_19 = IDX_18 + 1;    // 18
    public static final int IDX_20 = IDX_19 + 1;    // 19
    public static final int IDX_21 = IDX_20 + 1;    // 20
    public static final int IDX_22 = IDX_21 + 1;    // 21
    public static final int IDX_23 = IDX_22 + 1;    // 22
    public static final int IDX_24 = IDX_23 + 1;    // 23
    public static final int IDX_25 = IDX_24 + 1;    // 24
    public static final int IDX_26 = IDX_25 + 1;    // 25
    public PImage img_1;
    public PImage img_2;
    public PImage img_3;
    public PImage img_4;
    public PImage img_5;
    public PImage img_6;
    public PImage img_7;
    public PImage img_8;
    public PImage img_9;
    public PImage img_10;
    public PImage img_11;
    public PImage img_12;
    public PImage img_13;
    public PImage img_14;
    public PImage img_15;
    public PImage img_16;
    public PImage img_17;
    public PImage img_18;
    public PImage img_19;
    public PImage img_20;
    public PImage img_21;
    public PImage img_22;
    public PImage img_23;
    public PImage img_24;
    public PImage img_25;
    public PImage img_26;

    public SkeletonImageLoader(PApplet app) {
        super(app, 26, 0);
        image_names = new String[] {
            "nl/rumfumme/zoo/skeleton/img/1.png",   // 0
            "nl/rumfumme/zoo/skeleton/img/2.png",   // 1
            "nl/rumfumme/zoo/skeleton/img/3.png",   // 2
            "nl/rumfumme/zoo/skeleton/img/4.png",   // 3
            "nl/rumfumme/zoo/skeleton/img/5.png",   // 4
            "nl/rumfumme/zoo/skeleton/img/6.png",   // 5
            "nl/rumfumme/zoo/skeleton/img/7.png",   // 6
            "nl/rumfumme/zoo/skeleton/img/8.png",   // 7
            "nl/rumfumme/zoo/skeleton/img/9.png",   // 8
            "nl/rumfumme/zoo/skeleton/img/10.png",  // 9
            "nl/rumfumme/zoo/skeleton/img/11.png",  // 10
            "nl/rumfumme/zoo/skeleton/img/12.png",  // 11
            "nl/rumfumme/zoo/skeleton/img/13.png",  // 12
            "nl/rumfumme/zoo/skeleton/img/14.png",  // 13
            "nl/rumfumme/zoo/skeleton/img/15.png",  // 14
            "nl/rumfumme/zoo/skeleton/img/16.png",  // 15
            "nl/rumfumme/zoo/skeleton/img/17.png",  // 16
            "nl/rumfumme/zoo/skeleton/img/18.png",  // 17
            "nl/rumfumme/zoo/skeleton/img/19.png",  // 18
            "nl/rumfumme/zoo/skeleton/img/20.png",  // 19
            "nl/rumfumme/zoo/skeleton/img/21.png",  // 20
            "nl/rumfumme/zoo/skeleton/img/22.png",  // 21
            "nl/rumfumme/zoo/skeleton/img/23.png",  // 22
            "nl/rumfumme/zoo/skeleton/img/24.png",  // 23
            "nl/rumfumme/zoo/skeleton/img/25.png",  // 24
            "nl/rumfumme/zoo/skeleton/img/26.png"   // 25
        };
        x_offs = new int[] {
            269,    // 0
            272,    // 1
            293,    // 2
            310,    // 3
            508,    // 4
            736,    // 5
            674,    // 6
            347,    // 7
            362,    // 8
            384,    // 9
            416,    // 10
            435,    // 11
            402,    // 12
            412,    // 13
            422,    // 14
            480,    // 15
            512,    // 16
            557,    // 17
            577,    // 18
            626,    // 19
            681,    // 20
            625,    // 21
            795,    // 22
            733,    // 23
            465,    // 24
            612 // 25
        };
        y_offs = new int[] {
            444,    // 0
            624,    // 1
            606,    // 2
            744,    // 3
            742,    // 4
            793,    // 5
            779,    // 6
            858,    // 7
            878,    // 8
            909,    // 9
            919,    // 10
            904,    // 11
            930,    // 12
            961,    // 13
            1005,   // 14
            1036,   // 15
            1068,   // 16
            1097,   // 17
            1116,   // 18
            1160,   // 19
            1243,   // 20
            941,    // 21
            877,    // 22
            1071,   // 23
            902,    // 24
            837 // 25
        };

        for(int i = 0; i < 26; i++) {
            images[i] = app.loadImage(image_names[i]);
        }
        img_1 = images[IDX_1];
        img_2 = images[IDX_2];
        img_3 = images[IDX_3];
        img_4 = images[IDX_4];
        img_5 = images[IDX_5];
        img_6 = images[IDX_6];
        img_7 = images[IDX_7];
        img_8 = images[IDX_8];
        img_9 = images[IDX_9];
        img_10 = images[IDX_10];
        img_11 = images[IDX_11];
        img_12 = images[IDX_12];
        img_13 = images[IDX_13];
        img_14 = images[IDX_14];
        img_15 = images[IDX_15];
        img_16 = images[IDX_16];
        img_17 = images[IDX_17];
        img_18 = images[IDX_18];
        img_19 = images[IDX_19];
        img_20 = images[IDX_20];
        img_21 = images[IDX_21];
        img_22 = images[IDX_22];
        img_23 = images[IDX_23];
        img_24 = images[IDX_24];
        img_25 = images[IDX_25];
        img_26 = images[IDX_26];
    }
}
