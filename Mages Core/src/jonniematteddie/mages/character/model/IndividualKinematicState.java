package jonniematteddie.mages.character.model;

import com.badlogic.gdx.math.Vector2;

/**
 * Kinematic state of an {@link Individual}
 *
 * @author Matt
 */
public class IndividualKinematicState {
	private final Vector2 position, velocity, acceleration;

	/**
	 * Private constructor, used only by a builder
	 *
	 * @param position
	 * @param velocity
	 * @param acceleration
	 */
	private IndividualKinematicState(Vector2 position, Vector2 velocity, Vector2 acceleration) {
		this.position = position;
		this.velocity = velocity;
		this.acceleration = acceleration;
	}


	public Vector2 getPosition() {
		return position;
	}


	public Vector2 getVelocity() {
		return velocity;
	}


	public Vector2 getAcceleration() {
		return acceleration;
	}


	/**
	 * Builder of {@link IndividualKinematicState}
	 *
	 * @author Matt
	 */
	public static final class IndividualKinematicStateBuilder {
		private Vector2 position, velocity, acceleration;

		public IndividualKinematicStateBuilder withPosition(Vector2 position) {
			this.position = position;
			return this;
		}

		public IndividualKinematicStateBuilder withVelocity(Vector2 velocity) {
			this.velocity = velocity;
			return this;
		}

		public IndividualKinematicStateBuilder withAcceleration(Vector2 accerlation) {
			acceleration = accerlation;
			return this;
		}

		public IndividualKinematicState build() {
			if (position == null || velocity == null || acceleration == null) {
				throw new IllegalStateException("Position, Velocity and Acceleration must not be null");
			}

			return new IndividualKinematicState(position, velocity, acceleration);
		}
	}
}
