package jonniematteddie.mages.networking.control;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import jonniematteddie.mages.world.model.World;

/**
 * Handles {@link KeyPressedRequest} received
 *
 * @author Matt
 */
@Singleton
public class ClientInputRequestHandler {

	@Inject private World world;

	/**
	 * @param toHandle
	 */
	public void handlePress(KeyPressedRequest toHandle, int clientID) {
		MappedKey pressed = toHandle.getMappedKey();
		switch (pressed) {
			case MOVE_DOWN:
				world.getClientControlledIndividual(clientID).getKinematicState().getVelocity().y = -10;
				break;
			case MOVE_LEFT:
				world.getClientControlledIndividual(clientID).getKinematicState().getVelocity().x = -10;
				break;
			case MOVE_RIGHT:
				world.getClientControlledIndividual(clientID).getKinematicState().getVelocity().x = 10;
				break;
			case MOVE_UP:
				world.getClientControlledIndividual(clientID).getKinematicState().getVelocity().y = 10;
				break;
			default:
				break;
		}
	}

	public void handleRelease(KeyPressedRequest toHandle, int clientID) {
		MappedKey pressed = toHandle.getMappedKey();
		switch (pressed) {
			case MOVE_DOWN:
				world.getClientControlledIndividual(clientID).getKinematicState().getVelocity().y = 0;
				break;
			case MOVE_LEFT:
				world.getClientControlledIndividual(clientID).getKinematicState().getVelocity().x = 0;
				break;
			case MOVE_RIGHT:
				world.getClientControlledIndividual(clientID).getKinematicState().getVelocity().x = 0;
				break;
			case MOVE_UP:
				world.getClientControlledIndividual(clientID).getKinematicState().getVelocity().y = 0;
				break;
			default:
				break;
		}
	}
}