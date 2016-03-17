package jonniematteddie.mages.server;

import java.io.IOException;

import org.objenesis.strategy.StdInstantiatorStrategy;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;
import com.google.inject.Inject;

import jonniematteddie.mages.framework.InjectionUtilities;
import jonniematteddie.mages.networking.NetworkingUtils;
import jonniematteddie.mages.networking.framework.PingRequest;
import jonniematteddie.mages.networking.sync.SyncWorldNotification;
import jonniematteddie.mages.server.networking.ClientPings;
import jonniematteddie.mages.server.networking.ServerListener;
import jonniematteddie.mages.world.model.World;
import jonniematteddie.mages.world.service.WorldUpdateService;

/**
 * Server for hosting games
 *
 * @author Matt
 */
public class MagesServer {

	private static final long VERIFY_CLIENT_STATES = 500;
	private static final long CONDUCT_PING_REQUEST = 200;
	
	@Inject private Server server;
	@Inject private ServerListener serverListener;
	@Inject private ClientPings clientPings;

	private final int tcpPort;
	private final int udpPort;

	private Thread pingThread;
	private Thread syncThread;

	/**
	 * @param tcpPort port to use for TCP
	 * @param udpPort port to use for UDP
	 */
	private MagesServer(int tcpPort, int udpPort) {
		this.tcpPort = tcpPort;
		this.udpPort = udpPort;
	}

	/**
	 * @param tcpPort port to use for TCP
	 * @param udpPort port to use for UDP
	 *
	 * @return a new {@link MagesServer} configured using the specified TCP and UDP ports
	 */
	public static MagesServer server(int tcpPort, int udpPort) {
		return new MagesServer(tcpPort, udpPort);
	}


	/**
	 * Starts the server
	 */
	public void start() throws IOException {
		pingThread = new Thread(() -> {
			while (true) {
				try {
					Thread.sleep(CONDUCT_PING_REQUEST);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}

				PingRequest pingRequest = new PingRequest();
				pingRequest.prepare(-1);
				server.sendToAllTCP(pingRequest);
			}
		});

		WorldUpdateService.getWorldUpdateThread(
			InjectionUtilities.inject(WorldUpdateService.class),
			InjectionUtilities.inject(World.class)
		).start();
		pingThread.start();

		syncThread = new Thread(() -> {
			while (true) {
				try {
					Thread.sleep(VERIFY_CLIENT_STATES);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}

				// Verify kinematic states are the same as the server for every client. If that is not the case,
				// make them the same as the server.
				World world = InjectionUtilities.inject(World.class);
				synchronized (world) {
					for (Connection connection : server.getConnections()) {
						connection.sendTCP(new SyncWorldNotification(world, (int) clientPings.getPing(connection.getID())));
					}
				}
			}
		});
		syncThread.start();

		server.start();
		server.bind(tcpPort, udpPort);
		((Kryo.DefaultInstantiatorStrategy) server.getKryo().getInstantiatorStrategy()).setFallbackInstantiatorStrategy(new StdInstantiatorStrategy());

		for (Class<?> c : NetworkingUtils.getClassesToRegister()) {
			server.getKryo().register(c);
		}

		server.addListener(serverListener);
	}
}