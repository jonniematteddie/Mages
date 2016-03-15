package jonniematteddie.mages.networking.framework;

import jonniematteddie.mages.networking.Request;
import jonniematteddie.mages.networking.Response;

/**
 * A {@link Request} for a ping
 *
 * @author Matt
 */
public class PingRequest extends Request {
	private static final long serialVersionUID = 6352110431745812960L;
	
	private long sentTime;
	
	public PingRequest() {
	}
	
	
	@Override
	public void receive() {
	}
	

	@Override
	public Response respond() {
		return new PingResponse(sentTime, getRequestId());
	}


	@Override
	protected void internalPrepare() {
		sentTime = System.currentTimeMillis();
	}
}