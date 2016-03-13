package jonniematteddie.mages.server;

import java.io.IOException;

import org.objenesis.strategy.StdInstantiatorStrategy;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import jonniematteddie.mages.networking.NetworkingUtils;

/**
 * Server for hosting games
 *
 * @author Matt
 */
public class MagesServer {

	private final Server server;
	private final int tcpPort;
	private final int udpPort;

	/**
	 * @param tcpPort port to use for TCP
	 * @param udpPort port to use for UDP
	 */
	private MagesServer(int tcpPort, int udpPort) {
		this.tcpPort = tcpPort;
		this.udpPort = udpPort;
		this.server = new Server();
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
			public void received(final Connection connection, final Object object) {
				System.out.println("Received object of type: [" + object.getClass().getName() + "] from connection: [" + connection + "]");
			}
		});
	}
}