package jonniematteddie.mages.client.networking;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import jonniematteddie.mages.networking.NetworkingUtils;
import jonniematteddie.mages.networking.Notification;
import jonniematteddie.mages.networking.Request;
import jonniematteddie.mages.networking.Response;

/**
 * Mages Client specific implementation of {@link Listener}
 *
 * @author Matt
 */
@Singleton
public class ClientListener extends Listener {

	@Inject	private Client client;
	@Inject	private ClientNetworkUtils clientNetworkUtils;

	@Override
	public void received(Connection connection, Object received) {
		NetworkingUtils.run(() -> {
			try {
				Thread.sleep(50);
			} catch (Exception e) {}
			if (received instanceof Request) {
				Request request = (Request) received;
				request.receive();
				Response response = request.prepareResponse();
				switch (response.getProtocol()) {
				case TCP:
					client.sendTCP(response);
					break;
				case UDP:
					client.sendUDP(response);
					break;
				}
			} else if (received instanceof Response) {
				Response response = (Response) received;
				response.acknowledge(connection);

				clientNetworkUtils.notifyAsyncRequests(response);
			} else if (received instanceof Notification) {
				((Notification) received).receive();
			}
		});
	}
}
