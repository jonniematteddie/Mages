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
	 * Sends a request, either synchronously or asynchronously
	 *
	 * @param requestToSend
	 * @param synchronous whether or not the {@link Request} thread should wait until a {@link Response} is received and acknowledged
	 * @param timeout if this is a synchronous request, the timeout will dictate the time elapsed until we give up waiting for a response
	 */
	public static void sendTCP(Request requestToSend, boolean synchronous, long timeout) {
		requestToSend.prepare();
		
		InjectionUtilities.inject(Client.class).sendTCP(requestToSend);
		if (synchronous) {
			outstandingSynchronousRequests.put(requestToSend.getRequestId(), requestToSend);
			synchronized(requestToSend) {
				try {
					requestToSend.wait();
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}
	
	
	/**
	 * Attempts to notify an asynchronous {@link Request} that is waiting to be notified.
	 * 
	 * @param response received
	 */
	public static void notifyAsyncRequests(Response response) {
		Request request = outstandingSynchronousRequests.remove(response.getRequestID());
		if (request != null) {
			request.notify();
		}
	}
}