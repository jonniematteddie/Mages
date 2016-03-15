package jonniematteddie.mages.server.networking;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import jonniematteddie.mages.networking.Request;
import jonniematteddie.mages.networking.Response;

/**
 * Mages Client specific implementation of {@link Listener}
 *
 * @author Matt
 */
@Singleton
public class ServerListener extends Listener {

	@Inject
	private ClientPings clientPings;

	@Inject
	private Server server;

	@Override
	public void disconnected (Connection connection) {
		clientPings.removePing(connection.getID());
	}


	@Override
	public void received(final Connection connection, final Object received) {
		if (received instanceof Request) {
			Request request = (Request) received;

			request.receive();

			Response response = request.prepareResponse();

			if (response.replyToAll()) {
				switch (response.getProtocol()) {
				case TCP:
					server.sendToAllTCP(response);
					break;
				case UDP:
					server.sendToAllUDP(response);
					break;
				}
			} else {
				switch (response.getProtocol()) {
				case TCP:
					server.sendToTCP(connection.getID(), response);
					break;
				case UDP:
					server.sendToUDP(connection.getID(), response);
					break;
				}
			}
		} else if (received instanceof Response) {
			((Response) received).acknowledge(connection);
		}
	}
}