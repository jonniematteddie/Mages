package jonniematteddie.mages.client.application;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;

import jonniematteddie.mages.client.graphics.api.IndividualRenderer;
import jonniematteddie.mages.client.graphics.service.PlaceHolderIndividualRenderer;

/**
 * The game client for Mages
 *
 * @author Matt
 */
public class MagesClient implements ApplicationListener {

	/**
	 * THE Injector
	 */
	private static Injector injector;
	
	@Override
	public void create() {
		// Set up the injector
		injector = Guice.createInjector(new ClientModule());
		
		// Bind the input processor
		Gdx.input.setInputProcessor(new ClientInputProcessor());
	}


	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
	}


	@Override
	public void render() {
		// TODO Auto-generated method stub
	}


	@Override
	public void pause() {
		// TODO Auto-generated method stub
	}


	@Override
	public void resume() {
		// TODO Auto-generated method stub
	}


	@Override
	public void dispose() {
		// TODO Auto-generated method stub
	}


	/**
	 * @return THE Injector
	 */
	public static Injector getInjector() {
		return injector;
	}
}