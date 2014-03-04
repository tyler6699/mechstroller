package careless.walker;

import java.util.ArrayList;
import java.util.Iterator;
import careless.walker.Enums.MANTYPE;
import careless.walker.Enums.TYPE;

public class Wave {
	private Soldier soldier;
	boolean all_dead = true;
	boolean reset_wave = false;
	Device device;
	
	public Wave(Device device, ArrayList<Entity> entities){
		this.device = device;
		add_soldiers(entities);
	}
	
	private void add_soldiers(ArrayList<Entity> entities) {
		for (int i = 0; i < 10; i++){
			soldier = new Soldier(MANTYPE.RIFLE, device);
			entities.add(soldier);	
		}
    }

	public void tick(ArrayList<Entity> entities){
		if (all_dead){
		    Iterator<Entity> e = entities.iterator();
		    while(e.hasNext()){
		    	Entity entity = e.next();
		    	if (entity.type == TYPE.SOLDIER){
					e.remove();
				}
		    }
			add_soldiers(entities);
		}
	}
}