package jonniematteddie.mages.client.application;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Entry point of the game client
 *
 * @author Matt
 */
public class ClientEntryPoint {
	public static void main(String[] args) throws InterruptedException {
		// Configurations
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Project Mages";
		cfg.useGL30 = false;
		cfg.samples = 4;
		cfg.width = 900; // TODO Persist config
		cfg.height = 600; // TODO Persist config
		cfg.fullscreen = false; // TODO Persist config
		cfg.resizable = true;

		// Set up the Injector
		Injector injector = Guice.createInjector(new ClientModule());

		// Start the game client
		new LwjglApplication(injector.getInstance(MagesClient.class), cfg);
	}
}
