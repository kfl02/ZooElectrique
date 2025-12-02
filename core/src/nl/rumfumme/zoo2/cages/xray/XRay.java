package nl.rumfumme.zoo2.cages.xray;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Affine2;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

import nl.rumfumme.verlet.Entity;
import nl.rumfumme.verlet.Line;
import nl.rumfumme.verlet.Point;
import nl.rumfumme.verlet.RectangleConstraint;
import nl.rumfumme.zoo2.Cage;
import nl.rumfumme.zoo2.Zoo;
import space.earlygrey.shapedrawer.ShapeDrawer;

import static nl.rumfumme.util.Graphics.*;
import static nl.rumfumme.util.Math.*;
import static nl.rumfumme.util.Random.*;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class XRay extends Cage {
    private TextureAtlas atlas;
    private ShapeDrawer shape;
    private TextureRegion region;
    private Texture texture;

    private Stack<Affine2> matrixStack = new Stack<Affine2>();

    private final AtlasRegion[] images = new AtlasRegion[28];
    private static final int IDX_HAMATUM = 0; // 0
    private static final int IDX_CAPITATUM = IDX_HAMATUM + 1; // 1
    private static final int IDX_TRAPEZOIDEUM = IDX_CAPITATUM + 1; // 2
    private static final int IDX_TRAPEZIUM = IDX_TRAPEZOIDEUM + 1; // 3
    private static final int IDX_TRIQUETRUM_PISIFORME = IDX_TRAPEZIUM + 1; // 4
    private static final int IDX_LUNATUM = IDX_TRIQUETRUM_PISIFORME + 1; // 5
    private static final int IDX_SCAPHOIDEUM = IDX_LUNATUM + 1; // 6
    private static final int IDX_ULNA = IDX_SCAPHOIDEUM + 1; // 7
    private static final int IDX_RADIUS = IDX_ULNA + 1; // 8
    private static final int IDX_METACARPALE_PRIMUM = IDX_RADIUS + 1; // 9
    private static final int IDX_METACARPALE_SECUNDUM = IDX_METACARPALE_PRIMUM + 1; // 10
    private static final int IDX_METACARPALE_TERTIUM = IDX_METACARPALE_SECUNDUM + 1; // 11
    private static final int IDX_METACARPALE_QUARTUM = IDX_METACARPALE_TERTIUM + 1; // 12
    private static final int IDX_METACARPALE_QUINTUM = IDX_METACARPALE_QUARTUM + 1; // 13
    private static final int IDX_POLLEX_PROXIMALIS = IDX_METACARPALE_QUINTUM + 1; // 14
    private static final int IDX_POLLEX_DISTALIS = IDX_POLLEX_PROXIMALIS + 1; // 15
    private static final int IDX_INDEX_PROXIMALIS = IDX_POLLEX_DISTALIS + 1; // 16
    private static final int IDX_INDEX_MEDIA = IDX_INDEX_PROXIMALIS + 1; // 17
    private static final int IDX_INDEX_DISTALIS = IDX_INDEX_MEDIA + 1; // 18
    private static final int IDX_MEDIUS_PROXIMALIS = IDX_INDEX_DISTALIS + 1; // 19
    private static final int IDX_MEDIUS_MEDIA = IDX_MEDIUS_PROXIMALIS + 1; // 20
    private static final int IDX_MEDIUS_DISTALIS = IDX_MEDIUS_MEDIA + 1; // 21
    private static final int IDX_ANNULARIUS_PROXIMALIS = IDX_MEDIUS_DISTALIS + 1; // 22
    private static final int IDX_ANNULARIUS_MEDIA = IDX_ANNULARIUS_PROXIMALIS + 1; // 23
    private static final int IDX_ANNULARIUS_DISTALIS = IDX_ANNULARIUS_MEDIA + 1; // 24
    private static final int IDX_MINIMUS_PROXIMALIS = IDX_ANNULARIUS_DISTALIS + 1; // 25
    private static final int IDX_MINIMUS_MEDIA = IDX_MINIMUS_PROXIMALIS + 1; // 26
    private static final int IDX_MINIMUS_DISTALIS = IDX_MINIMUS_MEDIA + 1; // 27
    @SuppressWarnings("unused")
	private AtlasRegion img_hamatum;
    @SuppressWarnings("unused")
    private AtlasRegion img_capitatum;
    @SuppressWarnings("unused")
    private AtlasRegion img_trapezoideum;
    @SuppressWarnings("unused")
    private AtlasRegion img_trapezium;
    @SuppressWarnings("unused")
    private AtlasRegion img_triquetrum_pisiforme;
    @SuppressWarnings("unused")
    private AtlasRegion img_lunatum;
    @SuppressWarnings("unused")
    private AtlasRegion img_scaphoideum;
    @SuppressWarnings("unused")
    private AtlasRegion img_ulna;
    @SuppressWarnings("unused")
    private AtlasRegion img_radius;
    @SuppressWarnings("unused")
    private AtlasRegion img_metacarpale_primum;
    @SuppressWarnings("unused")
    private AtlasRegion img_metacarpale_secundum;
    @SuppressWarnings("unused")
    private AtlasRegion img_metacarpale_tertium;
    @SuppressWarnings("unused")
    private AtlasRegion img_metacarpale_quartum;
    @SuppressWarnings("unused")
    private AtlasRegion img_metacarpale_quintum;
    @SuppressWarnings("unused")
    private AtlasRegion img_pollex_proximalis;
    @SuppressWarnings("unused")
    private AtlasRegion img_pollex_distalis;
    @SuppressWarnings("unused")
    private AtlasRegion img_index_proximalis;
    @SuppressWarnings("unused")
    private AtlasRegion img_index_media;
    @SuppressWarnings("unused")
    private AtlasRegion img_index_distalis;
    @SuppressWarnings("unused")
    private AtlasRegion img_medius_proximalis;
    @SuppressWarnings("unused")
    private AtlasRegion img_medius_media;
    @SuppressWarnings("unused")
    private AtlasRegion img_medius_distalis;
    @SuppressWarnings("unused")
    private AtlasRegion img_annularius_proximalis;
    @SuppressWarnings("unused")
    private AtlasRegion img_annularius_media;
    @SuppressWarnings("unused")
    private AtlasRegion img_annularius_distalis;
    @SuppressWarnings("unused")
    private AtlasRegion img_minimus_proximalis;
    @SuppressWarnings("unused")
    private AtlasRegion img_minimus_media;
    @SuppressWarnings("unused")
    private AtlasRegion img_minimus_distalis;
    private final String[] image_names = new String[] {
            "hamatum", // 0
            "capitatum", // 1
            "trapezoideum", // 2
            "trapezium", // 3
            "triquetrum_pisiforme", // 4
            "lunatum", // 5
            "scaphoideum", // 6
            "ulna", // 7
            "radius", // 8
            "metacarpale_primum", // 9
            "metacarpale_secundum", // 10
            "metacarpale_tertium", // 11
            "metacarpale_quartum", // 12
            "metacarpale_quintum", // 13
            "pollex_proximalis", // 14
            "pollex_distalis", // 15
            "index_proximalis", // 16
            "index_media", // 17
            "index_distalis", // 18
            "medius_proximalis", // 19
            "medius_media", // 20
            "medius_distalis", // 21
            "annularius_proximalis", // 22
            "annularius_media", // 23
            "annularius_distalis", // 24
            "minimus_proximalis", // 25
            "minimus_media", // 26
            "minimus_distalis" // 27
    };
    private final int[] x_offs = {
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
    private final int[] y_offs = {
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

    Point points[];
    Point points_u[];
    Point points_d[];
    Set<Line> lines = new HashSet<Line>();
    Number[][] line_defs = {
            { IDX_RADIUS, IDX_ULNA },
            { IDX_RADIUS, IDX_SCAPHOIDEUM },
            { IDX_RADIUS, IDX_LUNATUM },
            { IDX_RADIUS, IDX_TRIQUETRUM_PISIFORME },
            { IDX_RADIUS, IDX_TRAPEZIUM },
            { IDX_RADIUS, IDX_TRAPEZOIDEUM },
            { IDX_RADIUS, IDX_CAPITATUM },
            { IDX_RADIUS, IDX_HAMATUM },
            { IDX_ULNA, IDX_SCAPHOIDEUM },
            { IDX_ULNA, IDX_LUNATUM },
            { IDX_ULNA, IDX_TRIQUETRUM_PISIFORME },
            { IDX_ULNA, IDX_TRAPEZIUM },
            { IDX_ULNA, IDX_TRAPEZOIDEUM },
            { IDX_ULNA, IDX_CAPITATUM },
            { IDX_ULNA, IDX_HAMATUM },
            { IDX_SCAPHOIDEUM, IDX_LUNATUM },
            { IDX_SCAPHOIDEUM, IDX_TRIQUETRUM_PISIFORME },
            { IDX_SCAPHOIDEUM, IDX_TRAPEZIUM },
            { IDX_SCAPHOIDEUM, IDX_TRAPEZOIDEUM },
            { IDX_SCAPHOIDEUM, IDX_CAPITATUM },
            { IDX_SCAPHOIDEUM, IDX_HAMATUM },
            { IDX_LUNATUM, IDX_TRIQUETRUM_PISIFORME },
            { IDX_LUNATUM, IDX_TRAPEZIUM },
            { IDX_LUNATUM, IDX_TRAPEZOIDEUM },
            { IDX_LUNATUM, IDX_CAPITATUM },
            { IDX_LUNATUM, IDX_HAMATUM },
            { IDX_TRIQUETRUM_PISIFORME, IDX_TRAPEZIUM },
            { IDX_TRIQUETRUM_PISIFORME, IDX_TRAPEZOIDEUM },
            { IDX_TRIQUETRUM_PISIFORME, IDX_CAPITATUM },
            { IDX_TRIQUETRUM_PISIFORME, IDX_HAMATUM },
            { IDX_TRAPEZIUM, IDX_TRAPEZOIDEUM },
            { IDX_TRAPEZIUM, IDX_CAPITATUM },
            { IDX_TRAPEZIUM, IDX_HAMATUM },
            { IDX_TRAPEZOIDEUM, IDX_CAPITATUM },
            { IDX_TRAPEZOIDEUM, IDX_HAMATUM },
            { IDX_CAPITATUM, IDX_HAMATUM },
            { IDX_TRAPEZIUM, IDX_METACARPALE_PRIMUM },
            { IDX_TRAPEZOIDEUM, IDX_METACARPALE_SECUNDUM },
            { IDX_CAPITATUM, IDX_METACARPALE_TERTIUM },
            { IDX_HAMATUM, IDX_METACARPALE_QUARTUM },
            { IDX_HAMATUM, IDX_METACARPALE_QUINTUM },
            { IDX_METACARPALE_PRIMUM, IDX_METACARPALE_SECUNDUM },
            { IDX_METACARPALE_SECUNDUM, IDX_METACARPALE_TERTIUM },
            { IDX_METACARPALE_TERTIUM, IDX_METACARPALE_QUARTUM },
            { IDX_METACARPALE_QUARTUM, IDX_METACARPALE_QUINTUM },
            { IDX_METACARPALE_PRIMUM, IDX_POLLEX_PROXIMALIS },
            { IDX_METACARPALE_SECUNDUM, IDX_INDEX_PROXIMALIS },
            { IDX_METACARPALE_TERTIUM, IDX_MEDIUS_PROXIMALIS },
            { IDX_METACARPALE_QUARTUM, IDX_ANNULARIUS_PROXIMALIS },
            { IDX_METACARPALE_QUINTUM, IDX_MINIMUS_PROXIMALIS },
            { IDX_POLLEX_PROXIMALIS, IDX_POLLEX_DISTALIS },
            { IDX_INDEX_PROXIMALIS, IDX_INDEX_MEDIA },
            { IDX_MEDIUS_PROXIMALIS, IDX_MEDIUS_MEDIA },
            { IDX_ANNULARIUS_PROXIMALIS, IDX_ANNULARIUS_MEDIA },
            { IDX_MINIMUS_PROXIMALIS, IDX_MINIMUS_MEDIA },
            { IDX_INDEX_MEDIA, IDX_INDEX_DISTALIS },
            { IDX_MEDIUS_MEDIA, IDX_MEDIUS_DISTALIS },
            { IDX_ANNULARIUS_MEDIA, IDX_ANNULARIUS_DISTALIS },
            { IDX_MINIMUS_MEDIA, IDX_MINIMUS_DISTALIS },
            { IDX_POLLEX_PROXIMALIS, IDX_INDEX_PROXIMALIS, 1f },
            { IDX_INDEX_PROXIMALIS, IDX_MEDIUS_PROXIMALIS, 1f },
            { IDX_MEDIUS_PROXIMALIS, IDX_ANNULARIUS_PROXIMALIS, 1f },
            { IDX_ANNULARIUS_PROXIMALIS, IDX_MINIMUS_PROXIMALIS, 1f },
            { IDX_POLLEX_DISTALIS, IDX_INDEX_MEDIA, 1f },
            { IDX_INDEX_MEDIA, IDX_MEDIUS_MEDIA, 1f },
            { IDX_MEDIUS_MEDIA, IDX_ANNULARIUS_MEDIA, 1f },
            { IDX_ANNULARIUS_MEDIA, IDX_MINIMUS_MEDIA, 1f },
            { IDX_POLLEX_DISTALIS, IDX_INDEX_DISTALIS, 1f },
            { IDX_INDEX_DISTALIS, IDX_MEDIUS_DISTALIS, 1f },
            { IDX_MEDIUS_DISTALIS, IDX_ANNULARIUS_DISTALIS, 1f },
            { IDX_ANNULARIUS_DISTALIS, IDX_MINIMUS_DISTALIS, 1f } };

    Entity ent_xray;
    
    long due_time_stamp;
    boolean show_lines = false;

    private Affine2 push(Affine2 m) {
        matrixStack.push(m);
        return new Affine2(m);
    }
    
    private Affine2 pop() {
        return matrixStack.pop();
    }

    public XRay(Zoo zoo) {
        super(zoo);

        atlas = new TextureAtlas(Gdx.files.internal("xray/xray.atlas"));

        for (int i = 0; i < 28; i++) {
            images[i] = findAtlasRegionFlipped(atlas, image_names[i]);
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

        Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
        
        pixmap.setColor(Color.WHITE);
        pixmap.drawPixel(0, 0);

        texture = new Texture(pixmap); //remember to dispose of later
        
        pixmap.dispose();
        
        region = new TextureRegion(texture, 0, 0, 1, 1);
        shape = new ShapeDrawer(zoo.batch, region);

        setWorldSize(1000, 1000);
    }
    
    private long millis() {
        return TimeUtils.millis();
    }

    private void setDueTimestamp() {
        due_time_stamp = millis() + (long)random(7000, 10000);
    }

    private void shake() {
        float dx = random(-100f, 100f);
        float dy = random(-100f, 100f);

        for (int i = 0; i < points.length; i++) {
            points[i].oldPos.x = points[i].pos.x + dx;
            points[i].oldPos.y = points[i].pos.y + dy;
            points_u[i].oldPos.x = points_u[i].pos.x + dx;
            points_u[i].oldPos.y = points_u[i].pos.y + dy;
            points_d[i].oldPos.x = points_d[i].pos.x + dx;
            points_d[i].oldPos.y = points_d[i].pos.y + dy;
        }
    }

    @Override
    public void show() {
        points = new Point[images.length];
        points_u = new Point[images.length];
        points_d = new Point[images.length];
        lines = new HashSet<Line>();

        Vector2 g = new Vector2(0.0f, -0.9f);
        Vector2 minus_g = new Vector2(-g.x, -g.y);

        float xmin = Float.MAX_VALUE;
        float xmax = Float.MIN_VALUE;
        float ymin = Float.MAX_VALUE;
        float ymax = Float.MIN_VALUE;

        for (int i = 0; i < images.length; i++) {
            xmin = min(x_offs[i], xmin);
            xmax = max(x_offs[i] + images[i].getRegionWidth(), xmax);
            ymin = min(y_offs[i], ymin);
            ymax = max(y_offs[i] + images[i].getRegionHeight(), ymax);
        }

        float xoffs = -xmin + 1000 / 2.0f - (xmax - xmin) / 2.0f;
        float yoffs = -ymin + 1000 / 2.0f - (ymax - ymin) / 2.0f;

        for (int i = 0; i < images.length; i++) {
            points[i] = new Point(xoffs + x_offs[i] + images[i].getRegionWidth() / 2f,
                    yoffs + y_offs[i] + images[i].getRegionHeight() / 2f);
            points[i].gravity = g;
            points_u[i] = new Point(xoffs + x_offs[i] + images[i].getRegionWidth() / 2f, yoffs + y_offs[i]);
            points_u[i].gravity = g;
            points_d[i] = new Point(xoffs + x_offs[i] + images[i].getRegionWidth() / 2f,
                    yoffs + y_offs[i] + images[i].getRegionHeight());
            points_d[i].gravity = minus_g;

            points[i].radius = 5;
            points_u[i].radius = 5;
            points_d[i].radius = 5;

            if (i == IDX_RADIUS || i == IDX_ULNA) {
                points[i].fixed = true;
                points_u[i].fixed = true;
                points_d[i].fixed = true;
            }
        }

        for (int i = 0; i < line_defs.length; i++) {
            Line l;

            l = new Line(points[(int) line_defs[i][0]], points[(int) line_defs[i][1]]);
            if (line_defs[i].length > 2) {
                l.stiffness = (float) line_defs[i][2];
            }
            lines.add(l);

            l = new Line(points_u[(int) line_defs[i][0]], points_u[(int) line_defs[i][1]]);
            if (line_defs[i].length > 2) {
                l.stiffness = (float) line_defs[i][2];
            }
            lines.add(l);

            l = new Line(points_d[(int) line_defs[i][0]], points_d[(int) line_defs[i][1]]);
            if (line_defs[i].length > 2) {
                l.stiffness = (float) line_defs[i][2];
            }
            lines.add(l);

            l = new Line(points_u[(int) line_defs[i][0]], points[(int) line_defs[i][1]]);
            if (line_defs[i].length > 2) {
                l.stiffness = (float) line_defs[i][2];
            }
            lines.add(l);

            l = new Line(points_d[(int) line_defs[i][0]], points[(int) line_defs[i][1]]);
            if (line_defs[i].length > 2) {
                l.stiffness = (float) line_defs[i][2];
            }
            lines.add(l);

            l = new Line(points_d[(int) line_defs[i][0]], points_u[(int) line_defs[i][1]]);
            if (line_defs[i].length > 2) {
                l.stiffness = (float) line_defs[i][2];
            }
            lines.add(l);
        }

        for (int i = 0; i < images.length; i++) {
            Line l;

            l = new Line(points[i], points_u[i]);
            lines.add(l);

            l = new Line(points_d[i], points[i]);
            lines.add(l);

            l = new Line(points_d[i], points_u[i]);
            lines.add(l);
        }

        ent_xray = new Entity(new RectangleConstraint(0.0f, 0.0f, 1000.0f, 1000.0f));
        ent_xray.iterations = 40;

        ent_xray.addLines(lines);

        show_lines = false;
        
        shake();
        setDueTimestamp();
    }

    @Override
    public void render(float delta) {
        if(millis() - due_time_stamp > 0) {
            shake();
            setDueTimestamp();
        }

        Affine2 m = new Affine2();

        camera.update();
        ScreenUtils.clear(0.0f, 0.0f, 0.0f, 1.0f);
        zoo.batch.setProjectionMatrix(camera.combined);
        zoo.batch.begin();

        zoo.batch.setColor(192 / 255.0f, 192 / 255.0f, 0, 255 / 255.0f);

        for (int i = 0; i < images.length; i++) {
            float dx;
            float dy;
            float a;

            dx = points[i].pos.x - points_u[i].pos.x;
            dy = points[i].pos.y - points_u[i].pos.y;
            a = atan2(dx, dy);

            m = push(m);
            m.translate(points_u[i].pos.x - images[i].getRegionWidth() / 2.0f, points_u[i].pos.y);
            m.rotateRad(-a);
            drawAffine(zoo.batch, images[i], m);
            m = pop();
        }

        zoo.batch.setColor(Color.WHITE);
        
        if(show_lines) {
            for(Line l : lines) {
                shape.line(l.p1.pos.x, l.p1.pos.y, l.p2.pos.x, l.p2.pos.y);
            }
        }

        ent_xray.update();
        zoo.batch.end();
    }

    @Override
    public boolean keyDown(int keycode) {
        switch(keycode) {
            case Keys.SPACE:
                for (Line l : lines) {
                    l.stiffness = 0.01f;
                }

                shake();
                setDueTimestamp();
                return true;
                
            case Keys.MINUS:
                show_lines = !show_lines;
                return true;
        }
        return false;
    }
}
