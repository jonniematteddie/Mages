package jonniematteddie.mages.networking;

import java.io.Serializable;

/**
 * A Request that is made between clients and server or vice versa.
 *
 * @author Matt
 */
public interface Request extends Serializable{
	
	/**
	 * Called on the sender immediately prior to being sent.
	 */
	public void prepare();
	

	/**
	 * Called on the receiver.
	 */
	public void receive();
	
	
	/**
	 * @return the {@link Response} the receiver sends back to the requester.
	 */
	public Response respond();
}