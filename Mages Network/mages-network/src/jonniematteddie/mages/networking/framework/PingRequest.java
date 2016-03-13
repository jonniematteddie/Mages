package jonniematteddie.mages.networking.framework;

import jonniematteddie.mages.networking.Request;
import jonniematteddie.mages.networking.Response;

public class PingRequest implements Request {
	private static final long serialVersionUID = 6352110431745812960L;

	@Override
	public void receive() {
		
	}

	@Override
	public Response respond() {
		return null;
	}

}
