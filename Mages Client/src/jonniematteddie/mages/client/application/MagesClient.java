package jonniematteddie.mages.client.application;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

import jonniematteddie.mages.networking.NetworkingUtils;
import jonniematteddie.mages.networking.Request;
import jonniematteddie.mages.networking.Response;

/**
 * The game client for Mages
 *
 * @author Matt
 */
public class MagesClient implements ApplicationListener {

	/**
	 * THE Injector
	 */
	private static Injector injector;
	
	/**
	 * The Kryonet {@link Client}
	 */
	@Inject
	private Client client;
	
	@Inject
	private ClientInputProcessor clientInputProcessor;
	
	@Override
	public void create() {
		// Set up the injector and inject dependencies
		injector = Guice.createInjector(new ClientModule());
		injector.injectMembers(this);
		
		// Bind the input processor
		Gdx.input.setInputProcessor(clientInputProcessor);
		
		setupKryonetClient("localhost", 30122, 30123);
	}


	private void setupKryonetClient(String address, int tcpPort, int udpPort) {
		for (Class<?> c : NetworkingUtils.getClassesToRegister()) {
			client.getKryo().register(c);
		}
		
		client.addListener(new Listener() {
			@Override
			public void received(Connection connection, Object received) {
				if (received instanceof Request) {
					Request request = (Request) received;
					
					request.receive();
					
					Response response = request.respond();
					switch (response.getProtocol()) {
					case TCP:
						client.sendTCP(response);
						break;
					case UDP:
						client.sendUDP(response);
						break;
					}
				} else if (received instanceof Response) {
					Response response = (Response) received;
					response.acknowledge(connection);
				}
			}
		});
		
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


	/**
	 * @return THE Injector
	 */
	public static Injector getInjector() {
		return injector;
	}
}