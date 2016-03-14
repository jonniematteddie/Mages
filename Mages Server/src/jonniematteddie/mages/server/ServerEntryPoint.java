package jonniematteddie.mages.server;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Entry point for the server
 *
 * @author Matt
 */
public class ServerEntryPoint {

	/**
	 * Entry point of the server
	 */
	public static void main(String[] args) throws Exception {
		// Set up the Injector and start the server
		Injector injector = Guice.createInjector(new ServerModule());
		MagesServer server = MagesServer.server(30122, 30123);
		injector.injectMembers(server);
		server.start();
	}
}