package nl.rumfumme.zoo2;

import java.util.Arrays;

import com.badlogic.gdx.Graphics.Monitor;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration.GLEmulation;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		
		Monitor[] monitors = Lwjgl3ApplicationConfiguration.getMonitors();
		Monitor primaryMonitor = Lwjgl3ApplicationConfiguration.getPrimaryMonitor();
		Monitor monitor;

		if(monitors.length == 1) {
		    monitor = primaryMonitor;
		} else {
	        monitor = Arrays.stream(monitors).filter(m -> !m.name.equals(primaryMonitor.name)).toArray(Monitor[]::new)[0];
		}

//		config.setFullscreenMode(Lwjgl3ApplicationConfiguration.getDisplayMode(monitor));
//		config.setMaximized(true);
//		config.setMaximizedMonitor(monitor);
		config.setForegroundFPS(60);
		config.setTitle("Le Zoo Électrique");
		config.setOpenGLEmulation(GLEmulation.GL32, 4, 2);
		
		new Lwjgl3Application(new Zoo(), config);
	}
}
