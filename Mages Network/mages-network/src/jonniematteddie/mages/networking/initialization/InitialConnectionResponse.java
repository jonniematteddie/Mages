package jonniematteddie.mages.networking.initialization;


import com.esotericsoftware.kryonet.Connection;

import jonniematteddie.mages.framework.InjectionUtilities;
import jonniematteddie.mages.networking.NetworkProtocol;
import jonniematteddie.mages.networking.Response;
import jonniematteddie.mages.networking.service.WorldSynchronizationService;
import jonniematteddie.mages.world.model.World;

/**
 * {@link Response} sent by the server to the client upon receiving a {@link InitialConnectionRequest}
 *
 * @author Matt
 */
public class InitialConnectionResponse extends Response {
	private static final long serialVersionUID = -3784983356217192464L;

	private World referenceWorld;

	@SuppressWarnings("unused")
	/** No-arg constructor required for Kryonet */
	private InitialConnectionResponse() {
		super(0L);
	}

	public InitialConnectionResponse(World referenceWorld, long requestID) {
		super(requestID);
		this.referenceWorld = referenceWorld;
	}


	@Override
	public void acknowledge(Connection connection) {
		// Client receives the world.
		World clientWorld = InjectionUtilities.inject(World.class);

		// Synchronise client world using the reference world
		InjectionUtilities.inject(WorldSynchronizationService.class).sync(clientWorld, referenceWorld);
	}


	@Override
	public boolean replyToAll() {
		return false;
	}


	@Override
	public NetworkProtocol getProtocol() {
		return NetworkProtocol.TCP;
	}
}