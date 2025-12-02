package nl.rumfumme.gl.enums;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.GL32;

public enum BufferParameter {
    BUFFER_ACCESS_FLAGS(GL32.GL_BUFFER_ACCESS_FLAGS),
    BUFFER_MAPPED(GL32.GL_BUFFER_MAPPED),
    BUFFER_MAP_LENGTH(GL32.GL_BUFFER_MAP_LENGTH),
    BUFFER_MAP_OFFSET(GL32.GL_BUFFER_MAP_OFFSET),
    BUFFER_SIZE(GL32.GL_BUFFER_SIZE),
    BUFFER_USAGE(GL32.GL_BUFFER_USAGE);
    
    public int GL;
    private static Map<Integer, BufferParameter> glMap = new HashMap<Integer, BufferParameter>();
    
    static {
        for(BufferParameter t : BufferParameter.values()) {
            glMap.put(t.GL, t);
        }
    }

    private BufferParameter(int GL) {
        this.GL = GL;
    }

    public static BufferParameter valueOf(int GL) {
        return glMap.get(GL);
    }

}
