package managers;

import java.util.ArrayList;
import java.util.HashMap;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.state.BasicGameState;

import core.Coordinate;
import core.Engine;
import entities.Entity;
import gamestates.Game;
import settings.Values;
import structures.Block;
import world.Background;
import world.Chunk;
import world.World;

// Will handle all of the game's graphics / display
public class DisplayManager {
	final private static double Span_Divide = 1.5;
	// Everything will be displayed relative to this center (which is the player)
	Coordinate center;
	
	// Tileset
	private HashMap<Integer, Integer> tileHash;
	private SpriteSheet tileset;
	
	private Background background;
	private Game game;
	
	public DisplayManager(Game g) throws SlickException {
		this.center = g.getPlayer().getPosition();
		
		this.background = new Background();
		this.game = g;
		
		
		// Tile set and hashings
		tileset = new SpriteSheet("res/tileset.png", 30, 30);
		//block image hashes: key = block id, 
		tileHash = new HashMap<Integer, Integer>();
		tileHash.put(1, 1); //block id 1 = dirt
		tileHash.put(2, 0); //block id 2 = grass
		tileHash.put(3, 2); //block id 3 = stone
		tileHash.put(4, 3);
		tileHash.put(5, 4);
		tileHash.put(6, 5);
	};
	
	public SpriteSheet getSpriteSheet() {
		return tileset;
	}
	public HashMap<Integer, Integer> getSpriteHash(){
		return tileHash;
	}
	public void renderBackground(Graphics g) {
		float[] backgroundPosition = positionOnScreen(0, Values.Surface);
		
		background.render(g, backgroundPosition[0], backgroundPosition[1]);
	}
	public void renderBlocks(Graphics g) {
		World world = game.getWorld();

		// X Span
		final int xSpan = (int) (Math.ceil(Engine.RESOLUTION_X / Coordinate.ConversionFactor) / Span_Divide);
		final int ySpan = (int) (Math.ceil(Engine.RESOLUTION_Y / Coordinate.ConversionFactor) / Span_Divide);
		
		// Iterate through every block that will be displayed on screen
		for(int i = -xSpan; i < xSpan; i++) {
			for(int j = -ySpan; j < ySpan; j++) {
				int blockX = (int) center.getX() + i;
				int blockY = (int) center.getY() + j;
				
				int relChunkX = blockX % Values.Chunk_Size_X;
				
				Chunk c = world.getChunk(blockX / Values.Chunk_Size_X);
				if(c == null) continue;
				
				int id = c.getBlocks()[relChunkX][blockY].getID();
				if(id == 0) continue; // Block ID 0: Air
				
				float[] position = positionOnScreen(blockX, blockY);
				if(id == 2) { // For Grass
					int variant = world.getGrassVariant(c.getBlocks(), blockX % Values.Chunk_Size_X, blockY, c.getX());
					if(variant == 7) {
						g.drawImage(tileset.getSubImage(0, 1), position[0], position[1]);
					}else {
						g.drawImage(tileset.getSubImage(variant, 0), position[0], position[1]);
					}
				} else {
					g.drawImage(tileset.getSubImage(0, tileHash.get(id)), position[0], position[1]);
				}
			}
		} 
	}
	public void renderEntities(Graphics g) {
		for(ArrayList<Entity> list: game.getAllEntities().values()) {
			for(Entity e: list) {
				float[] position = positionOnScreen(e.getPosition().getX(), e.getPosition().getY());
	    		e.render(g, position[0], position[1]);
			}	
    	}
		
	}
	
	public void renderPlayer(Graphics g) {
		game.getPlayer().render(g, Values.CenterX, Values.CenterY);
	}
	
	// Returns the pixel coordinates of some object relative to the screen's center.
	public float[] positionOnScreen(float x, float y) {
		float[] output = center.displacement(x, y);
		
		output[0] = output[0] * Coordinate.ConversionFactor + Values.CenterX;
		output[1] = Engine.RESOLUTION_Y - (output[1] * Coordinate.ConversionFactor + Values.CenterY);
		
		return output;
	}
	public float[] positionInGame(float x, float y) {
		float[] output = new float[2];
		
		// Find the distance from the pixel center
		output[0] = center.getX() + (x - Values.CenterX) / Coordinate.ConversionFactor;
		output[1] = center.getY() + 1 + (Values.CenterY - y) / Coordinate.ConversionFactor;
		
		return output;
	}
}