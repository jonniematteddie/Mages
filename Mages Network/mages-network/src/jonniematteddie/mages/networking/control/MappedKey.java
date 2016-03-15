package jonniematteddie.mages.networking.control;

import java.io.Serializable;
import java.util.Map;

import com.badlogic.gdx.Input;
import com.google.common.collect.Maps;

/**
 * Key mappings for input
 *
 * @author Matt
 */
public enum MappedKey implements Serializable {
	MOVE_UP(Input.Keys.W),
	MOVE_LEFT(Input.Keys.A),
	MOVE_DOWN(Input.Keys.S),
	MOVE_RIGHT(Input.Keys.D);

	private final int keyCode;
	private static Map<Integer, MappedKey> map;

	private MappedKey(int keyCode) {
		this.keyCode = keyCode;
		addToMap(keyCode);
	}

	private void addToMap(int keyCode) {
		if (map == null) {
			map = Maps.newHashMap();
		}
		MappedKey.map.put(keyCode, this);
	}


	/**
	 * @return the keyCode
	 */
	public int getKeyCode() {
		return keyCode;
	}


	/**
	 * @param keyCode
	 * @return the {@link MappedKey} for the given keyCode
	 */
	public static MappedKey getForKey(int keyCode) {
		return map.get(keyCode);
	}
}