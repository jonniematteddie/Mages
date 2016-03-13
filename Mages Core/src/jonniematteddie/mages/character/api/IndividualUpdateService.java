package jonniematteddie.mages.character.api;

import jonniematteddie.mages.character.model.Individual;

/**
 * Service used to update a {@link Individual}
 *
 * @author Matt
 */
public interface IndividualUpdateService {

	/**
	 * @param deltaSeconds, the time step used to update the character, measured in seconds
	 */
	public default void update(Individual individual, float deltaSeconds) {
		individual.getKinematicState().getPosition().x += individual.getKinematicState().getVelocity().x * deltaSeconds;
		individual.getKinematicState().getPosition().y += individual.getKinematicState().getVelocity().y * deltaSeconds;

		individual.getKinematicState().getVelocity().x += individual.getKinematicState().getAcceleration().x * deltaSeconds;
		individual.getKinematicState().getVelocity().y += individual.getKinematicState().getAcceleration().y * deltaSeconds;
	}
}