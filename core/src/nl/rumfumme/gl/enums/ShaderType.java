package nl.rumfumme.gl.enums;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.GL32;

public enum ShaderType {
    VERTEX_SHADER(GL32.GL_VERTEX_SHADER),
    FRAGMENT_SHADER(GL32.GL_FRAGMENT_SHADER),
    TESS_EVALUATION_SHADER(GL32.GL_TESS_EVALUATION_SHADER),
    TESS_CONTROL_SHADER(GL32.GL_TESS_CONTROL_SHADER),
    GEOMETRY_SHADER(GL32.GL_GEOMETRY_SHADER),
    COMPUTE_SHADER(GL32.GL_COMPUTE_SHADER);

    public int GL;
    private static Map<Integer, ShaderType> glMap = new HashMap<Integer, ShaderType>();
    
    static {
        for(ShaderType t : ShaderType.values()) {
            glMap.put(t.GL, t);
        }
    }

    private ShaderType(int GL) {
        this.GL = GL;
    }

    public static ShaderType valueOf(int GL) {
        return glMap.get(GL);
    }
}
