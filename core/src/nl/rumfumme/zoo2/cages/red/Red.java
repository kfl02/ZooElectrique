package nl.rumfumme.zoo2.cages.red;

import static nl.rumfumme.util.Constants.PI;
import static nl.rumfumme.util.Constants.TWO_PI;
import static nl.rumfumme.util.Math.constrain;
import static nl.rumfumme.util.Math.cos;
import static nl.rumfumme.util.Math.degrees;
import static nl.rumfumme.util.Math.min;
import static nl.rumfumme.util.Math.sin;
import static nl.rumfumme.util.Math.squareToQuad;
import static nl.rumfumme.util.Random.random;
import static nl.rumfumme.zoo2.cages.red.RedAtlas.height_back;
import static nl.rumfumme.zoo2.cages.red.RedAtlas.height_body;
import static nl.rumfumme.zoo2.cages.red.RedAtlas.height_tree;
import static nl.rumfumme.zoo2.cages.red.RedAtlas.width_back;
import static nl.rumfumme.zoo2.cages.red.RedAtlas.width_body;
import static nl.rumfumme.zoo2.cages.red.RedAtlas.width_tree;
import static nl.rumfumme.zoo2.cages.red.RedAtlas.x_offs_back;
import static nl.rumfumme.zoo2.cages.red.RedAtlas.x_offs_body;
import static nl.rumfumme.zoo2.cages.red.RedAtlas.x_offs_tree;
import static nl.rumfumme.zoo2.cages.red.RedAtlas.y_offs_back;
import static nl.rumfumme.zoo2.cages.red.RedAtlas.y_offs_body;
import static nl.rumfumme.zoo2.cages.red.RedAtlas.y_offs_tree;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL32;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Mesh.VertexDataType;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector4;
import com.badlogic.gdx.utils.ScreenUtils;

import nl.rumfumme.verlet.Point;
import nl.rumfumme.zoo2.Cage;
import nl.rumfumme.zoo2.Zoo;

public class Red extends Cage {
    private RedAtlas atlas;
    private SpriteBatch batch;
    private Mesh meshBack;
    private Mesh meshBlood;
    private ShaderProgram shaderBack;
    private int u_projTransBack;
    private int u_textureUVBack;
    private int u_textureBack;
    private ShaderProgram shaderBlood;
    private int u_projTransBlood;
    private int u_scaleBlood;
    private int u_mixBlood;
    private Vector2 gutter = new Vector2();
    private Vector2 screen = new Vector2();
    private Vector2 world = new Vector2();
    private Vector4 textureUV = new Vector4();
    
    private static final int D = 0; // number of divisions of the background image in both x and y direction
    private static final int MAX_BLOOD = 200; // maximum number of blood drops
    private static final int FLOATS_PER_VERTEX_BACK = 6;
    private static final int FLOATS_PER_VERTEX_BLOOD = 8;

    // variables to set up in constructor
    private short[] indicesBack; // mesh indices for triangle strip
    private float[] verticesBack;
    private float[] verticesBlood;

    // variables to set on show()
    private Polygon tree_quad; // the area the tree will be drawn into
    private float tree_rotation = 0.0f;
    private float tree_origin_swirl = 0.0f;
    private float tree_expand_left = 0.0f;
    private float tree_expand_right = 0.0f;
    private float tree_expand_top = 0.0f;
    private float rad_body = 0.0f;
    private float y_body = 0.0f;
    private float y_body_diff = 0.0f;
    private boolean gotcha; // tree caught the body
    private List<Point> blood; // blood drops

    public ShaderProgram createShader(String vertexFileName, String fragmentFileName) {
        ShaderProgram shader = new ShaderProgram(Gdx.files.internal(vertexFileName), Gdx.files.internal(fragmentFileName));
        if (!shader.isCompiled()) throw new IllegalArgumentException("Error compiling shader: " + shader.getLog());
        
        return shader;
    }

    public Red(Zoo zoo) {
        super(zoo);
        
        batch = zoo.batch;
        atlas = new RedAtlas();

        // initialize the background mesh vertices

        int num_vertices = (D + 2) * (D + 2);
        int num_indices = (D + 1) * (D + 2) * 2 + D * 2;

        verticesBack = new float[num_vertices * FLOATS_PER_VERTEX_BACK];
        indicesBack = new short[num_indices];

        textureUV.x = atlas.img_back.getU();
        textureUV.y = atlas.img_back.getV();
        textureUV.z = atlas.img_back.getU2();
        textureUV.w = atlas.img_back.getV2();
        
        float bu = atlas.img_back.getU();
        float bv = atlas.img_back.getV();
        float bw = atlas.img_back.getU2() - atlas.img_back.getU();
        float bh = atlas.img_back.getV2() - atlas.img_back.getV();

        for(int x = 0; x <= D + 1; x++) {
            for(int y = 0; y <= D + 1; y++) {
                int i = idx(x, y);
                float dx = (float)x / (float)(D + 1);
                float dy = (float)y / (float)(D + 1);

                verticesBack[i + 0] = x_offs_back + dx * width_back;
                verticesBack[i + 1] = y_offs_back + dy * height_back;
                verticesBack[i + 2] = 0.0f;
                verticesBack[i + 3] = 0.0f;
                verticesBack[i + 4] = bu + bw * dx;
                verticesBack[i + 5] = bv + bh * (1.0f - dy);
            }
        }
        
        // set up the triangle strip
        int idx = 0;

        for(int y = 0; y <= D; y++) {
            indicesBack[idx++] = (short) (y * (D + 2)); // lower left
            
            for(int x = 0; x <= D; x++) {
                indicesBack[idx++] = (short) ((y + 1) * (D + 2) + x); // upper left
                indicesBack[idx++] = (short) (y * (D + 2) + x + 1); // lower right
            }

            indicesBack[idx++] = (short) ((y + 2) * (D + 2) - 1); // upper right
            
            if(y < D) {
                // degenerate triangles
                indicesBack[idx++] = (short) ((y + 2) * (D + 2) - 1);
                indicesBack[idx++] = (short) ((y + 1) * (D + 2));
            }
        }

        // finish the mesh
        VertexDataType vertexDataType = VertexDataType.VertexBufferObjectWithVAO;

        meshBack = new Mesh(vertexDataType, false, num_vertices, num_indices,
                new VertexAttribute(Usage.Position, 4, ShaderProgram.POSITION_ATTRIBUTE),
                new VertexAttribute(Usage.TextureCoordinates, 2, ShaderProgram.TEXCOORD_ATTRIBUTE + "0")
                );

        meshBack.setVertices(verticesBack);
        meshBack.setIndices(indicesBack);

        verticesBlood = new float[MAX_BLOOD * FLOATS_PER_VERTEX_BLOOD];
        meshBlood = new Mesh(vertexDataType, false, MAX_BLOOD, 0,
                new VertexAttribute(Usage.Position, 4, ShaderProgram.POSITION_ATTRIBUTE),
                new VertexAttribute(Usage.Generic, 4, "a_grf")
                );

        meshBlood.setVertices(verticesBlood);

        // shader for background
        shaderBack = createShader("red/grid.vertex.glsl", "red/grid.fragment.glsl");
        u_projTransBack = shaderBack.getUniformLocation("u_projTrans");
        u_textureBack = shaderBack.getUniformLocation("u_texture");
        u_textureUVBack = shaderBack.getUniformLocation("u_textureUV");

        // shader for blood drops
        shaderBlood = createShader("red/point.vertex.glsl", "red/point.fragment.glsl");
        u_projTransBlood = shaderBlood.getUniformLocation("u_projTrans");
        u_scaleBlood = shaderBlood.getUniformLocation("u_scale");
        u_mixBlood = shaderBlood.getUniformLocation("u_mix");
 

        // set up camera
        setWorldSize(false, atlas.canvas_width, atlas.canvas_height);
    }
    
    @Override
    public void show() {
        // set up tree drawing area as a rectangle initially, will degenerate into a quad
        tree_quad = new Polygon(new float[] { 
                -width_tree / 2.0f, 0.0f, 
                width_tree / 2.0f, 0.0f, 
                width_tree / 2.0f, height_tree, 
                -width_tree / 2.0f, height_tree});
        
        tree_quad.setPosition(width_tree / 2.0f, y_offs_tree);

        // reset the other variables
        tree_rotation = 0.0f;
        tree_origin_swirl = 0.0f;
        tree_expand_left = 0.0f;
        tree_expand_right = 0.0f;
        tree_expand_top = 0.0f;
        rad_body = 0.0f;
        y_body = 0.0f;
        y_body_diff = 0.0f;
        gotcha = false;
        blood = new ArrayList<Point>(MAX_BLOOD);
    }
    
    // helper function for index into vertex array
    private int idx(int x, int y) {
        return (y * (D + 2) + x) * FLOATS_PER_VERTEX_BACK;
    }

    @Override
    public void render(float delta) {
        camera.update();
        ScreenUtils.clear(105.0f / 255.0f, 43.0f / 255.0f, 43.0f / 255.0f, 1.0f); // dark red
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        atlas.img_back.getTexture().bind();
        shaderBack.bind();
        shaderBack.setUniformMatrix(u_projTransBack, camera.combined);
        shaderBack.setUniformf(u_textureUVBack, textureUV);
        shaderBack.setUniformi(u_textureBack, 0);
        meshBack.render(shaderBack, GL20.GL_TRIANGLE_STRIP); // background mesh
        zoo.batch.getShader().bind();

        float[] quad = tree_quad.getVertices();

        if(!gotcha) {
            // let body float
            y_body = y_offs_body - sin(rad_body) * 20.0f + sin(tree_expand_top) * 40.0f + 10.0f;
            
            batch.draw(atlas.img_body, x_offs_body, y_body);
        } else {
            // attach body to tree
            y_body = quad[3 * 2 + 1] + y_body_diff;

            batch.draw(atlas.img_body, x_offs_body, y_body);

            // remove blood that ran out of screen
            blood.removeIf(p -> p.pos.y < 0.0f);

            // spawn new blood
            if (blood.size() < MAX_BLOOD) {
                int r = (int) min(random(0, MAX_BLOOD / 20), MAX_BLOOD - blood.size());

                for (int i = 0; i < r; i++) {
                    Point p = new Point(x_offs_body + width_body / 4.0f + random(0.0f, width_body / 2.0f),
                            y_body + height_body / 8.0f + random(0.0f, height_body / 4.0f) - 5.0f);
                    p.radius = random(2.0f, 8.0f);
                    p.gravity = new Vector2(random(-0.001f, 0.001f), random(-0.4f, -0.6f));

                    blood.add(p);
                }
            }

            {
                int i = 0;
                
                for(Point p : blood) {
                    verticesBlood[i * FLOATS_PER_VERTEX_BLOOD + 0] = p.pos.x;
                    verticesBlood[i * FLOATS_PER_VERTEX_BLOOD + 1] = p.pos.y;
                    verticesBlood[i * FLOATS_PER_VERTEX_BLOOD + 2] = p.oldPos.x;
                    verticesBlood[i * FLOATS_PER_VERTEX_BLOOD + 3] = p.oldPos.y;
                    verticesBlood[i * FLOATS_PER_VERTEX_BLOOD + 4] = p.gravity.x;
                    verticesBlood[i * FLOATS_PER_VERTEX_BLOOD + 5] = p.gravity.y;
                    verticesBlood[i * FLOATS_PER_VERTEX_BLOOD + 6] = p.radius;
                    verticesBlood[i * FLOATS_PER_VERTEX_BLOOD + 7] = p.friction;
                    
                    p.move();
                    
                    i++;
                }
            }
            
            meshBlood.updateVertices(0, verticesBlood, 0, blood.size() * FLOATS_PER_VERTEX_BLOOD);
            zoo.batch.flush();
            shaderBlood.bind();
            shaderBlood.setUniformMatrix(u_projTransBlood, camera.combined);
            shaderBlood.setUniformf(u_scaleBlood, screen.x / world.x);
            shaderBlood.setUniformf(u_mixBlood, 0.75f);
            Gdx.gl32.glEnable(GL32.GL_VERTEX_PROGRAM_POINT_SIZE);
            meshBlood.render(shaderBlood,GL32.GL_POINTS, 0, blood.size());
            zoo.batch.getShader().bind();
        }

        // move body for next frame
        rad_body = (rad_body + random(0.0f, 0.05f)) % TWO_PI;

        // rotate and move tree quad
        tree_quad.setRotation(degrees(sin(tree_rotation) * 0.05f));
        tree_quad.setPosition(x_offs_tree + width_tree / 2.0f + 0.49f + cos(tree_origin_swirl) * 0.01f * width_tree, y_offs_tree + (sin(tree_origin_swirl) * 0.02f) * height_tree);

        // calculate projection matrix to draw tree texture into the quad
        Vector2 q1 = tree_quad.getVertex(0, new Vector2());
        Vector2 q2 = tree_quad.getVertex(1, new Vector2());
        Vector2 q3 = tree_quad.getVertex(2, new Vector2());
        Vector2 q4 = tree_quad.getVertex(3, new Vector2());
        Matrix4 m = new Matrix4();
        boolean b = squareToQuad(q1, q2, q3, q4, m);
        
        if(b) {
            Matrix4 nm = new Matrix4(camera.combined).mul(m); // apply tree quad projection matrix before the camera
            Matrix4 s = new Matrix4().scl(1.0f / width_tree, 1.0f / height_tree, 1.0f);
            Matrix4 nms = new Matrix4(nm).mul(s); // scale down first
            
            batch.setProjectionMatrix(nms);
            batch.draw(atlas.img_tree, 0.0f, 0.0f);
        }

        batch.end();

        // move tree
        float rx3 = gotcha ? 0.0f : random(-1.0f * sin(tree_expand_left + PI / 4.0f), 1.0f * sin(tree_expand_left));
        float rx2 = gotcha ? 0.0f : random(-2.0f * sin(tree_expand_right + PI / 4.0f), 1.0f * sin(tree_expand_right));
        float ry = random(-1.0f * sin(tree_expand_top), 1.0f * sin(tree_expand_top + PI / 2.0f));
        
        quad[3 * 2] = constrain(quad[3 * 2] + rx3, -width_tree / 2.0f - 120.0f, -width_tree / 2.0f + 50.0f);
        quad[2 * 2] = constrain(quad[2 * 2] + rx2, width_tree / 2.0f - 150.0f, width_tree / 2.0f + 0.0f);
        quad[3 * 2 + 1] = constrain(quad[3 * 2 + 1] + ry, height_tree - 30.0f, height_tree + 100.0f);
        quad[2 * 2 + 1] = constrain(quad[3 * 2 + 1] + ry, height_tree - 30.0f, height_tree + 100.0f);
        
        tree_quad.setVertices(quad);

        tree_rotation = gotcha ? tree_rotation : (tree_rotation + random(0.0f, 0.05f)) % TWO_PI;
        tree_origin_swirl = (tree_origin_swirl + random(0.0f, 0.05f)) % TWO_PI;
        tree_expand_left = (tree_expand_left + random(0.0f, 0.25f)) % TWO_PI;
        tree_expand_right = (tree_expand_right + random(0.0f, 0.05f)) % TWO_PI;
        tree_expand_top = (tree_expand_top + random(0.0f, 0.05f)) % TWO_PI;

        // update background mesh
        for(int x = 1; x < D + 1; x++) {
            for(int y = 1; y < D + 1; y++) {
                int i = idx(x, y);
                verticesBack[i + 2] = 0.0f;
                verticesBack[i + 3] = 0.0f;
            }
        }

        
//        for(int i = 0; i < (D + 2) * (D + 2); i++) {
//            displacement[i].x = constrain(displacement[i].x + random(-DISPLACEMENT_MAX_RAND, DISPLACEMENT_MAX_RAND), -1.0f, 1.0f);
//            displacement[i].y = constrain(displacement[i].y + random(-DISPLACEMENT_MAX_RAND, DISPLACEMENT_MAX_RAND), -1.0f, 1.0f);
//            
//            int x = 1 + i % D;
//            int y = 1 + i / D;
//            
//            vertices[idx(x,y) + VX] = original_vertices[idx(x, y) + VX] + displacement[i].x * width_back / (D + 1) * DISPLACEMENT_FACTOR;
//            vertices[idx(x,y) + VY] = original_vertices[idx(x, y) + VY] + displacement[i].y * height_back / (D + 1) * DISPLACEMENT_FACTOR;
//        }
        
        meshBack.updateVertices(0, verticesBack);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        
        gutter.x = viewport.getScreenX();
        gutter.y = viewport.getScreenY();
        screen.x = viewport.getScreenWidth();
        screen.y = viewport.getScreenHeight();
        world.x = viewport.getWorldWidth();
        world.y = viewport.getWorldHeight();
    }

    @Override
    public boolean keyDown(int keycode) {
        switch(keycode) {
            case Keys.SPACE:
                // snap tree upwards
                tree_quad.setVertex(3, -width_tree / 2.0f + 120.0f, height_tree + 100.0f);
                tree_quad.setVertex(2, width_tree / 2.0f - 120.0f, height_tree + 100.0f);

                // did the tree catch the body?
                if(height_tree + 120.0f > y_body) {
                    gotcha = true;
                    y_body_diff = y_body - (height_tree + 120.0f);
                }
                return true;
        }
        
        return false;
    }
}
