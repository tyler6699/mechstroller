package careless.walker;

import java.util.ArrayList;

import careless.walker.Enums.FACING;
import careless.walker.Enums.MANTYPE;
import careless.walker.Enums.TYPE;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Soldier extends Entity{
	float bleed_time;
	
	public Animation rambo_anim, rambo_die_anim;
	public Texture rambo;
	TextureRegion[] rambo_frames,rambo_die_frames;
	
	// WEAPON
	public Weapon gun;
	
	public Entity vehicle;
	public Texture actions;
	public TextureRegion frame;
	public Animation anim, anim2;
	public FACING direction;
	public MANTYPE weapon;
	public Animation anim_run_right;
	public Animation anim_run_left;
	public Animation anim_die_right;
	public Animation anim_die_left;
	public Animation anim_shoot_right;
	public Animation anim_shoot_left;

	public static TextureRegion[] run_right_t;
	public static TextureRegion[] run_left_t;
	public static TextureRegion[] die_right_t;
	public static TextureRegion[] die_left_t;
	public static TextureRegion[] shoot_right_t;
	public static TextureRegion[] shoot_left_t;
	
	public boolean run_right 	= false;
	public boolean run_left 	= false;
	public boolean die_right 	= false;
	public boolean die_left 	= false;
	public boolean shoot_right 	= false;
	public boolean shoot_left  	= false;
	public boolean in_vehicle 	= false;
	
	public Soldier(MANTYPE type, Device device){
		super();
		weapon = type;
		this.type = TYPE.SOLDIER;
		w=40;
		h=40;
		alive = true;
		dying = false;
		bleed_time = 0;
					
		hitbox = new Rectangle(x, y, w, h);
	}
	
	public void check_collisions(Player bot, ArrayList<Entity> entities){
		if (alive && !dying){
			for (Bullet b: bot.gun.bulletList){
				if(b.hitbox.overlaps(hitbox)){
					hp --;
					if (hp <0){
					   die(entities);
					}
					bot.gun.bulletList.remove(b);
					break;
				}
			}
		}
		
		if (gun != null && gun.bulletList != null){
			for (Bullet b: gun.bulletList){
				if(b.hitbox.overlaps(bot.hitbox)){
					bot.hp -= b.damage;
					gun.bulletList.remove(b);
					break;
				}
			}
		}
	}
	
	public void shoot(Player bot){
		if (gun.isReady_to_fire()){
			Bullet bullet = new Bullet(x, y, bot.head_x, bot.head_y, gun.bullet_size, gun.bullet_size, gun.damage, gun.speed, gun.range);
			gun.bulletList.add(bullet);
			gun.setReady_to_fire(false);
		}
	}
	
	public void check_shoot_x(Player bot){
		if (x > bot.x){
			direction = FACING.LEFT;
			shoot_right = false;
			shoot_left = true;
		}
		
		if (x < bot.x){
			shoot_right = true;
			shoot_left = false;
			direction = FACING.RIGHT;
		}
	}
	
	public void check_run_x(Player bot){
		if (x > bot.x){
			direction = FACING.LEFT;
			run_right = false;
			shoot_left = true;
		}
		
		if (x < bot.x){
			shoot_right = true;
			run_left = false;
			direction = FACING.RIGHT;
		}
	}
	
	public void die(ArrayList<Entity> entities) {
		if (weapon == MANTYPE.HELICOPTER){
			for (Entity e: entities){
				if(e.type == TYPE.SOLDIER){
					if (((Soldier)e).vehicle == this){
						((Soldier)e).falling = true;
						((Soldier)e).die();
					}
				}
			}
		}
		
		reset();
		dying = true;
		tick = 0;
		if (direction == FACING.LEFT){
			die_left = true;	
		} else {
			die_right = true;
		}
			
	}
	
	public void die() {		
		reset();
		dying = true;
		tick = 0;
		if (direction == FACING.LEFT){
			die_left = true;	
		} else {
			die_right = true;
		}
			
	}
	
	protected void reset(){
		run_right 	= false;
		run_left 	= false;
		die_right 	= false;
		die_left 	= false;
		shoot_right = false;
		shoot_left  = false;
		in_vehicle 	= false;
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