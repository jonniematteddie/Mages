package jonniematteddie.mages.world.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import jonniematteddie.mages.character.model.Individual;
import jonniematteddie.mages.character.model.IndividualKinematicState;

public class WorldState implements Serializable {
	private static final long serialVersionUID = -8106059492518950183L;

	private Map<Long, IndividualKinematicState> kinematicStates;
	
	protected WorldState(Map<Long, IndividualKinematicState> kinematicStates) {
		this.kinematicStates = kinematicStates;
	}
}
