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

public class Helicopter extends Soldier{
	public Texture blades;
	public TextureRegion[] blade_frames;
	public Animation blade_anim;
	public TextureRegion blade_frame;
	
	public Helicopter(Device device){
		super(MANTYPE.HELICOPTER, device);
		actions = new Texture(Gdx.files.internal("data/walker/helicopter.png"));
		w = 180;
		h = 95;
		x 		 = device.w + device.random_int(0,100);
		dest_x   = device.random_int(100,device.w/2);
		y 		 = device.random_int(device.h/2,device.h-h);
		
		liveable = false;
		//run_left = true;
		run_left = true;
		
		direction = FACING.LEFT;
		
		blades = new Texture(Gdx.files.internal("data/walker/helicopter-blades.png"));
		blade_frames = TextureRegion.split(blades, 122, 21)[0];
		blade_anim = new Animation(0.01f, blade_frames);
		
		hitbox = new Rectangle(x, y, w, h);

		run_left_t 		= TextureRegion.split(actions, (int) w, (int) h)[1];
		run_right_t 	= TextureRegion.split(actions, (int) w, (int) h)[0];
		die_left_t 		= TextureRegion.split(actions, (int) w, (int) h)[3];
		die_right_t 	= TextureRegion.split(actions, (int) w, (int) h)[2];
		shoot_left_t 	= TextureRegion.split(actions, (int) w, (int) h)[1];
		shoot_right_t 	= TextureRegion.split(actions, (int) w, (int) h)[0];
		anim_run_left	 = new Animation(.01f, run_left_t);
		anim_run_right	 = new Animation(.01f, run_right_t);
		anim_die_left 	 = new Animation(.1f, die_left_t);
		anim_die_right 	 = new Animation(.1f, die_left_t);
		anim_shoot_left	 = new Animation(.01f, shoot_left_t);
		anim_shoot_right = new Animation(.01f, shoot_right_t);
	}
	
	public void tick(float delta, SpriteBatch batch, Player bot, ArrayList<Entity> entities) {
		hitbox.set(x, y, w/2, h);
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
		check_collisions(bot, entities);		
		batch.draw(frame, x, y, w/2, h/2, w, h, .49f, 1.94f, t, true);
	}	
	float t = 120;
	boolean up = true;
	float hover_time = 0;
	
	protected void logic(){
		if (run_right){
		
		} else if(run_left) {
			if (x > dest_x){
				x -= 4.5F;
				if(t > 70){
					t-=.2f;
				}
			} else {
				reset();
				tick = 0;
				shoot_left = true;
			}
		}else if(die_right) {
			if (y > 50){
				y -= 5;	
			}
			
		}else if(die_left) {
			if (y > 50){
				y -= 5;	
			}
		}else if(shoot_right) {
			
		}else if(shoot_left) {
			hover_time ++;
			if(t > 85 && up){
				t-=.05f;
			} else {
				up = false;
			}
			
			if(t < 93 && !up){
				t+=.05f;
			} else {
				up = true;
			}
			
			if(hover_time > 100){
				x -= 4.5F;
			}
		}
		
		vehicle_x = x + 40;
		vehicle_y = y + 25;
	}
	
	protected void get_frame(){
		if (run_right){
			frame = anim_run_right.getKeyFrame(tick, true);
		}else if(run_left) {
			frame = anim_run_left.getKeyFrame(tick, true);
		}else if(die_right) {
			frame = anim_die_right.getKeyFrame(tick, false);
		}else if(die_left) {
			frame = anim_die_left.getKeyFrame(tick, false);
		}else if(shoot_right) {
			frame = anim_shoot_right.getKeyFrame(tick, true);
		}else if(shoot_left) {			
			frame = anim_shoot_left.getKeyFrame(tick, true);
		}
	}
}