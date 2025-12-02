package nl.rumfumme.gl.enums;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.GL32;

public enum ShaderParameter {
    SHADER_TYPE(GL32.GL_SHADER_TYPE),
    DELETE_STATUS(GL32.GL_DELETE_STATUS),
    COMPILE_STATUS(GL32.GL_COMPILE_STATUS),
    INFO_LOG_LENGTH(GL32.GL_INFO_LOG_LENGTH),
    SHADER_SOURCE_LENGTH(GL32.GL_SHADER_SOURCE_LENGTH);

    public int GL;
    private static Map<Integer, ShaderParameter> glMap = new HashMap<Integer, ShaderParameter>();
    
    static {
        for(ShaderParameter t : ShaderParameter.values()) {
            glMap.put(t.GL, t);
        }
    }

    private ShaderParameter(int GL) {
        this.GL = GL;
    }

    public static ShaderParameter valueOf(int GL) {
        return glMap.get(GL);
    }
}
