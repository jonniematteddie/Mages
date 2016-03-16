package jonniematteddie.mages.networking.sync;

import jonniematteddie.mages.framework.InjectionUtilities;
import jonniematteddie.mages.networking.Notification;
import jonniematteddie.mages.networking.service.WorldSynchronizationService;
import jonniematteddie.mages.world.model.World;

/**
 * A {@link Notification} sent by server to clients to synchronize their {@link World} states
 *
 * @author Matt
 */
public class SyncWorldNotification implements Notification {
	private static final long serialVersionUID = 647356225361461334L;
	private World referenceWorld;
	private int ping;

	/** No-arg constructor required by Kryonet */
	SyncWorldNotification() {}

	public SyncWorldNotification(World referenceWorld, int ping) {
		this.referenceWorld = referenceWorld;
		this.ping = ping;
	}


	@Override
	public void prepare() {
		// Don't need to do anything
	}


	@Override
	public void receive() {
		InjectionUtilities.inject(WorldSynchronizationService.class).sync(
			InjectionUtilities.inject(World.class),
			referenceWorld,
			ping/2
		);
	}
}