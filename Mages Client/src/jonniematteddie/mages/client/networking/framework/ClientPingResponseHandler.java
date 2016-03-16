package jonniematteddie.mages.client.networking.framework;

import com.google.inject.Singleton;

import jonniematteddie.mages.networking.framework.PingResponse;
import jonniematteddie.mages.networking.framework.PingResponseHandler;

/**
 * useless bitch
 *
 * @author Eddie
 */
@Singleton
public class ClientPingResponseHandler implements PingResponseHandler {
	@Override
	public void handle(int clientId, PingResponse pingResponse) {
		System.out.println("Ping: " + Long.toString(System.currentTimeMillis() - pingResponse.getOriginalSentTime()));
	}
}
