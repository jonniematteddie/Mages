package jonniematteddie.mages.server.networking;

import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import jonniematteddie.mages.character.model.IndividualKinematicState.IndividualKinematicStateBuilder;
import jonniematteddie.mages.character.model.PlayerControlledIndividual.PlayerControlledIndividualBuilder;
import jonniematteddie.mages.framework.InjectionUtilities;
import jonniematteddie.mages.networking.Request;
import jonniematteddie.mages.networking.Response;
import jonniematteddie.mages.world.model.World;

/**
 * Mages Client specific implementation of {@link Listener}
 *
 * @author Matt
 */
@Singleton
public class ServerListener extends Listener {

	@Inject	private ClientPings clientPings;
	@Inject	private Server server;
	@Inject	private World world;

	@Override
	public void connected (Connection connection) {
		InjectionUtilities.inject(World.class).addIndividual(
			PlayerControlledIndividualBuilder.bulder()
				.withClientID(
					connection.getID()
				)
				.withKinematicState(
					IndividualKinematicStateBuilder.builder()
						.withPosition(new Vector2())
						.withVelocity(new Vector2())
						.withAcceleration(new Vector2())
					.build()
				)
			.build()
		);
	}


	@Override
	public void disconnected (Connection connection) {
		clientPings.removePing(connection.getID());
		world.removePlayerControlledIndividual(connection.getID());
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