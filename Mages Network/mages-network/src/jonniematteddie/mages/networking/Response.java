package jonniematteddie.mages.networking;

/**
 * A Reponse that is sent by the server to clients
 *
 * @author Matt
 */
public interface Response {

	/**
	 * Called client-side once received
	 */
	public void acknowledge();
}