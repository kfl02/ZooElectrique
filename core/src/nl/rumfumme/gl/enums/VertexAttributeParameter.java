package nl.rumfumme.gl.enums;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.GL32;

public enum VertexAttributeParameter {
    VERTEX_ATTRIB_ARRAY_BUFFER_BINDING(GL32.GL_VERTEX_ATTRIB_ARRAY_BUFFER_BINDING),
    VERTEX_ATTRIB_ARRAY_ENABLED(GL32.GL_VERTEX_ATTRIB_ARRAY_ENABLED),
    VERTEX_ATTRIB_ARRAY_SIZE(GL32.GL_VERTEX_ATTRIB_ARRAY_SIZE),
    VERTEX_ATTRIB_ARRAY_STRIDE(GL32.GL_VERTEX_ATTRIB_ARRAY_STRIDE),
    VERTEX_ATTRIB_ARRAY_TYPE(GL32.GL_VERTEX_ATTRIB_ARRAY_TYPE),
    VERTEX_ATTRIB_ARRAY_NORMALIZED(GL32.GL_VERTEX_ATTRIB_ARRAY_NORMALIZED),
    CURRENT_VERTEX_ATTRIB(GL32.GL_CURRENT_VERTEX_ATTRIB);

    public int GL;
    private static Map<Integer, VertexAttributeParameter> glMap = new HashMap<Integer, VertexAttributeParameter>();
    
    static {
        for(VertexAttributeParameter t : VertexAttributeParameter.values()) {
            glMap.put(t.GL, t);
        }
    }

    private VertexAttributeParameter(int GL) {
        this.GL = GL;
    }

    public static VertexAttributeParameter valueOf(int GL) {
        return glMap.get(GL);
    }
}
