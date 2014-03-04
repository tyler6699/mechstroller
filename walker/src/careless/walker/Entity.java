package careless.walker;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Entity {
	public int id;
	public String name;
	boolean alive;
	public float x, dest_x;
	public float y, dest_y;
	public float w;
	public float h;
	public Rectangle hitbox;	
	public Texture texture;
	public Texture alt_texture;
	public float alt_w;
	public float alt_h;
	public float tick;
	
	public Entity (){
		
	}

}
