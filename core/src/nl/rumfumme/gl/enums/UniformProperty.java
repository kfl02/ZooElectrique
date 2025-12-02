package nl.rumfumme.gl.enums;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.GL32;

public enum UniformProperty {
    UNIFORM_TYPE(GL32.GL_UNIFORM_TYPE),
    UNIFORM_SIZE(GL32.GL_UNIFORM_SIZE),
    UNIFORM_NAME_LENGTH(GL32.GL_UNIFORM_NAME_LENGTH),
    UNIFORM_BLOCK_INDEX(GL32.GL_UNIFORM_BLOCK_INDEX),
    UNIFORM_OFFSET(GL32.GL_UNIFORM_OFFSET),
    UNIFORM_ARRAY_STRIDE(GL32.GL_UNIFORM_ARRAY_STRIDE),
    UNIFORM_MATRIX_STRIDE(GL32.GL_UNIFORM_MATRIX_STRIDE),
    UNIFORM_IS_ROW_MAJOR(GL32.GL_UNIFORM_IS_ROW_MAJOR);

    public int GL;
    private static Map<Integer, UniformProperty> glMap = new HashMap<Integer, UniformProperty>();
    
    static {
        for(UniformProperty t : UniformProperty.values()) {
            glMap.put(t.GL, t);
        }
    }

    private UniformProperty(int GL) {
        this.GL = GL;
    }

    public static UniformProperty valueOf(int GL) {
        return glMap.get(GL);
    }
}
