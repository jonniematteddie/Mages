package jonniematteddie.mages.server;

import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;

import com.google.common.collect.Maps;
import com.google.inject.Singleton;

import jonniematteddie.mages.networking.framework.PingResponse;

@Singleton
public class ClientPings {

	private Map<Integer, Long> clientPings = Maps.newConcurrentMap();

	public void addPing(int clientId, PingResponse pingResponse) {
		clientPings.put(clientId, System.currentTimeMillis() - pingResponse.getOriginalSentTime());
	}
	
	public void removePing(int clientId) {
		clientPings.remove(clientId);
	}
	
	public void forEachEntry(Consumer<Entry<Integer, Long>> consumer) {
		clientPings.entrySet().forEach(consumer);
	}
}
