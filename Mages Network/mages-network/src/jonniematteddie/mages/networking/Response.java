package jonniematteddie.mages.networking;

import java.io.Serializable;

/**
 * A Reponse that is sent by the server to clients
 *
 * @author Matt
 */
public interface Response extends Serializable {

	/**
	 * Called client-side once received
	 */
	public void acknowledge();
	
	
	/**
	 * @return the connection ID to send the resopnse to, -1 means to all
	 */
	public int connectionID();
	
	
	/**
	 * @return the network protocol used to send the response
	 */
	public NetworkProtocol getProtocol();
}