package careless.walker;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "walker";
		cfg.useGL20 = false;
		cfg.width = 1024;
		cfg.height = 768;
		cfg.fullscreen = false;
		
		new LwjglApplication(new Walker(), cfg);
	}
}
