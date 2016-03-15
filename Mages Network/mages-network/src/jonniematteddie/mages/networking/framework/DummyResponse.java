package jonniematteddie.mages.networking.framework;

import com.esotericsoftware.kryonet.Connection;

import jonniematteddie.mages.networking.NetworkProtocol;
import jonniematteddie.mages.networking.Response;

/**
 * This response does nothing
 *
 * @author Matt
 */
public class DummyResponse extends Response {
	private static final long serialVersionUID = -1602770216645449238L;

	/** No-arg constructor for Kryonet */
	DummyResponse() {
		super(0L);
	}

	public DummyResponse(long requestID) {
		super(requestID);
	}


	@Override
	public void acknowledge(Connection connection) {
	}


	@Override
	public boolean replyToAll() {
		return false;
	}


	@Override
	public NetworkProtocol getProtocol() {
		return NetworkProtocol.UDP;
	}
}