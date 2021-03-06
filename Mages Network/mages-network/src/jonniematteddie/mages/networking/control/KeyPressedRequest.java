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
	private long frameNumber;

	/**
	 * @param mappedKey of the key pressed/released
	 * @param pressed - true if pressed, false if released
	 */
	public KeyPressedRequest(MappedKey mappedKey, boolean pressed, long frameNumber) {
		super();
		this.mappedKey = mappedKey;
		this.pressed = pressed;
		this.frameNumber = frameNumber;
	}
	
	
	/**
	 * @return the frame number when this was pressed/released
	 */
	public long getFrameNumber() {
		return frameNumber;
	}


	@Override
	protected void internalPrepare() {
	}


	@Override
	public void receive() {
		if (pressed) {
			InjectionUtilities.inject(InputRequestHandler.class).handlePress(this, getClientId());
		} else {
			InjectionUtilities.inject(InputRequestHandler.class).handleRelease(this, getClientId());
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