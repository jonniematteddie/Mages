package jonniematteddie.mages.server;

import java.io.IOException;

import org.objenesis.strategy.StdInstantiatorStrategy;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import jonniematteddie.mages.networking.NetworkingUtils;
import jonniematteddie.mages.networking.Request;
import jonniematteddie.mages.networking.Response;

/**
 * Server for hosting games
 *
 * @author Matt
 */
public class MagesServer {

	private final Server server;
	private final int tcpPort;
	private final int udpPort;
	
	private Thread synchronizationThread;

	/**
	 * @param tcpPort port to use for TCP
	 * @param udpPort port to use for UDP
	 */
	private MagesServer(int tcpPort, int udpPort) {
		this.tcpPort = tcpPort;
		this.udpPort = udpPort;
		this.server = new Server();
		
		synchronizationThread = new Thread(() -> {
			while (true) {
				try {
					Thread.sleep(100);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
				
			}
		});
		
		synchronizationThread.start();
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

		server.addListener(new Listener() {
			@Override
			public void received(final Connection connection, final Object received) {
				if (received instanceof Request) {
					Request request = (Request) received;
					
					request.receive();
					
					Response response = request.respond();
					int connectionID = response.connectionID();
					
					if (connectionID == -1) {
						switch (response.getProtocol()) {
						case TCP:
							server.sendToAllTCP(response);
							break;
						case UDP:
							server.sendToAllUDP(response);
							break;
						}
					} else {
						switch (response.getProtocol()) {
						case TCP:
							server.sendToTCP(response.connectionID(), response);
							break;
						case UDP:
							server.sendToUDP(response.connectionID(), response);
							break;
						}
					}
				}
			}
		});
	}
}