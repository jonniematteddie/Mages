package jonniematteddie.mages.client.application;

import com.badlogic.gdx.InputProcessor;
import com.esotericsoftware.kryonet.Client;
import com.google.inject.Inject;

import jonniematteddie.mages.networking.framework.PingRequest;

/**
 * {@link InputProcessor} used by {@link MagesClient}
 *
 * @author Matt
 */
public class ClientInputProcessor implements InputProcessor {
	
	@Inject
	private Client client;

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		
		PingRequest object = new PingRequest();
		object.prepare();
		client.sendTCP(object);
		return false;
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