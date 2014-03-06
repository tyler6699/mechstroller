package careless.walker;

import java.util.ArrayList;
import java.util.Iterator;
import careless.walker.Enums.MANTYPE;
import careless.walker.Enums.TYPE;

public class Wave {
	private Soldier soldier;
	private RifleMan rifle;
	private Bazooka bazooka;
	boolean all_dead = true;
	boolean reset_wave = false;
	Device device;
	int wave_no;
	int rifle_no;
	int motar_no;
	
	public Wave(Device device, ArrayList<Entity> entities){
		this.device = device;
		add_soldiers(entities);
		wave_no = 1;
		rifle_no = 5;
		motar_no = rifle_no/2;
	}
	
	public void next_wave(ArrayList<Entity> entities){
		wave_no ++;
		add_soldiers(entities);
	}
	
	private void add_soldiers(ArrayList<Entity> entities) {
		// RIFLE
		for (int i = 0; i < (wave_no*rifle_no); i++){
			rifle = new RifleMan(device);
			
			entities.add(rifle);	
		}
		
		// MOTAR
		for (int i = 0; i < (wave_no*motar_no); i++){
			bazooka = new Bazooka(device);
			entities.add(bazooka);	
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
			next_wave(entities);
		}
	}
}