package jonniematteddie.mages.networking;

import static com.google.common.collect.Lists.newLinkedList;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.esotericsoftware.kryonet.Client;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import jonniematteddie.mages.framework.InjectionUtilities;
import jonniematteddie.mages.networking.framework.PingRequest;
import jonniematteddie.mages.networking.framework.PingResponse;

/**
 * Class that provides utility methods related to networking
 *
 * @author Matt
 */
public class NetworkingUtils {

	/**
	 * The networking threadpool
	 */
	private static ExecutorService networkThreadpool = Executors.newCachedThreadPool(new ThreadFactoryBuilder().setNameFormat("networking-thread-%d").build());

	/**
	 * @return A list of classes to register for network serialization
	 */
	public static Collection<Class<?>> getClassesToRegister() {
		List<Class<?>> classesToRegister = newLinkedList();

		classesToRegister.add(PingRequest.class);
		classesToRegister.add(PingResponse.class);
		classesToRegister.add(Request.class);
		classesToRegister.add(Response.class);

		return classesToRegister;
	}


	/**
	 * Sends a request, either synchronously or asynchronously
	 *
	 * @param requestToSend
	 * @param synchronous whether or not the {@link Request} thread should wait until a {@link Response} is received and acknowledged
	 */
	public static void sendTCP(Request requestToSend, boolean synchronous) {
		// TODO synchronous / asynchronous
		InjectionUtilities.inject(Client.class).sendTCP(requestToSend);
	}
}
