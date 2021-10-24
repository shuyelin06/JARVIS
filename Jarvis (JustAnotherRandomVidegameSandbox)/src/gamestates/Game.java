package gamestates;

import java.util.ArrayList;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.util.Collection;

import core.Coordinate;
import core.Engine;
import entities.Enemy;
import entities.Player;
import settings.Values;
import structures.Block;
import world.Chunk;
import world.FileLoader;
import world.World;
import world.WorldGen;
import support.PerlinNoise;
import support.SimplexNoise;
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
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	
	
	// Constructor
	public Game(int id) 
	{
		this.id = id;
	}
	
	// Accessor Methods
	public int getID() { return id; }
	public Player getPlayer() { return player; }
	public ArrayList<Enemy> getEnemies(){ return enemies; }
	public GameContainer getGC() { return gc; }
	public World getWorld() { return world; }
	
	/* Initializing */
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException 
	{	
		// Saving the StateBasedGame
		this.sbg = sbg;
		this.gc = gc;		
		
		// Load Block Hashings
		FileLoader.LoadBlockHashings();
		
		// Initializations
		this.world = new World();
		this.player = new Player();
		this.enemies = new ArrayList<Enemy>();
		
	}
	
	/* Rendering - Game's Camera */
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException 
	{		
		float colorValue = 0.2f; // 255 / Engine.RESOLUTION_Y
		
		
		for(int i = 0; i < Engine.RESOLUTION_Y; i++) //really scuffed manual gradient, will replace with either image or proper gradient
		{
			g.setColor(new Color(20, (int) (colorValue * i), 255));
			g.fillRect(0, i, Engine.RESOLUTION_X, 1);
		}
		
		// Render all blocks in loaded chunks
		for(Chunk chunk: world.getAllChunks()) { // Iterate through every chunk
			Block[][] blocks = chunk.getBlocks(); // Get the blocks in the chunk
			
			// For every object, render its position relative to the player (with the player being in the center)
			for(int i = 0; i < Values.Chunk_Size_X; i++) {
				for(int j = 0; j < Values.Chunk_Size_Y; j++) {
					g.setColor(Values.BlockHash.get(blocks[i][j].getID()));
					
					float[] position = renderPosition(chunk.getX() * Values.Chunk_Size_X + i, j);
					g.fillRect(position[0], position[1], Coordinate.ConversionFactor, Coordinate.ConversionFactor);
				}
			}
		}
    
	    // Render the player
		player.render(g, Values.CenterX, Values.CenterY);
		
		// Render enemies
		//g.setColor(Color.red);
		
    	for(Enemy e : enemies) {
    		
    		float[] position = renderPosition(e.getPosition().getX(), e.getPosition().getY());
    		//g.draw(new Circle(position[0], position[1], 15));	
    		e.render(g, position[0], position[1]);
    	}
    
		
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
		// Check if any chunks need to be rendered or unrendered
		world.renderChunks((int) player.getPosition().getChunk());
		
		// Check player key presses
		controls();
		
		// Update the player's movement
		player.update();
		
   
		Spawning.spawnEnemy(this, Values.Spawn_Rate); // If you want to stop spawning, set 5f to 0f.
		for(Enemy e : enemies) {
			e.update();
		}
		Spawning.clearDead(this);
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
  			case Input.KEY_W: // Jump Key Mapping
  				player.jump();
  				break;
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
	
	
	public void mousePressed(int button, int x, int y)
	{
		
	}
}
