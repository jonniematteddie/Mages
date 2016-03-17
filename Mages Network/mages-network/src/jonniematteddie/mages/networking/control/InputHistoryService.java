package jonniematteddie.mages.networking.control;

import java.util.Set;

import com.google.inject.Inject;

import jonniematteddie.mages.networking.framework.ClientIDProvider;

/**
 * Service which process input history.
 * 
 * @author Eddie
 *
 */
public class InputHistoryService {
	
	@Inject InputHistory inputHistory;
	@Inject MagesInputProcessor magesInputProcessor;
	@Inject ClientIDProvider clientIDProvider;
	
	
	/**
	 * Processes any key down strokes. This is meant to process a single frame of input.
	 * 
	 * @param keysToProcess
	 */
	public void processKeyDown(Set<MappedKey> keysToProcess) { 
		if (keysToProcess != null) {
			keysToProcess.forEach(key -> {
				magesInputProcessor.keyDown(key.getKeyCode(), clientIDProvider.getClientID());
			});
		}
	}
	
	
	/**
	 * Processes any key releases. This is meant to process a single frame of input.
	 * 
	 * @param keysToProcess
	 */
	public void processKeyUp(Set<MappedKey> keysToProcess) {
		if (keysToProcess != null) {
			keysToProcess.forEach(key -> {
				magesInputProcessor.keyUp(key.getKeyCode(), clientIDProvider.getClientID());
			});
		}
	}
}
