package careless.walker;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Player {
	public float tick;
	public float x;
	public float y;
	public float head_x;
	public float head_y;
	float x_mod=0;
	float y_mod=0;
	int scale;
	float anim_speed;
	
	public Texture 			legs_texture;
	public TextureRegion[] 	leg_frames;
	public Animation 		legs_anim;
	public TextureRegion 	frame, head_frame;
	public double deg, rad;
	public int frameNumber;
	
	public Texture heads_texture;
	public Animation heads_anim;
	public static TextureRegion[] u_90_frames;
	public static TextureRegion[] u_72_frames;
	public static TextureRegion[] u_54_frames;
	public static TextureRegion[] u_36_frames;
	public static TextureRegion[] c_frames;
	public static TextureRegion[] d_36_frames;
	public static TextureRegion[] d_54_frames;
	public static TextureRegion[] d_72_frames;
	public static TextureRegion[] d_90_frames;
	
	public Texture player_alive;
	public Texture player_hit;
	public float height;
	public float width;
	public Rectangle hitbox;
	public Weapon gun;
	
	public Player(Device device){
		// WEAPON
		gun = new Weapon(5,1);
		anim_speed = .03f;
		scale = 1;
		width = device.w_scale * (63*scale);
		height = device.h_scale * (63*scale);
		tick = 16*anim_speed;
		legs_texture = new Texture(Gdx.files.internal("data/walker/legs.png"));
		leg_frames 	 = TextureRegion.split(legs_texture, 63, 63)[0];
		legs_anim    = new Animation(anim_speed, leg_frames);
		
		int hw = 45, hh = 61;	
		heads_texture = new Texture(Gdx.files.internal("data/walker/less-heads.png"));
		u_90_frames 	= TextureRegion.split(heads_texture, hw, hh)[0];
		u_54_frames 	= TextureRegion.split(heads_texture, hw, hh)[1];
		c_frames 		= TextureRegion.split(heads_texture, hw, hh)[2];
		d_54_frames 	= TextureRegion.split(heads_texture, hw, hh)[3];
		d_90_frames 	= TextureRegion.split(heads_texture, hw, hh)[4];
		
		heads_anim    = new Animation(1, c_frames);
		
		x = 10;//(device.w/2);
		y = 150;//(device.h/2);	
		
		head_x = x + 60 ;
		head_y = y + 45;
		hitbox = new Rectangle(x, y, width, height);
	}
	
	public void tick(float delta, SpriteBatch batch) {
		tick += delta;
		tick = tick > 0 ? tick : 16*anim_speed;
		
		//update head position
		head_x = x + 60 ;
		head_y = y + 45;
		
		frameNumber = (int)(tick / anim_speed);
		frameNumber = frameNumber % 16;
		set_offsets(frameNumber);

		head_frame = heads_anim.getKeyFrame(5, false);
		get_angle();
		frame = legs_anim.getKeyFrame(tick, true);
		
		batch.draw(frame, x, y, width, height);
		//batch.draw(frame, head_x, head_y, 8, 8);
		batch.draw(head_frame, x + 30 + x_mod, y+10 + y_mod, 45*scale, 61*scale);
	}
	
	void get_angle(){
		Vector2 mouse = new Vector2();
		mouse.set(Gdx.input.getX(),768 - Gdx.input.getY());
		rad = Math.atan2(mouse.x - head_x, head_y - mouse.y);
		deg = Math.toDegrees(rad);

		//System.out.println(deg);
		
		if (deg > 72 && deg < 108){ // centre
			heads_anim    = new Animation(1, c_frames);
			if (deg < 84){
				
				head_frame = heads_anim.getKeyFrame(0, false);
			} else if (deg < 96){
				head_frame = heads_anim.getKeyFrame(1, false);
			} else {
				head_frame = heads_anim.getKeyFrame(2, false);
			}
			
		}else if(deg > 36 && deg < 72){ // down 1
			heads_anim    = new Animation(1, d_54_frames);
			if (deg < 48){
				head_frame = heads_anim.getKeyFrame(0, false);
			} else if (deg < 60){
				head_frame = heads_anim.getKeyFrame(1, false);
			} else {
				head_frame = heads_anim.getKeyFrame(2, false);
			}
			
		}else if(deg > 0 && deg < 36 ){ // down 2
			heads_anim = new Animation(1, d_90_frames);
			if (deg < 12){
				head_frame = heads_anim.getKeyFrame(0, false);
			} else if (deg < 24){
				head_frame = heads_anim.getKeyFrame(1, false);
			} else {
				head_frame = heads_anim.getKeyFrame(2, false);
			}

		}else if(deg > 108 && deg < 144){ // up 1
			heads_anim    = new Animation(1, u_54_frames);
			if (deg < 120){
				head_frame = heads_anim.getKeyFrame(1, false);
			} else if (deg < 132){
				head_frame = heads_anim.getKeyFrame(3, false);
			} else {
				head_frame = heads_anim.getKeyFrame(2, false);
			}
			
		}else if(deg > 144 && deg < 180){ // up 2
			heads_anim    = new Animation(1, u_90_frames);
			if (deg < 156){
				head_frame = heads_anim.getKeyFrame(3, false);
			} else if (deg < 168){
				head_frame = heads_anim.getKeyFrame(1, false);
			} else {
				head_frame = heads_anim.getKeyFrame(2, false);
			}
			
		// NEGATIVES
			
		}else if(deg > -36 && deg < 0){ // DOWN 2
			heads_anim    = new Animation(1, d_90_frames);
			head_frame = heads_anim.getKeyFrame(0, false);
		
		}else if(deg > -72 && deg < -36){ // DOWN 1
			heads_anim    = new Animation(1, d_54_frames);
			head_frame = heads_anim.getKeyFrame(0, false);
		
		}else if(deg > -108 && deg < -72){ // CENTRE
			heads_anim    = new Animation(1, c_frames);
			head_frame = heads_anim.getKeyFrame(3, false);
			
		}else if(deg > -144 && deg < -108){ // UP 1
			heads_anim    = new Animation(1, u_54_frames);
			head_frame = heads_anim.getKeyFrame(3, false);
			
		}else if(deg > -180 && deg < -144){ // UP 2
			heads_anim    = new Animation(1, u_90_frames);
			head_frame = heads_anim.getKeyFrame(3, false);
		}
		
	}
	
	private void set_offsets(int frameNumber){
		if(frameNumber == 15){
			x_mod = 5;
			y_mod = -3;
		} else if (frameNumber == 14){
			x_mod = 3;
			y_mod = 1;
		} else if (frameNumber == 13){
			x_mod = 0;
			y_mod = 0;
		} else if (frameNumber == 12){
			x_mod = 0;
			y_mod = 0;
		} else if (frameNumber == 11){
			x_mod = 0;
			y_mod = 0;
		} else if (frameNumber == 10){
			x_mod = 0;
			y_mod = 0;
		} else if (frameNumber == 9){
			x_mod = -2;
		} else if (frameNumber == 8){
			y_mod = -2;
		} else if (frameNumber == 7){
			x_mod = -2;
			y_mod = -3;
		} else if (frameNumber == 6){
			x_mod = -3;
			y_mod = -3;
		} else if (frameNumber == 5){
			x_mod = -4;
			y_mod = -1;
		} else if (frameNumber == 4){
			x_mod = -6;
		} else if (frameNumber == 3){
			x_mod = -4;
		} else if (frameNumber == 2){
			x_mod = -2;
		} else if (frameNumber == 1){
			x_mod = -1;
			y_mod = -1;
		} else if (frameNumber == 0){
			x_mod = 5;
		}
		
	}
	
}