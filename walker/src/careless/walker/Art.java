package careless.walker;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Art {
	public static Texture bazooka_girl;
	public static Texture helicopter;
	public static Texture punk_rifle;
	public static Texture bike;
	public static Texture next_wave;
	public static TextureRegion wave_frame;
	
	public Art(){
		bazooka_girl = new Texture(Gdx.files.internal("data/walker/punk_girl.png"));
		helicopter = new Texture(Gdx.files.internal("data/walker/helicopter.png"));
		punk_rifle = new Texture(Gdx.files.internal("data/walker/red_punk_run.png"));
		bike = new Texture(Gdx.files.internal("data/walker/bike.png"));
		next_wave = new Texture(Gdx.files.internal("data/walker/next_wave.png"));
	}
}
