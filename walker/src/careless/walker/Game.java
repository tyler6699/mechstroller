package careless.walker;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Game {
	Device device;
	OrthographicCamera camera;
	Player bot;
	Texture cursor, temperature, heat;
	
	public Game(Device device, OrthographicCamera camera){
		this.device = device;
		this.camera = camera;
		bot = new Player(device);	
		cursor = new Texture(Gdx.files.internal("data/walker/crosshair.png"));
		temperature = new Texture(Gdx.files.internal("data/walker/temperature.png"));
		heat = new Texture(Gdx.files.internal("data/walker/heat.png"));
		Gdx.input.setCursorCatched(!Gdx.input.isCursorCatched());
	}
	
	public void tick(float delta, GameController gc){
		// MOVE BOT
		if (gc.move_left){
			bot.x -= 1.5f;	
		} else if (gc.move_right){
			bot.x += 1.5f;	
		}
		
		// FIRE BOTS GUNS
		if (gc.LMB){
			if (bot.gun.isReady_to_fire() && bot.gun.heat <= bot.gun.max_heat){
				Bullet bullet = new Bullet(bot.head_x, bot.head_y - 8, Gdx.input.getX(), 768 - Gdx.input.getY(), 2, 2);
				bot.gun.bulletList.add(bullet);
				bullet = new Bullet(bot.head_x - 20, bot.head_y - 10, Gdx.input.getX(), 768 - Gdx.input.getY(), 2, 2);
				bot.gun.bulletList.add(bullet);
				
				// GUN LOGIC
				bot.gun.setReady_to_fire(false);
				bot.gun.heat += 100;
			}
		}
		
		// WEAPON
		bot.gun.tick(delta, gc);
	}
	
	public void tick(float delta, SpriteBatch batch,GameController gc){
		// WEAPON
		bot.gun.tick(delta, batch);
		
		batch.draw(cursor, Gdx.input.getX()-16,768 - Gdx.input.getY()-16, 32,32);
		batch.draw(temperature, 10,758-temperature.getHeight(), temperature.getWidth(), temperature.getHeight());
		
		float max =  temperature.getHeight();
		float percent = (bot.gun.heat / bot.gun.max_heat) * max;
		percent = percent > max ? max : percent;
		//System.out.println(percent);
		batch.draw(heat, 10,758-temperature.getHeight(), temperature.getWidth(),percent);
		
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