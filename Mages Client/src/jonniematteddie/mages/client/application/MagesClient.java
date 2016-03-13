package jonniematteddie.mages.client.application;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

import jonniematteddie.mages.client.graphics.GraphicsUtilities;

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
	
	private float x = 0f, y = 0f;
	
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
		client.addListener(new Listener() {
			@Override
			public void received (Connection connection, Object object) {
				if (object instanceof String) {
					String received = (String) object;
					
					if (received.startsWith("x:")) {
						x = Float.parseFloat(received.replace("x:", ""));
					} else if (received.startsWith("y:")) {
						y = Float.parseFloat(received.replace("y:", ""));
					}
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
		GraphicsUtilities.clear();
		GraphicsUtilities.getShaperenderer().begin(ShapeType.Filled);
		
		GraphicsUtilities.getShaperenderer().circle(x, y, 10f);
		
		GraphicsUtilities.getShaperenderer().end();
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