package nl.rumfumme.zooelectrique;

import com.jme3.app.DebugKeysAppState;
import com.jme3.app.FlyCamAppState;
import com.jme3.app.SimpleApplication;
import com.jme3.app.StatsAppState;
import com.jme3.app.state.AppState;
import com.jme3.app.state.ConstantVerifierState;
import com.jme3.audio.AudioListenerState;
import com.jme3.system.AppSettings;
import nl.rumfumme.zooelectrique.cages.helloanybody.HelloAnybodyApp;
import org.je3gl.renderer.Camera2DRenderer;

/**
 * This is the Main Class of your Game. It should boot up your game and do initial initialisation
 * Move your Logic into AppStates or Controls or other java classes
 */
public class Zoo extends SimpleApplication {
    /*--- Constructors ------------------------------------------------------*/

    public Zoo() {
        super(new FlyCamAppState(),
              new AudioListenerState(),
              new DebugKeysAppState(),
              new ConstantVerifierState(),
              new HelloAnybodyApp(),
              new StatsAppState());
    }

    public Zoo(AppState... initialStates) {
        super(initialStates);
    }

    /*--- Overridden methods ------------------------------------------------*/

    @Override
    public void reshape(int w, int h) {
        super.reshape(w, h);

        HelloAnybodyApp app = stateManager.getState(HelloAnybodyApp.class);

        if(app != null && app.isEnabled() && app.isInitialized()) {
            app.reshape(w, h);
        }
    }

    @Override
    public void simpleInitApp() {
    }

    /*--- Public methods ----------------------------------------------------*/

    public static void main(String[] args) {
        AppSettings settings = new AppSettings(true);

        settings.setTitle("Le Zoo Électrique");
        settings.setResolution(1920, 1920/16*9);
        settings.setResizable(true);

        Zoo app = new Zoo();

        app.setSettings(settings);
        app.start();
    }
}
