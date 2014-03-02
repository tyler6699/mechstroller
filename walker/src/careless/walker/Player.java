package careless.walker;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Player {
	public float tick;
	public float x;
	public float y;
	float x_mod=0;
	float y_mod=0;
	int scale;
	
	public Texture 			legs_texture;
	public TextureRegion[] 	leg_frames;
	public Animation 		legs_anim;
	public TextureRegion 	frame, head_frame;
	
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
	
	public Player(Device device){
		scale = 1;
		width = device.w_scale * (63*scale);
		height = device.h_scale * (63*scale);
		tick = 1.28f;
		legs_texture = new Texture(Gdx.files.internal("data/walker/legs.png"));
		leg_frames 	 = TextureRegion.split(legs_texture, 63, 63)[0];
		legs_anim    = new Animation(.08f, leg_frames);
		
		int hw = 45, hh = 61;	
		heads_texture = new Texture(Gdx.files.internal("data/walker/heads.png"));
		u_90_frames 	= TextureRegion.split(heads_texture, hw, hh)[0];
		u_72_frames 	= TextureRegion.split(heads_texture, hw, hh)[1];
		u_54_frames 	= TextureRegion.split(heads_texture, hw, hh)[2];
		u_36_frames 	= TextureRegion.split(heads_texture, hw, hh)[3];
		c_frames 		= TextureRegion.split(heads_texture, hw, hh)[4];
		d_36_frames 	= TextureRegion.split(heads_texture, hw, hh)[5];
		d_54_frames 	= TextureRegion.split(heads_texture, hw, hh)[6];
		d_72_frames 	= TextureRegion.split(heads_texture, hw, hh)[7];
		d_90_frames 	= TextureRegion.split(heads_texture, hw, hh)[8];
		
		heads_anim    = new Animation(1, c_frames);
		
		x = 10;//(device.w/2);
		y = 30;//(device.h/2);	
		
		hitbox = new Rectangle(x, y, width, height);
	}
	
	public void tick(float delta, SpriteBatch batch) {
		tick += delta;
		tick = tick > 0 ? tick : 1.28f;
		
		int frameNumber = (int)(tick / 0.08f);
		frameNumber = frameNumber % 16;
		set_offsets(frameNumber);
		System.out.println(frameNumber);
		
		frame = legs_anim.getKeyFrame(tick, true);
		head_frame = heads_anim.getKeyFrame(5, false);
		batch.draw(frame, x, y, width, height);
		batch.draw(head_frame, x + 30 + x_mod, y+10 + y_mod, 45*scale, 61*scale);			
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