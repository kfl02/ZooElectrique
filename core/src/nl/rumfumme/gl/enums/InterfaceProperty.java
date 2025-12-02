package nl.rumfumme.gl.enums;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.GL32;

public enum InterfaceProperty {
    ACTIVE_RESOURCES(GL32.GL_ACTIVE_RESOURCES),
    MAX_NAME_LENGTH(GL32.GL_MAX_NAME_LENGTH),
    MAX_NUM_ACTIVE_VARIABLES(GL32.GL_MAX_NUM_ACTIVE_VARIABLES);

    public int GL;
    private static Map<Integer, InterfaceProperty> glMap = new HashMap<Integer, InterfaceProperty>();
    
    static {
        for(InterfaceProperty t : InterfaceProperty.values()) {
            glMap.put(t.GL, t);
        }
    }

    private InterfaceProperty(int GL) {
        this.GL = GL;
    }

    public static InterfaceProperty valueOf(int GL) {
        return glMap.get(GL);
    }
}
