package nl.rumfumme.gl.enums;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.GL32;

public enum Buffer {
    ARRAY_BUFFER(GL32.GL_ARRAY_BUFFER),
    COPY_READ_BUFFER(GL32.GL_COPY_READ_BUFFER),
    COPY_WRITE_BUFFER(GL32.GL_COPY_WRITE_BUFFER),
    ELEMENT_ARRAY_BUFFER(GL32.GL_ELEMENT_ARRAY_BUFFER),
    PIXEL_PACK_BUFFER(GL32.GL_PIXEL_PACK_BUFFER),
    PIXEL_UNPACK_BUFFER(GL32.GL_PIXEL_UNPACK_BUFFER),
    TRANSFORM_FEEDBACK_BUFFER(GL32.GL_TRANSFORM_FEEDBACK_BUFFER),
    UNIFORM_BUFFER(GL32.GL_UNIFORM_BUFFER);

    public int GL;
    private static Map<Integer, Buffer> glMap = new HashMap<Integer, Buffer>();
    
    static {
        for(Buffer t : Buffer.values()) {
            glMap.put(t.GL, t);
        }
    }

    private Buffer(int GL) {
        this.GL = GL;
    }

    public static Buffer valueOf(int GL) {
        return glMap.get(GL);
    }
}
