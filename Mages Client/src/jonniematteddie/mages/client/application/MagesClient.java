package jonniematteddie.mages.client.application;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Listener;
import com.google.inject.Inject;

import jonniematteddie.mages.client.networking.ClientListener;
import jonniematteddie.mages.networking.NetworkingUtils;

/**
 * The game client for Mages
 *
 * @author Matt
 */
public class MagesClient implements ApplicationListener {

	/** The Kryonet {@link Client} */
	@Inject private Client client;

	/** The {@link InputProcessor} used for the game client */
	@Inject private ClientInputProcessor clientInputProcessor;

	/** The {@link Listener} used by the Kryonet client */
	@Inject private ClientListener clientListener;

	@Override
	public void create() {
		// Bind the input processor
		Gdx.input.setInputProcessor(clientInputProcessor);

		setupKryonetClient("localhost", 30122, 30123);
	}


	/**
	 * Sets up the Kryonet client and connect to the server with the specified address and ports
	 *
	 * @param address of the server
	 * @param tcpPort of the server
	 * @param udpPort of the server
	 */
	private void setupKryonetClient(String address, int tcpPort, int udpPort) {
		for (Class<?> c : NetworkingUtils.getClassesToRegister()) {
			client.getKryo().register(c);
		}

		client.addListener(clientListener);

		try {
			client.start();
			client.connect(10000, address, tcpPort, udpPort);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}


	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
	}


	@Override
	public void render() {
	}


	@Override
	public void pause() {
		// TODO Auto-generated method stub
	}


	@Override
	public void resume() {
		// TODO Auto-generated method stub
	}


	@Override
	public void dispose() {
		// TODO Auto-generated method stub
	}
}