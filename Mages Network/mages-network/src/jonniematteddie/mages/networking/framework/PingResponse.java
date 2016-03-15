package jonniematteddie.mages.networking.framework;

import com.esotericsoftware.kryonet.Connection;

import jonniematteddie.mages.framework.InjectionUtilities;
import jonniematteddie.mages.networking.NetworkProtocol;
import jonniematteddie.mages.networking.Response;

/**
 * A ping response
 *
 * @author Matt
 */
public class PingResponse extends Response {
	private static final long serialVersionUID = 4873021113059919649L;

	private long originalSentTime;
	
	/** No-arg constructor required for Kryonet */
	private PingResponse() {
		super(0L);
	}

	public PingResponse(long originalSentTime, long requestID) {
		super(requestID);
		this.originalSentTime = originalSentTime;
	}


	@Override
	public void acknowledge(final Connection connection) {
		InjectionUtilities.inject(PingResponseHandler.class).handle(connection.getID(), this);
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
