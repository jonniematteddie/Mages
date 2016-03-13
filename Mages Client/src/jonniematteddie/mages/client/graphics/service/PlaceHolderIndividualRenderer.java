package jonniematteddie.mages.client.graphics.service;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import jonniematteddie.mages.character.model.Individual;
import jonniematteddie.mages.client.graphics.GraphicsUtilities;
import jonniematteddie.mages.client.graphics.api.IndividualRenderer;

/**
 * Used to {@link IndividualRenderer}, place holder only until art becomes available
 *
 * @author Matt
 */
@Singleton
public class PlaceHolderIndividualRenderer implements IndividualRenderer {

	@Inject
	public PlaceHolderIndividualRenderer() {
	}
	
	@Override
	public void render(Individual individual) {
		GraphicsUtilities.getShaperenderer().begin(ShapeType.Filled);

		GraphicsUtilities.getShaperenderer().rect(
			individual.getKinematicState().getPosition().x,
			individual.getKinematicState().getPosition().y,
			16,
			32
		);
		
		GraphicsUtilities.getShaperenderer().end();
	}
}