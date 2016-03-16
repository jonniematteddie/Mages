package jonniematteddie.mages.server.networking.framework;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import jonniematteddie.mages.networking.framework.PingResponse;
import jonniematteddie.mages.networking.framework.PingResponseHandler;
import jonniematteddie.mages.server.networking.ClientPings;

/**
 * Adds pings to a map of client ID keys.
 *
 * @author Eddie
 */
@Singleton
public class ServerPingResponseHandler implements PingResponseHandler {

	private final ClientPings clientPings;

	@Inject
	public ServerPingResponseHandler(final ClientPings clientPings) {
		this.clientPings = clientPings;
	}


	@Override
	public void handle(int clientId, PingResponse pingResponse) {
		clientPings.addPing(clientId, pingResponse);
	}
}