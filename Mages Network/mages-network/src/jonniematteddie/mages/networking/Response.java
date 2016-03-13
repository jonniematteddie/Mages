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
}