package jonniematteddie.mages.networking;

/**
 * A Request that is made from client to server
 *
 * @author Matt
 */
public interface Request {

	/**
	 * Called on the server once this {@link Request} is received
	 */
	public void receive();
	
	/**
	 * @return the {@link Response} the server sends back to the client
	 */
	public Response respond();
}