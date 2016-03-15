package jonniematteddie.mages.world.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import jonniematteddie.mages.character.service.IndividualUpdateService;
import jonniematteddie.mages.world.model.World;

/**
 * Service used to update a {@link World}
 *
 * @author Matt
 */
@Singleton
public class WorldUpdateService {
	public static float UPDATE_TICK = 1f/60f;

	@Inject private IndividualUpdateService individualUpdateService;

	/**
	 * @param world to update
	 * @param numberOfFrames the number of frames to update by
	 */
	public void updateWorld(World world, int numberOfFrames) {
		world.forEachIndividual(individual -> {
			individualUpdateService.updateIndividual(individual, numberOfFrames);
		});
	}


	/**
	 * @param updateService an instance of the {@link WorldUpdateService}
	 * @param worldToUpdate ...
	 *
	 * @return A thread that updates the world by 1 frame, ~60 times per second
	 */
	public static Thread getWorldUpdateThread(WorldUpdateService updateService, World worldToUpdate) {
		return new Thread(() -> {
			while (true) {
				try {
					Thread.sleep(16);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}

				updateService.updateWorld(worldToUpdate, 1);
			}
		});
	}
}