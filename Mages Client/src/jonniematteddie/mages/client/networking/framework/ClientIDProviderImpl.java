package jonniematteddie.mages.client.networking.framework;

import com.esotericsoftware.kryonet.Client;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import jonniematteddie.mages.networking.framework.ClientIDProvider;

/**
 * Returns the client ID
 *
 * @author Matt
 */
@Singleton
public class ClientIDProviderImpl implements ClientIDProvider {

	@Inject private Client client;
	
	@Override
	public int getClientID() {
		return client.getID();
	}
}