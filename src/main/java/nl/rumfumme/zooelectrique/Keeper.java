package nl.rumfumme.zooelectrique;

import com.jme3.app.Application;
import com.jme3.app.state.CompositeAppState;
import com.jme3.app.state.VideoRecorderAppState;
import org.je3gl.plugins.input.InputHandlerAppState;
import org.je3gl.util.TimerAppState;

public class Keeper extends CompositeAppState {
    protected TimerAppState timerAppState;
    protected VideoRecorderAppState videoRecorderAppState;
    protected InputHandlerAppState inputHandlerAppState;

    @Override
    protected void initialize(Application app) {
        addChild(timerAppState = new TimerAppState());
        addChild(videoRecorderAppState = new VideoRecorderAppState());
        addChild(inputHandlerAppState = new InputHandlerAppState());
    }
}
