package jonniematteddie.mages.networking.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import jonniematteddie.mages.character.model.Individual;
import jonniematteddie.mages.world.model.World;
import jonniematteddie.mages.world.service.WorldUpdateService;

/**
 * Service responsible for synchronising two {@link World} instances
 *
 * @author Matt
 */
@Singleton
public class WorldSynchronizationService {

	@Inject	private IndividualSynchronizationService individualSynchronizationService;

	/**
	 * Synchronises a given {@link World} with a reference {@link World}
	 *
	 * @param toSync world to sync
	 * @param referenceWorld world to use as reference
	 * @param ping the one-way ping between client/server
	 */
	public void sync(World toSync, World referenceWorld, int ping) {
		toSync.setGravity(referenceWorld.getGravity());

		// Update client frame number
		toSync.setFrameNumber(referenceWorld.getFrameNumber());

		// For each individual in the reference world, synchronize with the client counterpart.
		// If client does not contain the individual, add it
		referenceWorld.forEachIndividual(serverIndividual -> {
			Individual clientIndividual = toSync.getIndividual(serverIndividual.getUniqueIdentifier());
			if (clientIndividual == null) {
				toSync.addIndividual(serverIndividual);
			} else {
				individualSynchronizationService.sync(clientIndividual, serverIndividual, referenceWorld.getFrameNumber() - ping * 2 / WorldUpdateService.UPDATE_TICK + 1, ping * 2 / WorldUpdateService.UPDATE_TICK + 1);
			}
		});

		// For each individual in the client world, if it does not exist in the server world, remove it
		toSync.forEachIndividual(clientIndividual -> {
			Individual serverIndividual = referenceWorld.getIndividual(clientIndividual.getUniqueIdentifier());
			if (serverIndividual == null) {
				toSync.removeIndividual(clientIndividual.getUniqueIdentifier());
			}
		});
	}
}