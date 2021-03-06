package careless.walker;

import java.util.ArrayList;

import careless.walker.Enums.FACING;
import careless.walker.Enums.MANTYPE;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class RifleMan extends Soldier{
		
	public RifleMan(Device device){
		super(MANTYPE.RIFLE, device);
		hp = 1;
		w=40;
		h=40;
		
		if (device.random_int(0,100) > 50){
			x 		 = device.random_int(w,4*w);
			x = -x;
			dest_x   = device.random_int(device.w-w,device.w/4);
			y 		 = device.random_int(0,200);
			y 		 = device.random_int(0,200);
			dest_y = y;
			run_right = true;
			direction = FACING.RIGHT;
		} else {
			x 		 = device.w + device.random_int(0,100);
			dest_x   = device.random_int(device.w/4,device.w-w);
			y 		 = device.random_int(0,200);
			dest_y = y;
			run_left = true;
			direction = FACING.LEFT;
		}
		
		hitbox = new Rectangle(x, y, w, h);
		actions = Art.punk_rifle;
		int s = 40;
		
		gun = new Weapon(90,1, 1, 10, 25, 2);
		shoot_x = x;
		shoot_y = y;
		
		run_left_t 		= TextureRegion.split(actions, s, s)[0];
		run_right_t 	= TextureRegion.split(actions, s, s)[5];
		die_left_t 		= TextureRegion.split(actions, s, s)[1];
		shoot_left_t 	= TextureRegion.split(actions, s, s)[2];
		shoot_right_t 	= TextureRegion.split(actions, s, s)[3];
		die_right_t 	= TextureRegion.split(actions, s, s)[4];
		anim_run_left	 = new Animation(0.1f, run_left_t);
		anim_run_right	 = new Animation(0.1f, run_right_t);
		anim_die_left 	 = new Animation(0.1f, die_left_t);
		anim_die_right 	 = new Animation(0.1f, die_right_t);
		anim_shoot_left	 = new Animation(0.1f, shoot_left_t);
		anim_shoot_right = new Animation(0.1f, shoot_right_t);
	}
	
	public void tick(float delta, SpriteBatch batch, Player bot, ArrayList<Entity> entities) {
		hitbox.set(x, y, w, h);
		pos.set(x,y);
		shoot_x = x;
		shoot_y = y;
		
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
		if (in_vehicle){
			x = vehicle.vehicle_x;
			y = vehicle.vehicle_y;
			tick = 0;
			
			if (x < dest_x + 100 ){
				in_vehicle = false;
				falling = true;
			}
		} else if (falling) {
			// PARACHUTE
			vehicle = null;
			if (y > dest_y){
				y -= 4;
			} else {
				falling = false;
			}
			
		} else if (run_right && alive){
			check_run_x(bot);
			if (x < dest_x){
				x += 2.5F;
			} else {
				reset();
				tick = 0;
				shoot_right = true;
			}			
		} else if(run_left && alive) {
			if (x > dest_x){
				x -= 2.5F;
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
				x += bot.device.random_int(0.5f, 4);
			} else if (pos.dst(bot.pos.x, bot.pos.y) < (bot.device.random_int(25, 75)) ) {
				x -= bot.device.random_int(0.5f, 4);
			}
		}else if(shoot_left && alive) {
			check_shoot_x(bot);
			
			if (gun.isReady_to_fire()){
				HiFi.play_bike_shot(true);
				shoot(bot);
			}
			
			if (pos.dst(bot.pos.x, bot.pos.y) > (gun.range * gun.speed) ){
				x -= bot.device.random_int(0.5f, 4);
			} else if (pos.dst(bot.pos.x + w, bot.pos.y) < bot.device.random_int(100, 120) ) {
				x += bot.device.random_int(0.5f, 4);
			}
		} else {
			reset();			
		}
		gun.tick(delta);
	}
				
}