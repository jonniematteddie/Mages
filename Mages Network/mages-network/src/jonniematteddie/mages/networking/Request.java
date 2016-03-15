package jonniematteddie.mages.networking;

import java.io.Serializable;

import com.esotericsoftware.kryonet.Client;

/**
 * A Request that is made between clients and server or vice versa.
 *
 * @author Matt
 */
public abstract class Request implements Serializable {
	private static final long serialVersionUID = -888361604817009041L;

	/** The unique ID of this {@link Request} */
	private long requestId;
	/** The unique ID of the {@link Client} */
	private int clientId;

	/**
	 * Called on the sender immediately prior to being sent.
	 */
	public final void prepare(int clientID) {
		this.clientId = clientID;
		this.requestId = System.currentTimeMillis(); // TODO use a incrementing number generator
		internalPrepare();
	}


	/**
	 * Called on the sender immediately prior to being sent.
	 */
	protected abstract void internalPrepare();


	/**
	 * Called on the receiver.
	 */
	public abstract void receive();


	/**
	 * @return the {@link Response} the receiver sends back to the requester.
	 */
	public abstract Response prepareResponse();


	/**
	 * @return the unique request ID
	 */
	public long getRequestId() {
		return requestId;
	}


	/**
	 * @return the client ID
	 */
	public int getClientId() {
		return clientId;
	}
}