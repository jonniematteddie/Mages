package jonniematteddie.mages.framework;

import com.google.inject.Injector;

/**
 * Dependency injection utilities
 *
 * @author Matt
 */
public class InjectionUtilities {

	private static Injector injector;

	/**
	 * @return THE injector
	 */
	public static <T> T inject(Class<T> tClass) {
		return injector.getInstance(tClass);
	}


	/**
	 * @param toBeInjected object to inject dependencies into
	 */
	public static void injectMembers(Object toBeInjected) {
		injector.injectMembers(toBeInjected);
	}


	/**
	 * @param injector to set
	 */
	public static void setInjector(Injector injector) {
		InjectionUtilities.injector = injector;
	}
}