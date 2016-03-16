package jonniematteddie.mages.networking.control;

import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for {@link InputHistory}
 *
 * @author Matt
 */
public class TestInputHistory {

	/**
	 * Tests this case:
     *                                         u              v          w             x             y            z
     * Key pressed:         -----A---------B---|----A---------|----------|-------------|-----A-------|------A-----|--->
     * Key Released:        ---------A---------|--------------|-------A--|---B---------|-----B-------|------B-----|--->
     *                                         |              |          |             |             |            |
     * Input History Map:   ----[A]-[ ]---[B]--|---[A,B]------|------[B]-|--[ ]--------|----[A, B]---|-----[ ]----|--->
     *
     * The keys pressed at frame number u: [B]
     * The keys pressed at frame number v: [A, B]
     * The keys pressed at frame number w: [B]
     * The keys pressed at frame number x: [ ]
     * The keys pressed at frame number y: [A, B]
     * The keys pressed at frame number z: [ ]
	 */
	@Test
	public void testInputHistory() {
		InputHistory testSubject = new InputHistory();

		MappedKey A = MappedKey.MOVE_DOWN;
		MappedKey B = MappedKey.MOVE_UP;

		testSubject.keyPressed(0, A);
		testSubject.keyReleased(1, A);
		testSubject.keyPressed(2, B);

		// Assertion at point u
		Set<MappedKey> pressedKeysAtU = testSubject.getPressedKeys(3);
		Assert.assertTrue("Expected only one key to be pressed here", pressedKeysAtU.size() == 1);
		Assert.assertTrue("Expected B to be pressed here", pressedKeysAtU.contains(B));

		testSubject.keyPressed(4, A);

		// Assertion at point v
		Set<MappedKey> pressedKeysAtV = testSubject.getPressedKeys(5);
		Assert.assertTrue("Expected two keys to be pressed here", pressedKeysAtV.size() == 2);
		Assert.assertTrue("Expected A to be pressed here", pressedKeysAtV.contains(A));
		Assert.assertTrue("Expected B to be pressed here", pressedKeysAtV.contains(B));

		testSubject.keyReleased(6, A);

		// Assertion at point w
		Set<MappedKey> pressedKeysAtW = testSubject.getPressedKeys(7);
		Assert.assertTrue("Expected two keys to be pressed here", pressedKeysAtW.size() == 1);
		Assert.assertTrue("Expected B to be pressed here", pressedKeysAtW.contains(B));

		testSubject.keyReleased(8, B);

		// Assertion at point x
		Set<MappedKey> pressedKeysAtX = testSubject.getPressedKeys(9);
		Assert.assertTrue("Expected no keys to be pressed here", pressedKeysAtX.isEmpty());

		testSubject.keyPressed(10, A);
		testSubject.keyPressed(10, B);

		// Assertion at point y
		Set<MappedKey> pressedKeysAtY = testSubject.getPressedKeys(11);
		Assert.assertTrue("Expected two keys to be pressed here", pressedKeysAtY.size() == 2);
		Assert.assertTrue("Expected A to be pressed here", pressedKeysAtY.contains(A));
		Assert.assertTrue("Expected B to be pressed here", pressedKeysAtY.contains(B));

		testSubject.keyReleased(12, A);
		testSubject.keyReleased(12, B);

		// Assertion at point z
		Set<MappedKey> pressedKeysAtZ = testSubject.getPressedKeys(13);
		Assert.assertTrue("Expected two keys to be pressed here", pressedKeysAtZ.isEmpty());

		// Assert again just to test previous histories
		// --------------------------------------------------------------------------------------
		// Assertion at point u
		Set<MappedKey> pressedKeysAtU2 = testSubject.getPressedKeys(3);
		Assert.assertTrue("Expected only one key to be pressed here", pressedKeysAtU2.size() == 1);
		Assert.assertTrue("Expected B to be pressed here", pressedKeysAtU2.contains(B));

		// Assertion at point v
		Set<MappedKey> pressedKeysAtV2 = testSubject.getPressedKeys(5);
		Assert.assertTrue("Expected two keys to be pressed here", pressedKeysAtV2.size() == 2);
		Assert.assertTrue("Expected A to be pressed here", pressedKeysAtV2.contains(A));
		Assert.assertTrue("Expected B to be pressed here", pressedKeysAtV2.contains(B));

		// Assertion at point w
		Set<MappedKey> pressedKeysAtW2 = testSubject.getPressedKeys(7);
		Assert.assertTrue("Expected two keys to be pressed here", pressedKeysAtW2.size() == 1);
		Assert.assertTrue("Expected B to be pressed here", pressedKeysAtW2.contains(B));

		// Assertion at point x
		Set<MappedKey> pressedKeysAtX2 = testSubject.getPressedKeys(9);
		Assert.assertTrue("Expected no keys to be pressed here", pressedKeysAtX2.isEmpty());

		// Assertion at point y
		Set<MappedKey> pressedKeysAtY2 = testSubject.getPressedKeys(11);
		Assert.assertTrue("Expected two keys to be pressed here", pressedKeysAtY2.size() == 2);
		Assert.assertTrue("Expected A to be pressed here", pressedKeysAtY2.contains(A));
		Assert.assertTrue("Expected B to be pressed here", pressedKeysAtY2.contains(B));

		// Assertion at point z
		Set<MappedKey> pressedKeysAtZ2 = testSubject.getPressedKeys(13);
		Assert.assertTrue("Expected two keys to be pressed here", pressedKeysAtZ2.isEmpty());

		// Check the released keys
		// --------------------------------------------------------------------------------------
		Assert.assertTrue("Expected no keys released at this frame", testSubject.getReleasedKeys(0).isEmpty());
		Assert.assertTrue("Expected A to be released", testSubject.getReleasedKeys(1).contains(A));
		Assert.assertTrue("Expected no keys released at this frame", testSubject.getReleasedKeys(2).isEmpty());
		Assert.assertTrue("Expected no keys released at this frame", testSubject.getReleasedKeys(3).isEmpty());
		Assert.assertTrue("Expected no keys released at this frame", testSubject.getReleasedKeys(4).isEmpty());
		Assert.assertTrue("Expected no keys released at this frame", testSubject.getReleasedKeys(5).isEmpty());
		Assert.assertTrue("Expected A to be released", testSubject.getReleasedKeys(6).contains(A));
		Assert.assertTrue("Expected no keys released at this frame", testSubject.getReleasedKeys(7).isEmpty());
		Assert.assertTrue("Expected B to be released", testSubject.getReleasedKeys(8).contains(B));
		Assert.assertTrue("Expected no keys released at this frame", testSubject.getReleasedKeys(9).isEmpty());
		Assert.assertTrue("Expected no keys released at this frame", testSubject.getReleasedKeys(10).isEmpty());
		Assert.assertTrue("Expected no keys released at this frame", testSubject.getReleasedKeys(11).isEmpty());
		Assert.assertTrue("Expected A and B to be released", testSubject.getReleasedKeys(12).contains(A) && testSubject.getReleasedKeys(12).contains(B));
		Assert.assertTrue("Expected no keys released at this frame", testSubject.getReleasedKeys(13).isEmpty());

		// Purging, key pressed at frame 605, meaning all histories < frame number 5 are deleted
		// --------------------------------------------------------------------------------------
		testSubject.keyPressed(605, A);
		// Assertion at point u
		Set<MappedKey> pressedKeysAtU3 = testSubject.getPressedKeys(3);
		Assert.assertTrue("Should've been purged", pressedKeysAtU3.isEmpty());

		// Assertion at point v
		Set<MappedKey> pressedKeysAtV3 = testSubject.getPressedKeys(5);
		Assert.assertTrue("Should've been purged", pressedKeysAtV3.isEmpty());

		// Assertion at point w
		Set<MappedKey> pressedKeysAtW3 = testSubject.getPressedKeys(7);
		Assert.assertTrue("Expected two keys to be pressed here", pressedKeysAtW3.size() == 1);
		Assert.assertTrue("Expected B to be pressed here", pressedKeysAtW3.contains(B));

		// Assertion at point x
		Set<MappedKey> pressedKeysAtX3 = testSubject.getPressedKeys(9);
		Assert.assertTrue("Expected no keys to be pressed here", pressedKeysAtX3.isEmpty());

		// Assertion at point y
		Set<MappedKey> pressedKeysAtY3 = testSubject.getPressedKeys(11);
		Assert.assertTrue("Expected two keys to be pressed here", pressedKeysAtY3.size() == 2);
		Assert.assertTrue("Expected A to be pressed here", pressedKeysAtY3.contains(A));
		Assert.assertTrue("Expected B to be pressed here", pressedKeysAtY3.contains(B));

		// Assertion at point z
		Set<MappedKey> pressedKeysAtZ3 = testSubject.getPressedKeys(13);
		Assert.assertTrue("Expected two keys to be pressed here", pressedKeysAtZ3.isEmpty());

		Assert.assertTrue("Expected no keys released at this frame", testSubject.getReleasedKeys(1).isEmpty());
		Assert.assertTrue("Expected A to be released", testSubject.getReleasedKeys(6).contains(A));
		Assert.assertTrue("Expected B to be released", testSubject.getReleasedKeys(8).contains(B));
		Assert.assertTrue("Expected A and B to be released", testSubject.getReleasedKeys(12).contains(A) && testSubject.getReleasedKeys(12).contains(B));
	}
}