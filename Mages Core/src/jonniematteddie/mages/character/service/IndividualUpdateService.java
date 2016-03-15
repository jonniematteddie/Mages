package jonniematteddie.mages.character.service;

import com.google.inject.Singleton;

import jonniematteddie.mages.character.model.Individual;
import jonniematteddie.mages.character.model.IndividualKinematicState;
import jonniematteddie.mages.world.service.WorldUpdateService;

/**
 * Service used to update a {@link Individual}
 *
 * @author Matt
 */
@Singleton
public class IndividualUpdateService {

	/**
	 * @param individual to update
	 * @param numberOfFrames the number of frames to update by
	 *
	 * @return The updated {@link IndividualKinematicState}, without setting it on the {@link Individual}
	 */
	public IndividualKinematicState updateKinematics(Individual individual, int numberOfFrames) {
		IndividualKinematicState newState = individual.getKinematicState().copy();

		for (int i = 0; i < numberOfFrames; i++) {
			newState.getPosition().x += newState.getVelocity().x * WorldUpdateService.UPDATE_TICK;
			newState.getPosition().y += newState.getVelocity().y * WorldUpdateService.UPDATE_TICK;

			newState.getVelocity().x += newState.getAcceleration().x * WorldUpdateService.UPDATE_TICK;
			newState.getVelocity().y += newState.getAcceleration().y * WorldUpdateService.UPDATE_TICK;
		}

		return newState;
	}


	/**
	 * Updates the given {@link Individual}, updating all fields
	 *
	 * @param individual to update
	 * @param numberOfFrames the number of frames to update by
	 */
	public void updateIndividual(Individual individual, int numberOfFrames) {
		IndividualKinematicState updatedKinematics = updateKinematics(individual, numberOfFrames);

		individual.getKinematicState().getPosition().set(updatedKinematics.getPosition());
		individual.getKinematicState().getVelocity().set(updatedKinematics.getVelocity());
		individual.getKinematicState().getAcceleration().set(updatedKinematics.getAcceleration());
	}
}