package jonniematteddie.mages.character.api;

import jonniematteddie.mages.character.model.Individual;
import jonniematteddie.mages.character.model.IndividualKinematicState;

/**
 * Service used to update a {@link Individual}
 *
 * @author Matt
 */
public interface IndividualUpdateService {

	/**
	 * @param individual to update
	 * @param numberOfFrames the number of frames to update by
	 *
	 * @return The updated {@link IndividualKinematicState}, without setting it on the {@link Individual}
	 */
	public IndividualKinematicState updateKinematics(Individual individual, int numberOfFrames);
}