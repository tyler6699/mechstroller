package careless.walker;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Weapon {
	Texture bullet;
	private boolean ready_to_fire = true;
	private float   weapon_fire_rate = 10;
	private float   last_shot_counter = 0;
	private float   bullet_offset_x = 0;
	private float   bullet_offset_y = 0;
	private float   accuracy = 1;
	float           last_shot_count = 0;
	private float   kickback = 0;
	
	// AMMO
	public ArrayList<Bullet> bulletList = new ArrayList<Bullet>();
	
	public Weapon(float weapon_fire_rate, float accuracy) {
	 this.weapon_fire_rate = weapon_fire_rate;
	 this.accuracy = accuracy;
	 bullet  = new Texture(Gdx.files.internal("data/walker/bullet.png"));
	}
	
	public void tick(float delta, SpriteBatch batch){
		if (this.getLast_shot_counter() > getWeapon_fire_rate()) {
			setLast_shot_counter(0);
			setReady_to_fire(true);
		} else {
			if (!isReady_to_fire()) {
				setLast_shot_counter((float) this.getLast_shot_counter() + 1);
			}
		}
			
		for (int x = 0; x < bulletList.size(); x++) {
			Bullet b = bulletList.get(x);
			if (b.duration < b.max_duration) {
				b.move(delta);
				batch.draw(bullet, (float) (b.location.x), b.location.y, b.ammo_width, b.ammo_height);
			} else {
				// ADD BULLET DUST
				bulletList.remove(x);
			}
		}
	}
	
	public boolean isReady_to_fire() {
		return ready_to_fire;
	}
	public void setReady_to_fire(boolean ready_to_fire) {
		this.ready_to_fire = ready_to_fire;
	}
	public float getWeapon_fire_rate() {
		return weapon_fire_rate;
	}
	public void setWeapon_fire_rate(float weapon_fire_rate) {
		this.weapon_fire_rate = weapon_fire_rate;
	}
	public float getLast_shot_counter() {
		return last_shot_counter;
	}
	public void setLast_shot_counter(float last_shot_counter) {
		this.last_shot_counter = last_shot_counter;
	}
	public float getBullet_offset_x() {
		return bullet_offset_x;
	}
	public void setBullet_offset_x(float bullet_offset_x) {
		this.bullet_offset_x = bullet_offset_x;
	}
	public float getBullet_offset_y() {
		return bullet_offset_y;
	}
	public void setBullet_offset_y(float bullet_offset_y) {
		this.bullet_offset_y = bullet_offset_y;
	}
	public float getAccuracy() {
		return accuracy;
	}
	public void setAccuracy(float accuracy) {
		this.accuracy = accuracy;
	}

	public ArrayList<Bullet> getBulletList() {
		return bulletList;
	}

	public void setBulletList(ArrayList<Bullet> bulletList) {
		this.bulletList = bulletList;
	}
	
	public void clearBulletList() {
		bulletList.clear();
	}

	public float getKickback() {
		return kickback;
	}

	public void setKickback(float kickback) {
		this.kickback = kickback;
	}
}