package jonniematteddie.mages.client.graphics.api;

import com.google.inject.ImplementedBy;

import jonniematteddie.mages.character.model.Individual;
import jonniematteddie.mages.client.graphics.service.PlaceHolderIndividualRenderer;

/**
 * Class handles the rendering of {@link Individual}
 *
 * @author Matt
 */
@ImplementedBy(PlaceHolderIndividualRenderer.class)
public interface IndividualRenderer {

	/**
	 * @param individual to render
	 */
	public void render(Individual individual);
}