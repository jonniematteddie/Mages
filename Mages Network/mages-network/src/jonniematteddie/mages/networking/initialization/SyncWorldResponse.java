package jonniematteddie.mages.networking.initialization;


import com.esotericsoftware.kryonet.Connection;

import jonniematteddie.mages.framework.InjectionUtilities;
import jonniematteddie.mages.networking.NetworkProtocol;
import jonniematteddie.mages.networking.Response;
import jonniematteddie.mages.networking.service.WorldSynchronizationService;
import jonniematteddie.mages.world.model.World;

/**
 * {@link Response} sent by the server to the client upon receiving a {@link SyncWorldRequest}
 *
 * @author Matt
 */
public class SyncWorldResponse extends Response {
	private static final long serialVersionUID = -3784983356217192464L;

	private World referenceWorld;

	/** No-arg constructor required for Kryonet */
	private SyncWorldResponse() {
		super(0L);
	}


	public SyncWorldResponse(World referenceWorld, long requestID) {
		super(requestID);
		this.referenceWorld = referenceWorld;
	}


	@Override
	public void acknowledge(Connection connection) {
		InjectionUtilities.inject(WorldSynchronizationService.class).sync(
			InjectionUtilities.inject(World.class),
			referenceWorld,
			0
		);
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