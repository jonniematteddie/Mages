package jonniematteddie.mages.client.application;

import com.badlogic.gdx.InputProcessor;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import jonniematteddie.mages.client.networking.ClientNetworkUtils;
import jonniematteddie.mages.networking.control.InputHistory;
import jonniematteddie.mages.networking.control.KeyPressedRequest;
import jonniematteddie.mages.networking.control.MappedKey;
import jonniematteddie.mages.world.model.World;

/**
 * {@link InputProcessor} used by {@link MagesClient}
 *
 * @author Matt
 */
@Singleton
public class ClientInputProcessor implements InputProcessor {

	@Inject private ClientNetworkUtils clientNetworkUtils;
	@Inject private InputHistory inputHistory;
	@Inject private World world;

	@Override
	public boolean keyDown(int keycode) {
		MappedKey key = MappedKey.getForKey(keycode);
		if (key == null) {
			return false;
		} else {
			inputHistory.keyPressed(world.getFrameNumber(), key);
			clientNetworkUtils.sendTCPAsynchronous(new KeyPressedRequest(key, true));
			return true;
		}
	}


	@Override
	public boolean keyUp(int keycode) {
		MappedKey key = MappedKey.getForKey(keycode);
		if (key == null) {
			return false;
		} else {
			inputHistory.keyReleased(world.getFrameNumber(), key);
			clientNetworkUtils.sendTCPAsynchronous(new KeyPressedRequest(key, false));
			return true;
		}
	}


	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
}