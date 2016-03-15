package jonniematteddie.mages.server;

import com.google.inject.Guice;

import jonniematteddie.mages.framework.InjectionUtilities;

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
		InjectionUtilities.setInjector(Guice.createInjector(new ServerModule()));
		MagesServer server = MagesServer.server(30122, 30123);
		InjectionUtilities.injectMembers(server);
		server.start();
	}
}