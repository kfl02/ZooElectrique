package nl.rumfumme.zoo.xray;

import java.util.HashSet;
import java.util.Set;

import nl.rumfumme.pt.ProcessingApp;
import nl.rumfumme.verlet.Entity;
import nl.rumfumme.verlet.Line;
import nl.rumfumme.verlet.Point;
import nl.rumfumme.verlet.RectangleConstraint;
import nl.rumfumme.zoo.ZooApp;
import processing.core.PVector;
import static nl.rumfumme.zoo.xray.XrayImageLoader.*;

public class XrayApp extends ZooApp {
    XrayImageLoader img;
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
    
    int due_time_stamp;
    boolean show_lines = false;

    @Override
    public void load() {
        img = new XrayImageLoader(this);

        img.loadImages();
    }

    @Override
    public void init() {
        super.init();

        points = new Point[img.images.length];
        points_u = new Point[img.images.length];
        points_d = new Point[img.images.length];
        lines = new HashSet<Line>();

        PVector null_g = new PVector(0.0f, 0.0f);
        PVector g = new PVector(0.0f, -0.9f);
        PVector minus_g = new PVector(-g.x, -g.y);

        float xmin = Float.MAX_VALUE;
        float xmax = Float.MIN_VALUE;
        float ymin = Float.MAX_VALUE;
        float ymax = Float.MIN_VALUE;

        for (int i = 0; i < img.images.length; i++) {
            xmin = min(img.x_offs[i], xmin);
            xmax = max(img.x_offs[i] + img.images[i].width, xmax);
            ymin = min(img.y_offs[i], ymin);
            ymax = max(img.y_offs[i] + img.images[i].height, ymax);
        }

        float xoffs = -xmin + 1000 / 2.0f - (xmax - xmin) / 2.0f;
        float yoffs = -ymin + 1000 / 2.0f - (ymax - ymin) / 2.0f;

        for (int i = 0; i < img.images.length; i++) {
            points[i] = new Point(xoffs + img.x_offs[i] + img.images[i].width / 2f,
                    yoffs + img.y_offs[i] + img.images[i].height / 2f);
            points[i].gravity = g;
            points_u[i] = new Point(xoffs + img.x_offs[i] + img.images[i].width / 2f, yoffs + img.y_offs[i]);
            points_u[i].gravity = g;
            points_d[i] = new Point(xoffs + img.x_offs[i] + img.images[i].width / 2f,
                    yoffs + img.y_offs[i] + img.images[i].height);
            points_d[i].gravity = minus_g;

            points[i].radius = 5;
            points_u[i].radius = 5;
            points_d[i].radius = 5;
            points[i].draw = true;
            points_u[i].draw = true;
            points_d[i].draw = true;

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

        for (int i = 0; i < img.images.length; i++) {
            Line l;

            l = new Line(points[i], points_u[i]);
            lines.add(l);

            l = new Line(points_d[i], points[i]);
            lines.add(l);

            l = new Line(points_d[i], points_u[i]);
            lines.add(l);
        }

        ent_xray = new Entity(new RectangleConstraint(new PVector(0.0f, 0.0f), new PVector(1000.0f, 1000.0f)));

        ent_xray.addLines(lines);

        ent_xray.iterations = 40;
        
        show_lines = false;
        
        shake();
        set_due_time_stamp();
    }
    
    void set_due_time_stamp() {
        due_time_stamp = millis() + (int)random(7000, 10000);
    }

    @Override
    public void terminate() {
        // TODO Auto-generated method stub

    }

    @Override
    public void animate() {
        if(millis() - due_time_stamp > 0) {
            shake();
            set_due_time_stamp();
        }

        fitToScreen(1000, 1000);
        background(0);
        stroke(255);
        noFill();

        for (int i = 0; i < img.images.length; i++) {
            float dx;
            float dy;
            float a;

            dx = points[i].pos.x - points_u[i].pos.x;
            dy = points[i].pos.y - points_u[i].pos.y;
            a = atan2(dx, dy);

            push();
            tint(192, 192, 0, 255);
            translate(points_u[i].pos.x - img.images[i].width / 2.0f, points_u[i].pos.y);
            rotate(-a);
            image(img.images[i], 0, 0);
            pop();
        }
        
        if(show_lines) {
            push();
            stroke(255);

            for(Line l : lines) {
                line(l.p1.pos.x, l.p1.pos.y, l.p2.pos.x, l.p2.pos.y);
            }
            pop();
        }

        ent_xray.update();
    }
    
    void shake() {
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
    public void keyPressed() {
        switch(key) {
            case 'b':
                start_time = millis();

                for (Line l : lines) {
                    l.stiffness = 0.01f;
                }

                shake();
                set_due_time_stamp();
                break;
                
            case '-':
                show_lines = true;
                break;
        }
    }
}
