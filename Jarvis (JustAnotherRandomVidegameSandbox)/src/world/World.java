package world;

import java.util.ArrayList;

import gamestates.Game;

public class World 
{
	/*
	 * World Variables
	 */
	// Size of the world in chunks
	final public static int World_X_Size = 4;
	
	// World Name
	private String worldName;
	
	// All chunks rendered (into memory - we will have something loading and unloading chunks)
	int leftMostChunk;
	private ArrayList<Chunk> renderedChunks;
	
	// Generate world from scratch
	public World(String worldName)
	{
		this.worldName = worldName; 
		
		// Chunk Generation 
		renderedChunks = new ArrayList<Chunk>();
		
		for(int i = 0; i < World_X_Size; i++) {
			Chunk c = new Chunk(i);
			renderedChunks.add(c);
		}

	}
	
	public void renderChunks(int playerChunk) {
		int relRight = renderedChunks.get(renderedChunks.size() - 1).getX() - playerChunk;
		int relLeft = playerChunk - renderedChunks.get(0).getX();
		
		if(relRight < Game.Render_Distance) {
			// Load chunks to the right
			for(int i = relRight; i < Game.Render_Distance +  1; i++) {
				Chunk c = FileLoader.LoadChunk(worldName, playerChunk + i);
				
				if(c != null) {
					renderedChunks.add(c);
				}
			}
		} else {
			for(int i = relRight; i > Game.Render_Distance; i--) {
				// Derender chunks to the right
			}
		}
		
		if(relLeft < Game.Render_Distance) {
			// Load chunks to the right
			for(int i = relLeft; i < Game.Render_Distance + 1; i++) {
				Chunk c = FileLoader.LoadChunk(worldName, playerChunk - i);
				
				if(c != null) {
					renderedChunks.add(0, c);
				}
			}
		} else {
			// Derender chunks to the left
		}
	}
	
	public String getWorldName() {
		return worldName;
	}
	
//	public Chunk getChunk(int chunkX) {
//		int leftMost 
//	}
	public ArrayList<Chunk> getRenderedChunks(){
		return renderedChunks;
	}
}
