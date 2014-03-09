package careless.walker;

import java.util.ArrayList;

import careless.walker.Enums.FACING;
import careless.walker.Enums.MANTYPE;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Bazooka extends Soldier{
		
	public Bazooka(Device device){
		super(MANTYPE.MOTAR, device);
		hp = 5;
		w=40;
		h=40;
		x 		 = device.w + device.random_int(0,100);
		dest_x   = device.random_int(device.w/1.5f,device.w-w);
		y 		 = device.random_int(0,200);
		run_left = true;
		direction = FACING.LEFT;
		
		gun = new Weapon(100,1, 4, 10, 80, 4);
		
		hitbox = new Rectangle(x, y, w, h);
		actions = new Texture(Gdx.files.internal("data/walker/punk_girl.png"));;
		int s = 40;

		run_left_t 		= TextureRegion.split(actions, s, s)[0];
		die_left_t 		= TextureRegion.split(actions, s, s)[1];
		shoot_left_t 	= TextureRegion.split(actions, s, s)[2];
		shoot_right_t 	= TextureRegion.split(actions, s, s)[2];
		anim_run_left	 = new Animation(0.1f, run_left_t);
		anim_die_left 	 = new Animation(0.1f, die_left_t);
		anim_shoot_left	 = new Animation(0.1f, shoot_left_t);
		anim_shoot_right = new Animation(0.1f, shoot_right_t);
	}
		
	public void tick(float delta, SpriteBatch batch, Player bot, ArrayList<Entity> entities) {
		hitbox.set(x, y, w, h);
		
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
			
		} else if (run_right){
			
		}else if(run_left) {
			if (x > dest_x){
				x -= 2.5F;
			} else {
				reset();
				tick = 0;
				shoot_left = true;
			}
		}else if(die_right) {
			
		}else if(die_left) {
		
		}else if(shoot_right) {
			shoot(bot);
			//check_shoot_x(bot);
		}else if(shoot_left) {
			shoot(bot);
			//check_shoot_x(bot);
		}
		gun.tick(delta);
	}
}