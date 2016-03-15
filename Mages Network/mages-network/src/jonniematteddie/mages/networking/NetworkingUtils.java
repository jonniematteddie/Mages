package jonniematteddie.mages.networking;

import static com.google.common.collect.Lists.newLinkedList;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.badlogic.gdx.math.Vector2;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import jonniematteddie.mages.character.model.Individual;
import jonniematteddie.mages.character.model.IndividualKinematicState;
import jonniematteddie.mages.networking.framework.PingRequest;
import jonniematteddie.mages.networking.framework.PingResponse;
import jonniematteddie.mages.networking.initialization.InitialConnectionRequest;
import jonniematteddie.mages.networking.initialization.InitialConnectionResponse;
import jonniematteddie.mages.world.model.World;

/**
 * Class that provides utility methods related to networking
 *
 * @author Matt
 */
public class NetworkingUtils {

	/**
	 * The networking threadpool
	 */
	private static ExecutorService networkThreadpool = Executors.newCachedThreadPool(new ThreadFactoryBuilder().setNameFormat("networking-thread-%d").build());
	
	/**
	 * @return A list of classes to register for network serialization
	 */
	public static Collection<Class<?>> getClassesToRegister() {
		List<Class<?>> classesToRegister = newLinkedList();

		classesToRegister.add(ConcurrentHashMap.class);
		classesToRegister.add(Vector2.class);
		classesToRegister.add(IndividualKinematicState.class);
		classesToRegister.add(InitialConnectionResponse.class);
		classesToRegister.add(InitialConnectionRequest.class);
		classesToRegister.add(World.class);
		classesToRegister.add(Individual.class);
		classesToRegister.add(PingRequest.class);
		classesToRegister.add(PingResponse.class);
		classesToRegister.add(Request.class);
		classesToRegister.add(Response.class);

		return classesToRegister;
	}
}