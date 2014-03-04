package careless.walker;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;

public class Device {
	public float v_width;
	public float v_height;
	public float w_scale;
	public float h_scale;
	public float w;
	public float h;
	public float x;
	public float y;
	
	// TOUCH
	public float touchX = 0;
	public float touchY = 0;
	public boolean touched = false;
	public boolean checked_click = true;
	public Rectangle clicked_at = new Rectangle();
	
	public Device(){
		// SCREEN
		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();
				
		// SCALE / RATIO
		v_width = 1024;
		v_height = 768;
		w_scale = w/v_width;
		h_scale = h/v_height;
	}
	
	public int random_int(float Min, float Max){
		return (int) (Min + (int)(Math.random() * ((Max - Min) + 1)));	
	}
}