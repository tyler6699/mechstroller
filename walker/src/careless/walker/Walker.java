package careless.walker;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GLTexture;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Walker implements ApplicationListener {
	private OrthographicCamera camera;
	private SpriteBatch batch;
	Game game;		// MAIN GAME
	Device device;  // SCREEN & TOUCH 
	
	@Override
	public void create() {		
		GLTexture.setEnforcePotImages(false);
		device = new Device();
		
		camera = new OrthographicCamera(device.w, device.h);
		camera.position.set(device.w/2, device.h/2, 0);
				
		System.out.println(camera.viewportWidth + " " + camera.viewportHeight + " " + camera.position.x + " " + camera.position.y);
		batch = new SpriteBatch();
		game = new Game(device, camera);
	}

	@Override
	public void render() {		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
			game.tick(Gdx.graphics.getDeltaTime(), batch);
		batch.end();
	}

	@Override
	public void dispose() {

	}
	
	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
