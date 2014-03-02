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
	
	public Texture 			legs_texture;
	public TextureRegion[] 	leg_frames;
	public Animation 		legs_anim;
	public TextureRegion 	frame;
	
	public Texture player_alive;
	public Texture player_hit;
	public float height;
	public float width;
	public Rectangle hitbox;
	
	public Player(Device device){
		width = device.w_scale * 126;
		height = device.h_scale * 126;
		
		legs_texture = new Texture(Gdx.files.internal("data/walker/legs.png"));
		leg_frames 	 = TextureRegion.split(legs_texture, 63, 63)[0];
		legs_anim    = new Animation(.08f, leg_frames);
				
		x = 1336;//(device.w/2);
		y = 10;//(device.h/2);	
		
		hitbox = new Rectangle(x, y, width, height);
	}
	
	public void tick(float delta, SpriteBatch batch) {
		tick += delta;
		frame = legs_anim.getKeyFrame(tick, true);
		batch.draw(frame, x, y, width, height);
	}
	
}