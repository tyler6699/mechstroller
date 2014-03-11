package careless.walker;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class HiFi {
	private static Sound ak47;
	private static float ak47_count;
	private Sound gattling;
	private float gattling_count;
	private Sound jump_1;
	private Sound jump_2;
	private Sound unlock;
	
	public HiFi(){
		ak47 = Gdx.audio.newSound(Gdx.files.internal("sound/Shot1.wav")); 
		gattling = Gdx.audio.newSound(Gdx.files.internal("sound/gattling.mp3"));
		//jump_1 = Gdx.audio.newSound(Gdx.files.internal("data/sounds/jump_1.wav"));
		//jump_2 = Gdx.audio.newSound(Gdx.files.internal("data/sounds/jump_2.wav"));
		//unlock = Gdx.audio.newSound(Gdx.files.internal("data/sounds/unlock.wav"));
	}
	
	public static void play_ak47(boolean sound_on){
		ak47_count ++;
		if (ak47_count == 1){
			ak47.play(.3f);;
		} else if (ak47_count > 60) {
			ak47_count = 0;
		}
	}
	
	public void play_gattling(boolean sound_on){		
		gattling_count ++;
		if (gattling_count == 1){
			gattling.play(.7f);
		} else if (gattling_count > 260) {
			gattling_count = 0;
		}
	}
	
	public void stop_gattling(boolean sound_on){
		gattling.stop();
		gattling_count = 0;
	}
	
	public void play_jump(int i, boolean sound_on){
		if (sound_on){
			if (i == 1){
				jump_1.play();
			} else {
				jump_2.play();
			}
		}
	}
	
	public void play_unlock(boolean sound_on){
		if (sound_on){
			unlock.play();	
		}
	}
}