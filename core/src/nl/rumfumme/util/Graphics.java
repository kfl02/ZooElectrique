package nl.rumfumme.util;

import static nl.rumfumme.util.Constants.*;
import static nl.rumfumme.util.Math.*;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Affine2;
import com.badlogic.gdx.math.ConvexHull;
import com.badlogic.gdx.utils.FloatArray;

import space.earlygrey.shapedrawer.ShapeDrawer;

public class Graphics {
    public static final int FLOATS_PER_VERTEX = 5;
    public static final int VX = 0;
    public static final int VY = 1;
    public static final int VC = 2;
    public static final int VU = 3;
    public static final int VV = 4;
    public static final int V1X = 0;
    public static final int V1Y = 1;
    public static final int V1C = 2;
    public static final int V1U = 3;
    public static final int V1V = 4;
    public static final int V2X = 5;
    public static final int V2Y = 6;
    public static final int V2C = 7;
    public static final int V2U = 8;
    public static final int V2V = 9;
    public static final int V3X = 10;
    public static final int V3Y = 11;
    public static final int V3C = 12;
    public static final int V3U = 13;
    public static final int V3V = 14;
    public static final int V4X = 15;
    public static final int V4Y = 16;
    public static final int V4C = 17;
    public static final int V4U = 18;
    public static final int V4V = 19;
    
    public static ShapeDrawer createShapeDrawer(SpriteBatch batch) {
        Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
        
        pixmap.setColor(Color.WHITE);
        pixmap.drawPixel(0, 0);

        Texture texture = new Texture(pixmap); //remember to dispose of later
        TextureRegion region = new TextureRegion(texture, 0, 0, 1, 1);
        
        pixmap.dispose();

        return new ShapeDrawer(batch, region);
    }

    public static Pixmap convexHullPixmap(Texture t, int x, int y, int w, int h) {
        TextureData textureData = t.getTextureData();
        
        if(!textureData.isPrepared()) {
            textureData.prepare();
        }

        Pixmap pm= new Pixmap(w, h, textureData.getFormat());
        
        pm.drawPixmap(textureData.consumePixmap(), 
                0, 0, 
                x, y, 
                w, h);
        
        pm.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        
        FloatArray fa = new FloatArray(pm.getHeight() * 4);

        for(int y1 = 0; y1 < pm.getHeight(); y1++) {
            int x1;
            int x2;

            for(x1 = 0; x1 < pm.getWidth(); x1++) {
                int p = pm.getPixel(x1, y1);
                
                if((p & 0xff) != 0) {
                    break;
                }
            }
            
            if(x1 == pm.getWidth()) {
                break;
            }
            
            for(x2 = pm.getWidth() - 1; x2 >= 0; x2--) {
                int p = pm.getPixel(x2, y1);
                
                if((p & 0xff) != 0) {
                    break;
                }
            }
            
            fa.add(x1, y1);
            fa.add(x2, y1);
        }
        
        ConvexHull cv = new ConvexHull();
        
        FloatArray fa2 = cv.computePolygon(fa, false);
        
        for(int i = 0; i < (fa2.size / 2) - 1; i++) {
            pm.drawLine((int)fa2.get(i * 2), (int)fa2.get(i * 2 + 1), (int)fa2.get(i * 2 + 2), (int)fa2.get(i * 2 + 3));
        }

        for(int y1 = 0; y1 < pm.getHeight(); y1++) {
            int x1;
            int x2;

            for(x1 = 0; x1 < pm.getWidth(); x1++) {
                int p = pm.getPixel(x1, y1);
                
                if((p & 0xff) != 0) {
                    break;
                }
            }
            
            if(x1 == pm.getWidth()) {
                break;
            }
            
            for(x2 = pm.getWidth() - 1; x2 >= 0; x2--) {
                int p = pm.getPixel(x2, y1);
                
                if((p & 0xff) != 0) {
                    break;
                }
            }
            
            pm.drawLine(x1, y1, x2, y1);
        }
        
        return pm;
    }

    public static Pixmap xyMaskPixmap(Texture t, int x, int y, int w, int h) {
        TextureData textureData = t.getTextureData();
        
        if(!textureData.isPrepared()) {
            textureData.prepare();
        }

        Pixmap pm= new Pixmap(w, h, textureData.getFormat());
        
        pm.drawPixmap(textureData.consumePixmap(), 
                0, 0, 
                x, y, 
                w, h);
        
        pm.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        
        for(int y0 = 0; y0 < pm.getHeight(); y0++) {
            int x1;
            int x2;

            for(x1 = 0; x1 < pm.getWidth(); x1++) {
                int p = pm.getPixel(x1, y0);
                
                if((p & 0xff) != 0) {
                    break;
                }
            }
            
            if(x1 == pm.getWidth()) {
                break;
            }
            
            for(x2 = pm.getWidth() - 1; x2 >= 0; x2--) {
                int p = pm.getPixel(x2, y0);
                
                if((p & 0xff) != 0) {
                    break;
                }
            }
            
            pm.drawLine(x1, y0, x2, y0);
        }
        
        for(int x0 = 0; x < pm.getWidth(); x0++) {
            int y1;
            int y2;

            for(y1 = 0; y1 < pm.getHeight(); y1++) {
                int p = pm.getPixel(x0, y1);
                
                if((p & 0xff) != 0) {
                    break;
                }
            }
            
            if(y1 == pm.getHeight()) {
                break;
            }
            
            for(y2 = pm.getHeight() - 1; y2 >= 0; y2--) {
                int p = pm.getPixel(x0, y2);
                
                if((p & 0xff) != 0) {
                    break;
                }
            }
            
            pm.drawLine(x0, y1, x0, y2);
        }
        
        return pm;
    }

    public static AtlasRegion findAtlasRegionFlipped(TextureAtlas atlas, String name) {
        AtlasRegion region = atlas.findRegion(name);
        region.flip(false, true);
 
        return region;
    }

    public static AtlasRegion findAtlasRegionFlipped(TextureAtlas atlas, String name, int index) {
        AtlasRegion region = atlas.findRegion(name, index);
        region.flip(false, true);
 
        return region;
    }
    
    public static void drawAffine(SpriteBatch batch, TextureRegion img, Affine2 m) {
        batch.draw(img, img.getRegionWidth(), img.getRegionHeight(), m);
    }

    public static void drawAffineXY(SpriteBatch batch, TextureRegion img, float x, float y, Affine2 m) {
        batch.draw(img, img.getRegionWidth(), img.getRegionHeight(), new Affine2(m).translate(x, y));
    }

    public static void drawScaled(SpriteBatch batch, TextureRegion img, float x, float y, float scaleX, float scaleY) {
        batch.draw(img, 
                x, y, 
                0.0f, 0.0f, 
                img.getRegionWidth(), img.getRegionHeight(), 
                scaleX, scaleY, 
                0.0f);
    }

    public static void drawRotatedCentered(SpriteBatch batch, TextureRegion img, float x, float y, float rot) {
        batch.draw(img, 
                x, y, 
                img.getRegionWidth() / 2.0f, img.getRegionHeight() / 2.0f, 
                img.getRegionWidth(), img.getRegionHeight(), 
                1.0f, 1.0f, 
                degrees(rot));
    }

    public static void drawRotated(SpriteBatch batch, TextureRegion img, float x, float y, float rot) {
        batch.draw(img, 
                x, y, 
                0.0f, 0.0f, 
                img.getRegionWidth(), img.getRegionHeight(), 
                1.0f, 1.0f, 
                degrees(rot));
    }

    public static void drawScaledRotated(SpriteBatch batch, TextureRegion img, float x, float y, float scaleX, float scaleY, float rot) {
        batch.draw(img, 
                x, y, 
                0.0f, 0.0f, 
                img.getRegionWidth(), img.getRegionHeight(), 
                scaleX, scaleY, 
                degrees(rot));
    }

    public static void drawScaledRotatedCentered(SpriteBatch batch, TextureRegion img, float x, float y, float scaleX, float scaleY, float rot) {
        batch.draw(img, 
                x, y, 
                img.getRegionWidth() / 2.0f, img.getRegionHeight() / 2.0f, 
                img.getRegionWidth(), img.getRegionHeight(), 
                scaleX, scaleY, 
                degrees(rot));
    }

    public static void drawFlipped(SpriteBatch batch, Texture img, float x, float y) {
        batch.draw(img, 
            x, y,
            (float)img.getWidth(), (float)img.getHeight(),
            0, 0,
            img.getWidth(), img.getHeight(),
            false, true);
    }

    public static void drawFlippedScaledRotated(SpriteBatch batch, Texture img, float x, float y, float scaleX, float scaleY, float rot) {
        batch.draw(img,
            x, y,
            0.0f, 0.0f,
            (float)img.getWidth(), (float)img.getHeight(),
            scaleX, scaleY,
            rot * RAD_TO_DEG,
            0, 0,
            img.getWidth(), img.getHeight(),
            false, true);
    }

    public static void drawFlippedScaled(SpriteBatch batch, Texture img, float x, float y, float scaleX, float scaleY) {
        batch.draw(img,
            x, y,
            0.0f, 0.0f,
            (float)img.getWidth(), (float)img.getHeight(),
            scaleX, scaleY,
            0.0f,
            0, 0,
            img.getWidth(), img.getHeight(),
            false, true);
    }

    public static void drawFlippedRotated(SpriteBatch batch, Texture img, float x, float y, float rot) {
        batch.draw(img,
            x, y,
            0.0f, 0.0f,
            (float)img.getWidth(), (float)img.getHeight(),
            1.0f, 1.0f,
            rot * RAD_TO_DEG,
            0, 0,
            img.getWidth(), img.getHeight(),
            false, true);
    }

    public static Color fromHSV(final float hue, final float saturation, final float value, final float alpha) {
        //vec4 K = vec4(1.0, 2.0 / 3.0, 1.0 / 3.0, 3.0);
        final float
            Kx=1f,
            Ky=2f/3f,
            Kz=1f/3f,
            Kw=3f;
        //vec3 p = abs(fract(c.xxx + K.xyz) * 6.0 - K.www);
        final float
            px=Math.abs(fract(hue+Kx)*6f-Kw),
            py=Math.abs(fract(hue+Ky)*6f-Kw),
            pz=Math.abs(fract(hue+Kz)*6f-Kw);
        //return c.z * mix(K.xxx, clamp(p - K.xxx, 0.0, 1.0), c.y);
        return new Color(
            value*mix(Kx,clamp(px-Kx,0f,1f),saturation),
            value*mix(Kx,clamp(py-Kx,0f,1f),saturation),
            value*mix(Kx,clamp(pz-Kz,0f,1f),saturation),
            alpha
        );
    }
    public static Color fromHSV(final float hue, final float saturation, final float value) {
        //vec4 K = vec4(1.0, 2.0 / 3.0, 1.0 / 3.0, 3.0);
        final float
            Kx=1f,
            Ky=2f/3f,
            Kz=1f/3f,
            Kw=3f;
        //vec3 p = abs(fract(c.xxx + K.xyz) * 6.0 - K.www);
        final float
            px=Math.abs(fract(hue+Kx)*6f-Kw),
            py=Math.abs(fract(hue+Ky)*6f-Kw),
            pz=Math.abs(fract(hue+Kz)*6f-Kw);
        //return c.z * mix(K.xxx, clamp(p - K.xxx, 0.0, 1.0), c.y);
        return new Color(
            value*mix(Kx,clamp(px-Kx,0f,1f),saturation),
            value*mix(Kx,clamp(py-Kx,0f,1f),saturation),
            value*mix(Kx,clamp(pz-Kz,0f,1f),saturation),
            1f
        );
    }
}
