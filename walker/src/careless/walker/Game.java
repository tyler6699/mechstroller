package careless.walker;

import java.util.ArrayList;
import java.util.Collections;
import careless.walker.Enums.TYPE;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Game {
	Device device;
	OrthographicCamera camera;
	Player bot;
	Wave wave;
	Texture cursor, temperature, heat, background;
	float max;
	float percent;
	boolean last_move_forward;
	ArrayList<Entity> entities;
	
	public Game(Device device, OrthographicCamera camera){
		this.device = device;
		this.camera = camera;
		
		// All Entities
		entities = new ArrayList<Entity>();
		
		// Player
		bot = new Player(device);
		entities.add(bot);
		
		// WAVE ATTACKS
		wave = new Wave(device, entities);
		
		background = new Texture(Gdx.files.internal("data/walker/view.jpg"));
		cursor = new Texture(Gdx.files.internal("data/walker/crosshair.png"));
		temperature = new Texture(Gdx.files.internal("data/walker/temperature.png"));
		heat = new Texture(Gdx.files.internal("data/walker/heat.png"));
		max =  temperature.getHeight();
				
		Gdx.input.setCursorPosition((int)bot.head_x, (int)bot.head_y);
		Gdx.input.setCursorCatched(!Gdx.input.isCursorCatched());
	}
	
	public void tick(float delta, GameController gc){		
		// FIRE BOTS GUNS
		if (gc.LMB){
			if (bot.gun.isReady_to_fire() && bot.gun.heat <= bot.gun.max_heat && (in_shoot_area()) ){
				Bullet bullet = new Bullet(bot.head_x, bot.head_y - 8, Gdx.input.getX(), 768 - Gdx.input.getY(), 2, 2);
				bot.gun.bulletList.add(bullet);
				bullet = new Bullet(bot.head_x - 20, bot.head_y - 10, Gdx.input.getX(), 768 - Gdx.input.getY(), 2, 2);
				bot.gun.bulletList.add(bullet);
				
				// GUN LOGIC
				bot.gun.setReady_to_fire(false);
				bot.gun.heat += 80;
			}
		}
				
		// WEAPON
		bot.gun.tick(delta, gc);
	}
	
	public boolean in_shoot_area(){
		return (bot.deg > -180 && bot.deg < -120) || (bot.deg > -60 && bot.deg < 180);
	}

	public void tick(float delta, SpriteBatch batch,GameController gc){		
		batch.draw(background, 0,0,device.w,device.h);
		
		// HUD
		batch.draw(cursor, Gdx.input.getX()-16,768 - Gdx.input.getY()-16, 32,32);
		batch.draw(temperature, 10,758-temperature.getHeight(), temperature.getWidth(), temperature.getHeight());
				
		percent = (bot.gun.heat / bot.gun.max_heat) * max;
		percent = percent > max ? max : percent;
		batch.draw(heat, 10,758-temperature.getHeight(), temperature.getWidth(),percent);
				
		// DRAW & LOGIC
		wave.all_dead = true;
		
		for (Entity e : entities){
			if(e.type == TYPE.BOT){
				bot.tick(delta, batch, this, gc);
			} else if (e.type == TYPE.SOLDIER){
				//((Soldier) e).tick(delta, batch, bot);
				e.tick(delta, batch, bot);
				
				if (((Soldier) e).alive){
					wave.all_dead = false;
				}
			}
		}
		
		// WEAPON
		bot.gun.tick(delta, batch);
		
		// Y Sorting
		Collections.sort(entities);
		
		//WAVE
		wave.tick(entities);
	}
	
}