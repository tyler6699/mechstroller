package careless.walker;

import java.util.ArrayList;
import java.util.Iterator;
import careless.walker.Enums.TYPE;

public class Wave {
	private RifleMan rifle;
	private Bazooka bazooka;
	private Bike bike;
	private Helicopter helicopter;
	
	boolean all_dead = true;
	boolean reset_wave = true;
	Device device;
	int wave_no;
	int rifle_no;
	int motar_no;
	int bike_no;
	int next_wave_count;
	public boolean walk_to_next_wave = false;
	
	public Wave(Device device, ArrayList<Entity> entities){
		this.device = device;
		add_soldiers(entities);
		wave_no = 0;
		rifle_no = 3;
		motar_no = rifle_no/2;
		bike_no = 1;
		next_wave_count = 300;
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
		
		
		// BIKE
		if (wave_no == 4){
			bike = new Bike(device);
			entities.add(bike);	
		}
		
		if (wave_no > 9){
			for (int i = 0; i < ((wave_no-9)*bike_no); i++){
				bike = new Bike(device);
				entities.add(bike);	
			}
		}
		
		
		// MOTAR
		if (wave_no > 5){
			for (int i = 0; i < ((wave_no-5)*motar_no); i++){
				bazooka = new Bazooka(device);
				entities.add(bazooka);	
			}
		}
			
		if (wave_no > 7){
			helicopter = new Helicopter(device);
			entities.add(helicopter);
			
			for (int i = 0; i < (wave_no*rifle_no); i++){
				rifle = new RifleMan(device);
				rifle.vehicle = helicopter;
				rifle.x = helicopter.x;
				rifle.y = helicopter.y;
				rifle.in_vehicle = true;
				rifle.gun.damage = 3;
				rifle.gun.bullet_size = 3;
				entities.add(rifle);	
			}
		}
		
		if (wave_no > 10){
			helicopter = new Helicopter(device);
			entities.add(helicopter);
			
			for (int i = 0; i < (wave_no*rifle_no); i++){
				rifle = new RifleMan(device);
				rifle.vehicle = helicopter;
				rifle.x = helicopter.x;
				rifle.y = helicopter.y;
				rifle.in_vehicle = true;
				rifle.gun.damage = 4;
				rifle.gun.bullet_size = 3;
				entities.add(rifle);	
			}
		}
		
    }
	
	public void tick(ArrayList<Entity> entities){
		if (all_dead && next_wave_count == 0 && !walk_to_next_wave){
		    Iterator<Entity> e = entities.iterator();
		    while(e.hasNext()){
		    	Entity entity = e.next();
		    	if (entity.type == TYPE.SOLDIER){
					e.remove();
				}
		    }
			next_wave(entities);
			next_wave_count = 300;
		} else if (all_dead && next_wave_count > 0){
			//System.out.println(wave_no);
			
			//if (wave_no > 10){
			// Drop medi pack
			//}
			
			walk_to_next_wave = true;
		} else if (all_dead && next_wave_count <= 0){
			walk_to_next_wave = false;
		}
	}
	
	public void reset(ArrayList<Entity> entities){
	    Iterator<Entity> e = entities.iterator();
		while(e.hasNext()){
		   	Entity entity = e.next();
		   	if (entity.type == TYPE.SOLDIER){
				e.remove();
			}
		}
		wave_no = 0;
		next_wave_count = 300;
		walk_to_next_wave = true;
	}
}