package careless.walker;

import careless.walker.Enums.MANTYPE;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Game {
	Device device;
	OrthographicCamera camera;
	Player bot;
	Texture cursor, temperature, heat;
	float max;
	float percent;
	boolean last_move_forward;
	Soldier man;
	
	public Game(Device device, OrthographicCamera camera){
		this.device = device;
		this.camera = camera;
		bot = new Player(device);	
		cursor = new Texture(Gdx.files.internal("data/walker/crosshair.png"));
		temperature = new Texture(Gdx.files.internal("data/walker/temperature.png"));
		heat = new Texture(Gdx.files.internal("data/walker/heat.png"));
		max =  temperature.getHeight();
		Gdx.input.setCursorPosition((int)bot.head_x, (int)bot.head_y);
		Gdx.input.setCursorCatched(!Gdx.input.isCursorCatched());
		
		// TEST PUT IN ARRAY
		man = new Soldier(MANTYPE.RIFLE);
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
		
		for (Bullet b:bot.gun.bulletList){
			if (b.hitbox.overlaps(man.hitbox)){
				man.die();
			}
		}
	}
	
	public boolean in_shoot_area(){
		return (bot.deg > -180 && bot.deg < -120) || (bot.deg > -60 && bot.deg < 180);
	}
	
	public void tick(float delta, SpriteBatch batch,GameController gc){
		// WEAPON
		bot.gun.tick(delta, batch);
		man.tick(delta, batch);
		batch.draw(cursor, Gdx.input.getX()-16,768 - Gdx.input.getY()-16, 32,32);
		batch.draw(temperature, 10,758-temperature.getHeight(), temperature.getWidth(), temperature.getHeight());
		
		percent = (bot.gun.heat / bot.gun.max_heat) * max;
		percent = percent > max ? max : percent;

		batch.draw(heat, 10,758-temperature.getHeight(), temperature.getWidth(),percent);
		
		// MOVE BOT - Should be in logic
		if (gc.move_left){
			last_move_forward = false;
			bot.x -= 4f;	
		} else if (gc.move_right){
			last_move_forward = true;
			bot.x += 4f;	
			delta -= 2*delta;
		} else if (last_move_forward && bot.frameNumber != 15 && bot.frameNumber != 6){
			bot.x += 4f;	
			delta -= 2*delta;
		} else if (!last_move_forward && bot.frameNumber != 15 && bot.frameNumber != 6){
			bot.x -= 4f;	
		} else {
			delta = 0;
		}
				
		bot.tick(delta, batch);
	}
	
}