package jonniematteddie.mages.character.model;

import java.io.Serializable;

/**
 * Class representing an Individual, player or NPC controlled
 *
 * @author Matt
 */
public class Individual implements Serializable {
	private static final long serialVersionUID = -7562064661378311140L;

	private IndividualKinematicState kinematicState;
	private long uniqueIdentifier;

	/** No-arg constructor for Kryonet */
	Individual() {}

	/**
	 * Private constructor used by a builder
	 *
	 * @param kinematicState
	 */
	Individual(IndividualKinematicState kinematicState, long uniqueIdentifier) {
		this.kinematicState = kinematicState;
		this.uniqueIdentifier = uniqueIdentifier;
	}


	/**
	 * @return the {@link IndividualKinematicState} of this {@link Individual}
	 */
	public IndividualKinematicState getKinematicState() {
		return kinematicState;
	}


	/**
	 * @return the unique identifier associated with this {@link Individual}
	 */
	public long getUniqueIdentifier() {
		return uniqueIdentifier;
	}


	/**
	 * Builder class for {@link Individual}
	 *
	 * @author Matt
	 */
	public static class IndividualBuilder {
		protected IndividualKinematicState kinematicState;

		public static IndividualBuilder builder() {
			return new IndividualBuilder();
		}

		public IndividualBuilder withKinematicState(IndividualKinematicState kinematicState) {
			this.kinematicState = kinematicState;
			return this;
		}

		public Individual build() {
			if (kinematicState == null) {
				throw new IllegalStateException("Kinematic state must not be null");
			}

			return new Individual(
				kinematicState,
				System.currentTimeMillis() // TODO use a incrementing ID generator
			);
		}
	}
}