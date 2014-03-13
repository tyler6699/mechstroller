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
	int heli_rifle_no;
	int motar_no;
	int bike_no;
	int heli_no;
	int next_wave_count;
	public boolean walk_to_next_wave = false;
	
	public Wave(Device device, ArrayList<Entity> entities, Player bot, Game game){
		this.device = device;
		add_soldiers(entities, bot, game);
		wave_no = 0;
		rifle_no = 0;
		motar_no = 0;
		bike_no = 0;
		heli_no = 0;
		next_wave_count = 300;
	}
	
	public void next_wave(ArrayList<Entity> entities, Player bot, Game game){
		wave_no ++;
		add_soldiers(entities, bot, game);
	}
	
	private void add_soldiers(ArrayList<Entity> entities, Player bot, Game game) {
		//System.out.println(wave_no + " PRE: " + bot.hp + " " + bot.max_hp + " " + bot.gun.max_heat);
		if (wave_no > 0 && bot.hp < bot.max_hp){
			
			if (wave_no > 12){
				bot.hp += wave_no * 30;
			} else if (wave_no > 9){
				bot.hp += wave_no * 15;
			} else {
				bot.hp += wave_no * 5;	
			}
			
			if(bot.hp > bot.max_hp){
				bot.hp = bot.max_hp;
			}
			
			bot.gun.max_heat += wave_no * 30;
		}
		
		//System.out.println(wave_no + " POST: " +bot.hp + " " + bot.max_hp + " " + bot.gun.max_heat);
				
		if (wave_no == 0){
			//rifle_no = device.random_int(1, 5);
		}else if (wave_no == 1){
			rifle_no = device.random_int(3, 5);
		}else if (wave_no == 2){
			rifle_no = device.random_int(3, 7);
			motar_no = device.random_int(0, 2);
		}else if (wave_no == 3){
			rifle_no = device.random_int(5, 9);
			motar_no = device.random_int(0, 4);
		}else if (wave_no == 4){
			rifle_no = device.random_int(8, 10);
			motar_no = device.random_int(0, 4);
			bike_no  = 1;
		}else if (wave_no == 5){
			rifle_no = device.random_int(9, 14);
			motar_no = device.random_int(1, 5);
			bike_no  = 1;
		}else if (wave_no == 6){
			rifle_no = device.random_int(8, 14);
			motar_no = device.random_int(1, 5);
			bike_no  = 1;
		}else if (wave_no == 7){
			rifle_no = device.random_int(8, 15);
			motar_no = device.random_int(1, 5);
			bike_no  = 2;
		}else if (wave_no == 8){
			rifle_no = device.random_int(8, 15);
			motar_no = device.random_int(1, 5);
			bike_no  = device.random_int(1, 4);
		}else if (wave_no == 9){
			rifle_no = device.random_int(10, 15);
			motar_no = device.random_int(1, 7);
			bike_no  = device.random_int(0, 2);
			heli_no  = 1;
			heli_rifle_no = 3;
		}else if (wave_no == 10){
			rifle_no = device.random_int(10, 15);
			motar_no = device.random_int(1, 7);
			bike_no  = device.random_int(0, 3);
			heli_no  = 1;
			heli_rifle_no = 3;
		}else if (wave_no == 11){
			rifle_no = device.random_int(10, 15);
			motar_no = device.random_int(1, 7);
			bike_no  = device.random_int(0, 3);
			heli_no  = 1;
			heli_rifle_no = 5;
		}else if (wave_no == 12){
			rifle_no = device.random_int(10, 15);
			motar_no = device.random_int(1, 9);
			bike_no  = device.random_int(1, 3);
			heli_no  = 1;
			heli_rifle_no = device.random_int(5, 10);
		}else if (wave_no == 13){
			rifle_no = device.random_int(10, 15);
			motar_no = device.random_int(3, 8);
			bike_no  = device.random_int(1, 4);
			heli_no  = 1;
			heli_rifle_no = device.random_int(5, 10);
		}else if (wave_no == 14){
			rifle_no = device.random_int(10, 15);
			motar_no = device.random_int(3, 8);
			bike_no  = device.random_int(1, 4);
			heli_no  = 2;
			heli_rifle_no = device.random_int(1, 5);
		}else if (wave_no == 15){
			rifle_no = device.random_int(10, 15);
			motar_no = device.random_int(5, 8);
			bike_no  = device.random_int(1, 4);
			heli_no  = 2;
			heli_rifle_no = device.random_int(5, 10);
		}else if (wave_no == 16){
			rifle_no = 0;
			motar_no = 0;
			bike_no  = 0;
			heli_no  = 6;
			heli_rifle_no = 0;
		}else if (wave_no == 17){
			rifle_no = 0;
			motar_no = 0;
			bike_no  = 0;
			heli_no  = 10;
			heli_rifle_no = 0;
		}else if (wave_no == 18){
			rifle_no = 0;
			motar_no = 0;
			bike_no  = 10;
			heli_no  = 0;
			heli_rifle_no = 10;
		}else if (wave_no == 19){
			rifle_no = 0;
			motar_no = 30;
			bike_no  = 0;
			heli_no  = 0;
			heli_rifle_no = 0;
		}else if (wave_no == 20){
			rifle_no = 30;
			motar_no = 0;
			bike_no  = 0;
			heli_no  = 0;
			heli_rifle_no = 0;
		}else if (wave_no == 21){
			rifle_no = 50;
			motar_no = 0;
			bike_no  = 0;
			heli_no  = 0;
		}else if (wave_no == 22){
			game.complete = true;
		}
		
		// ADD ALL THE ENEMIES
		
		// RIFLE
		for (int i = 0; i < (rifle_no); i++){
			rifle = new RifleMan(device);
			entities.add(rifle);	
		}
		
		
		// BIKE
		for (int i = 0; i < bike_no; i++){
			bike = new Bike(device);
			entities.add(bike);	
		}

		
		
		// MOTAR
		for (int i = 0; i < motar_no; i++){
			bazooka = new Bazooka(device);
			entities.add(bazooka);	
		}
	
			
		for (int i = 0; i < heli_no; i++){
			helicopter = new Helicopter(device);
			entities.add(helicopter);
			
			for (int j = 0; j < heli_rifle_no; j++){
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
			
    }
	
	public void tick(ArrayList<Entity> entities, Player bot, Game game){
		if (all_dead && next_wave_count == 0 && !walk_to_next_wave){
		    Iterator<Entity> e = entities.iterator();
		    while(e.hasNext()){
		    	Entity entity = e.next();
		    	if (entity.type == TYPE.SOLDIER){
					e.remove();
				}
		    }
			next_wave(entities, bot, game);
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