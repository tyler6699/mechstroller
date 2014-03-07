package careless.walker;

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

		run_left_t 		= TextureRegion.split(actions, (int) w, (int) h)[0];
		die_left_t 		= TextureRegion.split(actions, (int) w, (int) h)[0];
		rifle_left_t 	= TextureRegion.split(actions, (int) w, (int) h)[0];
		anim_run_left	 = new Animation(1f, run_left_t);
		anim_die_left 	 = new Animation(1f, die_left_t);
		anim_rifle_left	 = new Animation(1f, rifle_left_t);
	}
	
	public void tick(float delta, SpriteBatch batch, Player bot) {
		hitbox.set(x, y, w/2, h);
		tick += delta;
		if (dying){
			if(bleed_time < 70){
				bleed_time ++;
			} else {
				alive = false;
			}
		}
		get_frame();
		check_collisions(bot);
		//batch.draw(frame, x, y, w, h);
		//frame.flip(false, true);
		blade_frame = blade_anim.getKeyFrame(tick, true);
		
		//batch.draw(blade_frame, x+15, y+67, 0,0, 122, 21, 2, 1, 90, true);
		
		batch.draw(frame, x, y, w, h);	
		
		batch.draw(frame, x, y-200, w/2, h/2, w, h, .55f, 1.95f, t, true);
		
	}	
	
	float t = 120;
	boolean up = true;
	float hover_time = 0;
	
	protected void get_frame(){
		if (run_right){
			frame = anim_run_left.getKeyFrame(0, true);
		}else if(run_left) {
			frame = anim_run_left.getKeyFrame(1, true);
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
			frame = anim_die_right.getKeyFrame(tick, false);
		}else if(die_left) {
			frame = anim_die_left.getKeyFrame(tick, false);
		}else if(shoot_right) {
			frame = anim_rifle_right.getKeyFrame(0, true);
		}else if(shoot_left) {
			hover_time ++;
			if(t > 85 && up){
				System.out.println("up: " + t);
				t-=.05f;
			} else {
				up = false;
			}
			
			if(t < 93 && !up){
				System.out.println("down: " + t);
				t+=.05f;
			} else {
				up = true;
			}
			
			if(hover_time > 100){
				x -= 4.5F;
			}
			
			
			frame = anim_rifle_left.getKeyFrame(1, true);
		}
	}
}