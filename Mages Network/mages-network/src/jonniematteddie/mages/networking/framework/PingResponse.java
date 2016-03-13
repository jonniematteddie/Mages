package jonniematteddie.mages.networking.framework;

import com.esotericsoftware.kryonet.Connection;

import jonniematteddie.mages.networking.NetworkProtocol;
import jonniematteddie.mages.networking.Response;

/**
 * A ping response
 *
 * @author Matt
 */
public class PingResponse implements Response {
	private static final long serialVersionUID = 4873021113059919649L;
	
	private long originalSentTime;
	
	/** No-arg constructor required for Kryonet */
	public PingResponse() {}
	
	public PingResponse(long originalSentTime) {
		this.originalSentTime = originalSentTime;
	}
	

	@Override
	public void acknowledge(final Connection connection) {
		System.out.println("Client: [" + connection.getID() + "], Ping:[" + Long.toString(System.currentTimeMillis() - getOriginalSentTime()) + "]");
	}

	
	@Override
	public NetworkProtocol getProtocol() {
		return NetworkProtocol.TCP;
	}


	@Override
	public boolean replyToAll() {
		return false;
	}

	public long getOriginalSentTime() {
		return originalSentTime;
	}
}
