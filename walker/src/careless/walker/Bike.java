package careless.walker;

import java.util.ArrayList;

import careless.walker.Enums.FACING;
import careless.walker.Enums.MANTYPE;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Bike extends Soldier{
		
	public Bike(Device device){
		super(MANTYPE.BIKE, device);
		hp = 25;
		w=124;
		h=62;
	
		if (device.random_int(0,100) > 50){
			x 		 = -200;//device.w + device.random_int(0,100);
			dest_x   = device.random_int(device.w-w, device.w/4);
			y 		 = device.random_int(0,200);
			dest_y = y;
			run_right = true;
			direction = FACING.RIGHT;
		} else {
			x 		 = device.w + device.random_int(0,100);
			dest_x   = device.random_int(device.w/4, device.w-w);
			y 		 = device.random_int(0,200);
			dest_y = y;
			run_left = true;
			direction = FACING.LEFT;
		}
		
		hitbox = new Rectangle(x, y, w, h);
		actions = Art.bike;
		
		gun = new Weapon(100,1, 6, 10, 45, 5);
		shoot_x = x - 5;
		shoot_y = y + 5;
		
		run_left_t 		 = TextureRegion.split(actions, (int)w, (int)h)[1];
		run_right_t 	 = TextureRegion.split(actions, (int)w, (int)h)[0];
		shoot_left_t 	 = TextureRegion.split(actions, (int)w, (int)h)[3];
		shoot_right_t 	 = TextureRegion.split(actions, (int)w, (int)h)[2];
		die_right_t 	 = TextureRegion.split(actions, (int)w, (int)h)[4];
		die_left_t 		 = TextureRegion.split(actions, (int)w, (int)h)[5];
		anim_run_left	 = new Animation(0.05f, run_left_t);
		anim_run_right	 = new Animation(0.05f, run_right_t);
		anim_die_left 	 = new Animation(0.1f, die_left_t);
		anim_die_right 	 = new Animation(0.1f, die_right_t);
		anim_shoot_left	 = new Animation(0.05f, shoot_left_t);
		anim_shoot_right = new Animation(0.05f, shoot_right_t);
	}
	
	public void tick(float delta, SpriteBatch batch, Player bot, ArrayList<Entity> entities) {
		hitbox.set(x, y, w, h);
		pos.set(x,y);
		shoot_x = x +30;
		shoot_y = y + 25;
		
		tick += delta;
		if (dying){
			if(bleed_time < 70){
				bleed_time ++;
			} else {
				alive = false;
			}
		}
		logic(bot, delta);
		get_frame();
		check_collisions(bot, entities);
		batch.draw(frame, x, y, w, h);		
		gun.tick(delta, batch);
	}
	
	protected void logic(Player bot, float delta){	
		if (run_right && alive){
			HiFi.play_bike(true);
			if (x < dest_x){
				x += 6F;
			} else {
				reset();
				tick = 0;
				shoot_right = true;
			}			
			check_run_x(bot);			
		} else if(run_left && alive) {
			HiFi.play_bike(true);
			if (x > dest_x){
				x -= 6F;
			} else {
				reset();
				tick = 0;
				shoot_left = true;
			}			
			
			check_run_x(bot);
		}else if(die_right) {

		}else if(die_left) {

		}else if(shoot_right && alive) {
			if (gun.isReady_to_fire()){
				HiFi.play_bike_shot(true);
				shoot(bot);
			}
			check_shoot_x(bot);
			
			if (pos.dst(bot.pos.x + w, bot.pos.y) > (gun.range * gun.speed) ){
				x += 2.5f;
			} else if (pos.dst(bot.pos.x, bot.pos.y) < (100) ) {
				x -= 3.5f;
			}
		}else if(shoot_left && alive) {
			check_shoot_x(bot);
			if (gun.isReady_to_fire()){
				HiFi.play_bike_shot(true);
				shoot(bot);
			}
			
			if (pos.dst(bot.pos.x, bot.pos.y) > (gun.range * gun.speed) ){
				x -= 2.5f;
			} else if (pos.dst(bot.pos.x+w, bot.pos.y) < 100 ) {
				x += 3.5f;
			}
		} else {
			reset();			
		}
		gun.tick(delta);
	}
				
}