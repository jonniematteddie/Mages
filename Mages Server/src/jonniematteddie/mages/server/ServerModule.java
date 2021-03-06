package jonniematteddie.mages.server;

import com.esotericsoftware.kryonet.Server;
import com.google.inject.Binder;
import com.google.inject.Module;

import jonniematteddie.mages.networking.framework.ClientIDProvider;
import jonniematteddie.mages.networking.framework.PingResponseHandler;
import jonniematteddie.mages.server.networking.framework.ExceptionClientIDProvider;
import jonniematteddie.mages.server.networking.framework.ServerPingResponseHandler;
import jonniematteddie.mages.world.model.World;
import jonniematteddie.mages.world.model.World.WorldBuilder;

/**
 * Server {@link Module}
 *
 * @author Eddie
 */
public class ServerModule implements Module {

	@Override
	public void configure(Binder binder) {
		binder.bind(Server.class).toInstance(new Server());
		binder.bind(World.class).toInstance(WorldBuilder.world().build());
		binder.bind(PingResponseHandler.class).to(ServerPingResponseHandler.class);
		binder.bind(ClientIDProvider.class).to(ExceptionClientIDProvider.class);
	}
}