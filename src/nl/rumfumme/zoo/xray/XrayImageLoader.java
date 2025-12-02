package nl.rumfumme.zoo.xray;

import processing.core.PApplet;
import processing.core.PImage;

public class XrayImageLoader {
    private PApplet app;

    public XrayImageLoader(PApplet app) {
        this.app = app;
    }

    public final PImage[] images = new PImage[28];
    public static final int IDX_HAMATUM = 0; // 0
    public static final int IDX_CAPITATUM = IDX_HAMATUM + 1; // 1
    public static final int IDX_TRAPEZOIDEUM = IDX_CAPITATUM + 1; // 2
    public static final int IDX_TRAPEZIUM = IDX_TRAPEZOIDEUM + 1; // 3
    public static final int IDX_TRIQUETRUM_PISIFORME = IDX_TRAPEZIUM + 1; // 4
    public static final int IDX_LUNATUM = IDX_TRIQUETRUM_PISIFORME + 1; // 5
    public static final int IDX_SCAPHOIDEUM = IDX_LUNATUM + 1; // 6
    public static final int IDX_ULNA = IDX_SCAPHOIDEUM + 1; // 7
    public static final int IDX_RADIUS = IDX_ULNA + 1; // 8
    public static final int IDX_METACARPALE_PRIMUM = IDX_RADIUS + 1; // 9
    public static final int IDX_METACARPALE_SECUNDUM = IDX_METACARPALE_PRIMUM + 1; // 10
    public static final int IDX_METACARPALE_TERTIUM = IDX_METACARPALE_SECUNDUM + 1; // 11
    public static final int IDX_METACARPALE_QUARTUM = IDX_METACARPALE_TERTIUM + 1; // 12
    public static final int IDX_METACARPALE_QUINTUM = IDX_METACARPALE_QUARTUM + 1; // 13
    public static final int IDX_POLLEX_PROXIMALIS = IDX_METACARPALE_QUINTUM + 1; // 14
    public static final int IDX_POLLEX_DISTALIS = IDX_POLLEX_PROXIMALIS + 1; // 15
    public static final int IDX_INDEX_PROXIMALIS = IDX_POLLEX_DISTALIS + 1; // 16
    public static final int IDX_INDEX_MEDIA = IDX_INDEX_PROXIMALIS + 1; // 17
    public static final int IDX_INDEX_DISTALIS = IDX_INDEX_MEDIA + 1; // 18
    public static final int IDX_MEDIUS_PROXIMALIS = IDX_INDEX_DISTALIS + 1; // 19
    public static final int IDX_MEDIUS_MEDIA = IDX_MEDIUS_PROXIMALIS + 1; // 20
    public static final int IDX_MEDIUS_DISTALIS = IDX_MEDIUS_MEDIA + 1; // 21
    public static final int IDX_ANNULARIUS_PROXIMALIS = IDX_MEDIUS_DISTALIS + 1; // 22
    public static final int IDX_ANNULARIUS_MEDIA = IDX_ANNULARIUS_PROXIMALIS + 1; // 23
    public static final int IDX_ANNULARIUS_DISTALIS = IDX_ANNULARIUS_MEDIA + 1; // 24
    public static final int IDX_MINIMUS_PROXIMALIS = IDX_ANNULARIUS_DISTALIS + 1; // 25
    public static final int IDX_MINIMUS_MEDIA = IDX_MINIMUS_PROXIMALIS + 1; // 26
    public static final int IDX_MINIMUS_DISTALIS = IDX_MINIMUS_MEDIA + 1; // 27
    public PImage img_hamatum;
    public PImage img_capitatum;
    public PImage img_trapezoideum;
    public PImage img_trapezium;
    public PImage img_triquetrum_pisiforme;
    public PImage img_lunatum;
    public PImage img_scaphoideum;
    public PImage img_ulna;
    public PImage img_radius;
    public PImage img_metacarpale_primum;
    public PImage img_metacarpale_secundum;
    public PImage img_metacarpale_tertium;
    public PImage img_metacarpale_quartum;
    public PImage img_metacarpale_quintum;
    public PImage img_pollex_proximalis;
    public PImage img_pollex_distalis;
    public PImage img_index_proximalis;
    public PImage img_index_media;
    public PImage img_index_distalis;
    public PImage img_medius_proximalis;
    public PImage img_medius_media;
    public PImage img_medius_distalis;
    public PImage img_annularius_proximalis;
    public PImage img_annularius_media;
    public PImage img_annularius_distalis;
    public PImage img_minimus_proximalis;
    public PImage img_minimus_media;
    public PImage img_minimus_distalis;
    final String[] image_names = new String[] {
            "nl/rumfumme/zoo/xray/img/hamatum.png", // 0
            "nl/rumfumme/zoo/xray/img/capitatum.png", // 1
            "nl/rumfumme/zoo/xray/img/trapezoideum.png", // 2
            "nl/rumfumme/zoo/xray/img/trapezium.png", // 3
            "nl/rumfumme/zoo/xray/img/triquetrum_pisiforme.png", // 4
            "nl/rumfumme/zoo/xray/img/lunatum.png", // 5
            "nl/rumfumme/zoo/xray/img/scaphoideum.png", // 6
            "nl/rumfumme/zoo/xray/img/ulna.png", // 7
            "nl/rumfumme/zoo/xray/img/radius.png", // 8
            "nl/rumfumme/zoo/xray/img/metacarpale_primum.png", // 9
            "nl/rumfumme/zoo/xray/img/metacarpale_secundum.png", // 10
            "nl/rumfumme/zoo/xray/img/metacarpale_tertium.png", // 11
            "nl/rumfumme/zoo/xray/img/metacarpale_quartum.png", // 12
            "nl/rumfumme/zoo/xray/img/metacarpale_quintum.png", // 13
            "nl/rumfumme/zoo/xray/img/pollex_proximalis.png", // 14
            "nl/rumfumme/zoo/xray/img/pollex_distalis.png", // 15
            "nl/rumfumme/zoo/xray/img/index_proximalis.png", // 16
            "nl/rumfumme/zoo/xray/img/index_media.png", // 17
            "nl/rumfumme/zoo/xray/img/index_distalis.png", // 18
            "nl/rumfumme/zoo/xray/img/medius_proximalis.png", // 19
            "nl/rumfumme/zoo/xray/img/medius_media.png", // 20
            "nl/rumfumme/zoo/xray/img/medius_distalis.png", // 21
            "nl/rumfumme/zoo/xray/img/annularius_proximalis.png", // 22
            "nl/rumfumme/zoo/xray/img/annularius_media.png", // 23
            "nl/rumfumme/zoo/xray/img/annularius_distalis.png", // 24
            "nl/rumfumme/zoo/xray/img/minimus_proximalis.png", // 25
            "nl/rumfumme/zoo/xray/img/minimus_media.png", // 26
            "nl/rumfumme/zoo/xray/img/minimus_distalis.png" // 27
    };
    public final int[] x_offs = {
            200, // 0
            230, // 1
            275, // 2
            299, // 3
            182, // 4
            205, // 5
            249, // 6
            176, // 7
            218, // 8
            316, // 9
            265, // 10
            233, // 11
            193, // 12
            156, // 13
            363, // 14
            385, // 15
            296, // 16
            294, // 17
            291, // 18
            237, // 19
            239, // 20
            240, // 21
            191, // 22
            193, // 23
            192, // 24
            152, // 25
            156, // 26
            158 // 27
    };
    public final int[] y_offs = {
            531, // 0
            526, // 1
            528, // 2
            532, // 3
            564, // 4
            594, // 5
            559, // 6
            626, // 7
            602, // 8
            419, // 9
            343, // 10
            344, // 11
            374, // 12
            400, // 13
            333, // 14
            278, // 15
            228, // 16
            166, // 17
            122, // 18
            219, // 19
            143, // 20
            93, // 21
            260, // 22
            190, // 23
            139, // 24
            311, // 25
            262, // 26
            216 // 27
    };

    public void loadImages() {
        for (int i = 0; i < 28; i++) {
            images[i] = app.loadImage(image_names[i]);
        }
        img_hamatum = images[IDX_HAMATUM];
        img_capitatum = images[IDX_CAPITATUM];
        img_trapezoideum = images[IDX_TRAPEZOIDEUM];
        img_trapezium = images[IDX_TRAPEZIUM];
        img_triquetrum_pisiforme = images[IDX_TRIQUETRUM_PISIFORME];
        img_lunatum = images[IDX_LUNATUM];
        img_scaphoideum = images[IDX_SCAPHOIDEUM];
        img_ulna = images[IDX_ULNA];
        img_radius = images[IDX_RADIUS];
        img_metacarpale_primum = images[IDX_METACARPALE_PRIMUM];
        img_metacarpale_secundum = images[IDX_METACARPALE_SECUNDUM];
        img_metacarpale_tertium = images[IDX_METACARPALE_TERTIUM];
        img_metacarpale_quartum = images[IDX_METACARPALE_QUARTUM];
        img_metacarpale_quintum = images[IDX_METACARPALE_QUINTUM];
        img_pollex_proximalis = images[IDX_POLLEX_PROXIMALIS];
        img_pollex_distalis = images[IDX_POLLEX_DISTALIS];
        img_index_proximalis = images[IDX_INDEX_PROXIMALIS];
        img_index_media = images[IDX_INDEX_MEDIA];
        img_index_distalis = images[IDX_INDEX_DISTALIS];
        img_medius_proximalis = images[IDX_MEDIUS_PROXIMALIS];
        img_medius_media = images[IDX_MEDIUS_MEDIA];
        img_medius_distalis = images[IDX_MEDIUS_DISTALIS];
        img_annularius_proximalis = images[IDX_ANNULARIUS_PROXIMALIS];
        img_annularius_media = images[IDX_ANNULARIUS_MEDIA];
        img_annularius_distalis = images[IDX_ANNULARIUS_DISTALIS];
        img_minimus_proximalis = images[IDX_MINIMUS_PROXIMALIS];
        img_minimus_media = images[IDX_MINIMUS_MEDIA];
        img_minimus_distalis = images[IDX_MINIMUS_DISTALIS];
    }

    public void loadTileImages() {
    }
}
