package jonniematteddie.mages.networking;

import java.io.Serializable;

/**
 * A {@link Notification} is a one-way message that requires no reply
 *
 * @author Matt
 */
public interface Notification extends Serializable {
	
	/**
	 * Called by the receiver of this {@link Notification}
	 */
	public void prepare();

	/**
	 * Called by the receiver of this {@link Notification}
	 */
	public void receive();
}