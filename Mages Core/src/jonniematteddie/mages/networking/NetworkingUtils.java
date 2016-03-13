package jonniematteddie.mages.networking;

import static com.google.common.collect.Lists.newLinkedList;

import java.util.Collection;
import java.util.List;

/**
 * Class that provides utility methods related to networking
 *
 * @author Matt
 */
public class NetworkingUtils {


	/**
	 * @return A list of classes to register for network serialization
	 */
	public static Collection<Class<?>> getClassesToRegister() {
		List<Class<?>> classesToRegister = newLinkedList();
		return classesToRegister;
	}
}
