package careless.walker;

import careless.walker.Enums.FACING;
import careless.walker.Enums.MANTYPE;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
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
		
}