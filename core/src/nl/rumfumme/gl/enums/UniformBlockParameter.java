package nl.rumfumme.gl.enums;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.GL32;

public enum UniformBlockParameter {
    UNIFORM_BLOCK_BINDING(GL32.GL_UNIFORM_BLOCK_BINDING),
    UNIFORM_BLOCK_DATA_SIZE(GL32.GL_UNIFORM_BLOCK_DATA_SIZE),
    UNIFORM_BLOCK_NAME_LENGTH(GL32.GL_UNIFORM_BLOCK_NAME_LENGTH),
    UNIFORM_BLOCK_ACTIVE_UNIFORMS(GL32.GL_UNIFORM_BLOCK_ACTIVE_UNIFORMS),
    UNIFORM_BLOCK_ACTIVE_UNIFORM_INDICES(GL32.GL_UNIFORM_BLOCK_ACTIVE_UNIFORM_INDICES),
    UNIFORM_BLOCK_REFERENCED_BY_VERTEX_SHADER(GL32.GL_UNIFORM_BLOCK_REFERENCED_BY_VERTEX_SHADER),
    UNIFORM_BLOCK_REFERENCED_BY_FRAGMENT_SHADER(GL32.GL_UNIFORM_BLOCK_REFERENCED_BY_FRAGMENT_SHADER);

    public int GL;
    private static Map<Integer, UniformBlockParameter> glMap = new HashMap<Integer, UniformBlockParameter>();
    
    static {
        for(UniformBlockParameter t : UniformBlockParameter.values()) {
            glMap.put(t.GL, t);
        }
    }

    private UniformBlockParameter(int GL) {
        this.GL = GL;
    }

    public static UniformBlockParameter valueOf(int GL) {
        return glMap.get(GL);
    }
}
