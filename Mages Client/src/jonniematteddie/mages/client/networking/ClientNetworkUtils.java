package jonniematteddie.mages.client.networking;

import java.util.Map;

import com.esotericsoftware.kryonet.Client;
import com.google.common.collect.Maps;

import jonniematteddie.mages.framework.InjectionUtilities;
import jonniematteddie.mages.networking.Request;
import jonniematteddie.mages.networking.Response;

/**
 * Client specific network helper methods
 *
 * @author Matt
 */
public class ClientNetworkUtils {

	/**
	 * Map containing all {@link Request}s that are blocked and waiting for a response
	 */
	private static Map<Long, Request> outstandingSynchronousRequests = Maps.newConcurrentMap();

	/**
	 * Sends a request, synchronously
	 * The {@link Request} thread will wait until a {@link Response} is received and acknowledged
	 *
	 * @param requestToSend
	 * @param timeout if this is a synchronous request, the timeout will dictate the time elapsed until we give up waiting for a response
	 */
	public static void sendTCPSynchronous(Request requestToSend, long timeout) {
		requestToSend.prepare();

		InjectionUtilities.inject(Client.class).sendTCP(requestToSend);
		outstandingSynchronousRequests.put(requestToSend.getRequestId(), requestToSend);
		synchronized(requestToSend) {
			// TODO implement timeout
			try {
				requestToSend.wait();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}


	/**
	 * Sends a request, asynchronously
	 *
	 * @param requestToSend
	 */
	public static void sendTCPAsynchronous(Request requestToSend) {
		requestToSend.prepare();
		InjectionUtilities.inject(Client.class).sendTCP(requestToSend);
	}


	/**
	 * Attempts to notify an asynchronous {@link Request} that is waiting to be notified.
	 *
	 * @param response received
	 */
	public static void notifyAsyncRequests(Response response) {
		Request request = outstandingSynchronousRequests.remove(response.getRequestID());
		if (request != null) {
			synchronized (request) {
				request.notify();
			}
		}
	}
}