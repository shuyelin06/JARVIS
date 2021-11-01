package world;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import core.Coordinate;
import core.Engine;
import entities.Entity.EntType;
import entities.other.EBlock;
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
			blocks[x % Values.Chunk_Size_X][y] = new Block(2);
		} catch(Exception e) {}
	}
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
	
	//precondition: the block at i, j  is a grass block
	public int getGrassVariant(Block[][] blocks, int i, int j, int chunkIndex) {
		Block block = blocks[i][j];
		if(i >0 && i < 15 && j > 0 && j < Values.Chunk_Size_Y-1) {
			if(blocks[i][j+1].getID() == 0) { //top grass
				if(blocks[i-1][j].getID() == 0) { //left grass
					if(blocks[i+1][j].getID() == 0) {//right grass
						return 3; //left, top, right
					}else {
						return 1; //left, top
					}
				}else if(blocks[i+1][j].getID() == 0) {
					return 2; //top, right
				}else {
					return 0; //top
				}
				
			}else if(blocks[i-1][j].getID() == 0) {
				if(blocks[i+1][j].getID() == 0) {
					return 6; //left, right
				}else {
					return 4; //left
				}
			}else if(blocks[i+1][j].getID() == 0) {
				return 5; //right
			}
			
		}
		return 7;
		
	}
	//doesn't work yet
	public Block getAdjacentBlock(Block[][] blocks, int i, int j, int chunkIndex, int direction) {
		if(i + direction == Values.Chunk_Size_X) {
			Block[][] b = renderedChunks.get(chunkIndex-1).getBlocks();
			return b[0][j];
		}else if(i+direction < 0) {
			Block[][] b = renderedChunks.get(chunkIndex-1).getBlocks();
			return b[Values.Chunk_Size_X -1][j];
		}
		return blocks[i + direction][j];
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