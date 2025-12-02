package nl.rumfumme.gl.enums;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.GL32;

public enum TransformFeedbackBufferMode {
    SEPARATE_ATTRIBS(GL32.GL_SEPARATE_ATTRIBS),
    INTERLEAVED_ATTRIBS(GL32.GL_INTERLEAVED_ATTRIBS);

    public int GL;
    private static Map<Integer, TransformFeedbackBufferMode> glMap = new HashMap<Integer, TransformFeedbackBufferMode>();
    
    static {
        for(TransformFeedbackBufferMode t : TransformFeedbackBufferMode.values()) {
            glMap.put(t.GL, t);
        }
    }

    private TransformFeedbackBufferMode(int GL) {
        this.GL = GL;
    }

    public static TransformFeedbackBufferMode valueOf(int GL) {
        return glMap.get(GL);
    }
}
