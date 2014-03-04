package careless.walker;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import careless.walker.Enums.MANTYPE;

public class Wave {
	
	public ArrayList<Soldier> soldiers;
	private Soldier soldier;
	boolean all_dead = true;
	boolean reset_wave = false;
	Device device;
	
	public Wave(Device device){
		this.device = device;
		soldiers = new ArrayList<Soldier>();
		add_soldiers();
	}
	
	private void add_soldiers() {
		for (int i = 0; i < 10; i++){
			soldier = new Soldier(MANTYPE.RIFLE, device);
			soldiers.add(soldier);	
		}
    }

	public void tick(float delta, SpriteBatch batch, Player bot){
		all_dead = true;
		
		for (Soldier soldier:soldiers){
			if (soldier.alive){
				all_dead = false;
				for (Bullet b: bot.gun.bulletList){
					if(b.hitbox.overlaps(soldier.hitbox)){
						soldier.die();
						bot.gun.bulletList.remove(b);
						break;
					}
				}
			}
			
			// DRAW
			soldier.tick(delta, batch);
		}
		
		if (all_dead){
			add_soldiers();
		}
	}
}