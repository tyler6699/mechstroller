package careless.walker;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Art {
	public static Texture bazooka_girl;
	public static  Texture helicopter;
	public static Texture punk_rifle;

	public Art(){
		bazooka_girl = new Texture(Gdx.files.internal("data/walker/punk_girl.png"));
		helicopter = new Texture(Gdx.files.internal("data/walker/helicopter.png"));
		punk_rifle = new Texture(Gdx.files.internal("data/walker/red_punk_run.png"));
	}
}
