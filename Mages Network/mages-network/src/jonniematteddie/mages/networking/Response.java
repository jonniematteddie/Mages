package jonniematteddie.mages.networking;

import java.io.Serializable;

import com.esotericsoftware.kryonet.Connection;

/**
 * A Reponse that is sent by the server to clients
 *
 * @author Matt
 */
public abstract class Response implements Serializable {
	private static final long serialVersionUID = 8717728511583837619L;

	/** The unique ID of the request that resulted in the creation of this {@link Response} */
	private long requestID;

	/**
	 * Constructor
	 */
	protected Response(long requestID) {
		this.requestID = requestID;
	}


	/**
	 * Called client-side once received
	 */
	public abstract void acknowledge(final Connection connection);


	/**
	 * @return whether or not the response should be sent to all
	 */
	public abstract boolean replyToAll();


	/**
	 * @return the network protocol used to send the response
	 */
	public abstract NetworkProtocol getProtocol();


	/**
	 * @return unique ID of the request that resulted in the creation of this {@link Response}
	 */
	public long getRequestID() {
		return requestID;
	}
}