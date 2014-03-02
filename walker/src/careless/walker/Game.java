package careless.walker;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Game {
	Device device;
	OrthographicCamera camera;
	Player bot;
	
	public Game(Device device, OrthographicCamera camera){
		this.device = device;
		this.camera = camera;
		bot = new Player(device);	
	}
	
	public void tick(float delta, SpriteBatch batch){
		bot.tick(delta, batch);
	}
}