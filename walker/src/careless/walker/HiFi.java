package careless.walker;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class HiFi {
	private static Sound explosion_1;
	private static Sound gattling;
	private static Sound gattling_stop;
	private static Sound helicopter;
	private static Sound single_shot;
	private static Sound bazooka;
	private static Sound heli_shot;
	private static Sound bike_shot;
	private static Sound die;
	private static Sound bazooka_die;
	private static float heli_count;
	private static float bike_count;
	private static float stop_count;
	private static Sound bike;
	
	public HiFi(){
		gattling = Gdx.audio.newSound(Gdx.files.internal("sound/gattling_gun.ogg"));
		gattling_stop = Gdx.audio.newSound(Gdx.files.internal("sound/gattling_stop.ogg"));
		helicopter =  Gdx.audio.newSound(Gdx.files.internal("sound/heli.ogg"));
		explosion_1 =  Gdx.audio.newSound(Gdx.files.internal("sound/explosion_1.ogg"));
		heli_shot = Gdx.audio.newSound(Gdx.files.internal("sound/heli_shot.ogg"));
		bike_shot = Gdx.audio.newSound(Gdx.files.internal("sound/bike_shot.ogg"));
		single_shot = Gdx.audio.newSound(Gdx.files.internal("sound/single_shot.ogg"));
		bazooka = Gdx.audio.newSound(Gdx.files.internal("sound/bazooka.ogg"));
		die = Gdx.audio.newSound(Gdx.files.internal("sound/die.ogg"));
		bazooka_die = Gdx.audio.newSound(Gdx.files.internal("sound/bazooka_die.ogg"));
		bike = Gdx.audio.newSound(Gdx.files.internal("sound/bike.ogg"));
	}
		
	public static void play_rifle_shot(boolean sound_on){
		single_shot.play(1f);
	}
	
	public static void play_bazooka(boolean sound_on){
		bazooka.play(1f);
	}
	
	public static void play_bazooka_die(boolean sound_on){
		bazooka_die.play(1f);
	}
	
	
	public static void play_die(boolean sound_on){
		die.play(1);
	}
	
	public void play_gattling(boolean sound_on){		
		gattling.play(.5f);
	}
	
	public static void stop_gattling(boolean sound_on){
		stop_count ++;
		if (stop_count == 1){
			gattling_stop.play(.7f);
		} else if (stop_count > 20) {
			stop_count = 0;
		}
		
	}
	
	public static void play_helicopter(boolean sound_on){
		heli_count ++;
		if (heli_count == 1){
			helicopter.play(.5f);;
		} else if (heli_count > 35) {
			heli_count = 0;
		}
	}
	
	public static void play_bike(boolean sound_on){
		bike_count ++;
		if (bike_count == 1){
			bike.play(.5f);;
		} else if (bike_count > 20) {
			bike_count = 0;
		}
	}
	
	public static void play_helicopter_shot(boolean sound_on){
		heli_shot.play(.5f);;
	}
	
	public static void play_bike_shot(boolean sound_on){
		bike_shot.play(.2f);;
	}
	
	public static void play_helicopter_die(boolean sound_on){
		explosion_1.play(.9f);;
	}
	
}