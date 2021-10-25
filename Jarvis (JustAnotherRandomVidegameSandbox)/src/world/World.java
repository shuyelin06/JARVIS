package world;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import core.Coordinate;
import gamestates.Game;
import settings.Values;
import structures.Block;

public class World 
{
	// World Name
	private String worldName;
	
	// All chunks rendered into memory
	private HashMap<Integer, Chunk> renderedChunks;
	
	// Generate world from scratch
	public World()
	{
		this.worldName = "Default World"; 
		
		// Chunk Generation 
		renderedChunks = new HashMap<Integer, Chunk>();
	}
	
	public void renderChunks(int playerChunk) {
		int leftMostChunk = playerChunk - Values.Render_Distance;
		int rightMostChunk = playerChunk + Values.Render_Distance;
		
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
				System.out.println("Unloading Chunk: " + i);
				FileLoader.SaveChunk(worldName, renderedChunks.get(i));
				
				iterator.remove();
			}
		}
	}
	
	public void changeName(String worldName) { this.worldName = worldName; }
	
	public Collection<Chunk> getAllChunks() { return renderedChunks.values(); }
	public Chunk getChunk(int i) { return renderedChunks.get(i); }
	public String getWorldName() { return worldName; }
	
	/*
	 * Simple block destroying / placing
	 */
	public void placeBlock(int x, int y) {
		try {
			int chunkX = x / Values.Chunk_Size_X;
			
			Block[][] blocks = renderedChunks.get(chunkX).getBlocks();
			blocks[x % Values.Chunk_Size_X][y] = new Block(1);
			
			System.out.println("Block Placed");
		} catch(Exception e) {
			System.out.println("Error in placing blocks");
		}
	}
	public void destroyBlock(int x, int y) {
		try {
			// Find the chunk the block is in
			int chunkX = x / Values.Chunk_Size_X;
			
			Block[][] blocks = renderedChunks.get(chunkX).getBlocks();
			blocks[x % Values.Chunk_Size_X][y] = new Block(0);
			
			System.out.println("Block Destroyed");
		} catch(Exception e) {
			System.out.println("Error in destroying blocks");
		}
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