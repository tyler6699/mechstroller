package careless.walker;

import careless.walker.Enums.FACING;
import careless.walker.Enums.MANTYPE;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class RifleMan extends Soldier{
		
	public RifleMan(Device device){
		super(MANTYPE.RIFLE, device);
		w=40;
		h=40;
		x 		 = device.w + device.random_int(0,100);
		dest_x   = device.random_int(device.w/4,device.w-w);
		y 		 = device.random_int(0,200);
		dest_y = y;
		run_left = true;
		direction = FACING.LEFT;
		hitbox = new Rectangle(x, y, w, h);
		actions = new Texture(Gdx.files.internal("data/walker/red_punk_run.png"));;
		int s = 40;

		run_left_t 		= TextureRegion.split(actions, s, s)[0];
		die_left_t 		= TextureRegion.split(actions, s, s)[1];
		rifle_left_t 	= TextureRegion.split(actions, s, s)[2];
		anim_run_left	 = new Animation(0.1f, run_left_t);
		anim_die_left 	 = new Animation(0.1f, die_left_t);
		anim_rifle_left	 = new Animation(0.1f, rifle_left_t);
	}
	
	public void tick(float delta, SpriteBatch batch, Player bot) {
		hitbox.set(x, y, w, h);
		tick += delta;
		if (dying){
			if(bleed_time < 70){
				bleed_time ++;
			} else {
				alive = false;
			}
		}
		logic();
		get_frame();
		check_collisions(bot);
		batch.draw(frame, x, y, w, h);		
	}
	
	protected void logic(){
		
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
			if (vehicle != null){
				if (y > dest_y){
					y -= 4;
				} else {
					//run_left = true;
					falling = false;
				}
			}
		} else if (run_right && alive){

		} else if(run_left && alive) {
			if (x > dest_x){
				x -= 2.5F;
			} else {
				reset();
				tick = 0;
				shoot_left = true;
			}			
		}else if(die_right) {

		}else if(die_left) {

		}else if(shoot_right && alive) {
			
		}else if(shoot_left && alive) {
			
		} else {
			reset();			
		}
	}
		
}