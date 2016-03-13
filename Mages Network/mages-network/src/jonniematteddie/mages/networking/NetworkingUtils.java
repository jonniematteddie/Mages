package jonniematteddie.mages.networking;

import static com.google.common.collect.Lists.newLinkedList;

import java.util.Collection;
import java.util.List;

import jonniematteddie.mages.networking.framework.PingRequest;
import jonniematteddie.mages.networking.framework.PingResponse;

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
		
		classesToRegister.add(PingRequest.class);
		classesToRegister.add(PingResponse.class);
		classesToRegister.add(Request.class);
		classesToRegister.add(Response.class);
		
		return classesToRegister;
	}
}
