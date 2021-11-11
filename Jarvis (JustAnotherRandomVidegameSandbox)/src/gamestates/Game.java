package gamestates;

import java.util.ArrayList;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import background.Background;

import java.util.HashMap;
import java.util.function.Predicate;

import core.Coordinate;
import core.Engine;
import core.Values;
import entities.Entity;
import entities.living.*;
import entities.other.Projectile;
import managers.DisplayManager;
import entities.Entity.EntType;
import structures.Block;
import world.Chunk;
import world.World;
import world.WorldGen;
import support.Destroyer;
import support.FileLoader;
import support.Spawning;
import support.Utility;

public class Game extends BasicGameState {
	// Slick2D Variables
	private GameContainer gc;
	private StateBasedGame sbg;
	
	private int id;
	
	// Managers
	public DisplayManager displaymanager; // Manages the display / graphics in the game
	

	// Spawning
	Destroyer d; // Despawning
	
	// The Player
	private Player player;
	// Entities
	private HashMap<EntType, ArrayList<Entity>> entities;
	// The World
	private World world;
	
	// Constructor
	public Game(int id) { this.id = id; } 
	
	// Accessor Methods
	public DisplayManager getDisplayManager() { return displaymanager; }
	public int getID() { return id; }
	public Player getPlayer() { return player; }
	public GameContainer getGC() { return gc; }
	public World getWorld() { return world; }
	public ArrayList<Entity> getEntities(EntType type) { return entities.get(type); }
	public HashMap<EntType, ArrayList<Entity>> getAllEntities(){ return entities; }
	
	public void addEntity(EntType type, Entity e) { entities.get(type).add(e); }
	
	/* Initializing */
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException 
	{	
		// Saving the StateBasedGame
		this.sbg = sbg;
		this.gc = gc;		
		
		// Initializing the World, Player, and Entities list
		this.world = new World();
		this.player = new Player();
		this.entities = new HashMap<EntType, ArrayList<Entity>>() {
			private static final long serialVersionUID = 1L;
		{
			put(EntType.Hostiles, new ArrayList<Entity>());
			put(EntType.Items, new ArrayList<Entity>());
			put(EntType.Projectiles, new ArrayList<Entity>());
		}};
		
		// Initializing Destroying and Spawning Behaviors
		this.d = new Destroyer(this);
		
		// Initializing the Managers
		this.displaymanager = new DisplayManager(this);
	}
	
	/* Rendering - Game's Camera */
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException 
	{
		displaymanager.renderBackground(g); // Render the background of the game
		displaymanager.renderBlocks(g); // Render all blocks
		displaymanager.renderEntities(g); // Render all entities
		displaymanager.renderPlayer(g); // Render player
	}

	/*
	 * Update - Update different behaviors of the game
	 */
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException
	{	 
		// Check player key presses
		controls();
		// Mouse input, x and y
		cursorInput(gc.getInput().getMouseX(), gc.getInput().getMouseY());	
		
		// Check if any chunks need to be rendered or unrendered
		world.renderChunks((int) player.getPosition().getChunk());
		
		// Increments world time
		world.incrementTime();
		
		// Spawning Mechanics
		Spawning.spawnEnemy(this, Values.Spawn_Rate); // If you want to stop spawning, set 5f to 0f.
		// Derendering Mechanics
		d.update();
		
		// Update the player
		player.update();
		
		// Update all entities
		for(ArrayList<Entity> list: entities.values()) {
			for(Entity e: list) {
				if(e.isMarked()) continue;
				e.update();
			}
    	}
		
		// Remove all entities marked for removal
		Predicate<Entity> filter = (Entity e) -> (e.isMarked());
		for(ArrayList<Entity> list: entities.values()) {
			list.removeIf(filter);
		}
		
		// Sends to pause if player died
		if (!player.isAlive()) {
			player.update();
			Values.LastState = Engine.Game_ID;
			sbg.enterState(Engine.Pause_ID);
		}
	}

	public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException {}
	public void leave(GameContainer gc, StateBasedGame sbg) {}

	/* Key Mappings */
	public void keyPressed(int key, char c)
	{
  		switch(key) {
  			case Input.KEY_ESCAPE: // Exit the game
  				gc.exit();
  				break;

  			case Input.KEY_SPACE: // Jump Key Mapping (Space & W)
  			case Input.KEY_W:{
  				player.jump(20);
  				break;
  			}
  			
  			case Input.KEY_P: // Pause Key Binding
  				Values.LastState = Engine.Game_ID;
  				sbg.enterState(Engine.Pause_ID);
  				break;
  			case Input.KEY_BACKSLASH: // Debug Key Binding
  				Values.LastState = Engine.Game_ID;
  				sbg.enterState(Engine.Debug_ID);
  				break;
  			
  			case Input.KEY_R:
  				float x = gc.getInput().getAbsoluteMouseX() - Values.CenterX;
  				float y = Values.CenterY - gc.getInput().getAbsoluteMouseY();
  				
  				double theta = Math.atan(y / x);
  				if(x < 0) theta = theta + Math.PI;
  				
  				Projectile p = new Projectile(player.getPosition().getX(), player.getPosition().getY(), theta);
  				entities.get(EntType.Projectiles).add(p);
  				break;
  			
  			// All Inventory Key Bindings
  			case Input.KEY_1:
  			case Input.KEY_2:
  			case Input.KEY_3:
  			case Input.KEY_4:
  			case Input.KEY_5:
  			case Input.KEY_6:
  			case Input.KEY_7:
  			case Input.KEY_8:
  			case Input.KEY_9:
  			case Input.KEY_0:{
  				player.changeInventorySlot(key - 2);
  				break;
  			}
  			
  		}
  		
	}
	public void controls() //all the control stuff
	{
		if ( gc.getInput().isKeyDown(Input.KEY_D) ) player.setXSpeed(5f); // Right Movement
		if ( gc.getInput().isKeyDown(Input.KEY_A) ) player.setXSpeed(-5f); // Left Movement
		if ( gc.getInput().isKeyDown(Input.KEY_S) ) player.fall(); // Downwards movement
	}
	
	public void cursorInput(float x, float y)
	{
		float[] mouseCoordinate = displaymanager.positionInGame(x, y);
		
		// Do not process cursor input outside the player's reach, or if the player is dead.
		if(player.getPosition().magDisplacement(mouseCoordinate) > Values.Player_Reach || !player.isAlive()) return;
		
		if(gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) // Left click - destroy a block
		{
			world.destroyBlock((int) mouseCoordinate[0], (int) mouseCoordinate[1]);
		}
		
		if(gc.getInput().isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON)) // Right click - place a block
		{
			world.placeBlock((int) mouseCoordinate[0], (int) mouseCoordinate[1]);
		}
		
		if(gc.getInput().isKeyDown(Input.KEY_E)) // Test explosion, just for fun lol
		{
			for(int i = -2; i < 3; i++)
			{
				for(int j = -2; j < 3; j++)
				{
					world.destroyBlock((int) mouseCoordinate[0] + i, (int) mouseCoordinate[1] + j);
				}
			}
		}
	}
	
	public void mouseWheelMoved(int change) {
		player.adjustInventorySlot(change);
	}
}
