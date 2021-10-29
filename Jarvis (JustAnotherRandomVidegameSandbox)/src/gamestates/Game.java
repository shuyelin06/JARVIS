package gamestates;

import java.util.ArrayList;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.util.HashMap;
import java.util.function.Predicate;

import core.Coordinate;
import core.Engine;
import entities.Entity;
import entities.living.*;
import entities.Entity.EntType;
import settings.Values;
import structures.Block;
import world.Background;
import world.Chunk;
import world.FileLoader;
import world.World;
import world.WorldGen;
import support.Spawning;

public class Game extends BasicGameState {
	// Slick2D Variables
	private GameContainer gc;
	private StateBasedGame sbg;
	
	private int id;
	
	// The World
	private World world;
	private boolean createNewWorld = true; // If testing worldGen, change to true.
	
	// The Player
	private Player player;
	private HashMap<EntType, ArrayList<Entity>> entities;
	
	Background bg;
	
	// Constructor
	public Game(int id) { this.id = id; } 
	
	// Accessor Methods
	public int getID() { return id; }
	public Player getPlayer() { return player; }
	public GameContainer getGC() { return gc; }
	public World getWorld() { return world; }
	public ArrayList<Entity> getEntities(EntType type) { return entities.get(type); }
	
	public void addEntity(EntType type, Entity e) { entities.get(type).add(e); }
	
	/* Initializing */
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException 
	{	
		// Saving the StateBasedGame
		this.sbg = sbg;
		this.gc = gc;		
		
		// Load Block Hashings
		FileLoader.LoadBlockHashings();
		
		this.bg = new Background();
		
		// Initializations
		this.world = new World();
		
		this.player = new Player();

		this.entities = new HashMap<EntType, ArrayList<Entity>>() {
			private static final long serialVersionUID = 1L;
		{
			put(EntType.Hostiles, new ArrayList<Entity>());
			put(EntType.Items, new ArrayList<Entity>());
			put(EntType.Projectiles, new ArrayList<Entity>());
		}};
	}
	
	/* Rendering - Game's Camera */
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException 
	{	
		float[] bgPosition = renderPosition(0, Values.Surface);
		bg.render(g, bgPosition[0], bgPosition[1]);
		
		// Render all blocks in loaded chunks
		for(Chunk chunk: world.getAllChunks()) { // Iterate through every chunk
			Block[][] blocks = chunk.getBlocks(); // Get the blocks in the chunk
			
			// For every object, render its position relative to the player (with the player being in the center)
			for(int i = 0; i < Values.Chunk_Size_X; i++) {
				for(int j = 0; j < Values.Chunk_Size_Y; j++) {
					g.setColor(Values.BlockHash.get(blocks[i][j].getID()));
					
					float[] position = renderPosition(chunk.getX() * Values.Chunk_Size_X + i, j);
					if(position[0] > -Coordinate.ConversionFactor && position[0] < Engine.RESOLUTION_X
							&& position[1] > -Coordinate.ConversionFactor && position[1] < Engine.RESOLUTION_Y)
					{
						g.fillRect(position[0], position[1], Coordinate.ConversionFactor, Coordinate.ConversionFactor);
					}
				}
			}
		}
		
		// Render all entities
		for(ArrayList<Entity> list: entities.values()) {
			for(Entity e: list) {
				float[] position = renderPosition(e.getPosition().getX(), e.getPosition().getY());
	    		e.render(g, position[0], position[1]);
			}	
    	}
		
		// Render the player
		player.render(g, Values.CenterX, Values.CenterY);
	}
	// Given two coordinates, display where they should be displayed on screen
	private float[] renderPosition(float x2, float y2) {
		float[] output = player.getPosition().displacement(x2, y2);
		
		output[0] = output[0] * Coordinate.ConversionFactor + Values.CenterX;
		output[1] = Engine.RESOLUTION_Y - (output[1] * Coordinate.ConversionFactor + Values.CenterY);
		
		return output;
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
		
		// Update the player's movement
		player.update();
		
		// Spawning Mechanics
		Spawning.spawnEnemy(this, Values.Spawn_Rate); // If you want to stop spawning, set 5f to 0f.
		
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
	}

	public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException 
	{
		// Generate a new world using Steven y's code
		if(createNewWorld) {
			WorldGen gen = new WorldGen(world.getWorldName(), (int) (Math.random() * 10000));
			gen.generateWorld();
		}
		
	}

	public void leave(GameContainer gc, StateBasedGame sbg)  {}

	/* Key Mappings */
	public void keyPressed(int key, char c)
	{
  		switch(key) {
  			case Input.KEY_ESCAPE: // Exit the game
  				gc.exit();
  				break;

  			case Input.KEY_SPACE: // Jump Key Mapping (Space & W)
  			case Input.KEY_W:{
  				player.jump();
  				break;
  			}	
  			case Input.KEY_P: // Pause Key Binding
  				sbg.enterState(Engine.Pause_ID);
  				break;
  			case Input.KEY_BACKSLASH: // Debug Key Binding
  				sbg.enterState(Engine.Debug_ID);
  				break;
  		}
  		
	}
	public void controls() //all the control stuff
	{
		if ( gc.getInput().isKeyDown(Input.KEY_D) ) player.moveRight(); // Right Movement
		if ( gc.getInput().isKeyDown(Input.KEY_A) ) player.moveLeft(); // Left Movement
		if ( gc.getInput().isKeyDown(Input.KEY_S) ) player.fall(); // Downwards movement

	}
	
	public void cursorInput(float x, float y)
	{
		float[] mouseCoordinate = getAbsoluteCoordinate(x, y);
		
		
		if(gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON))
		{
			world.destroyBlock((int) mouseCoordinate[0], (int) mouseCoordinate[1]);
		}
		
		if(gc.getInput().isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON))
		{
			world.placeBlock((int) mouseCoordinate[0], (int) mouseCoordinate[1]);
		}
		
		if(gc.getInput().isKeyDown(Input.KEY_E)) //test explosion, just for fun lol
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
	
	
	// Given some pixel on screen, returns their coordinate position in the game (might be slightly off)
	public float[] getAbsoluteCoordinate(float x, float y) {
		float[] output = new float[2];
		
		// Find the distance from the pixel center
		output[0] = player.getPosition().getX() + (x - Values.CenterX) / Coordinate.ConversionFactor;
		output[1] = player.getPosition().getY() + 1 + (Values.CenterY - y) / Coordinate.ConversionFactor;
		
		return output;
	}
}
