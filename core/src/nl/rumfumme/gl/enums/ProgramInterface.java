package nl.rumfumme.gl.enums;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.GL32;

public enum ProgramInterface {
    UNIFORM(GL32.GL_UNIFORM),
    UNIFORM_BLOCK(GL32.GL_UNIFORM_BLOCK),
    ATOMIC_COUNTER_BUFFER(GL32.GL_ATOMIC_COUNTER_BUFFER),
    PROGRAM_INPUT(GL32.GL_PROGRAM_INPUT),
    PROGRAM_OUTPUT(GL32.GL_PROGRAM_OUTPUT),
    TRANSFORM_FEEDBACK_VARYING(GL32.GL_TRANSFORM_FEEDBACK_VARYING),
    BUFFER_VARIABLE(GL32.GL_BUFFER_VARIABLE),
    SHADER_STORAGE_BLOCK(GL32.GL_SHADER_STORAGE_BLOCK),
    TRANSFORM_FEEDBACK_BUFFER(GL32.GL_TRANSFORM_FEEDBACK_BUFFER);

    public int GL;
    private static Map<Integer, ProgramInterface> glMap = new HashMap<Integer, ProgramInterface>();
    
    static {
        for(ProgramInterface t : ProgramInterface.values()) {
            glMap.put(t.GL, t);
        }
    }

    private ProgramInterface(int GL) {
        this.GL = GL;
    }

    public static ProgramInterface valueOf(int GL) {
        return glMap.get(GL);
    }
}
