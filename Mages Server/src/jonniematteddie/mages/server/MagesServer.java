package jonniematteddie.mages.server;

import java.io.IOException;

import org.objenesis.strategy.StdInstantiatorStrategy;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Server;
import com.google.inject.Inject;

import jonniematteddie.mages.networking.NetworkingUtils;
import jonniematteddie.mages.networking.framework.PingRequest;
import jonniematteddie.mages.server.networking.ClientPings;
import jonniematteddie.mages.server.networking.ServerListener;

/**
 * Server for hosting games
 *
 * @author Matt
 */
public class MagesServer {
	private final int tcpPort;
	private final int udpPort;

	private Thread pingThread;

	@Inject
	private Server server;

	@Inject
	private ClientPings clientPings;

	@Inject
	private ServerListener serverListener;

	/**
	 * @param tcpPort port to use for TCP
	 * @param udpPort port to use for UDP
	 */
	private MagesServer(int tcpPort, int udpPort) {
		this.tcpPort = tcpPort;
		this.udpPort = udpPort;

		pingThread = new Thread(() -> {
			while (true) {
				try {
					Thread.sleep(500);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}

				PingRequest pingRequest = new PingRequest();
				pingRequest.prepare();
				server.sendToAllTCP(pingRequest);

				clientPings.forEachEntry(entry -> {
					System.out.println("Client: [" + entry.getKey() + "] Has Ping: [" + entry.getValue() + "]");
				});
			}
		});

		pingThread.start();
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
		server.start();
		server.bind(tcpPort, udpPort);
		((Kryo.DefaultInstantiatorStrategy) server.getKryo().getInstantiatorStrategy()).setFallbackInstantiatorStrategy(new StdInstantiatorStrategy());

		for (Class<?> c : NetworkingUtils.getClassesToRegister()) {
			server.getKryo().register(c);
		}

		server.addListener(serverListener);
	}
}