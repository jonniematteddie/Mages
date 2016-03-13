package jonniematteddie.mages.character.model;

/**
 * Class representing an Individual, player or NPC controlled
 *
 * @author Matt
 */
public class Individual {
	private final IndividualKinematicState kinematicState;

	/**
	 * Private constructor used by a builder
	 *
	 * @param kinematicState
	 */
	private Individual(IndividualKinematicState kinematicState) {
		this.kinematicState = kinematicState;
	}


	/**
	 * @return the {@link IndividualKinematicState} of this {@link Individual}
	 */
	public IndividualKinematicState getKinematicState() {
		return kinematicState;
	}


	/**
	 * Builder class for {@link Individual}
	 *
	 * @author Matt
	 */
	public static class IndividualBuilder {
		private IndividualKinematicState kinematicState;

		public IndividualBuilder withKinematicState(IndividualKinematicState kinematicState) {
			this.kinematicState = kinematicState;
			return this;
		}

		public Individual build() {
			if (kinematicState == null) {
				throw new IllegalStateException("Kinematic state must not be null");
			}

			return new Individual(kinematicState);
		}
	}
}