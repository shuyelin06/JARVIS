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

import core.Coordinate;
import core.Engine;
import entities.Enemy;
import entities.Player;
import settings.Values;
import structures.Block;
import world.Chunk;
import world.World;
import support.Spawning;

public class Game extends BasicGameState 
{		
	// Render distance
	final public static int Render_Distance = 2;
	
	// Gamestate ID
	int id;
	
	// The World
	World world = new World("Test World"); 
	
	// The Player
	private Player player = new Player();
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	
	// The world's seed
	public static int seed = (int) (Math.random() * 10000); //generates seed
	
	//slick2d variables
	public static GameContainer gc;
	
	
	public Game(int id) 
	{
		this.id = id;
	}

	// Initializer, first time
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException 
	{
		gc.setShowFPS(true); // Shows the FPS of the game
		this.gc = gc;
	}
	
	public Player getP() {
		return player;
	}
	public ArrayList<Enemy> getEnemies(){
		return enemies;
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
		// Render all blocks in loaded chunks

		for(Chunk chunk: world.getRenderedChunks()) {

			Block[][] blocks = chunk.getBlocks(); // Get the blocks in the chunk
			
			// For every object, render its position relative to the player (with the player being in the center)
			for(int i = 0; i < Chunk.Chunk_Size_X; i++) {
				for(int j = 0; j < Chunk.Chunk_Size_Y; j++) {
					int id = blocks[i][j].getID();
					
					g.setColor(Values.BlockHash.get(id));
					
					float[] position = renderPosition(chunk.getX() * Chunk.Chunk_Size_X + i, j);
					blocks[i][j].render(g,
							position[0],
							position[1],
							Coordinate.ConversionFactor,
							Coordinate.ConversionFactor);
				}
			}
		}
    
    // Render the player
		g.setColor(new Color(255f, 255f, 255f, 1f));
		g.draw(new Circle(CenterX, CenterY, player.getSize())); // Render the player in the middle of the screen
		player.render(g);
		
    //render enemies
    	for(Enemy e : enemies) {
    		e.render(g, player.getPosition().getX(), player.getPosition().getY()); //render the enemy relative to the player
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
		// world.chunkRendering(player.getPosition().getChunk());
		
//		// Render new chunks
//		for(int i = -(Render_Distance); i < Render_Distance + 1; i++){
//			if(!world.chunkRendered(i)) world.renderChunks(i);
//		}
		
		// Update the player's movement
		player.update();
		
		Spawning.spawnEnemy(this, 1f);
		for(Enemy e : enemies) {
			e.update();
		}
		Spawning.clearDead(this);
	}

	public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException 
	{
		
	}

	public void leave(GameContainer gc, StateBasedGame sbg) 
	{
		
	}


	public void keyPressed(int key, char c)
	{
		if(key == Input.KEY_D) { // Move right
			player.moveRight();
		} else if (key == Input.KEY_A){
			player.moveLeft();
		}
		
		if(key == Input.KEY_W) {
			player.jump();
		} else if(key == Input.KEY_S) {
			player.fall();
		}
	}
	
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
