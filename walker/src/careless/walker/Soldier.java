package careless.walker;

import careless.walker.Enums.MANTYPE;
import careless.walker.Enums.TYPE;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Soldier extends Entity{
	public Texture actions;
	public TextureRegion frame;
	public Animation anim, anim2;
	
	public Animation anim_run_right;
	public Animation anim_run_left;
	public Animation anim_die_right;
	public Animation anim_die_left;
	public Animation anim_rifle_right;
	public Animation anim_rifle_left;
	public Animation anim_motar_right;
	public Animation anim_motar_left;
		
	public static TextureRegion[] run_right_t;
	public static TextureRegion[] run_left_t;
	public static TextureRegion[] die_right_t;
	public static TextureRegion[] die_left_t;
	public static TextureRegion[] rifle_right_t;
	public static TextureRegion[] rifle_left_t;
	public static TextureRegion[] motar_right_t;
	public static TextureRegion[] motar_left_t;

	public boolean run_right 	= false;
	public boolean run_left 	= false;
	public boolean die_right 	= false;
	public boolean die_left 	= false;
	public boolean rifle_right 	= false;
	public boolean rifle_left  	= false;
	public boolean motar_right 	= false;
	public boolean motar_left	= true;
	
	public Soldier(MANTYPE type, Device device){
		super();
		this.type = TYPE.SOLDIER;
		w=34;
		h=28;
		alive = true;
		
		// SET RANDOM POSITIONS
		x = device.w + device.random_int(0,50);
		dest_x = device.random_int(0,device.w-30);
		y = device.random_int(0,200);
		run_left = true;
		// *******************
		
		hitbox = new Rectangle(x, y, w, h);
		actions = new Texture(Gdx.files.internal("data/walker/punk_man_1.png"));;
		
		run_right_t		= TextureRegion.split(actions, 17, 14)[0];
		run_left_t 		= TextureRegion.split(actions, 17, 14)[1];
		die_right_t		= TextureRegion.split(actions, 17, 14)[2];
		die_left_t 		= TextureRegion.split(actions, 17, 14)[3];
		rifle_right_t 	= TextureRegion.split(actions, 17, 14)[4];
		rifle_left_t 	= TextureRegion.split(actions, 17, 14)[5];
		motar_right_t	= TextureRegion.split(actions, 17, 14)[6];
		motar_left_t 	= TextureRegion.split(actions, 17, 14)[7];
		
		anim_run_right	 = new Animation(0.1f, run_right_t);
		anim_run_left	 = new Animation(0.1f, run_left_t);
		anim_die_right	 = new Animation(0.1f, die_right_t);
		anim_die_left 	 = new Animation(0.1f, die_left_t);
		anim_rifle_right = new Animation(0.1f, rifle_right_t);
		anim_rifle_left	 = new Animation(0.1f, rifle_left_t);
		anim_motar_right = new Animation(0.1f, motar_right_t);
		anim_motar_left  = new Animation(0.1f, motar_left_t);
	}
	
	public void tick(float delta, SpriteBatch batch, Player bot) {
		hitbox.set(x, y, w, h);
		tick += delta;
				
		if (run_right){
			frame = anim_run_right.getKeyFrame(tick, true);
		}else if(run_left) {
			frame = anim_run_left.getKeyFrame(tick, true);
			if (x > dest_x){
				x -= 2.5F;
			} else {
				reset();
				rifle_left = true;
			}
		}else if(die_right) {
			frame = anim_die_right.getKeyFrame(tick, false);
		}else if(die_left) {
			frame = anim_die_left.getKeyFrame(tick, false);
		}else if(rifle_right) {
			frame = anim_rifle_right.getKeyFrame(tick, true);
		}else if(rifle_left) {
			frame = anim_rifle_left.getKeyFrame(tick, true);
		}else if(motar_right) {
			frame = anim_motar_right.getKeyFrame(tick, true);
		}else { //motar_left
			frame = anim_motar_left.getKeyFrame(tick, true);
		}
		
		check_collisions(bot);
		
		batch.draw(frame, x, y, w, h);
	}

	private void check_collisions(Player bot){
		if (alive){
			//all_dead = false;
			for (Bullet b: bot.gun.bulletList){
				if(b.hitbox.overlaps(hitbox)){
					die();
					bot.gun.bulletList.remove(b);
					break;
				}
			}
		}
	}
	
	public void die() {
		reset();
		alive = false;
		tick = 0;
		die_left = true;		
	}
	
	private void reset(){
		run_right 	= false;
		run_left 	= false;
		die_right 	= false;
		die_left 	= false;
		rifle_right = false;
		rifle_left  = false;
		motar_right = false;
		motar_left	= false;
	}
}