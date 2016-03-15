package jonniematteddie.mages.world.model;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

import com.google.common.collect.Maps;

import jonniematteddie.mages.character.model.Individual;
import jonniematteddie.mages.character.model.PlayerControlledIndividual;

/**
 * A {@link World} holds information about:
 *
 * {@link Individual}s within this {@link World}
 *
 * @author Matt
 */
public class World implements Serializable {
	private static final long serialVersionUID = 1L;
	public static float DEFAULT_GRAVITY = 9.81f;

	private final Map<Long, Individual> individuals = Maps.newConcurrentMap();
	private final Map<Integer, Long> playerControlledIndividuals = Maps.newConcurrentMap();

	private float gravity;

	/**
	 * The frame number defines the game 'time'
	 */
	private AtomicLong frameNumber;

	/** No-arg constructor for Kryonet */
	private World() {}

	/**
	 * Private constructor, used by a builder
	 *
	 * @param gravity
	 */
	private World(float gravity) {
		this.gravity = gravity;
		this.frameNumber.set(System.currentTimeMillis());
	}


	/**
	 * @return see {@link #frameNumber}
	 */
	public long getFrameNumber() {
		return frameNumber.get();
	}


	/**
	 * @param individualConsumer {@link Consumer} to apply to each {@link Individual} of this {@link World}
	 */
	public void forEachIndividual(Consumer<Individual> individualConsumer) {
		for (Individual i : individuals.values()) {
			individualConsumer.accept(i);
		}
	}


	/**
	 * Gets an individual with the given unique identifier
	 *
	 * @param uniqueIdentifier of the individual to retrieve
	 * @return the individual with the given unique ID
	 */
	public Individual getIndividual(long uniqueIdentifier) {
		return individuals.get(uniqueIdentifier);
	}


	/**
	 * Gets a player controlled individual with the given client ID
	 *
	 * @param uniqueIdentifier of the individual to retrieve
	 * @return the individual with the given unique ID
	 */
	public Individual getClientControlledIndividual(int clientID) {
		return individuals.get(playerControlledIndividuals.get(clientID));
	}


	/**
	 * Removes an individual with the given unique identifier
	 *
	 * @param uniqueIdentifier of the individual to remove
	 * @return the removed individual
	 */
	public Individual removeIndividual(long uniqueIdentifier) {
		return individuals.remove(uniqueIdentifier);
	}


	/**
	 * Removes a player controlled individual with the given clientID
	 *
	 * @param clientID of the individual to remove
	 * @return the unique ID of the removed individual
	 */
	public long removePlayerControlledIndividual(int clientID) {
		return playerControlledIndividuals.remove(clientID);
	}


	/**
	 * Adds an {@link Individual} to this world
	 * @param toAdd {@link Individual} to add
	 */
	public void addIndividual(Individual toAdd) {
		individuals.put(toAdd.getUniqueIdentifier(), toAdd);

		if (toAdd instanceof PlayerControlledIndividual) {
			playerControlledIndividuals.put(((PlayerControlledIndividual) toAdd).getClientID(), toAdd.getUniqueIdentifier());
		}
	}


	public static class WorldBuilder {
		private Float gravity = DEFAULT_GRAVITY;


		public static WorldBuilder world() {
			return new WorldBuilder();
		}


		public WorldBuilder withGravity(float gravity) {
			this.gravity = gravity;
			return this;
		}


		public World build() {
			if (gravity == null) {
				throw new IllegalStateException("Gravity must not be null");
			}

			return new World(gravity);
		}
	}


	/**
	 * @return the gravitational acceleration value
	 */
	public float getGravity() {
		return gravity;
	}


	/**
	 * @param gravity to set
	 */
	public void setGravity(float gravity) {
		this.gravity = gravity;
	}
}