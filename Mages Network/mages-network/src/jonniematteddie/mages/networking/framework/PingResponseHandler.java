package jonniematteddie.mages.networking.framework;

/**
 * Responsible for handling a {@link PingResponse}.
 * 
 * @author Eddie
 */
public interface PingResponseHandler {
	/**
	 * handles shit.
	 */
	public void handle(int clientId, PingResponse pingResponse);
}
