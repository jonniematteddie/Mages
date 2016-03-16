package jonniematteddie.mages.networking.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import jonniematteddie.mages.character.model.Individual;
import jonniematteddie.mages.character.service.IndividualUpdateService;
import jonniematteddie.mages.world.model.World;

/**
 * Service responsible for synchronising two {@link World} instances
 *
 * @author Matt
 */
@Singleton
public class IndividualSynchronizationService {
	
	@Inject private IndividualUpdateService individualUpdateService;

	/**
	 * Synchronises a given {@link Individual} with a reference {@link Individual}
	 *
	 * @param toSync individual to sync
	 * @param referenceIndividual individual to use as reference
	 */
	public void sync(Individual toSync, Individual referenceIndividual, long fromFrameNumber, long numberOfFramesToProject) {
		individualUpdateService.updateIndividual(referenceIndividual, fromFrameNumber, (int) numberOfFramesToProject);
		
		toSync.getKinematicState().getPosition().set(referenceIndividual.getKinematicState().getPosition());
		toSync.getKinematicState().getVelocity().set(referenceIndividual.getKinematicState().getVelocity());
		toSync.getKinematicState().getAcceleration().set(referenceIndividual.getKinematicState().getAcceleration());
	}
}