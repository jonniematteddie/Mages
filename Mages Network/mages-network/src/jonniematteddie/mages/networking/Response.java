package jonniematteddie.mages.networking;

import java.io.Serializable;

import com.esotericsoftware.kryonet.Connection;

/**
 * A Reponse that is sent by the server to clients
 *
 * @author Matt
 */
public interface Response extends Serializable {

	/**
	 * Called client-side once received
	 */
	public void acknowledge(final Connection connection);


	/**
	 * @return whether or not the response should be sent to all
	 */
	public boolean replyToAll();


	/**
	 * @return the network protocol used to send the response
	 */
	public NetworkProtocol getProtocol();
}