package jonniematteddie.mages.networking.control;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import jonniematteddie.mages.world.model.World;

/**
 * Functionally processes controls
 *
 * @author Matt
 */
@Singleton
public class MagesInputProcessor {
	
	@Inject private World world;

	public boolean keyDown(int keycode, int clientID) {
		MappedKey pressed = MappedKey.getForKey(keycode);
		if (pressed == null) {
			return false;
		}
		
		switch (pressed) {
			case MOVE_DOWN:
				world.getClientControlledIndividual(clientID).getKinematicState().getVelocity().y = -40;
				break;
			case MOVE_LEFT:
				world.getClientControlledIndividual(clientID).getKinematicState().getVelocity().x = -40;
				break;
			case MOVE_RIGHT:
				world.getClientControlledIndividual(clientID).getKinematicState().getVelocity().x = 40;
				break;
			case MOVE_UP:
				world.getClientControlledIndividual(clientID).getKinematicState().getVelocity().y = 40;
				break;
			default:
				break;
		}
		return true;
	}

	
	public boolean keyUp(int keycode, int clientID) {
		MappedKey pressed = MappedKey.getForKey(keycode);
		if (pressed == null) {
			return false;
		}
		
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
		return true;
	}

	
	public boolean keyTyped(char character, int clientID) {
		return false;
	}

	
	public boolean touchDown(int screenX, int screenY, int pointer, int button, int clientID) {
		return false;
	}

	
	public boolean touchUp(int screenX, int screenY, int pointer, int button, int clientID) {
		return false;
	}


	public boolean touchDragged(int screenX, int screenY, int pointer, int clientID) {
		return false;
	}


	public boolean mouseMoved(int screenX, int screenY, int clientID) {
		return false;
	}


	public boolean scrolled(int amount, int clientID) {
		return false;
	}
}