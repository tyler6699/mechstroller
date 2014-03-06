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
	GameController gc;
		
	@Override
	public void create() {		
		GLTexture.setEnforcePotImages(false);
		device = new Device();
		camera = new OrthographicCamera(device.w, device.h);
		camera.position.set(device.w/2, device.h/2, 0);
		batch = new SpriteBatch();
		game = new Game(device, camera);
		gc = new GameController(camera);
		Gdx.input.setInputProcessor(gc);
	}

	@Override
	public void render() {		
		float delta = Gdx.graphics.getDeltaTime();
		Gdx.gl.glClearColor(0,0,0,0);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		camera.update();
		
		game.tick(delta,gc);
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
			game.tick(delta, batch, gc);
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
