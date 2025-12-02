package nl.rumfumme.zoo2.cages.red;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

public class RedAtlas {
    public int canvas_width = 1288;
    public int canvas_height = 888;
    public AtlasRegion[] images = new AtlasRegion[3];
    public static final int IDX_BACK = 0; // 0
    public static final int IDX_TREE = IDX_BACK + 1;  // 1
    public static final int IDX_BODY = IDX_TREE + 1;  // 2
    public AtlasRegion img_back;
    public AtlasRegion img_tree;
    public AtlasRegion img_body;
    public static final String[] image_names = new String[] {
        "back", // 0
        "tree", // 1
        "body"  // 2
    };
    public static final int[] width = {
        1288, // 0
        357, // 1
        312  // 2
    };
    public static final int width_back = 1288; // 0
    public static final int width_tree = 357; // 1
    public static final int width_body = 312;  // 2
    public static final int[] height = {
        888, // 0
        493, // 1
        201  // 2
    };
    public static final int height_back = 888; // 0
    public static final int height_tree = 493; // 1
    public static final int height_body = 201;  // 2
    public static final int[] x_offs = {
        0, // 0
        490, // 1
        495  // 2
    };
    public static final int x_offs_back = 0; // 0
    public static final int x_offs_tree = 490; // 1
    public static final int x_offs_body = 495;  // 2
    public static final int[] y_offs_lu = {
        0, // 0
        364, // 1
        94  // 2
    };
    public static final int y_offs_lu_back = 0; // 0
    public static final int y_offs_lu_tree = 364; // 1
    public static final int y_offs_lu_body = 94;  // 2
    public static final int[] y_offs = {
        0, // 0
        31, // 1
        593  // 2
    };
    public static final int y_offs_back = 0; // 0
    public static final int y_offs_tree = 31; // 1
    public static final int y_offs_body = 593;  // 2
    public TextureAtlas atlas;

    public RedAtlas() {
        // TODO add path
        atlas = new TextureAtlas(Gdx.files.internal("red/Red.atlas"));

        for(int i = 0; i < 3; i++) {
            images[i] = atlas.findRegion(image_names[i]);
        }
        img_back = images[IDX_BACK];
        img_tree = images[IDX_TREE];
        img_body = images[IDX_BODY];
    }
}
