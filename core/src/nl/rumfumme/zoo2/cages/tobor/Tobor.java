package nl.rumfumme.zoo2.cages.tobor;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Affine2;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

import nl.rumfumme.zoo2.Cage;
import nl.rumfumme.zoo2.Zoo;

import static nl.rumfumme.util.Math.*;
import static nl.rumfumme.util.Random.*;
import static nl.rumfumme.util.Constants.*;
import static nl.rumfumme.zoo2.cages.tobor.Tobor_33Atlas.*;

public class Tobor extends Cage {
    private Tobor_33Atlas atlas;
    private SpriteBatch batch;

    private static final int[] pathTooth = {
            540,    CANVAS_HEIGHT - 141,    542,    CANVAS_HEIGHT - 116,
            519,    CANVAS_HEIGHT - 148,    527,    CANVAS_HEIGHT - 108,
            496,    CANVAS_HEIGHT - 148,    509,    CANVAS_HEIGHT - 101,
            462,    CANVAS_HEIGHT - 144,    489,    CANVAS_HEIGHT - 95,
            555,    CANVAS_HEIGHT - 131,    576,    CANVAS_HEIGHT - 161,
            544,    CANVAS_HEIGHT - 134,    557,    CANVAS_HEIGHT - 177,
            527,    CANVAS_HEIGHT - 139,    535,    CANVAS_HEIGHT - 186,
            508,    CANVAS_HEIGHT - 142,    508,    CANVAS_HEIGHT - 194,
            491,    CANVAS_HEIGHT - 144,    485,    CANVAS_HEIGHT - 192,
            466,    CANVAS_HEIGHT - 142,    461,    CANVAS_HEIGHT - 194,
    };

    private static final int[] pathThoot = {
            554,    CANVAS_HEIGHT - 734,    554,    CANVAS_HEIGHT - 756,
            543,    CANVAS_HEIGHT - 734,    540,    CANVAS_HEIGHT - 756,
            531,    CANVAS_HEIGHT - 733,    528,    CANVAS_HEIGHT - 755,
            510,    CANVAS_HEIGHT - 715,    511,    CANVAS_HEIGHT - 731,
            497,    CANVAS_HEIGHT - 718,    497,    CANVAS_HEIGHT - 740,
            485,    CANVAS_HEIGHT - 720,    483,    CANVAS_HEIGHT - 749,
            550,    CANVAS_HEIGHT - 738,    551,    CANVAS_HEIGHT - 709,
            540,    CANVAS_HEIGHT - 736,    541,    CANVAS_HEIGHT - 712,
            527,    CANVAS_HEIGHT - 736,    527,    CANVAS_HEIGHT - 716,
            509,    CANVAS_HEIGHT - 717,    509,    CANVAS_HEIGHT - 700,
            491,    CANVAS_HEIGHT - 721,    492,    CANVAS_HEIGHT - 694,
            480,    CANVAS_HEIGHT - 724,    481,    CANVAS_HEIGHT - 696,
    };

    private static final int[] pathArmH = {
            392, CANVAS_HEIGHT - 357, 301, CANVAS_HEIGHT - 334
    };

    private static final int[] pathArmV = {
            392, CANVAS_HEIGHT - 357, 349, CANVAS_HEIGHT - 571
    };

    private static final float[] minMaxArmR = {
            -25.0f, 25.0f
    };
    
    private static final float[] minMaxEye = {
            -20.0f, -25.0f, 50.0f, 25.0f
    };

    private static final int xRotCenterArm = 470;
    private static final int yRotCenterArm = CANVAS_HEIGHT - 332;

    private static final int radiusHand = 148 / 2;
    private static final int xCenterHand = 114 + radiusHand;
    private static final int yCenterHand = CANVAS_HEIGHT - (333 - radiusHand);

    private static final int xCenterYdob = 440;
    private static final int yCenterYdob = CANVAS_HEIGHT - 760;

    private static final int xCenterEcaf = 507;
    private static final int yCenterEcaf = CANVAS_HEIGHT - 773;
    
    private static final int xRotCenterMra = 523;
    private static final int yRotCenterMra = CANVAS_HEIGHT - 732;

    private static final int xRotCenterDnah = 729;
    private static final int yRotCenterDnah = CANVAS_HEIGHT - 519;

    // [joint][finger]
    private static final float[][] rotFingerMin = { { 0.0f, 0.0f, 0.0f, 0.0f },
            { -30.0f, -50.0f, -30.0f, -50.0f },
            { -30.0f, -40.0f, -55.0f, -60.0f },
            { 0.0f, 0.0f, 0.0f, 0.0f }
        };
    private static final float[][] rotFingerMax = { { 0.0f, 0.0f, 0.0f, 0.0f },
            { 70.0f, 50.0f, 70.0f, 50.0f },
            { 20.0f, 40.0f, 30.0f, 40.0f },
            { 0.0f, 0.0f, 0.0f, 0.0f }
        };

    private static final float[][] xRotCenterFingerMin = {
            { xCenterHand, xCenterHand, xCenterHand, xCenterHand },
            { 72.0f, 99.0f, 160.0f, 219.0f },
            { 34.0f, 66.0f, 135.0f, 187.0f },
            { 0.0f, 32.0f, 98.0f, 152.0f }
        };
    private static final float[][] xRotCenterFingerMax = {
            { xCenterHand, xCenterHand, xCenterHand, xCenterHand },
            { 69.0f, 123.0f, 184.0f, 244.0f },
            { 28.0f, 68.0f, 144.0f, 198.0f },
            { 0.0f, 32.0f, 98.0f, 152.0f }
        };

    private static final float[][] yRotCenterFingerMin = {
            { yCenterHand, yCenterHand, yCenterHand, yCenterHand },
            { CANVAS_HEIGHT - 263.0f, CANVAS_HEIGHT - 180.0f, CANVAS_HEIGHT - 135.0f, CANVAS_HEIGHT - 138.0f },
            { CANVAS_HEIGHT - 283.0f, CANVAS_HEIGHT - 169.0f, CANVAS_HEIGHT - 113.0f, CANVAS_HEIGHT - 95.0f },
            { CANVAS_HEIGHT - 0.0f, CANVAS_HEIGHT - 174.0f, CANVAS_HEIGHT - 106.0f, CANVAS_HEIGHT - 80.0f }
        };
    private static final float[][] yRotCenterFingerMax = {
            { yCenterHand, yCenterHand, yCenterHand, yCenterHand },
            { CANVAS_HEIGHT - 243.0f, CANVAS_HEIGHT - 168.0f, CANVAS_HEIGHT - 136.0f, CANVAS_HEIGHT - 138.0f },
            { CANVAS_HEIGHT - 271.0f, CANVAS_HEIGHT - 144.0f, CANVAS_HEIGHT - 88.0f, CANVAS_HEIGHT - 66.0f },
            { CANVAS_HEIGHT - 0.0f, CANVAS_HEIGHT - 174.0f, CANVAS_HEIGHT - 106.0f, CANVAS_HEIGHT - 80.0f }
        };
    
    private static final float[] rotJoint1Offs = { 102.0f, 64.0f, 20.0f, 0.0f };

    private boolean doAnimateWorld = true;
    private boolean doAnimateBody = true;
    private boolean doAnimateScrews = true;
    private boolean doAnimateHead = true;
    private boolean doAnimateEye = true;
    private boolean doAnimateTeeth = true;
    private boolean doAnimateArm = true;
    private boolean doAnimatePalm = true;
    private boolean doAnimateFingers = true;
    private boolean doAnimateJoints = true;
    private boolean doAnimateYdob = true;
    private boolean doAnimateEcaf = true;
    private boolean doAnimateDils = true;
    private boolean doAnimateTheet = true;
    private boolean doAnimateMra = true;
    private boolean doAnimateDnah = true;

    private boolean isStopPalm = false;
    private boolean[] isMoveDil = { false, false };
    private boolean isMoveArmH = false;
    private boolean isMoveArmV = false;
    private boolean iaRotateArm = false;
    private boolean isMoveJoints = false;
    private boolean isStopEye = false;

    private int framesPalm = 0;
    private int[] framesDil = { 0, 0 };
    private int framesArmH = 0;
    private int framesArmV = 0;
    private int framesArmR = 0;
    private int framesEye = 0;

    private float[] incDil = { 0.0f, 0.0f };
    private float incPalm = 0.0f;
    private float incArmH = 0.0f;
    private float incArmV = 0.0f;
    private float incArmR = 0.0f;
    private float incFinger[] = { 1.0f, 1.0f, 1.0f, 1.0f };
    private float incEye[] = { 0.0f, 0.0f };

    private float posEye[] = { 0.0f, 0.0f };

    private float rotWorld = 0.0f;
    private float rotPalm = 0.0f;
    private float rotBody = 0.0f;
    private float rotHead = 0.0f;
    private float[] rotDil = { 0.0f, 0.0f };

    private float[] phaseScrew = { 0.0f, 0.0f, 0.0f };
    private float[] phaseTooth = { 0.25f, 0.25f, 0.25f, 0.25f, 0.25f, 0.25f, 0.25f, 0.25f, 0.25f, 0.25f };
    private float[] phaseThoot = { 0.25f, 0.25f, 0.25f, 0.25f, 0.25f, 0.25f, 0.25f, 0.25f, 0.25f, 0.25f, 0.25f, 0.25f };
    private float phaseArmH = 0.0f;
    private float phaseArmV = 0.0f;
    private float phaseArmR = 0.5f;
    private float phaseJoint[] = { 0.5f, 0.5f, 0.5f, 0.5f };
    private float phaseYdob = 0.0f;
    private float phaseEcaf = 0.0f;
    private float phaseMra = 0.0f;
    private float phaseDnah = 0.0f;
    
    abstract class JointMover {
        protected static final float stepMin = 0.005f;
        protected static final float stepMax = 0.05f;
        
        protected float phase;
        protected float step;

        abstract void init();
        abstract void move();

        boolean finished() {
            return phase >= 1.0f;
        }
    }
    
    private JointMover[] jointMovers = {
            new JointMover() {
                float sign;
                float range;

                void init() {
                    phase = 0.0f;
                    step = random(stepMin, stepMax);
                    sign = (random(1.0f) > 0.5f) ? 1.0f : -1.0f;
                    range = (random(1.0f) > 0.5f) ? PI : TWO_PI;
                }

                void move() {
                    for(int finger = 0; finger < 4; finger++) {
                        phaseJoint[finger] = (sign * sin(phase * range) + 1.0f) / 2.0f;
                    }
                    
                    phase += step;
                }
            },
            new JointMover() {
                int finger;
                float sign;
                float range;

                void init() {
                    finger = (int) random(0.0f, 4.0f);
                    phase = 0.0f;
                    step = random(stepMin, stepMax);
                    sign = (random(1.0f) > 0.5f) ? 1.0f : -1.0f;
                    range = (random(1.0f) > 0.5f) ? PI : TWO_PI;
                }

                void move() {
                    phaseJoint[finger] = (sign * sin(phase * range) + 1.0f) / 2.0f;
                    phase += step;
                }
            },
            new JointMover() {
                float sign;
                float range;
                float gap;
                boolean backwards;
                
                void init() {
                    phase = 0.0f;
                    step = random(stepMin, stepMax) / 3.0f;
                    sign = (random(1.0f) > 0.5f) ? 1.0f : -1.0f;
                    range = (random(1.0f) > 0.5f) ? PI : TWO_PI;
                    gap = random(0.05f);
                    backwards = (random(1.0f) > 0.5f) ? true : false;
                }
                
                float fn(float x) {
                    float y;
                    
                    if(x >= 1.0f / 3.0f && x < 2.0f / 3.0f) {
                        y = (sign * sin((x * 3.0f - 1.0f) * range) + 1.0f) / 2.0f;
                    } else {
                        y = 0.5f;
                    }
                    
                    return y;
                }

                void move() {
                    for(int finger = 0; finger < 4; finger++) {
                        int idx = backwards ? 3 - finger : finger;
                        phaseJoint[idx] = fn(phase + finger * gap);
                    }
                    
                    phase += step;
                }
            }
    };
    
    private JointMover jointMover;
    

    private float squareF(float x, int n) {
        float r = 0.0f;

        for(int i = 1; i <= n; i++) {
            float m = 2.0f * (float)i - 1.0f;

            r += 1.0f / m * sin(m * x);
        }

        r *= 4.0f / PI;

        return r;
    }

    private void randPalm() {
        framesPalm = (int)random(10.0f, isStopPalm ? 50.0f : 100.0f);
        incPalm = random(-2.0f, 20.0f) * (random(1.0f) >= 0.5f ? -1.0f : 1.0f);

        for(int i = 0; i < 4; i++) {
            incFinger[i] = Math.signum(incFinger[i]) * incPalm / 10.0f;
        }
    }

    private void randDil() {
        for(int i = 0; i < 2; i++) {
            framesDil[i] = (int)random(5.0f, 40.0f);
            incDil[i] = random(-20.0f, 20.0f);
        }
    }

    private void randArmH() {
        framesArmH = (int)random(3.0f, 50.0f);
        
        if(random(1.0f) < 0.5f) {
            incArmH = random(-0.07f, 0.07f);
        } else {
            incArmH = 0.0f;
        }
    }

    private void randArmV() {
        framesArmV = (int)random(3.0f, 30.0f);

        if(random(1.0f) < 0.5f) {
            incArmV = random(-0.07f, 0.07f);
        } else {
            incArmV = 0.0f;
        }
    }

    private void randArmR() {
        framesArmR = (int)random(3.0f, 30.0f);

        if(random(1.0f) < 0.5f) {
            incArmR = random(-0.05f, 0.05f);
        } else {
            incArmR = 0.0f;
        }
    }
    
    private void randEye() {
        framesEye = (int)random(1.0f, 10.0f);

        if(random(1.0f) < 0.5f) {
            incEye[0] = random(-3.0f, 3.0f);
            incEye[1] = random(-3.0f, 3.0f);
        } else {
            incEye[0] = 0.0f;
            incEye[1] = 0.0f;
        }

    }
    
    private void randJoint() {
        for(int finger = 0; finger < 4; finger ++) {
            phaseJoint[finger] = 0.5f;
        }
        
        jointMover = jointMovers[(int) random(0.0f, jointMovers.length)];
        
        jointMover.init();
    }

    public Tobor(Zoo zoo) {
        super(zoo);

        batch = zoo.batch;
        atlas = new Tobor_33Atlas();

        float m = CANVAS_HEIGHT * sqrt(2.0f);

        setWorldSize(false, m, m);
    }

    @Override
    public void show() {
        randPalm();
        randDil();
        randArmH();
        randArmV();
        randArmR();
        randEye();
    }

    private Affine2 m = new Affine2();
    
    private Vector2 positionHead() {
        return new Vector2(0.0f, -(sin(rotHead * DEG_TO_RAD) + 1.0f) * 5.0f);
    }
    
    private Vector2 positionTooth(int i) {
        int j = i - IDX_TOOTH_UPPER4;
        int t = j * 4;
        float x1 = lerp(pathTooth[t],
                pathTooth[t + 2],
                (sin((1.0f - phaseTooth[j]) * TWO_PI) + 1.0f) / 2.0f)
                - pathTooth[t];
        float y1 = lerp(pathTooth[t + 1],
                pathTooth[t + 3],
                (sin((1.0f - phaseTooth[j]) * TWO_PI) + 1.0f) / 2.0f)
                - (pathTooth[t + 1]);
        
        return new Vector2(x1, y1);
    }
    
    private Vector2 positionThoot(int i) {
        int j = i - IDX_THOOT_LOWER6;
        int t = j * 4;
        float x3 = lerp(pathThoot[t],
                pathThoot[t + 2],
                (sin((1.0f - phaseThoot[j]) * TWO_PI) + 1.0f) / 2.0f)
                - pathThoot[t];
        float y3 = lerp(pathThoot[t + 1],
                pathThoot[t + 3],
                (sin((1.0f - phaseThoot[j]) * TWO_PI) + 1.0f) / 2.0f)
                - (pathThoot[t + 1]);

        return new Vector2(x3, y3).rotateDeg(rotationEcaf());
    }
    
    private Vector2 positionBody() {
        return new Vector2((sin(rotBody * DEG_TO_RAD) + 1.0f) * 10.0f, 0.0f);
    }
    
    private Vector2 positionArm() {
        float offsXArm = lerp(pathArmV[0], pathArmV[2], phaseArmV) - pathArmV[0]
                - (lerp(pathArmH[0], pathArmH[2], phaseArmH) - pathArmH[0]);
        float offsYArm = lerp(pathArmV[1], pathArmV[3], phaseArmV) - (pathArmV[1])
                - (lerp(pathArmH[1], pathArmH[3], phaseArmH) - (pathArmH[1]));
        
        return new Vector2(offsXArm, offsYArm);
    }

    private float rotationScrew(int i) {
        return phaseScrew[i - IDX_HEAD_SCREW3] * 360.f;
    }

    private float rotationYdob() {
        return sin(phaseYdob * TWO_PI) * 30.0f;
    }
    
    private float rotationEcaf() {
        return sin(phaseEcaf * TWO_PI) * 30.0f;
    }
    
    private float rotationMra() {
        return pow(sin(phaseMra * TWO_PI), 3.0f) * 35.0f;
    }

    private float rotationDnah() {
        return sin(phaseDnah * TWO_PI) * 25.0f;
    }

    @Override
    public void render(float delta) {
        camera.update();
        ScreenUtils.clear(1.0f, 1.0f, 1.0f, 1.0f);

        float sq = squareF(rotWorld, 30);
        float rot = 0.0f;

        if(doAnimateWorld) {
            rot = sq * sq * sq * 90.0f + 270.0f;
        }

        Matrix4 cam = camera.combined
                .translate(3.0f * CANVAS_HEIGHT / 4.0f,
                        3.0f * CANVAS_WIDTH / 4.0f,
                        0.0f)
                .rotate(new Vector3(0.0f, 0.0f, 1.0f), -rot)
                .translate(-CANVAS_HEIGHT / 2.0f,
                        -CANVAS_HEIGHT / 2.0f,
                        0.0f);

        batch.setProjectionMatrix(cam);
        batch.begin();

        float rotArm = lerp(minMaxArmR[0], minMaxArmR[1], phaseArmR);

        for(int i = 0; i < NUM_IMAGES; ++i) {
            boolean skipDraw = false;

            switch(i) {
                case IDX_HEAD:
                case IDX_EYE_BALL:
                case IDX_MOUTH_INSIDE: {
                    m = m.idt()
                            .translate(new Vector2(x_offs[i], y_offs[i]).add(positionHead()));
                    
                    break;
                }

                case IDX_HEAD_SCREW1:
                case IDX_HEAD_SCREW2:
                case IDX_HEAD_SCREW3: {
                    float x1 = atlas.images[i].getRegionWidth() / 2.0f;
                    float y1 = atlas.images[i].getRegionHeight() / 2.0f;

                    m = m.idt()
                            .translate(x1, y1).rotate(rotationScrew(i)).translate(-x1, -y1)
                            .preTranslate(x_offs[i], y_offs[i]);

                    break;
                }
                
                case IDX_EYE: {
                    m = m.idt()
                            .translate(new Vector2(x_offs[i], y_offs[i]).add(posEye[0], posEye[1]).add(positionHead()));

                    break;
                }

                case IDX_PALM: {
                    float x1 = xRotCenterArm - x_offs[i];
                    float y1 = yRotCenterArm - y_offs[i];
                    float x2 = atlas.images[i].getRegionWidth() / 2.0f;
                    float y2 = atlas.images[i].getRegionHeight() / 2.0f;

                    m = m.idt()
                            .translate(x1, y1).rotate(rotArm).translate(-x1, -y1)
                            .translate(x2, y2).rotate(rotPalm).translate(-x2, -y2)
                            .preTranslate(new Vector2(x_offs[i], y_offs[i]).add(positionArm()));

                    break;
                }

                case IDX_ARM: {
                    float x1 = xRotCenterArm - x_offs[i];
                    float y1 = yRotCenterArm - y_offs[i];

                    m = m.idt()
                            .translate(x1, y1).rotate(rotArm).translate(-x1, -y1)
                            .preTranslate(new Vector2(x_offs[i], y_offs[i]).add(positionArm()));

                    break;
                }

                case IDX_HAND: {
                    float x1 = xRotCenterArm - x_offs[i];
                    float y1 = yRotCenterArm - y_offs[i];

                    m = m.idt()
                            .translate(x1, y1).rotate(rotArm).translate(-x1, -y1)
                            .preTranslate(new Vector2(x_offs[i], y_offs[i]).add(positionArm()));

                    break;
                }

                case IDX_FINGER1_1:
                case IDX_FINGER1_2:
                case IDX_FINGER1_3:
                case IDX_FINGER2_1:
                case IDX_FINGER2_2:
                case IDX_FINGER2_3:
                case IDX_FINGER2_4:
                case IDX_FINGER3_1:
                case IDX_FINGER3_2:
                case IDX_FINGER3_3:
                case IDX_FINGER3_4:
                case IDX_FINGER4_1:
                case IDX_FINGER4_2:
                case IDX_FINGER4_3:
                case IDX_FINGER4_4: {
                    int finger = 0;
                    int joint = 0;

                    switch(i) {
                        case IDX_FINGER1_1:
                        case IDX_FINGER1_2:
                        case IDX_FINGER1_3:
                            finger = 0;
                            
                            break;

                        case IDX_FINGER2_1:
                        case IDX_FINGER2_2:
                        case IDX_FINGER2_3:
                        case IDX_FINGER2_4:
                            finger = 1;
                            
                            break;

                        case IDX_FINGER3_1:
                        case IDX_FINGER3_2:
                        case IDX_FINGER3_3:
                        case IDX_FINGER3_4:
                            finger = 2;
                            
                            break;

                        case IDX_FINGER4_1:
                        case IDX_FINGER4_2:
                        case IDX_FINGER4_3:
                        case IDX_FINGER4_4:
                            finger = 3;
                    
                            break;
                    }

                    switch(i) {
                        case IDX_FINGER1_1:
                        case IDX_FINGER2_1:
                        case IDX_FINGER3_1:
                        case IDX_FINGER4_1:
                            joint = 0;
                            
                            break;

                        case IDX_FINGER1_2:
                        case IDX_FINGER2_2:
                        case IDX_FINGER3_2:
                        case IDX_FINGER4_2:
                            joint = 1;
                            
                            break;

                        case IDX_FINGER1_3:
                        case IDX_FINGER2_3:
                        case IDX_FINGER3_3:
                        case IDX_FINGER4_3:
                            joint = 2;
                            
                            break;

                        case IDX_FINGER2_4:
                        case IDX_FINGER3_4:
                        case IDX_FINGER4_4:
                            joint = 3;
                    
                            break;
                    }

                    float x1 = xRotCenterArm - x_offs[i];
                    float y1 = yRotCenterArm - y_offs[i];

                    m = m.idt()
                            .translate(x1, y1).rotate(rotArm).translate(-x1, -y1);
                    
                    for(int j = 0; j <= joint; j++) {
                        float x2 = lerp(xRotCenterFingerMin[j][finger], xRotCenterFingerMax[j][finger], phaseJoint[finger]) - x_offs[i];
                        float y2 = lerp(yRotCenterFingerMin[j][finger], yRotCenterFingerMax[j][finger], phaseJoint[finger])- y_offs[i];
                        float a = lerp(rotFingerMin[j][finger], rotFingerMax[j][finger], phaseJoint[finger]);

                        m.translate(x2, y2).rotate(a).translate(-x2, -y2);
                    }
                            
                    m.preTranslate(new Vector2(x_offs[i], y_offs[i]).add(positionArm()));
                    
                    break;
                }

                case IDX_BODY1: {
                    m = m.idt()
                            .translate(new Vector2(x_offs[i], y_offs[i]).add(positionBody()));
                    
                    break;
                }

                case IDX_TOOTH_LOWER1:
                case IDX_TOOTH_LOWER2:
                case IDX_TOOTH_LOWER3:
                case IDX_TOOTH_LOWER4:
                case IDX_TOOTH_LOWER5:
                case IDX_TOOTH_LOWER6:
                case IDX_TOOTH_UPPER1:
                case IDX_TOOTH_UPPER2:
                case IDX_TOOTH_UPPER3:
                case IDX_TOOTH_UPPER4: {
                    m = m.idt()
                            .translate(new Vector2(x_offs[i], y_offs[i]).add(positionTooth(i)).add(positionHead()));
                    
                    break;
                }

                case IDX_YDOB: {
                    float x1 = xCenterYdob - x_offs[IDX_YDOB];
                    float y1 = yCenterYdob - y_offs[IDX_YDOB];

                    m = m.idt()
                            .translate(x1, y1).rotate(rotationYdob()).translate(-x1, -y1)
                            .preTranslate(x_offs[i], y_offs[i]);
                    
                    break;
                }

                case IDX_ECAF: {
                    float x1 = xCenterYdob - x_offs[i];
                    float y1 = yCenterYdob - y_offs[i];
                    float x2 = xCenterEcaf - x_offs[i];
                    float y2 = yCenterEcaf - y_offs[i];

                    m = m.idt()
                            .translate(x1, y1).rotate(rotationYdob()).translate(-x1, -y1)
                            .translate(x2, y2).rotate(rotationEcaf()).translate(-x2, -y2)
                            .preTranslate(x_offs[i], y_offs[i]);

                    break;
                }

                case IDX_DIL1:
                case IDX_DIL2: {
                    float x1 = xCenterYdob - x_offs[i];
                    float y1 = yCenterYdob - y_offs[i];
                    float x2 = xCenterEcaf - x_offs[i];
                    float y2 = yCenterEcaf - y_offs[i];
                    float x3 = atlas.images[i].getRegionWidth() / 2.0f;
                    float y3 = atlas.images[i].getRegionHeight() / 2.0f;

                    m = m.idt()
                            .translate(x1, y1).rotate(rotationYdob()).translate(-x1, -y1)
                            .translate(x2, y2).rotate(rotationEcaf()).translate(-x2, -y2)
                            .translate(x3, y3).rotate(rotDil[i - IDX_DIL2]).translate(-x3, -y3)
                            .preTranslate(x_offs[i], y_offs[i]);

                    break;
                }

                case IDX_THOOT_LOWER1:
                case IDX_THOOT_LOWER2:
                case IDX_THOOT_LOWER3:
                case IDX_THOOT_LOWER4:
                case IDX_THOOT_LOWER5:
                case IDX_THOOT_LOWER6:
                case IDX_THOOT_UPPER1:
                case IDX_THOOT_UPPER2:
                case IDX_THOOT_UPPER3:
                case IDX_THOOT_UPPER4:
                case IDX_THOOT_UPPER5:
                case IDX_THOOT_UPPER6: {
                    float x1 = xCenterYdob - x_offs[i];
                    float y1 = yCenterYdob - y_offs[i];
                    float x2 = xCenterEcaf - x_offs[i];
                    float y2 = yCenterEcaf - y_offs[i];

                    m = m.idt()
                            .translate(x1, y1).rotate(rotationYdob()).translate(-x1, -y1)
                            .translate(x2, y2).rotate(rotationEcaf()).translate(-x2, -y2)
                            .preTranslate(positionThoot(i).add(x_offs[i], y_offs[i]))
                            ;

                    break;
                }

                case IDX_THUOM: {
                    float x1 = xCenterYdob - x_offs[i];
                    float y1 = yCenterYdob - y_offs[i];
                    float x2 = xCenterEcaf - x_offs[i];
                    float y2 = yCenterEcaf - y_offs[i];

                    m = m.idt()
                            .translate(x1, y1).rotate(rotationYdob()).translate(-x1, -y1)
                            .translate(x2, y2).rotate(rotationEcaf()).translate(-x2, -y2)
                            .preTranslate(x_offs[i], y_offs[i]);

                    break;
                }

                case IDX_MRA: {
                    float x1 = xCenterYdob - x_offs[i];
                    float y1 = yCenterYdob - y_offs[i];
                    float x2 = xCenterEcaf - x_offs[i];
                    float y2 = yCenterEcaf - y_offs[i];
                    float x3 = xRotCenterMra - x_offs[i];
                    float y3 = yRotCenterMra - y_offs[i];

                    m = m.idt()
                            .translate(x1, y1).rotate(rotationYdob()).translate(-x1, -y1)
                            .translate(x2, y2).rotate(rotationEcaf()).translate(-x2, -y2)
                            .translate(x3, y3).rotate(rotationMra()).translate(-x3, -y3)
                            .preTranslate(x_offs[i], y_offs[i]);

                    break;
                }

                case IDX_DNAH: {
                    float x1 = xCenterYdob - x_offs[i];
                    float y1 = yCenterYdob - y_offs[i];
                    float x2 = xCenterEcaf - x_offs[i];
                    float y2 = yCenterEcaf - y_offs[i];
                    float x3 = xRotCenterMra - x_offs[i];
                    float y3 = yRotCenterMra - y_offs[i];
                    float x4 = xRotCenterDnah - x_offs[i];
                    float y4 = yRotCenterDnah - y_offs[i];

                    m = m.idt()
                            .translate(x1, y1).rotate(rotationYdob()).translate(-x1, -y1)
                            .translate(x2, y2).rotate(rotationEcaf()).translate(-x2, -y2)
                            .translate(x3, y3).rotate(rotationMra()).translate(-x3, -y3)
                            .translate(x4, y4).rotate(rotationDnah()).translate(-x4, -y4)
                            .preTranslate(x_offs[i], y_offs[i]);

                    break;
                }

                default: {
                    m = m.idt()
                            .preTranslate(x_offs[i], y_offs[i]);

                    break;
                }
            }

            if(!skipDraw) {
                batch.draw(atlas.images[i], atlas.images[i].getRegionWidth(), atlas.images[i].getRegionHeight(), m);
            }
        }

        // world
        if(doAnimateWorld) {
            rotWorld = (rotWorld + TWO_PI / random(1800.0f, 3600.0f)) % 360.0f;
        }

        // screws
        if(doAnimateScrews) {
            for(int i = 0; i < 3; i++) {
                phaseScrew[i] = phaseScrew[i] + random(0.008f);
            }
        }

        // palm
        if(doAnimatePalm) {
            if(!isStopPalm) {
                rotPalm = (rotPalm + incPalm) % 360.0f;
            }

            if(--framesPalm == 0) {
                isStopPalm = !isStopPalm;
                randPalm();
            }
        }

        // arm
        if(doAnimateArm) {
            if(isMoveArmH) {
                phaseArmH += incArmH;

                if(phaseArmH < 0.0f) {
                    phaseArmH = 0.0f;
                    isMoveArmH = false;
                    randArmH();
                } else if(phaseArmH > 1.0f) {
                    phaseArmH = 1.0f;
                    isMoveArmH = false;
                    randArmH();
                }
            }

            if(--framesArmH == 0) {
                isMoveArmH = true;
                randArmH();
            }

            if(isMoveArmV) {
                phaseArmV += incArmV;

                if(phaseArmV < 0.0f) {
                    phaseArmV = 0.0f;
                    isMoveArmV = false;
                    randArmV();
                } else if(phaseArmV > 1.0f) {
                    phaseArmV = 1.0f;
                    isMoveArmV = false;
                    randArmV();
                }
            }

            if(--framesArmV == 0) {
                isMoveArmV = true;
                randArmV();
            }

            if(iaRotateArm) {
                phaseArmR += incArmR;

                if(phaseArmR < 0.0f) {
                    phaseArmR = 0.0f;
                    iaRotateArm = false;
                    randArmR();
                } else if(phaseArmR > 1.0f) {
                    phaseArmR = 1.0f;
                    iaRotateArm = false;
                    randArmR();
                }
            }

            if(--framesArmR == 0) {
                iaRotateArm = true;
                randArmR();
            }
        }

        // body
        if(doAnimateBody) {
            rotBody = (rotBody + random(0.1f * RAD_TO_DEG)) % 360.0f;
        }

        // head
        if(doAnimateHead) {
            rotHead = (rotHead + random(0.1f * RAD_TO_DEG)) % 360.0f;
        }
        
        if(doAnimateEye) {
            if(!isStopEye) {
                float x = posEye[0] + incEye[0];
                float y = posEye[1] + incEye[1];

                if(x < minMaxEye[0] || x > minMaxEye[2] || y < minMaxEye[1] || y > minMaxEye[3]) {
                    isStopEye = true;
                } else {
                    posEye[0] = x;
                    posEye[1] = y;
                }
            }
            
            if(--framesEye == 0) {
                isStopEye = false;
                randEye();
            }
        }

        // teeth
        if(doAnimateTeeth) {
            for(int i = 0; i < 10; i++) {
                phaseTooth[i] = (phaseTooth[i] + random(0.02f)) % 1.0f;
            }
        }

        // theet
        if(doAnimateTheet) {
            for(int i = 0; i < 12; i++) {
                phaseThoot[i] = (phaseThoot[i] + random(0.05f)) % 1.0f;
            }
        }

        // dil
        if(doAnimateDils) {
            for(int i = 0; i < 2; i++) {
                if(!isMoveDil[i]) {
                    if(--framesDil[i] == 0) {
                        isMoveDil[i] = true;
                        randDil();
                    }
                } else {
                    float r = rotDil[i] + incDil[i];

                    if(r <= 0.0f || r >= 360.0f) {
                        rotDil[i] = 0.0f;
                        isMoveDil[i] = false;
                        randDil();
                    } else if((rotDil[i] <= 180.0f && r >= 180.0f)
                            || (rotDil[i] >= -180.0f && r <= -180.0f)) {
                        rotDil[i] = 180.0f;
                        isMoveDil[i] = false;
                        randDil();
                    } else {
                        rotDil[i] = r;
                    }
                }
            }
        }

        // finger
        if(doAnimateFingers) {
            if(!isStopPalm) {
                for(int finger = 0; finger < 4; finger++) {
                    rotFingerMin[0][finger] += incFinger[finger];

                    if(rotFingerMin[0][finger] + rotJoint1Offs[finger] > 180.0f) {
                        rotFingerMin[0][finger] = 180.0f - rotJoint1Offs[finger];
                        incFinger[finger] *= -1.0f;
                    } else if(rotFingerMin[0][finger] + rotJoint1Offs[finger] < 0.0f) {
                        rotFingerMin[0][finger] = -rotJoint1Offs[finger];
                        incFinger[finger] *= -1.0f;
                    }
                    
                    rotFingerMax[0][finger] = rotFingerMin[0][finger];
                }
            }
        }
        
        // ydob
        if(doAnimateYdob) {
            phaseYdob = (phaseYdob + random(0.001f, 0.005f)) % 1.0f;
        }

        // ecaf
        if(doAnimateEcaf) {
            phaseEcaf = (phaseEcaf + random(0.005f, 0.01f)) % 1.0f;
        }

        // mra
        if(doAnimateMra) {
            phaseMra = (phaseMra + random(0.005f, 0.01f)) % 1.0f;
        }
        
        // dnah
        if(doAnimateDnah) {
            phaseDnah = (phaseDnah + random(0.025f, 0.05f)) % 1.0f;
        }
        
        // joints
        if(doAnimateJoints) {
            if(isMoveJoints) {
                jointMover.move();
                
                if(jointMover.finished()) {
                    for(int finger = 0; finger < 4; finger ++) {
                        phaseJoint[finger] = 0.5f;
                    }

                    isMoveJoints = false;
                }
            } else if(random(1.0f) < 0.01f) {
                randJoint();
                
                isMoveJoints = true;
            }
        }

        batch.end();
    }
}
