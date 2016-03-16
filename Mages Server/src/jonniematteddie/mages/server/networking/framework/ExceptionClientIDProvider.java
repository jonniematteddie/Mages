package jonniematteddie.mages.server.networking.framework;

import com.google.inject.Singleton;

import jonniematteddie.mages.networking.framework.ClientIDProvider;

/**
 * This should not ever be called
 *
 * @author Matt
 */
@Singleton
public class ExceptionClientIDProvider implements ClientIDProvider {

	@Override
	public int getClientID() {
		throw new UnsupportedOperationException();
	}
}