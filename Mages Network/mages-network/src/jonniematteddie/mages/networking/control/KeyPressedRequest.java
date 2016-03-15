package jonniematteddie.mages.networking.control;

import jonniematteddie.mages.framework.InjectionUtilities;
import jonniematteddie.mages.networking.Request;
import jonniematteddie.mages.networking.Response;
import jonniematteddie.mages.networking.framework.DummyResponse;

/**
 * {@link Request} sent by client when a key is pressed
 *
 * @author Matt
 */
public class KeyPressedRequest extends Request {
	private static final long serialVersionUID = 4804317738948004502L;
	private MappedKey mappedKey;
	private boolean pressed;

	/**
	 * @param mappedKey of the key pressed/released
	 * @param pressed - true if pressed, false if released
	 */
	public KeyPressedRequest(MappedKey mappedKey, boolean pressed) {
		super();
		this.mappedKey = mappedKey;
		this.pressed = pressed;
	}


	@Override
	protected void internalPrepare() {
	}


	@Override
	public void receive() {
		if (pressed) {
			InjectionUtilities.inject(ClientInputRequestHandler.class).handlePress(this, getClientId());
		} else {
			InjectionUtilities.inject(ClientInputRequestHandler.class).handleRelease(this, getClientId());
		}
	}


	@Override
	public Response prepareResponse() {
		return new DummyResponse(getRequestId());
	}


	/**
	 * @return the {@link MappedKey} that has been pressed
	 */
	public MappedKey getMappedKey() {
		return mappedKey;
	}


	/**
	 * @return true if pressed, false if released
	 */
	public boolean pressed() {
		return pressed;
	}
}