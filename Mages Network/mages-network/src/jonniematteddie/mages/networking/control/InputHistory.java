package jonniematteddie.mages.networking.control;

import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListMap;

import com.google.common.collect.Sets;
import com.google.inject.Singleton;

/**
 * Holds information about the input history of a client
 *
 *
 * Graphical example:
 *                                                     u              v          w             x
 * Key pressed:                     -----A---------B---|----A---------|----------|-------------|------------>
 * Key Released:                    ---------A---------|--------------|-------A--|---B---------|------------>
 *                                                     |              |          |             |
 * Input History key pressed Map:   ----[A]-[ ]---[B]--|---[A,B]------|------[B]-|--[ ]--------|------------>
 * Input History key released Map:  --------[A]--------|--------------|------[A]-|--[B]--------|------------>
 *
 * The keys pressed at frame number u: [B]
 * The keys pressed at frame number v: [A, B]
 * The keys pressed at frame number w: [B]
 * The keys pressed at frame number x: [ ]
 *
 * i.e. the pressed keys at a given frameNumber are given by the {@link Set} returned as a result of calling {@link ConcurrentSkipListMap#floorEntry(Object)} on the {@link #inputHistory} map
 *
 * Note:
 * The {@link #inputHistory} map won't hold all inputs from the beginning of time, there will be a maximum history, probably somewhere in the region of <10s
 *
 * @author Matt
 */
@Singleton
public class InputHistory {

	/** Given the game updates at ~60 fps, 10 seconds corresponds to 600 frames */
	private static final long MAXIMUM_HISTORIC_FRAMES = 600;

	/**
	 * Maps the frame number to the set of keys that are pressed down
	 */
	private final ConcurrentSkipListMap<Long, Set<MappedKey>> inputHistoryPressed = new ConcurrentSkipListMap<>();

	/**
	 * Maps the frame number to the set of keys that have been released
	 */
	private final ConcurrentSkipListMap<Long, Set<MappedKey>> inputHistoryReleased = new ConcurrentSkipListMap<>();


	/**
	 * Removes all entries from {@link #inputHistory} where the number of frames from the given reference frame exceeds {@link #MAXIMUM_HISTORIC_FRAMES}
	 *
	 * @param frameNumber to use as a reference point
	 */
	private void purge(long frameNumber) {
		inputHistoryPressed.headMap(frameNumber - MAXIMUM_HISTORIC_FRAMES).keySet().forEach(frame -> {
			inputHistoryPressed.remove(frame);
		});
		inputHistoryReleased.headMap(frameNumber - MAXIMUM_HISTORIC_FRAMES).keySet().forEach(frame -> {
			inputHistoryReleased.remove(frame);
		});
	}


	/**
	 * The current pressed keys are given by the {@link Set} returned as a result of calling {@link ConcurrentSkipListMap#floorEntry(Object)} on the {@link #inputHistory} map
	 *
	 * @param frameNumber to get pressed keys at
	 * @return set of keys held down at a given frame
	 */
	public Set<MappedKey> getPressedKeys(long frameNumber) {
		Entry<Long, Set<MappedKey>> floor = inputHistoryPressed.floorEntry(frameNumber);
		return floor == null ? Sets.newConcurrentHashSet() : floor.getValue();
	}


	/**
	 * The keys that were released at a given frame
	 *
	 * @param frameNumber to get released keys at
	 * @return set of {@link MappedKey} that have been released at a given frame
	 */
	public Set<MappedKey> getReleasedKeys(long frameNumber) {
		Set<MappedKey> released = inputHistoryReleased.get(frameNumber);
		return released == null ? Sets.newHashSet() : released;
	}


	/**
	 * Adds a new {@link MappedKey} to the {@link #inputHistory}
	 *
	 * @param frameNumber when the key was pressed
	 * @param pressed the {@link MappedKey} that was pressed
	 */
	public void keyPressed(long frameNumber, MappedKey pressed) {
		purge(frameNumber);
		if (inputHistoryPressed.containsKey(frameNumber)) {
			inputHistoryPressed.get(frameNumber).add(pressed);
		} else {
			Set<MappedKey> newPressedKeys = Sets.newConcurrentHashSet();
			Entry<Long, Set<MappedKey>> currentPressedKeys = inputHistoryPressed.floorEntry(frameNumber);

			if (currentPressedKeys != null) {
				newPressedKeys.addAll(currentPressedKeys.getValue());
			}

			newPressedKeys.add(pressed);
			inputHistoryPressed.put(frameNumber, newPressedKeys);
		}
	}


	/**
	 * Adds a new {@link MappedKey} to the {@link #inputHistory}
	 *
	 * @param frameNumber when the key was pressed
	 * @param pressed the {@link MappedKey} that was pressed
	 */
	public void keyReleased(long frameNumber, MappedKey pressed) {
		purge(frameNumber);
		if (inputHistoryPressed.containsKey(frameNumber)) {
			inputHistoryPressed.get(frameNumber).remove(pressed);
		} else {
			Set<MappedKey> newPressedKeys = Sets.newConcurrentHashSet();
			Entry<Long, Set<MappedKey>> currentPressedKeys = inputHistoryPressed.floorEntry(frameNumber);

			if (currentPressedKeys != null) {
				newPressedKeys.addAll(currentPressedKeys.getValue());
			}

			newPressedKeys.remove(pressed);
			inputHistoryPressed.put(frameNumber, newPressedKeys);
		}

		if (inputHistoryReleased.containsKey(frameNumber)) {
			inputHistoryReleased.get(frameNumber).add(pressed);
		} else {
			Set<MappedKey> newConcurrentHashSet = Sets.newConcurrentHashSet();
			newConcurrentHashSet.add(pressed);

			inputHistoryReleased.put(frameNumber, newConcurrentHashSet);
		}
	}
}