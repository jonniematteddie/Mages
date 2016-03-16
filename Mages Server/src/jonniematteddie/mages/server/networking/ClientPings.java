package jonniematteddie.mages.server.networking;

import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;

import com.google.common.collect.Maps;
import com.google.inject.Singleton;

import jonniematteddie.mages.networking.framework.PingResponse;

/**
 * Wrapper class containing ping of all connected clients
 *
 * @author Eddie
 */
@Singleton
public class ClientPings {

	private Map<Integer, Long> clientPings = Maps.newConcurrentMap();

	/**
	 * Add an entry to the ping map
	 *
	 * @param clientId to add
	 * @param pingResponse to add
	 */
	public void addPing(int clientId, PingResponse pingResponse) {
		clientPings.put(clientId, System.currentTimeMillis() - pingResponse.getOriginalSentTime());
	}


	/**
	 * Remove an entry from the ping map
	 *
	 * @param clientId to remove
	 */
	public void removePing(int clientId) {
		clientPings.remove(clientId);
	}


	/**
	 * get the ping of a specified client
	 *
	 * @param clientId
	 */
	public long getPing(int clientId) {
		Long ping = clientPings.get(clientId);
		return ping == null ? 0 : ping;
	}


	/**
	 * Process each entry in the ping map
	 *
	 * @param consumer for the processing of entries
	 */
	public void forEachEntry(Consumer<Entry<Integer, Long>> consumer) {
		clientPings.entrySet().forEach(consumer);
	}
}
