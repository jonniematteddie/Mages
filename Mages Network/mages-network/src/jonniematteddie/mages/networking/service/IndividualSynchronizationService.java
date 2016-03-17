package jonniematteddie.mages.networking.service;

import java.util.Set;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import jonniematteddie.mages.character.model.Individual;
import jonniematteddie.mages.character.service.IndividualUpdateService;
import jonniematteddie.mages.networking.control.InputHistory;
import jonniematteddie.mages.networking.control.InputHistoryService;
import jonniematteddie.mages.networking.control.MappedKey;
import jonniematteddie.mages.world.model.World;

/**
 * Service responsible for synchronising two {@link World} instances
 *
 * @author Matt
 */
@Singleton
public class IndividualSynchronizationService {

	@Inject private IndividualUpdateService individualUpdateService;
	@Inject private InputHistory inputHistory;
	@Inject private InputHistoryService inputHistoryService;

	/**
	 * Synchronises a given {@link Individual} with a reference {@link Individual}
	 *
	 * @param toSync individual to sync
	 * @param referenceIndividual individual to use as reference
	 */
	public void sync(Individual toSync, Individual referenceIndividual, long fromFrameNumber, long numberOfFramesToProject) {
		if (numberOfFramesToProject > 0) {
			for (int i = 0; i <= numberOfFramesToProject; i++) {
				// Process down key strokes
				Set<MappedKey> pressedKeys = inputHistory.getPressedKeys(fromFrameNumber + i);
				inputHistoryService.processKeyDown(pressedKeys);

				// Process key releases.
				Set<MappedKey> releasedKeys = inputHistory.getReleasedKeys(fromFrameNumber + i);
				inputHistoryService.processKeyUp(releasedKeys);

				// Update the individual, given the inputs
				individualUpdateService.updateIndividual(referenceIndividual, 1);
			}
		}

		toSync.getKinematicState().getPosition().set(referenceIndividual.getKinematicState().getPosition());
		// toSync.getKinematicState().getVelocity().set(referenceIndividual.getKinematicState().getVelocity());
		// toSync.getKinematicState().getAcceleration().set(referenceIndividual.getKinematicState().getAcceleration());
	}
}