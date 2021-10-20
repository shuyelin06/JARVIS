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
import support.SimplexNoise;
import support.Spawning;

public class Game extends BasicGameState

{
  final String worldName = "Test2";
	boolean createNewWorld = true; // If testing worldGen, change to true.
	// Later to be moved to a worldselect gamestate
	
	// Render distance
	final public static int Render_Distance = 2;
	final public static float Spawn_Rate = 5f;
	
	
	// Gamestate ID
	int id;
	
	// The World
	public static World world = new World("Test2"); 
	
	// Use the "Test World" world for now until we get random world generation that can generate a new world, chunks and all, from scratch.
	
	// The Player
  private Player player;
  private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	
	// Slick2D Variables
	public static GameContainer gc;
	
	public Game(int id) 
	{
		this.id = id;
	}
	
	public Player getP() {
		return player;
	}
	public ArrayList<Enemy> getEnemies(){
		return enemies;
	}

  
	/*
	 * Initializing
	 */
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException 
	{		
		// Load Block Hashings
		FileLoader.LoadBlockHashings();
		
		// Shows the game's FPS
		gc.setShowFPS(true);
		this.gc = gc;
		
		player = new Player(world); //Had to move this here cause slickException
		// enemies = new ArrayList<Enemy>();
		
		/*
		 * Later to be put in the WorldSelect gamestate
		 */

	}
	
	/*
	 * Rendering - Game's Camera 
	 */
	// Where the player is centered on screen
	private final static float CenterX = Engine.RESOLUTION_X / 2;
	private final static float CenterY = Engine.RESOLUTION_Y / 2;
	
	// Debug Mode Tools
	ArrayList<Line> GridLines = getGridLines();
	public static boolean debugMode = false;
	
	public static float centerX() {
		return CenterX;
	}
	public static float centerY() {
		return CenterY;
	}
	
	// Render all entities on screen
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
			for(int i = 0; i < Chunk.Chunk_Size_X; i++) {
				for(int j = 0; j < Chunk.Chunk_Size_Y; j++) {
					g.setColor(Values.BlockHash.get(blocks[i][j].getID()));
					
					float[] position = renderPosition(chunk.getX() * Chunk.Chunk_Size_X + i, j);
					g.fillRect(position[0], position[1], Coordinate.ConversionFactor, Coordinate.ConversionFactor);
				}
			}
		}
    
	    // Render the player
		player.render(g, CenterX, CenterY);
		
//		//render enemies
		g.setColor(Color.red);
    	for(Enemy e : enemies) {
    		float[] position = renderPosition(e.getPosition().getX(), e.getPosition().getY());
    		g.draw(new Circle(position[0], position[1], 15));	
    	}
    
	}
	// Given two coordinates, display where they should be displayed on screen
	private float[] renderPosition(float x2, float y2) {
		float[] output = player.getPosition().displacement(x2, y2);
		
		output[0] = output[0] * Coordinate.ConversionFactor + CenterX;
		output[1] = Engine.RESOLUTION_Y - (output[1] * Coordinate.ConversionFactor + CenterY);
		
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
		
   
		Spawning.spawnEnemy(this, Spawn_Rate); // If you want to stop spawning, set 5f to 0f.
		for(Enemy e : enemies) {
			e.update();
		}
		Spawning.clearDead(this);
	}

	public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException 
	{
		// Generate a new world using steven y's code
		if(createNewWorld) {
			WorldGen gen = new WorldGen(world.getWorldName(), new SimplexNoise((int) (Math.random() * 10000)));
			gen.generateWorld();
		}
	}

	public void leave(GameContainer gc, StateBasedGame sbg) 
	{
		
	}

	/*
	 * Key Press Controls
	 */
	public void keyPressed(int key, char c)
	{
  		if(key == Input.KEY_W) { // Jumping
			player.jump();
		} 
	}
	public void controls() //all the control stuff
	{
		if ( gc.getInput().isKeyDown(Input.KEY_D) ) // Right movement
		{
			player.moveRight();
		}
		
		if ( gc.getInput().isKeyDown(Input.KEY_A) ) // Left movement
		{

			player.moveLeft();
		}
		
		if ( gc.getInput().isKeyDown(Input.KEY_S) ) // Downwards movement
		{
			player.fall();
		}
	}
	
	
//	public void isKeyPressed(int key, char c)
//	{
//		if(key == Input.KEY_SPACE) {
//			player.jump();
//		} else if(key == Input.KEY_S) {
//			player.fall();
//		}
//	}
	
	public void mousePressed(int button, int x, int y)
	{
		
	}
	
	
	// Returns the ID code for this game state
	public int getID() 
	{
		return id;
	}

	private ArrayList<Line> getGridLines(){
		ArrayList<Line> lines = new ArrayList<Line>();
		
		// Get horizontal gridlines
		for(int i = 0; i < (int) (Engine.RESOLUTION_Y / Coordinate.ConversionFactor) + 1; i++) {
			float y = Coordinate.ConversionFactor * i;
			lines.add(new Line(0, y, Engine.RESOLUTION_X, y));
		}
		
		// Get vertical gridlines
		for(int i = 0; i < (int) (Engine.RESOLUTION_X / Coordinate.ConversionFactor) + 1; i++) {
			float x = Coordinate.ConversionFactor * i;
			lines.add(new Line(x, 0, x, Engine.RESOLUTION_Y));
		}
		
		return lines;
	}
}
