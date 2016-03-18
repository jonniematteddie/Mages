package jonniematteddie.mages.world.model;

import java.io.Serializable;
import java.util.Map;

import jonniematteddie.mages.character.model.IndividualKinematicState;

/**
 * Class which stores the kinematic state of every individual.
 * 
 * @author Eddie
 */
class WorldState implements Serializable {
	private static final long serialVersionUID = -8106059492518950183L;

	private Map<Long, IndividualKinematicState> kinematicStates;
	private static float gravity = World.DEFAULT_GRAVITY;
	
	
	protected WorldState(Map<Long, IndividualKinematicState> kinematicStates) {
		this.kinematicStates = kinematicStates;
	}
	
	
	public IndividualKinematicState getIndividualKinematicState(Long individualIdentifier) {
		return kinematicStates.get(individualIdentifier);
	}

	
	/**
	 * @return The gravity of this world state.
	 */
	public static float getGravity() {
		return gravity;
	}


	/**
	 * @see #getGravity().
	 */
	public static void setGravity(float gravity) {
		WorldState.gravity = gravity;
	}
}
