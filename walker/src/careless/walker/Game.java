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
	Art art;
	HiFi hifi;
	
	Entity shop_1, shop_2, shop_3;
	boolean play_rifle;
	
	public Game(Device device, OrthographicCamera camera, Art art){
		this.device = device;
		this.camera = camera;
		this.art = art;
		this.hifi = new HiFi();
		// All Entities
		entities = new ArrayList<Entity>();
		
		// Player
		bot = new Player(device);
		entities.add(bot);
		
		// SHOPS
		shop_1 = new Entity();
		shop_1.x = 40;
		shop_1.y = 200;
		shop_1.texture = new Texture(Gdx.files.internal("data/walker/cyber-shop.png"));
		shop_1.w = shop_1.texture.getWidth();
		shop_1.h = shop_1.texture.getHeight();
		
		shop_2 = new Entity();
		shop_2.x = shop_1.x + shop_1.w + 80;
		shop_2.y = 200;
		shop_2.texture = new Texture(Gdx.files.internal("data/walker/careless-shop.png"));
		shop_2.w = shop_2.texture.getWidth();
		shop_2.h = shop_2.texture.getHeight();
		
		shop_3 = new Entity();
		shop_3.x = shop_2.x + shop_2.w + 70;
		shop_3.y = 200;
		shop_3.texture = new Texture(Gdx.files.internal("data/walker/pixel-shop.png"));
		shop_3.w = shop_3.texture.getWidth();
		shop_3.h = shop_3.texture.getHeight();

		// WAVE ATTACKS
		wave = new Wave(device, entities);
		
		background = new Texture(Gdx.files.internal("data/walker/background.png"));
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

				// SOUND
				hifi.play_gattling(true);
				
				Bullet bullet = new Bullet(bot.head_x, bot.head_y - 8, Gdx.input.getX(), 768 - Gdx.input.getY(), 2, 2, 1, 20, bot.gun.range);
				bullet.friendly = true;
				bot.gun.bulletList.add(bullet);
				bullet = new Bullet(bot.head_x - 20, bot.head_y - 10, Gdx.input.getX(), 768 - Gdx.input.getY(), 2, 2, 1, 20, bot.gun.range);
				bullet.friendly = true;
				bot.gun.bulletList.add(bullet);
				
				// GUN LOGIC
				bot.gun.setReady_to_fire(false);
				bot.gun.heat += 80;
			} else if (bot.gun.heat >= bot.gun.max_heat)   {
				hifi.stop_gattling(true);
			}
		} else {
			hifi.stop_gattling(true);
		}
				
		// WEAPON
		bot.gun.tick(delta, gc);
		
		// CHECK SHOP X
		if (shop_1.x + shop_1.w < 0){
			shop_1.x = 1.3f*device.h ;
		}
		
		if (shop_2.x + shop_2.w < 0){
			shop_2.x = shop_1.x + shop_1.w + 80;
		}
		
		if (shop_3.x + shop_3.w < 0){
			shop_3.x = shop_2.x + shop_2.w + 70;
		}
	}
	
	public boolean in_shoot_area(){
		return (bot.deg > -180 && bot.deg < -120) || (bot.deg > -60 && bot.deg < 180);
	}

	public void tick(float delta, SpriteBatch batch,GameController gc){		
		batch.draw(background, 0,0,device.w,device.h);
		batch.draw(shop_1.texture, shop_1.x,shop_1.y,shop_1.w,shop_1.h);
		batch.draw(shop_2.texture, shop_2.x,shop_2.y,shop_2.w,shop_2.h);
		batch.draw(shop_3.texture, shop_3.x,shop_3.y,shop_3.w,shop_3.h);
		
		// HUD
		batch.draw(cursor, Gdx.input.getX()-16,768 - Gdx.input.getY()-16, 32,32);
		batch.draw(temperature, 10,758-temperature.getHeight(), temperature.getWidth(), temperature.getHeight());
		batch.draw(temperature, device.w - temperature.getWidth() - 10 ,758-temperature.getHeight(), temperature.getWidth(), temperature.getHeight());
		
		percent = (bot.gun.heat / bot.gun.max_heat) * max;
		percent = percent > max ? max : percent;
		batch.draw(heat, 10,758-temperature.getHeight(), temperature.getWidth(),percent);
				
		// DRAW & LOGIC
		wave.all_dead = true;
		play_rifle = false;
		
		for (Entity e : entities){
			if(e.type == TYPE.BOT){
				bot.tick(delta, batch, this, gc);
			} else if (e.type == TYPE.SOLDIER){
				//((Soldier) e).tick(delta, batch, bot);
				e.tick(delta, batch, bot, entities);
				
				if (e.alive && e.liveable){
					wave.all_dead = false;
					play_rifle = true;
				}
			}
		}
		
		if (play_rifle){
			//HiFi.play_ak47(true);
		}
		// WEAPON
		bot.gun.tick(delta, batch);
		
		// Y Sorting
		Collections.sort(entities);
		
		//WAVE
		wave.tick(entities);
	}
	
}