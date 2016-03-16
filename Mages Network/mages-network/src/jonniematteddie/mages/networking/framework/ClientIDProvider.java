package jonniematteddie.mages.networking.framework;

/**
 * Provides client ID, only returns a meaningful value if run on a client
 *
 * @author Matt
 */
public interface ClientIDProvider {

	/**
	 * @return the client ID
	 */
	public int getClientID();
}