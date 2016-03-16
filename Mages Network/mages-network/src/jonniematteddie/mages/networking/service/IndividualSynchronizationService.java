package jonniematteddie.mages.networking.service;

import java.util.Set;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import jonniematteddie.mages.character.model.Individual;
import jonniematteddie.mages.character.service.IndividualUpdateService;
import jonniematteddie.mages.networking.control.InputHistory;
import jonniematteddie.mages.networking.control.MagesInputProcessor;
import jonniematteddie.mages.networking.control.MappedKey;
import jonniematteddie.mages.networking.framework.ClientIDProvider;
import jonniematteddie.mages.world.model.World;

/**
 * Service responsible for synchronising two {@link World} instances
 *
 * @author Matt
 */
@Singleton
public class IndividualSynchronizationService {
	
	@Inject private IndividualUpdateService individualUpdateService;
	@Inject private MagesInputProcessor magesInputProcessor;
	@Inject private InputHistory inputHistory;
	@Inject private ClientIDProvider clientIDProvider;

	/**
	 * Synchronises a given {@link Individual} with a reference {@link Individual}
	 *
	 * @param toSync individual to sync
	 * @param referenceIndividual individual to use as reference
	 */
	public void sync(Individual toSync, Individual referenceIndividual, long fromFrameNumber, long numberOfFramesToProject) {
		
		for (int i = 1; i <= numberOfFramesToProject; i++) {
			Set<MappedKey> pressedKeys = inputHistory.getPressedKeys(fromFrameNumber + i);
			if (pressedKeys != null) {
				pressedKeys.forEach(key -> {
					magesInputProcessor.keyDown(key.getKeyCode(), clientIDProvider.getClientID());
				});
			}
			
			individualUpdateService.updateIndividual(referenceIndividual, 1);
		}
		
		toSync.getKinematicState().getPosition().set(referenceIndividual.getKinematicState().getPosition());
		toSync.getKinematicState().getVelocity().set(referenceIndividual.getKinematicState().getVelocity());
		toSync.getKinematicState().getAcceleration().set(referenceIndividual.getKinematicState().getAcceleration());
	}
}