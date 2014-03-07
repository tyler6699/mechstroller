package careless.walker;

import careless.walker.Enums.TYPE;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Entity implements Comparable<Entity>{
	public int id;
	public String name;
	public TYPE type;
	boolean alive, dying;
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
	public boolean liveable;
	
	public Entity (){
		liveable = true;
	}

	public int compareTo(Entity entity) {
		float temp_y =  entity.y;
		float compare_y = this.y;
				
		// FOR entities where Y is incorrect due to space at bottom of image
		if(this.type == TYPE.NULL){
			compare_y +=0.25;
		} else if (entity.type == TYPE.NULL){
			temp_y +=0.25;
		}
		
		return (temp_y < compare_y ) ? -1: (temp_y > compare_y) ? 1:0 ;
	}

	public void tick(float delta, SpriteBatch batch, Player bot) {
		
	}

}