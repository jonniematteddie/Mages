package jonniematteddie.mages.client.application;

import com.esotericsoftware.kryonet.Client;
import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Module;

/**
 * Configures {@link Guice} bindings for the game client
 *
 * @author Matt
 */
public class ClientModule implements Module {

	@Override
	public void configure(Binder binder) {
		// We only ever want one instance of the client
		binder.bind(Client.class).toInstance(new Client());
		binder.bind(ClientInputProcessor.class).toInstance(new ClientInputProcessor());
	}
}