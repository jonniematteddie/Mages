package jonniematteddie.mages.server;

/**
 * Entry point for the server
 *
 * @author Matt
 */
public class ServerEntryPoint {

	public static void main(String[] args) throws Exception {
		MagesServer.server(30122, 30123).start();
	}
}