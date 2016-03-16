package jonniematteddie.mages.client.application;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.esotericsoftware.kryonet.Client;
import com.google.inject.Inject;

import jonniematteddie.mages.client.graphics.GraphicsUtilities;
import jonniematteddie.mages.client.networking.ClientListener;
import jonniematteddie.mages.client.networking.ClientNetworkUtils;
import jonniematteddie.mages.framework.InjectionUtilities;
import jonniematteddie.mages.networking.NetworkingUtils;
import jonniematteddie.mages.networking.initialization.SyncWorldRequest;
import jonniematteddie.mages.world.model.World;
import jonniematteddie.mages.world.service.WorldUpdateService;

/**
 * The game client for Mages
 *
 * @author Matt
 */
public class MagesClient implements ApplicationListener {

	@Inject private Client client;
	@Inject private ClientInputProcessor clientInputProcessor;
	@Inject private ClientListener clientListener;
	@Inject private World world;
	@Inject private ClientNetworkUtils clientNetworkUtils;

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

			// Once connected, request basic information from the server and set up the game world
			connected();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}


	/**
	 * Sends requests to the server for data to set up the game world
	 *
	 */
	private void connected() {
		clientNetworkUtils.sendTCPSynchronous(new SyncWorldRequest(), 10000);
		enableInterpolation();
	}


	/**
	 * Sets up a thread that updates the client world, effectively enabling interpolation
	 */
	private void enableInterpolation() {
		// Start the client interpolation thread (update thread)
		WorldUpdateService.getWorldUpdateThread(
			InjectionUtilities.inject(WorldUpdateService.class),
			InjectionUtilities.inject(World.class)
		).start();
	}


	@Override
	public void resize(int width, int height) {
	}


	@Override
	public void render() {
		GraphicsUtilities.clear();
		GraphicsUtilities.getShaperenderer().begin(ShapeType.Filled);
		world.forEachIndividual(individual -> {
			GraphicsUtilities.getShaperenderer().circle(
				individual.getKinematicState().getPosition().x,
				individual.getKinematicState().getPosition().y,
				3f
			);
		});
		GraphicsUtilities.getShaperenderer().end();
	}


	@Override
	public void pause() {
	}


	@Override
	public void resume() {
	}


	@Override
	public void dispose() {
	}
}