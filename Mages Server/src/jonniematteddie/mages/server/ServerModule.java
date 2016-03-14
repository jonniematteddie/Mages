package jonniematteddie.mages.server;

import com.esotericsoftware.kryonet.Server;
import com.google.inject.Binder;
import com.google.inject.Module;

/**
 * Server {@link Module}
 *
 * @author Eddie
 */
public class ServerModule implements Module {

	@Override
	public void configure(Binder binder) {
		binder.bind(Server.class).toInstance(new Server());
	}
}