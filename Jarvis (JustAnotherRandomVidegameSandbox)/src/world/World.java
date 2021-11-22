package world;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import core.Coordinate;
import core.Engine;
import core.Values;
import entities.living.Player;
import entities.Entity.EntType;
import entities.other.EBlock;
import gamestates.Game;
import items.Item;
import structures.Block;
import support.FileLoader;

public class World 
{
	// Memory addresses for variables to be used
	private Player player;
	
	// World Name
	private String worldName;
	
	// All chunks rendered into memory
	private HashMap<Integer, Chunk> renderedChunks;
	private int leftMostChunk;
	private int rightMostChunk;
	
	//int for time, day cycle time
	private int time;
	private int timeCycle;
	
	// Generate world from scratch
	public World(Game game)
	{
		// Default World Name
		this.worldName = "Untitled"; 
		
		// Time Settings (36000 means 1 day per 10 min)
		this.time = 0;
		this.timeCycle = 18000;
		
		// Chunk Generation 
		renderedChunks = new HashMap<Integer, Chunk>();
		
		// Memory Addresses
		this.player = game.getPlayer();
	}
	
	// Accessor Methods
	public Collection<Chunk> getAllChunks() { return renderedChunks.values(); }
	public Chunk getChunk(int i) { return renderedChunks.get(i); }
	public String getWorldName() { 
		renderedChunks.clear();
		return worldName; 
	}
	public int getTime() { return time; }
	
	// Mutator Methods
	public void changeName(String worldName) { this.worldName = worldName; }
	
	// Main method in world that is called in game
	public void update() {
		renderChunks((int) player.getPosition().getChunk());
		incrementTime();
	}
	
	// Renders all chunks at a certain render distance from the player
	public void renderChunks(int playerChunk) {
		leftMostChunk = playerChunk - Values.Render_Distance;
		rightMostChunk = playerChunk + Values.Render_Distance;
		
		// Render New Chunks
		for(Integer x = leftMostChunk; x < rightMostChunk + 1; x++) {
			// Check if chunk is rendered
			if(renderedChunks.containsKey(x)) continue;
			else   {
				Chunk c = FileLoader.LoadChunk(worldName, x);
				if(c != null) renderedChunks.put(x, c);
			}
		}
		
		// Derender Old Chunks
		Iterator<Integer> iterator = renderedChunks.keySet().iterator();
		while(iterator.hasNext()) {
			Integer i = iterator.next();
			if(i < leftMostChunk || i > rightMostChunk) {
				FileLoader.SaveChunk(worldName, renderedChunks.get(i));
				iterator.remove();
			}
		}
	}
	
	// Increments the world time
	public void incrementTime() {
		time++; // Increment the world time
		time %= timeCycle; // At the Time Cycle, loop back to 0
	}
		
	public void clearChunks() {
		renderedChunks.clear();
	}
	/*
	 * Block Placement and Destruction
	 */
	public void destroyBlock(int x, int y) {
		try {
			// Find the chunk the block is in
			Block[][] blocks = renderedChunks.get(x / Values.Chunk_Size_X).getBlocks();
			
			int relX = x % Values.Chunk_Size_X;
			Block b = blocks[relX][y];
			
			if(b.getID() != 0) {
				blocks[relX][y] = new Block(0);
				Engine.game.addEntity(EntType.Items, new EBlock(b.getID(), x, y));
			} 
		} catch(Exception e) {}
	}
	
	/*
	 * World helper methods
	 */
	//precondition: the block at i, j  is a grass block
	public int getGrassVariant(Block[][] blocks, int i, int j, int chunkIndex) {
		int lBlock = getAdjacentBlock(blocks, i, j, chunkIndex, -1);
		int rBlock = getAdjacentBlock(blocks, i, j, chunkIndex, 1);
		int tBlock = blocks[i][j+1].getID();
		if(j > 0 && j < Values.Chunk_Size_Y-1) {
			if(tBlock == 0) { //top grass
				if(lBlock == 0) { //left grass
					if(rBlock == 0) {//right grass
						return 3; //left, top, right
					} else {
						return 1; //left, top
					}
				}else if(rBlock == 0) {
					return 2; //top, right
				}else {
					return 0; //top
				}
				
			}else if(lBlock == 0) {
				if(rBlock == 0) {
					return 6; //left, right
				}else {
					return 4; //left
				}
			}else if(rBlock == 0) {
				return 5; //right
			}
			
		}
		return 7;
		
	}
	public int getAdjacentBlock(Block[][] blocks, int i, int j, int chunkIndex, int direction) {
		if(i + direction == Values.Chunk_Size_X && chunkIndex < rightMostChunk) {//if it is on the right edge of the chunk
			Chunk c = getChunk(chunkIndex + 1);
			
			if(c == null) return 0;
			else return c.getBlocks()[0][j].getID();
		}else if(i + direction < 0 && chunkIndex > leftMostChunk) { //if on left edge of chunk
			Chunk c = getChunk(chunkIndex - 1);
			
			if(c == null) return 0;
			else return c.getBlocks()[Values.Chunk_Size_X-1][j].getID();
		}else if(direction > 0) {
			if(i >= 0 && i < Values.Chunk_Size_X-1) {
				return getChunk(chunkIndex).getBlocks()[i+direction][j].getID();
			}
		}else if(direction < 0) {
			if(i > 0 && i <= Values.Chunk_Size_X) {
				return getChunk(chunkIndex).getBlocks()[i+direction][j].getID();
			}
		}
			
		return 0;
	}
	
	//given a coordinate, return what the index of the block array it should be
	public int[] getBlockIndex(Coordinate c) {
		int[] blockIndex = new int[2];
		blockIndex[0] = (int) (c.getX() % Values.Chunk_Size_X);
		blockIndex[1] = (int) c.getY();
		
		return blockIndex;
	}
	
	public boolean openArea(Coordinate c, int w, int h) {
		int x = getBlockIndex(c)[0];
		int y = getBlockIndex(c)[1];
		//System.out.println("checking for open area at: " + x + ", " + y);
		for(int i = x - w; i < x + w; i++) {
			for(int j = y - h; j < y + h; j++) {
				if(this.getChunk((int)c.getChunk()).getBlocks()[x][y].getID() != 0){
					//System.out.println("solid block here");
					return false;
				}
			}
		}
		return true;
	}
}