package jonniematteddie.mages.character.model;

/**
 * A player controllable {@link Individual}
 *
 * @author Matt
 */
public class PlayerControlledIndividual extends Individual {
	private static final long serialVersionUID = -4746032455639081071L;

	/** The client that controls this {@link PlayerControlledIndividual} */
	private int clientID;

	/** No-arg constructor for Kryonet */
	PlayerControlledIndividual() {}

	/**
	 * Private constructor used by a builder
	 *
	 * @param kinematicState
	 */
	PlayerControlledIndividual(IndividualKinematicState kinematicState, long uniqueIdentifier, int clientID) {
		super(kinematicState, uniqueIdentifier);
		this.clientID = clientID;
	}


	/**
	 * @return The client that controls this {@link PlayerControlledIndividual}/
	 */
	public int getClientID() {
		return clientID;
	}


	/**
	 * Builder class for {@link PlayerControlledIndividual}
	 *
	 * @author Matt
	 */
	public static class PlayerControlledIndividualBuilder extends IndividualBuilder {
		private int clientID;

		public static PlayerControlledIndividualBuilder bulder() {
			return new PlayerControlledIndividualBuilder();
		}

		public PlayerControlledIndividualBuilder withClientID(int clientID) {
			this.clientID = clientID;
			return this;
		}

		@Override
		public PlayerControlledIndividual build() {
			return new PlayerControlledIndividual(
				kinematicState,
				System.currentTimeMillis(), // TODO use a incrementing ID generator
				clientID
			);
		}
	}
}
