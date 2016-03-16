package jonniematteddie.mages.networking.control;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Handles {@link KeyPressedRequest} received
 *
 * @author Matt
 */
@Singleton
public class InputRequestHandler {

	@Inject private MagesInputProcessor magesInputProcessor;

	/**
	 * @param toHandle
	 * @param clientID
	 */
	public void handlePress(KeyPressedRequest toHandle, int clientID) {
		magesInputProcessor.keyDown(toHandle.getMappedKey().getKeyCode(), clientID);
	}

	/**
	 * @param toHandle
	 * @param clientID
	 */
	public void handleRelease(KeyPressedRequest toHandle, int clientID) {
		magesInputProcessor.keyUp(toHandle.getMappedKey().getKeyCode(), clientID);
	}
}