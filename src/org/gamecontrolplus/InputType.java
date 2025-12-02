package org.gamecontrolplus;

public enum InputType {
    BUTTON_TYPE(1),
    HAT_TYPE(2),
    SLIDER_TYPE(3);
    
    public final int value;
    
    private InputType(int value) {
        this.value = value;
    }
    
    public static InputType valueOf(int value) {
        for(InputType it : values()) {
            if(value == it.value) {
                return it;
            }
        }
        
        return null;
    }
}
