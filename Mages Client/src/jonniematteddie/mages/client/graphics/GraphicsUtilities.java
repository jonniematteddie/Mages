package jonniematteddie.mages.client.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Class containing utility methods for rendering
 *
 * @author Matt
 */
public class GraphicsUtilities {

	private static final ShapeRenderer shapeRenderer = new ShapeRenderer();

	/**
	 * @return the {@link ShapeRenderer}
	 */
	public static ShapeRenderer getShaperenderer() {
		return shapeRenderer;
	}


	/**
	 * Clears the current active buffer
	 */
	public static void clear() {
		Gdx.gl.glClearColor(0f, 0f, 0f, 0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}
}