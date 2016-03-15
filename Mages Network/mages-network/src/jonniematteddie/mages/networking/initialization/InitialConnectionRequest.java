package jonniematteddie.mages.networking.initialization;

import jonniematteddie.mages.framework.InjectionUtilities;
import jonniematteddie.mages.networking.Request;
import jonniematteddie.mages.networking.Response;
import jonniematteddie.mages.world.model.World;

/**
 * {@link Request} made by the client when it connects to the server. Performs basic setup
 *
 * @author Matt
 */
public class InitialConnectionRequest extends Request {
	private static final long serialVersionUID = -8966601831465936709L;


	@Override
	public void receive() {
		// Server receives this request, but we don't need to do anything here either
	}


	@Override
	public Response prepareResponse() {
		return new InitialConnectionResponse(InjectionUtilities.inject(World.class), getRequestId());
	}


	@Override
	protected void internalPrepare() {
		// We don't need to do anything here
	}
}