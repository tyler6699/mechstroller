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
	
	
	public void tick(float delta, GameController gc){
		if (gc.move_left){
			bot.x -= 1.5f;	
		} else if (gc.move_right){
			bot.x += 1.5f;	
		}
	}
	
	public void tick(float delta, SpriteBatch batch,GameController gc){
		if (gc.move_left){
			bot.x -= 1.5f;	
		} else if (gc.move_right){
			bot.x += 1.5f;	
			delta -= 2*delta;
		} else {
			delta = 0;
		}
		bot.tick(delta, batch);
	}
	
}