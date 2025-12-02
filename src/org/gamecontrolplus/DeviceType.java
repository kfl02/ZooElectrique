package org.gamecontrolplus;

import java.util.HashMap;
import java.util.Map;

import net.java.games.input.Controller;

public enum DeviceType {
    UNKNOWN(0x00000001, Controller.Type.UNKNOWN),
    MOUSE(0x00000002, Controller.Type.MOUSE),
    KEYBOARD(0x00000004, Controller.Type.KEYBOARD),
    FINGERSTICK(0x00000008, Controller.Type.FINGERSTICK),
    GAMEPAD(0x00000010, Controller.Type.GAMEPAD),
    HEADTRACKER(0x00000020, Controller.Type.HEADTRACKER),
    RUDDER(0x00000040, Controller.Type.RUDDER),
    STICK(0x00000080, Controller.Type.STICK),
    TRACKBALL(0x00000100, Controller.Type.TRACKBALL),
    TRACKPAD(0x00000200, Controller.Type.TRACKPAD),
    WHEEL(0x00000400, Controller.Type.WHEEL);

    public final int value;
    public final Controller.Type type;
    
    private DeviceType(int value, Controller.Type type) {
        this.value = value;
        this.type = type;
    }
    
    private static Map<Integer, DeviceType> byValueMap = new HashMap<>();
    private static Map<Controller.Type, DeviceType> byTypeMap = new HashMap<>();
    
    static {
        for(DeviceType dt : values()) {
            byValueMap.put(dt.value, dt);
            byTypeMap.put(dt.type, dt);
        }
    }
    
    public static DeviceType valueOf(int value) {
        return byValueMap.get(value);
    }

    public static DeviceType valueOf(Controller.Type type) {
        return byTypeMap.get(type);
    }
}
