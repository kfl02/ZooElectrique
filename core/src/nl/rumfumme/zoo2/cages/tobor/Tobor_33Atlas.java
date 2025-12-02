package nl.rumfumme.zoo2.cages.tobor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
public class Tobor_33Atlas {
	public static final int CANVAS_WIDTH = 770;
	public static final int CANVAS_HEIGHT = 916;
	public static final int NUM_IMAGES = 58;
	public AtlasRegion[] images = new AtlasRegion[NUM_IMAGES];
	public static final int IDX_MOUTH_INSIDE = 0;	//	0
	public static final int IDX_TOOTH_UPPER4 = IDX_MOUTH_INSIDE + 1;	//	1
	public static final int IDX_TOOTH_UPPER3 = IDX_TOOTH_UPPER4 + 1;	//	2
	public static final int IDX_TOOTH_UPPER2 = IDX_TOOTH_UPPER3 + 1;	//	3
	public static final int IDX_TOOTH_UPPER1 = IDX_TOOTH_UPPER2 + 1;	//	4
	public static final int IDX_TOOTH_LOWER6 = IDX_TOOTH_UPPER1 + 1;	//	5
	public static final int IDX_TOOTH_LOWER5 = IDX_TOOTH_LOWER6 + 1;	//	6
	public static final int IDX_TOOTH_LOWER4 = IDX_TOOTH_LOWER5 + 1;	//	7
	public static final int IDX_TOOTH_LOWER3 = IDX_TOOTH_LOWER4 + 1;	//	8
	public static final int IDX_TOOTH_LOWER2 = IDX_TOOTH_LOWER3 + 1;	//	9
	public static final int IDX_TOOTH_LOWER1 = IDX_TOOTH_LOWER2 + 1;	//	10
	public static final int IDX_EYE_BALL = IDX_TOOTH_LOWER1 + 1;	//	11
	public static final int IDX_EYE = IDX_EYE_BALL + 1;	//	12
	public static final int IDX_HAND = IDX_EYE + 1;	//	13
	public static final int IDX_ARM = IDX_HAND + 1;	//	14
	public static final int IDX_BODY3 = IDX_ARM + 1;	//	15
	public static final int IDX_BODY2 = IDX_BODY3 + 1;	//	16
	public static final int IDX_HEAD = IDX_BODY2 + 1;	//	17
	public static final int IDX_EAR = IDX_HEAD + 1;	//	18
	public static final int IDX_HEAD_SCREW3 = IDX_EAR + 1;	//	19
	public static final int IDX_HEAD_SCREW2 = IDX_HEAD_SCREW3 + 1;	//	20
	public static final int IDX_HEAD_SCREW1 = IDX_HEAD_SCREW2 + 1;	//	21
	public static final int IDX_BODY1 = IDX_HEAD_SCREW1 + 1;	//	22
	public static final int IDX_YDOB = IDX_BODY1 + 1;	//	23
	public static final int IDX_FINGER4_4 = IDX_YDOB + 1;	//	24
	public static final int IDX_FINGER4_3 = IDX_FINGER4_4 + 1;	//	25
	public static final int IDX_FINGER4_2 = IDX_FINGER4_3 + 1;	//	26
	public static final int IDX_FINGER4_1 = IDX_FINGER4_2 + 1;	//	27
	public static final int IDX_FINGER3_4 = IDX_FINGER4_1 + 1;	//	28
	public static final int IDX_FINGER3_3 = IDX_FINGER3_4 + 1;	//	29
	public static final int IDX_FINGER3_2 = IDX_FINGER3_3 + 1;	//	30
	public static final int IDX_FINGER3_1 = IDX_FINGER3_2 + 1;	//	31
	public static final int IDX_FINGER2_4 = IDX_FINGER3_1 + 1;	//	32
	public static final int IDX_FINGER2_3 = IDX_FINGER2_4 + 1;	//	33
	public static final int IDX_FINGER2_2 = IDX_FINGER2_3 + 1;	//	34
	public static final int IDX_FINGER2_1 = IDX_FINGER2_2 + 1;	//	35
	public static final int IDX_FINGER1_3 = IDX_FINGER2_1 + 1;	//	36
	public static final int IDX_FINGER1_2 = IDX_FINGER1_3 + 1;	//	37
	public static final int IDX_FINGER1_1 = IDX_FINGER1_2 + 1;	//	38
	public static final int IDX_MRA = IDX_FINGER1_1 + 1;	//	39
	public static final int IDX_DNAH = IDX_MRA + 1;	//	40
	public static final int IDX_ECAF = IDX_DNAH + 1;	//	41
	public static final int IDX_THOOT_LOWER6 = IDX_ECAF + 1;	//	42
	public static final int IDX_THOOT_LOWER5 = IDX_THOOT_LOWER6 + 1;	//	43
	public static final int IDX_THOOT_LOWER4 = IDX_THOOT_LOWER5 + 1;	//	44
	public static final int IDX_THOOT_LOWER3 = IDX_THOOT_LOWER4 + 1;	//	45
	public static final int IDX_THOOT_LOWER2 = IDX_THOOT_LOWER3 + 1;	//	46
	public static final int IDX_THOOT_LOWER1 = IDX_THOOT_LOWER2 + 1;	//	47
	public static final int IDX_THOOT_UPPER6 = IDX_THOOT_LOWER1 + 1;	//	48
	public static final int IDX_THOOT_UPPER5 = IDX_THOOT_UPPER6 + 1;	//	49
	public static final int IDX_THOOT_UPPER4 = IDX_THOOT_UPPER5 + 1;	//	50
	public static final int IDX_THOOT_UPPER3 = IDX_THOOT_UPPER4 + 1;	//	51
	public static final int IDX_THOOT_UPPER2 = IDX_THOOT_UPPER3 + 1;	//	52
	public static final int IDX_THOOT_UPPER1 = IDX_THOOT_UPPER2 + 1;	//	53
	public static final int IDX_THUOM = IDX_THOOT_UPPER1 + 1;	//	54
	public static final int IDX_DIL2 = IDX_THUOM + 1;	//	55
	public static final int IDX_DIL1 = IDX_DIL2 + 1;	//	56
	public static final int IDX_PALM = IDX_DIL1 + 1;	//	57
	public AtlasRegion img_mouth_inside;
	public AtlasRegion img_tooth_upper4;
	public AtlasRegion img_tooth_upper3;
	public AtlasRegion img_tooth_upper2;
	public AtlasRegion img_tooth_upper1;
	public AtlasRegion img_tooth_lower6;
	public AtlasRegion img_tooth_lower5;
	public AtlasRegion img_tooth_lower4;
	public AtlasRegion img_tooth_lower3;
	public AtlasRegion img_tooth_lower2;
	public AtlasRegion img_tooth_lower1;
	public AtlasRegion img_eye_ball;
	public AtlasRegion img_eye;
	public AtlasRegion img_hand;
	public AtlasRegion img_arm;
	public AtlasRegion img_body3;
	public AtlasRegion img_body2;
	public AtlasRegion img_head;
	public AtlasRegion img_ear;
	public AtlasRegion img_head_screw3;
	public AtlasRegion img_head_screw2;
	public AtlasRegion img_head_screw1;
	public AtlasRegion img_body1;
	public AtlasRegion img_ydob;
	public AtlasRegion img_finger4_4;
	public AtlasRegion img_finger4_3;
	public AtlasRegion img_finger4_2;
	public AtlasRegion img_finger4_1;
	public AtlasRegion img_finger3_4;
	public AtlasRegion img_finger3_3;
	public AtlasRegion img_finger3_2;
	public AtlasRegion img_finger3_1;
	public AtlasRegion img_finger2_4;
	public AtlasRegion img_finger2_3;
	public AtlasRegion img_finger2_2;
	public AtlasRegion img_finger2_1;
	public AtlasRegion img_finger1_3;
	public AtlasRegion img_finger1_2;
	public AtlasRegion img_finger1_1;
	public AtlasRegion img_mra;
	public AtlasRegion img_dnah;
	public AtlasRegion img_ecaf;
	public AtlasRegion img_thoot_lower6;
	public AtlasRegion img_thoot_lower5;
	public AtlasRegion img_thoot_lower4;
	public AtlasRegion img_thoot_lower3;
	public AtlasRegion img_thoot_lower2;
	public AtlasRegion img_thoot_lower1;
	public AtlasRegion img_thoot_upper6;
	public AtlasRegion img_thoot_upper5;
	public AtlasRegion img_thoot_upper4;
	public AtlasRegion img_thoot_upper3;
	public AtlasRegion img_thoot_upper2;
	public AtlasRegion img_thoot_upper1;
	public AtlasRegion img_thuom;
	public AtlasRegion img_dil2;
	public AtlasRegion img_dil1;
	public AtlasRegion img_palm;
	public static final String[] image_names = new String[] {
        "mouth_inside",	//	0	IDX_MOUTH_INSIDE
        "tooth_upper4",	//	1	IDX_TOOTH_UPPER4
        "tooth_upper3",	//	2	IDX_TOOTH_UPPER3
        "tooth_upper2",	//	3	IDX_TOOTH_UPPER2
        "tooth_upper1",	//	4	IDX_TOOTH_UPPER1
        "tooth_lower6",	//	5	IDX_TOOTH_LOWER6
        "tooth_lower5",	//	6	IDX_TOOTH_LOWER5
        "tooth_lower4",	//	7	IDX_TOOTH_LOWER4
        "tooth_lower3",	//	8	IDX_TOOTH_LOWER3
        "tooth_lower2",	//	9	IDX_TOOTH_LOWER2
        "tooth_lower1",	//	10	IDX_TOOTH_LOWER1
        "eye_ball",	//	11	IDX_EYE_BALL
        "eye",	//	12	IDX_EYE
        "hand",	//	13	IDX_HAND
        "arm",	//	14	IDX_ARM
        "body3",	//	15	IDX_BODY3
        "body2",	//	16	IDX_BODY2
        "head",	//	17	IDX_HEAD
        "ear",	//	18	IDX_EAR
        "head_screw3",	//	19	IDX_HEAD_SCREW3
        "head_screw2",	//	20	IDX_HEAD_SCREW2
        "head_screw1",	//	21	IDX_HEAD_SCREW1
        "body1",	//	22	IDX_BODY1
        "ydob",	//	23	IDX_YDOB
        "finger4_4",	//	24	IDX_FINGER4_4
        "finger4_3",	//	25	IDX_FINGER4_3
        "finger4_2",	//	26	IDX_FINGER4_2
        "finger4_1",	//	27	IDX_FINGER4_1
        "finger3_4",	//	28	IDX_FINGER3_4
        "finger3_3",	//	29	IDX_FINGER3_3
        "finger3_2",	//	30	IDX_FINGER3_2
        "finger3_1",	//	31	IDX_FINGER3_1
        "finger2_4",	//	32	IDX_FINGER2_4
        "finger2_3",	//	33	IDX_FINGER2_3
        "finger2_2",	//	34	IDX_FINGER2_2
        "finger2_1",	//	35	IDX_FINGER2_1
        "finger1_3",	//	36	IDX_FINGER1_3
        "finger1_2",	//	37	IDX_FINGER1_2
        "finger1_1",	//	38	IDX_FINGER1_1
        "mra",	//	39	IDX_MRA
        "dnah",	//	40	IDX_DNAH
        "ecaf",	//	41	IDX_ECAF
        "thoot_lower6",	//	42	IDX_THOOT_LOWER6
        "thoot_lower5",	//	43	IDX_THOOT_LOWER5
        "thoot_lower4",	//	44	IDX_THOOT_LOWER4
        "thoot_lower3",	//	45	IDX_THOOT_LOWER3
        "thoot_lower2",	//	46	IDX_THOOT_LOWER2
        "thoot_lower1",	//	47	IDX_THOOT_LOWER1
        "thoot_upper6",	//	48	IDX_THOOT_UPPER6
        "thoot_upper5",	//	49	IDX_THOOT_UPPER5
        "thoot_upper4",	//	50	IDX_THOOT_UPPER4
        "thoot_upper3",	//	51	IDX_THOOT_UPPER3
        "thoot_upper2",	//	52	IDX_THOOT_UPPER2
        "thoot_upper1",	//	53	IDX_THOOT_UPPER1
        "thuom",	//	54	IDX_THUOM
        "dil2",	//	55	IDX_DIL2
        "dil1",	//	56	IDX_DIL1
        "palm"	//	57	IDX_PALM
	};
	public static final int[] x_offs = {
		464,	//	0	IDX_MOUTH_INSIDE
		534,	//	1	IDX_TOOTH_UPPER4
		506,	//	2	IDX_TOOTH_UPPER3
		483,	//	3	IDX_TOOTH_UPPER2
		453,	//	4	IDX_TOOTH_UPPER1
		553,	//	5	IDX_TOOTH_LOWER6
		536,	//	6	IDX_TOOTH_LOWER5
		516,	//	7	IDX_TOOTH_LOWER4
		494,	//	8	IDX_TOOTH_LOWER3
		472,	//	9	IDX_TOOTH_LOWER2
		445,	//	10	IDX_TOOTH_LOWER1
		491,	//	11	IDX_EYE_BALL
		491,	//	12	IDX_EYE
		114,	//	13	IDX_HAND
		214,	//	14	IDX_ARM
		567,	//	15	IDX_BODY3
		441,	//	16	IDX_BODY2
		438,	//	17	IDX_HEAD
		628,	//	18	IDX_EAR
		646,	//	19	IDX_HEAD_SCREW3
		645,	//	20	IDX_HEAD_SCREW2
		645,	//	21	IDX_HEAD_SCREW1
		320,	//	22	IDX_BODY1
		300,	//	23	IDX_YDOB
		132,	//	24	IDX_FINGER4_4
		140,	//	25	IDX_FINGER4_3
		183,	//	26	IDX_FINGER4_2
		194,	//	27	IDX_FINGER4_1
		73,	//	28	IDX_FINGER3_4
		86,	//	29	IDX_FINGER3_3
		130,	//	30	IDX_FINGER3_2
		150,	//	31	IDX_FINGER3_1
		0,	//	32	IDX_FINGER2_4
		18,	//	33	IDX_FINGER2_3
		57,	//	34	IDX_FINGER2_2
		88,	//	35	IDX_FINGER2_1
		14,	//	36	IDX_FINGER1_3
		23,	//	37	IDX_FINGER1_2
		63,	//	38	IDX_FINGER1_1
		477,	//	39	IDX_MRA
		678,	//	40	IDX_DNAH
		404,	//	41	IDX_ECAF
		543,	//	42	IDX_THOOT_LOWER6
		531,	//	43	IDX_THOOT_LOWER5
		520,	//	44	IDX_THOOT_LOWER4
		505,	//	45	IDX_THOOT_LOWER3
		483,	//	46	IDX_THOOT_LOWER2
		474,	//	47	IDX_THOOT_LOWER1
		545,	//	48	IDX_THOOT_UPPER6
		533,	//	49	IDX_THOOT_UPPER5
		521,	//	50	IDX_THOOT_UPPER4
		504,	//	51	IDX_THOOT_UPPER3
		487,	//	52	IDX_THOOT_UPPER2
		478,	//	53	IDX_THOOT_UPPER1
		470,	//	54	IDX_THUOM
		515,	//	55	IDX_DIL2
		446,	//	56	IDX_DIL1
		157	//	57	IDX_PALM
	};
	public static final int x_offs_mouth_inside = 464;	//	0	IDX_MOUTH_INSIDE
	public static final int x_offs_tooth_upper4 = 534;	//	1	IDX_TOOTH_UPPER4
	public static final int x_offs_tooth_upper3 = 506;	//	2	IDX_TOOTH_UPPER3
	public static final int x_offs_tooth_upper2 = 483;	//	3	IDX_TOOTH_UPPER2
	public static final int x_offs_tooth_upper1 = 453;	//	4	IDX_TOOTH_UPPER1
	public static final int x_offs_tooth_lower6 = 553;	//	5	IDX_TOOTH_LOWER6
	public static final int x_offs_tooth_lower5 = 536;	//	6	IDX_TOOTH_LOWER5
	public static final int x_offs_tooth_lower4 = 516;	//	7	IDX_TOOTH_LOWER4
	public static final int x_offs_tooth_lower3 = 494;	//	8	IDX_TOOTH_LOWER3
	public static final int x_offs_tooth_lower2 = 472;	//	9	IDX_TOOTH_LOWER2
	public static final int x_offs_tooth_lower1 = 445;	//	10	IDX_TOOTH_LOWER1
	public static final int x_offs_eye_ball = 491;	//	11	IDX_EYE_BALL
	public static final int x_offs_eye = 491;	//	12	IDX_EYE
	public static final int x_offs_hand = 114;	//	13	IDX_HAND
	public static final int x_offs_arm = 214;	//	14	IDX_ARM
	public static final int x_offs_body3 = 567;	//	15	IDX_BODY3
	public static final int x_offs_body2 = 441;	//	16	IDX_BODY2
	public static final int x_offs_head = 438;	//	17	IDX_HEAD
	public static final int x_offs_ear = 628;	//	18	IDX_EAR
	public static final int x_offs_head_screw3 = 646;	//	19	IDX_HEAD_SCREW3
	public static final int x_offs_head_screw2 = 645;	//	20	IDX_HEAD_SCREW2
	public static final int x_offs_head_screw1 = 645;	//	21	IDX_HEAD_SCREW1
	public static final int x_offs_body1 = 320;	//	22	IDX_BODY1
	public static final int x_offs_ydob = 300;	//	23	IDX_YDOB
	public static final int x_offs_finger4_4 = 132;	//	24	IDX_FINGER4_4
	public static final int x_offs_finger4_3 = 140;	//	25	IDX_FINGER4_3
	public static final int x_offs_finger4_2 = 183;	//	26	IDX_FINGER4_2
	public static final int x_offs_finger4_1 = 194;	//	27	IDX_FINGER4_1
	public static final int x_offs_finger3_4 = 73;	//	28	IDX_FINGER3_4
	public static final int x_offs_finger3_3 = 86;	//	29	IDX_FINGER3_3
	public static final int x_offs_finger3_2 = 130;	//	30	IDX_FINGER3_2
	public static final int x_offs_finger3_1 = 150;	//	31	IDX_FINGER3_1
	public static final int x_offs_finger2_4 = 0;	//	32	IDX_FINGER2_4
	public static final int x_offs_finger2_3 = 18;	//	33	IDX_FINGER2_3
	public static final int x_offs_finger2_2 = 57;	//	34	IDX_FINGER2_2
	public static final int x_offs_finger2_1 = 88;	//	35	IDX_FINGER2_1
	public static final int x_offs_finger1_3 = 14;	//	36	IDX_FINGER1_3
	public static final int x_offs_finger1_2 = 23;	//	37	IDX_FINGER1_2
	public static final int x_offs_finger1_1 = 63;	//	38	IDX_FINGER1_1
	public static final int x_offs_mra = 477;	//	39	IDX_MRA
	public static final int x_offs_dnah = 678;	//	40	IDX_DNAH
	public static final int x_offs_ecaf = 404;	//	41	IDX_ECAF
	public static final int x_offs_thoot_lower6 = 543;	//	42	IDX_THOOT_LOWER6
	public static final int x_offs_thoot_lower5 = 531;	//	43	IDX_THOOT_LOWER5
	public static final int x_offs_thoot_lower4 = 520;	//	44	IDX_THOOT_LOWER4
	public static final int x_offs_thoot_lower3 = 505;	//	45	IDX_THOOT_LOWER3
	public static final int x_offs_thoot_lower2 = 483;	//	46	IDX_THOOT_LOWER2
	public static final int x_offs_thoot_lower1 = 474;	//	47	IDX_THOOT_LOWER1
	public static final int x_offs_thoot_upper6 = 545;	//	48	IDX_THOOT_UPPER6
	public static final int x_offs_thoot_upper5 = 533;	//	49	IDX_THOOT_UPPER5
	public static final int x_offs_thoot_upper4 = 521;	//	50	IDX_THOOT_UPPER4
	public static final int x_offs_thoot_upper3 = 504;	//	51	IDX_THOOT_UPPER3
	public static final int x_offs_thoot_upper2 = 487;	//	52	IDX_THOOT_UPPER2
	public static final int x_offs_thoot_upper1 = 478;	//	53	IDX_THOOT_UPPER1
	public static final int x_offs_thuom = 470;	//	54	IDX_THUOM
	public static final int x_offs_dil2 = 515;	//	55	IDX_DIL2
	public static final int x_offs_dil1 = 446;	//	56	IDX_DIL1
	public static final int x_offs_palm = 157;	//	57	IDX_PALM
	public static final int[] y_offs = {
		789,	//	0	IDX_MOUTH_INSIDE
		776,	//	1	IDX_TOOTH_UPPER4
		770,	//	2	IDX_TOOTH_UPPER3
		769,	//	3	IDX_TOOTH_UPPER2
		770,	//	4	IDX_TOOTH_UPPER1
		751,	//	5	IDX_TOOTH_LOWER6
		738,	//	6	IDX_TOOTH_LOWER5
		732,	//	7	IDX_TOOTH_LOWER4
		727,	//	8	IDX_TOOTH_LOWER3
		728,	//	9	IDX_TOOTH_LOWER2
		725,	//	10	IDX_TOOTH_LOWER1
		817,	//	11	IDX_EYE_BALL
		817,	//	12	IDX_EYE
		581,	//	13	IDX_HAND
		542,	//	14	IDX_ARM
		16,	//	15	IDX_BODY3
		305,	//	16	IDX_BODY2
		588,	//	17	IDX_HEAD
		510,	//	18	IDX_EAR
		519,	//	19	IDX_HEAD_SCREW3
		547,	//	20	IDX_HEAD_SCREW2
		580,	//	21	IDX_HEAD_SCREW1
		230,	//	22	IDX_BODY1
		0,	//	23	IDX_YDOB
		826,	//	24	IDX_FINGER4_4
		818,	//	25	IDX_FINGER4_3
		775,	//	26	IDX_FINGER4_2
		718,	//	27	IDX_FINGER4_1
		795,	//	28	IDX_FINGER3_4
		794,	//	29	IDX_FINGER3_3
		771,	//	30	IDX_FINGER3_2
		717,	//	31	IDX_FINGER3_1
		718,	//	32	IDX_FINGER2_4
		722,	//	33	IDX_FINGER2_3
		728,	//	34	IDX_FINGER2_2
		684,	//	35	IDX_FINGER2_1
		593,	//	36	IDX_FINGER1_3
		617,	//	37	IDX_FINGER1_2
		647,	//	38	IDX_FINGER1_1
		246,	//	39	IDX_MRA
		282,	//	40	IDX_DNAH
		22,	//	41	IDX_ECAF
		162,	//	42	IDX_THOOT_LOWER6
		162,	//	43	IDX_THOOT_LOWER5
		164,	//	44	IDX_THOOT_LOWER4
		183,	//	45	IDX_THOOT_LOWER3
		169,	//	46	IDX_THOOT_LOWER2
		166,	//	47	IDX_THOOT_LOWER1
		177,	//	48	IDX_THOOT_UPPER6
		178,	//	49	IDX_THOOT_UPPER5
		177,	//	50	IDX_THOOT_UPPER4
		198,	//	51	IDX_THOOT_UPPER3
		193,	//	52	IDX_THOOT_UPPER2
		191,	//	53	IDX_THOOT_UPPER1
		140,	//	54	IDX_THUOM
		99,	//	55	IDX_DIL2
		102,	//	56	IDX_DIL1
		621	//	57	IDX_PALM
	};
	public static final int y_offs_mouth_inside = 789;	//	0	IDX_MOUTH_INSIDE
	public static final int y_offs_tooth_upper4 = 776;	//	1	IDX_TOOTH_UPPER4
	public static final int y_offs_tooth_upper3 = 770;	//	2	IDX_TOOTH_UPPER3
	public static final int y_offs_tooth_upper2 = 769;	//	3	IDX_TOOTH_UPPER2
	public static final int y_offs_tooth_upper1 = 770;	//	4	IDX_TOOTH_UPPER1
	public static final int y_offs_tooth_lower6 = 751;	//	5	IDX_TOOTH_LOWER6
	public static final int y_offs_tooth_lower5 = 738;	//	6	IDX_TOOTH_LOWER5
	public static final int y_offs_tooth_lower4 = 732;	//	7	IDX_TOOTH_LOWER4
	public static final int y_offs_tooth_lower3 = 727;	//	8	IDX_TOOTH_LOWER3
	public static final int y_offs_tooth_lower2 = 728;	//	9	IDX_TOOTH_LOWER2
	public static final int y_offs_tooth_lower1 = 725;	//	10	IDX_TOOTH_LOWER1
	public static final int y_offs_eye_ball = 817;	//	11	IDX_EYE_BALL
	public static final int y_offs_eye = 817;	//	12	IDX_EYE
	public static final int y_offs_hand = 581;	//	13	IDX_HAND
	public static final int y_offs_arm = 542;	//	14	IDX_ARM
	public static final int y_offs_body3 = 16;	//	15	IDX_BODY3
	public static final int y_offs_body2 = 305;	//	16	IDX_BODY2
	public static final int y_offs_head = 588;	//	17	IDX_HEAD
	public static final int y_offs_ear = 510;	//	18	IDX_EAR
	public static final int y_offs_head_screw3 = 519;	//	19	IDX_HEAD_SCREW3
	public static final int y_offs_head_screw2 = 547;	//	20	IDX_HEAD_SCREW2
	public static final int y_offs_head_screw1 = 580;	//	21	IDX_HEAD_SCREW1
	public static final int y_offs_body1 = 230;	//	22	IDX_BODY1
	public static final int y_offs_ydob = 0;	//	23	IDX_YDOB
	public static final int y_offs_finger4_4 = 826;	//	24	IDX_FINGER4_4
	public static final int y_offs_finger4_3 = 818;	//	25	IDX_FINGER4_3
	public static final int y_offs_finger4_2 = 775;	//	26	IDX_FINGER4_2
	public static final int y_offs_finger4_1 = 718;	//	27	IDX_FINGER4_1
	public static final int y_offs_finger3_4 = 795;	//	28	IDX_FINGER3_4
	public static final int y_offs_finger3_3 = 794;	//	29	IDX_FINGER3_3
	public static final int y_offs_finger3_2 = 771;	//	30	IDX_FINGER3_2
	public static final int y_offs_finger3_1 = 717;	//	31	IDX_FINGER3_1
	public static final int y_offs_finger2_4 = 718;	//	32	IDX_FINGER2_4
	public static final int y_offs_finger2_3 = 722;	//	33	IDX_FINGER2_3
	public static final int y_offs_finger2_2 = 728;	//	34	IDX_FINGER2_2
	public static final int y_offs_finger2_1 = 684;	//	35	IDX_FINGER2_1
	public static final int y_offs_finger1_3 = 593;	//	36	IDX_FINGER1_3
	public static final int y_offs_finger1_2 = 617;	//	37	IDX_FINGER1_2
	public static final int y_offs_finger1_1 = 647;	//	38	IDX_FINGER1_1
	public static final int y_offs_mra = 246;	//	39	IDX_MRA
	public static final int y_offs_dnah = 282;	//	40	IDX_DNAH
	public static final int y_offs_ecaf = 22;	//	41	IDX_ECAF
	public static final int y_offs_thoot_lower6 = 162;	//	42	IDX_THOOT_LOWER6
	public static final int y_offs_thoot_lower5 = 162;	//	43	IDX_THOOT_LOWER5
	public static final int y_offs_thoot_lower4 = 164;	//	44	IDX_THOOT_LOWER4
	public static final int y_offs_thoot_lower3 = 183;	//	45	IDX_THOOT_LOWER3
	public static final int y_offs_thoot_lower2 = 169;	//	46	IDX_THOOT_LOWER2
	public static final int y_offs_thoot_lower1 = 166;	//	47	IDX_THOOT_LOWER1
	public static final int y_offs_thoot_upper6 = 177;	//	48	IDX_THOOT_UPPER6
	public static final int y_offs_thoot_upper5 = 178;	//	49	IDX_THOOT_UPPER5
	public static final int y_offs_thoot_upper4 = 177;	//	50	IDX_THOOT_UPPER4
	public static final int y_offs_thoot_upper3 = 198;	//	51	IDX_THOOT_UPPER3
	public static final int y_offs_thoot_upper2 = 193;	//	52	IDX_THOOT_UPPER2
	public static final int y_offs_thoot_upper1 = 191;	//	53	IDX_THOOT_UPPER1
	public static final int y_offs_thuom = 140;	//	54	IDX_THUOM
	public static final int y_offs_dil2 = 99;	//	55	IDX_DIL2
	public static final int y_offs_dil1 = 102;	//	56	IDX_DIL1
	public static final int y_offs_palm = 621;	//	57	IDX_PALM
	public TextureAtlas atlas;

	public Tobor_33Atlas() {
		atlas = new TextureAtlas(Gdx.files.internal("tobor/Tobor-33.atlas"));

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
		img_mouth_inside = images[IDX_MOUTH_INSIDE];
		img_tooth_upper4 = images[IDX_TOOTH_UPPER4];
		img_tooth_upper3 = images[IDX_TOOTH_UPPER3];
		img_tooth_upper2 = images[IDX_TOOTH_UPPER2];
		img_tooth_upper1 = images[IDX_TOOTH_UPPER1];
		img_tooth_lower6 = images[IDX_TOOTH_LOWER6];
		img_tooth_lower5 = images[IDX_TOOTH_LOWER5];
		img_tooth_lower4 = images[IDX_TOOTH_LOWER4];
		img_tooth_lower3 = images[IDX_TOOTH_LOWER3];
		img_tooth_lower2 = images[IDX_TOOTH_LOWER2];
		img_tooth_lower1 = images[IDX_TOOTH_LOWER1];
		img_eye_ball = images[IDX_EYE_BALL];
		img_eye = images[IDX_EYE];
		img_hand = images[IDX_HAND];
		img_arm = images[IDX_ARM];
		img_body3 = images[IDX_BODY3];
		img_body2 = images[IDX_BODY2];
		img_head = images[IDX_HEAD];
		img_ear = images[IDX_EAR];
		img_head_screw3 = images[IDX_HEAD_SCREW3];
		img_head_screw2 = images[IDX_HEAD_SCREW2];
		img_head_screw1 = images[IDX_HEAD_SCREW1];
		img_body1 = images[IDX_BODY1];
		img_ydob = images[IDX_YDOB];
		img_finger4_4 = images[IDX_FINGER4_4];
		img_finger4_3 = images[IDX_FINGER4_3];
		img_finger4_2 = images[IDX_FINGER4_2];
		img_finger4_1 = images[IDX_FINGER4_1];
		img_finger3_4 = images[IDX_FINGER3_4];
		img_finger3_3 = images[IDX_FINGER3_3];
		img_finger3_2 = images[IDX_FINGER3_2];
		img_finger3_1 = images[IDX_FINGER3_1];
		img_finger2_4 = images[IDX_FINGER2_4];
		img_finger2_3 = images[IDX_FINGER2_3];
		img_finger2_2 = images[IDX_FINGER2_2];
		img_finger2_1 = images[IDX_FINGER2_1];
		img_finger1_3 = images[IDX_FINGER1_3];
		img_finger1_2 = images[IDX_FINGER1_2];
		img_finger1_1 = images[IDX_FINGER1_1];
		img_mra = images[IDX_MRA];
		img_dnah = images[IDX_DNAH];
		img_ecaf = images[IDX_ECAF];
		img_thoot_lower6 = images[IDX_THOOT_LOWER6];
		img_thoot_lower5 = images[IDX_THOOT_LOWER5];
		img_thoot_lower4 = images[IDX_THOOT_LOWER4];
		img_thoot_lower3 = images[IDX_THOOT_LOWER3];
		img_thoot_lower2 = images[IDX_THOOT_LOWER2];
		img_thoot_lower1 = images[IDX_THOOT_LOWER1];
		img_thoot_upper6 = images[IDX_THOOT_UPPER6];
		img_thoot_upper5 = images[IDX_THOOT_UPPER5];
		img_thoot_upper4 = images[IDX_THOOT_UPPER4];
		img_thoot_upper3 = images[IDX_THOOT_UPPER3];
		img_thoot_upper2 = images[IDX_THOOT_UPPER2];
		img_thoot_upper1 = images[IDX_THOOT_UPPER1];
		img_thuom = images[IDX_THUOM];
		img_dil2 = images[IDX_DIL2];
		img_dil1 = images[IDX_DIL1];
		img_palm = images[IDX_PALM];
	}
}
