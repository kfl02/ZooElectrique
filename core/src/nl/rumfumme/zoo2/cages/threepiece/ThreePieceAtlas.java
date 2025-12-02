package nl.rumfumme.zoo2.cages.threepiece;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
public class ThreePieceAtlas {
	public static final int CANVAS_WIDTH = 1200;
	public static final int CANVAS_HEIGHT = 900;
	public static final int NUM_IMAGES = 6;
	public AtlasRegion[] images = new AtlasRegion[NUM_IMAGES];
	public static final int IDX_EYEBALL = 0;	//	0
	public static final int IDX_IRIS = IDX_EYEBALL + 1;	//	1
	public static final int IDX_PENIS = IDX_IRIS + 1;	//	2
	public static final int IDX_COVER = IDX_PENIS + 1;	//	3
	public static final int IDX_STAMP = IDX_COVER + 1;	//	4
	public static final int IDX_CHEST = IDX_STAMP + 1;	//	5
	public AtlasRegion img_eyeball;
	public AtlasRegion img_iris;
	public AtlasRegion img_penis;
	public AtlasRegion img_cover;
	public AtlasRegion img_stamp;
	public AtlasRegion img_chest;
	public static final String[] image_names = new String[] {
        "eyeball",	//	0	IDX_EYEBALL
        "iris",	//	1	IDX_IRIS
        "penis",	//	2	IDX_PENIS
        "cover",	//	3	IDX_COVER
        "stamp",	//	4	IDX_STAMP
        "chest"	//	5	IDX_CHEST
	};
	public static final int[] width = {
		78,	//	0	IDX_EYEBALL
		28,	//	1	IDX_IRIS
		205,	//	2	IDX_PENIS
		199,	//	3	IDX_COVER
		394,	//	4	IDX_STAMP
		336	//	5	IDX_CHEST
	};
	public static final int width_eyeball = 78;	//	0	IDX_EYEBALL
	public static final int width_iris = 28;	//	1	IDX_IRIS
	public static final int width_penis = 205;	//	2	IDX_PENIS
	public static final int width_cover = 199;	//	3	IDX_COVER
	public static final int width_stamp = 394;	//	4	IDX_STAMP
	public static final int width_chest = 336;	//	5	IDX_CHEST
	public static final int[] height = {
		93,	//	0	IDX_EYEBALL
		39,	//	1	IDX_IRIS
		491,	//	2	IDX_PENIS
		174,	//	3	IDX_COVER
		383,	//	4	IDX_STAMP
		312	//	5	IDX_CHEST
	};
	public static final int height_eyeball = 93;	//	0	IDX_EYEBALL
	public static final int height_iris = 39;	//	1	IDX_IRIS
	public static final int height_penis = 491;	//	2	IDX_PENIS
	public static final int height_cover = 174;	//	3	IDX_COVER
	public static final int height_stamp = 383;	//	4	IDX_STAMP
	public static final int height_chest = 312;	//	5	IDX_CHEST
	public static final int[] x_offs = {
		783,	//	0	IDX_EYEBALL
		814,	//	1	IDX_IRIS
		707,	//	2	IDX_PENIS
		719,	//	3	IDX_COVER
		50,	//	4	IDX_STAMP
		661	//	5	IDX_CHEST
	};
	public static final int x_offs_eyeball = 783;	//	0	IDX_EYEBALL
	public static final int x_offs_iris = 814;	//	1	IDX_IRIS
	public static final int x_offs_penis = 707;	//	2	IDX_PENIS
	public static final int x_offs_cover = 719;	//	3	IDX_COVER
	public static final int x_offs_stamp = 50;	//	4	IDX_STAMP
	public static final int x_offs_chest = 661;	//	5	IDX_CHEST
	public static final int[] y_offs = {
		730,	//	0	IDX_EYEBALL
		754,	//	1	IDX_IRIS
		381,	//	2	IDX_PENIS
		0,	//	3	IDX_COVER
		103,	//	4	IDX_STAMP
		109	//	5	IDX_CHEST
	};
	public static final int y_offs_eyeball = 730;	//	0	IDX_EYEBALL
	public static final int y_offs_iris = 754;	//	1	IDX_IRIS
	public static final int y_offs_penis = 381;	//	2	IDX_PENIS
	public static final int y_offs_cover = 0;	//	3	IDX_COVER
	public static final int y_offs_stamp = 103;	//	4	IDX_STAMP
	public static final int y_offs_chest = 109;	//	5	IDX_CHEST
	public TextureAtlas atlas;

	public ThreePieceAtlas() {
		atlas = new TextureAtlas(Gdx.files.internal("threepiece/Three_piece.atlas"));

		for(int i = 0; i < NUM_IMAGES; i++) {
			if(image_names[i].matches(".*_[0-9]+")) {
				int u = image_names[i].lastIndexOf('_');
				String image_name = image_names[i].substring(0, u);
				int idx = Integer.parseInt(image_names[i].substring(u + 1));
				images[i] = atlas.findRegion(image_name, idx);
			} else {
				images[i] = atlas.findRegion(image_names[i]);
			}
		}
		img_eyeball = images[IDX_EYEBALL];
		img_iris = images[IDX_IRIS];
		img_penis = images[IDX_PENIS];
		img_cover = images[IDX_COVER];
		img_stamp = images[IDX_STAMP];
		img_chest = images[IDX_CHEST];
	}
}
