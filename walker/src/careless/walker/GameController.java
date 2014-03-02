package careless.walker;

import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class GameController extends InputAdapter implements InputProcessor {
	public OrthographicCamera camera;
	public Boolean            reset_batch = false;
	public Float              o = 0.03125f;	
	public float              view_height, view_width, cull_size;
	public int                render_blocks_x, render_blocks_y;
	public final int[]        actions = {0,1,2,3,4,5,6,7,8};
	public int                action = 0;
	public boolean            allow_text = false;
	public boolean            input_text = false;
	public boolean            input_textbox = false;
	public Boolean            show_menu = false;
	
	// TEXT BOXES
	public Boolean            input = false;
	public String             input_str = "";
	public boolean            caps_on;
	private boolean           shift;

	// POSITION
	public Vector3  curr  = new Vector3();
	public Vector3  last  = new Vector3(-1, -1, -1);
	public Vector3  delta = new Vector3();
	private Vector3 viewLocation = new Vector3();
	
	// MAP
	public float map_height;
	public float map_width;

	// MOUSE
	public int     mouse_x, mouse_y, mouse_flip_y;
	public Vector2 mouse_map_vec;
	public Vector2 mouse_screen_vec;
	public Vector2 mouse_screen_click_at;
	public Vector2 mouse_map_click_at;
	public boolean processed_click;
	public Polygon click_screen_area;
	public Polygon click_map_area;
	public float   mouse_time;
	public boolean load_map, save_map, reset_map;
	
	// Distance from Centre
	Vector2      vec_center = new Vector2(0,0);
	public float off_centre_dst = 0;
	
	// MOVEMENT
	public Boolean move_right = false;
	public Boolean move_left  = false;
	public Boolean move_up    = false;
	public Boolean move_down  = false;
	public Boolean LMB        = false;
	public Boolean RMB        = false;
	public Boolean jump       = false;
	public Boolean rotate_ac  = false;
	public Boolean paused     = false;
	public Rumble  rumble;
	
	public GameController(OrthographicCamera camera){
		this.camera     = camera;
		this.rumble = new Rumble(); // Used for Screen Shake
		
		float[]diamond = {0*o,8*o,16*o,16*o,32*o,8*o,16*o,0*o};
		float[]square  = {0,0, 0,16, 16,16, 16,0};
		
		this.mouse_screen_click_at = new Vector2();
		this.mouse_map_click_at    = new Vector2();
		this.click_screen_area     = new Polygon(square);
		this.click_map_area        = new Polygon(diamond);
		this.mouse_time            = 0;
		this.mouse_map_vec         = new Vector2();
		this.mouse_screen_vec      = new Vector2();
	}
	
	public Vector2 get_map_coords(Vector2 mouse_coords){
		viewLocation.set(0, 0, 0);
		this.camera.unproject(viewLocation);

		// VERTICAL -Y-
		view_height   = this.camera.viewportHeight * this.camera.zoom;
		float ratio_h = view_height / Gdx.graphics.getHeight();
		float ratio_y = ratio_h *mouse_coords.y;
		
		// HORIZONTAL -X-
		view_width    = this.camera.viewportWidth * this.camera.zoom;
		float ratio_w = view_width /  Gdx.graphics.getWidth();
		float ratio_x = ratio_w * mouse_coords.x;
		
		// CALCULATE WORLD VECTOR
		float y = (this.map_height - ratio_y) + (viewLocation.y - this.map_height);
		float x = (ratio_x + viewLocation.x);
		
		return new Vector2(x,y);
	}
	
	public void number_blocks_in_view(){
		view_height     = this.camera.viewportHeight * this.camera.zoom;
		view_width      = this.camera.viewportWidth * this.camera.zoom;
		render_blocks_x = (int) (view_width/2) + 1;
		render_blocks_y = (int) view_height + 1;
		cull_size = render_blocks_x + 1;
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		// ONLY FIRE IF GUI CLOSED
		if( button == Buttons.LEFT){
			LMB = true;
		}
		if( button == Buttons.RIGHT){
			RMB = true;
		}
		
		// MOUSE CLICKED UPDATE VALUES
		float flip_y = Gdx.graphics.getHeight() - y;
		// SCREEN COORDS
		this.mouse_screen_click_at.set(x, flip_y);
		// MAP COORDS
		this.mouse_map_click_at.set(get_map_coords(new Vector2(x,y)));
		this.processed_click = false;
		this.click_screen_area.setPosition(x-8, flip_y-8);
		click_map_area.setPosition(mouse_map_click_at.x-0.5f, mouse_map_click_at.y-0.5f);
		this.mouse_time = 0;
		
		return false;
	}
	
	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {	
		if( button == Buttons.LEFT){
			LMB = false;
		}
		if( button == Buttons.RIGHT){
			RMB = false;
		}
		
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

	@Override
	public boolean mouseMoved(int x, int y) {
		mouse_x      = x;
		mouse_y      = y;
		mouse_flip_y = Gdx.graphics.getHeight() - y;
		mouse_screen_vec.set(x, y);
		mouse_map_vec.set(get_map_coords(new Vector2(mouse_x, mouse_y)));
		
		float mouse_offset_x = x - (Gdx.graphics.getWidth()/2);
		float mouse_offset_y = y - (Gdx.graphics.getHeight()/2);
		off_centre_dst = vec_center.dst(new Vector2(mouse_offset_x, mouse_offset_y));
		
		return false;
	}
	
	@Override
	public boolean keyDown(int keycode) {
		if(allow_text){
			if (keycode == Keys.SHIFT_LEFT || keycode == Keys.SHIFT_RIGHT){
				shift = true;
			}
			
			if (input_textbox){
				String s = toString(keycode);
				input_str += s;
				
				if (keycode == Keys.ENTER){
					input_textbox = false;
					allow_text = false;
				}
			} else {
			// CHAT ACCESS
				if (keycode == Keys.ENTER){
					input = !input;
					input_str = "";
				} else {
					if (input){
						String s = toString(keycode);
						input_str += s;
					}	
				}
			}			
		} else {
			check_controls(keycode);
		}
		
		return false;
	}
	
	private void check_controls(int keycode) {
		if (keycode == Keys.G)
			Gdx.input.setCursorCatched(!Gdx.input.isCursorCatched());

		switch (keycode) {
		case Keys.W:
			move_up = true;
			break;
		case Keys.S:
			move_down = true;
			break;
		case Keys.A:
			move_left = true;
			break;
		case Keys.D:
			move_right = true;
			break;
		case Keys.SPACE:
			jump = true;
			break;
		case Keys.PERIOD:
			reset_map = true;
			//this.renderer.setDebug_mode(!this.renderer.isDebug_mode());
			break;
		case Keys.L:
			load_map = true;
			// Shot Rate Up
			//this.hero.getCurrent_weapon().setWeapon_fire_rate(this.hero.getCurrent_weapon().getWeapon_fire_rate() - 1);
			break;
		case Keys.O:
			save_map = true;
			// Shot Rate Up
			//this.hero.getCurrent_weapon().setWeapon_fire_rate(this.hero.getCurrent_weapon().getWeapon_fire_rate() - 1);
			break;
		case Keys.M:
			show_menu = true;
		case Keys.Y:
			break;
		case Keys.P:
			paused = !paused;
			break;
		case Keys.ESCAPE:
			Gdx.app.exit();
			break;
		case Keys.BACKSPACE:
			Gdx.app.exit();
			break;
		case Keys.NUM_0:
			break;
		case Keys.NUM_1:
			action = 0;
			break;
		case Keys.NUM_2:
			action = 1;
			break;
		case Keys.NUM_3:
			action = 2;
			break;
		case Keys.NUM_4:
			action = 3;
			break;
		case Keys.NUM_5:
			action = 4;
			break;
		case Keys.NUM_6:
			action = 5;
			break;
		case Keys.NUM_7:
			action = 6;
			break;
		case Keys.NUM_8:
			action = 7;
			break;
		case Keys.NUM_9:
			action = 8;
			break;
		}
    }

	@Override
	public boolean keyUp(int keycode) {
		switch (keycode) {
		case Keys.W:
			move_up    = false;
			break;
		case Keys.S:
			move_down  = false;
			break;
		case Keys.A:
			move_left  = false;
			break;
		case Keys.D:
			move_right = false;
			break;
		case Keys.SPACE:
			LMB       = false;
			break;
		case Keys.COMMA:
			input_text = true;
		case Keys.SHIFT_LEFT:
			shift = false;
			break;
		case Keys.SHIFT_RIGHT:
			shift = false;
			break;
		}
		return false;
	}

	public void update_mouse_map_v(){
		mouse_map_vec.set(get_map_coords(new Vector2(mouse_x, mouse_y)));
	}
	
	public String toString(int keycode) {
		caps_on = Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK);
		
		if (keycode < 0) {
			throw new IllegalArgumentException("keycode cannot be negative, keycode: " + keycode);
         }
         if (keycode > 255) {throw new IllegalArgumentException("keycode cannot be greater than 255, keycode: " + keycode);}
        
         switch (keycode) {
             case Keys.UNKNOWN:
            	 return "";
             case Keys.NUM_0:
                 return shift ? ")" : "0";
             case Keys.NUM_1:
                 return shift ? "!" : "1";
             case Keys.NUM_2:
                 return shift ? "@" : "2";
             case Keys.NUM_3:
                 return shift ? "£" : "3";
             case Keys.NUM_4:
                 return shift ? "$" : "4";
             case Keys.NUM_5:
                 return shift ? "%" : "5";
             case Keys.NUM_6:
                 return shift ? "^" : "6";
             case Keys.NUM_7:
                 return shift ? "&" : "7";
             case Keys.NUM_8:
                 return shift ? "*" : "8";
             case Keys.NUM_9:
                 return shift ? "(" : "9";
             case Keys.STAR:
                 return "*";
             case Keys.POUND:
                 return "#";
             case Keys.UP:
                 return "Up";
             case Keys.DOWN:
                 return "Down";
             case Keys.LEFT:
                 return "Left";
             case Keys.RIGHT:
                 return "Right";
             case Keys.CENTER:
                 return "Center";
             case Keys.CLEAR:
                 return "Clear";
             case Keys.A:
                 return shift || caps_on ? "A" : "a";
             case Keys.B:
                 return shift || caps_on ? "B" : "b";
             case Keys.C:
                 return shift || caps_on ? "C" : "c";
             case Keys.D:
                 return shift || caps_on ? "D" : "d";
             case Keys.E:
                 return shift || caps_on ? "E" : "e";
             case Keys.F:
                 return shift || caps_on ? "F" : "f";
             case Keys.G:
                 return shift || caps_on ? "G" : "g";
             case Keys.H:
                 return shift || caps_on ? "H" : "h";
             case Keys.I:
                 return shift || caps_on ? "I" : "i";
             case Keys.J:
                 return shift || caps_on ? "J" : "j";
             case Keys.K:
                 return shift || caps_on ? "K" : "k";
             case Keys.L:
                 return shift || caps_on ? "L" : "l";
             case Keys.M:
                 return shift || caps_on ? "M" : "m";
             case Keys.N:
                 return shift || caps_on ? "N" : "n";
             case Keys.O:
                 return shift || caps_on ? "O" : "o";
             case Keys.P:
                 return shift || caps_on ? "P" : "p";
             case Keys.Q:
                 return shift || caps_on ? "Q" : "q";
             case Keys.R:
                 return shift || caps_on ? "R" : "r";
             case Keys.S:
                 return shift || caps_on ? "S" : "s";
             case Keys.T:
                 return shift || caps_on ? "T" : "t";
             case Keys.U:
                 return shift || caps_on ? "U" : "u";
             case Keys.V:
                 return shift || caps_on ? "V" : "v";
             case Keys.W:
                 return shift || caps_on ? "W" : "w";
             case Keys.X:
                 return shift || caps_on ? "X" : "x";
             case Keys.Y:
                 return shift || caps_on ? "Y" : "y";
             case Keys.Z:
                 return shift || caps_on ? "Z" : "z";
             case Keys.COMMA:
                 return ",";
             case Keys.PERIOD:
                 return shift ? ">" : ".";
             case Keys.TAB:
                 return "    ";
             case Keys.SPACE:
                 return " ";
             case Keys.ENTER:
                 return "Enter";
             case Keys.DEL:
            	 if (input_str.length() > 0){
            		 input_str = input_str.substring(0, input_str.length()-1); 
            	 }
                 return ""; // also BACKSPACE
             case Keys.GRAVE:
                 return "`";
             case Keys.MINUS:
                 return shift ? "_" : "-";
             case Keys.EQUALS:
                 return "=";
             case Keys.LEFT_BRACKET:
                 return shift ? "{" : "[";
             case Keys.RIGHT_BRACKET:
                 return shift ? "}" : "]";
             case Keys.BACKSLASH:
                 return shift ? "|" : "\\";
             case Keys.SEMICOLON:
                 return shift ? ":" : ";";
             case Keys.APOSTROPHE:
                 return shift ? "\"" : "'";
             case Keys.SLASH:
                 return shift ? "?" : "/";
             case Keys.AT:
                 return shift ? "@" : "2";
             case Keys.NUM:
                 return "Num";
             case Keys.PLUS:
                 return "Plus";
             case Keys.MENU:
                 return "Menu";
             case Keys.ESCAPE:
                 return "Escape";
             case Keys.END:
                 return "End";
             case Keys.INSERT:
                 return "Insert";
             case Keys.COLON:
                 return ":";
             case Keys.F1:
                 return "F1";
             case Keys.F2:
                 return "F2";
             case Keys.F3:
                 return "F3";
             case Keys.F4:
                 return "F4";
             case Keys.F5:
                 return "F5";
             case Keys.F6:
                 return "F6";
             case Keys.F7:
                 return "F7";
             case Keys.F8:
                 return "F8";
             case Keys.F9:
                 return "F9";
             case Keys.F10:
                 return "F10";
             case Keys.F11:
                 return "F11";
             case Keys.F12:
                 return "F12";
             default:
            	 // key name not found
                 return "";
         }
 }

}