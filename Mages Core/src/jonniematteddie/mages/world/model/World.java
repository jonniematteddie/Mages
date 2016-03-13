package jonniematteddie.mages.world.model;

import java.util.Collection;
import java.util.function.Consumer;

import com.google.common.collect.Lists;

import jonniematteddie.mages.character.model.Individual;

/**
 * A {@link World} holds information about:
 *
 * {@link Individual}s within this {@link World}
 *
 * @author Matt
 */
public class World {

	private final Collection<Individual> individuals = Lists.newLinkedList();
	private final float gravity;

	/**
	 * Private constructor, used by a builder
	 *
	 * @param gravity
	 */
	private World(float gravity) {
		this.gravity = gravity;
	}


	/**
	 * @return the gravitational acceleration value
	 */
	public float getGravity() {
		return gravity;
	}


	/**
	 * @param individualConsumer {@link Consumer} to apply to each {@link Individual} of this {@link World}
	 */
	public void forEachIndividual(Consumer<Individual> individualConsumer) {
		for (Individual i : individuals) {
			individualConsumer.accept(i);
		}
	}


	/**
	 * Adds an {@link Individual} to this world
	 * @param toAdd {@link Individual} to add
	 */
	public void addIndividual(Individual toAdd) {
		individuals.add(toAdd);
	}


	public static class WorldBuilder {
		private Float gravity;

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
}