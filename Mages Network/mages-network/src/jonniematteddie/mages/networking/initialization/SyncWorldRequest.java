package jonniematteddie.mages.networking.initialization;

import jonniematteddie.mages.framework.InjectionUtilities;
import jonniematteddie.mages.networking.Request;
import jonniematteddie.mages.networking.Response;
import jonniematteddie.mages.world.model.World;

/**
 * {@link Request} made by the client to request a sync with server world
 *
 * @author Matt
 */
public class SyncWorldRequest extends Request {
	private static final long serialVersionUID = -8966601831465936709L;


	@Override
	public void receive() {
		// Server receives this request, but we don't need to do anything here either
	}


	@Override
	public Response prepareResponse() {
		return new SyncWorldResponse(InjectionUtilities.inject(World.class), getRequestId());
	}


	@Override
	protected void internalPrepare() {
		// We don't need to do anything here
	}
}