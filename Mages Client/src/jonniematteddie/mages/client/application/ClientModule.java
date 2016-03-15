package jonniematteddie.mages.client.application;

import com.esotericsoftware.kryonet.Client;
import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Module;

import jonniematteddie.mages.client.networking.framework.ClientPingResponseHandler;
import jonniematteddie.mages.networking.framework.PingResponseHandler;
import jonniematteddie.mages.world.model.World;
import jonniematteddie.mages.world.model.World.WorldBuilder;

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
		binder.bind(World.class).toInstance(WorldBuilder.world().build());
		binder.bind(PingResponseHandler.class).to(ClientPingResponseHandler.class);
	}
}