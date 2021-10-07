package world;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import gamestates.Game;

public class World 
{
	/*
	 * World Variables
	 */
	// Size of the world in chunks
	final public static int World_X_Size = 50;
	
	// World Name
	private String worldName;
	
	// All chunks rendered (into memory - we will have something loading and unloading chunks)
	int leftMostChunk;
	private HashMap<Integer, Chunk> renderedChunks;
	
	// Generate world from scratch
	public World(String worldName)
	{
		this.worldName = worldName; 
		
		// Chunk Generation 
		renderedChunks = new HashMap<Integer, Chunk>();
		
//		foddr(int i = 0; i < World_X_Size; i++) {
//			renderedChunks.put(i, new Chunk(i));
//		}
	}
	
	public void renderChunks(int playerChunk) {
		System.out.println(renderedChunks.size());
		int leftMostChunk = playerChunk - Game.Render_Distance;
		int rightMostChunk = playerChunk + Game.Render_Distance;
		
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
		for(Integer i: renderedChunks.keySet()) {
			if(i < leftMostChunk || i > rightMostChunk) {
				System.out.println("Attempting to unload chunk " + i);
				FileLoader.SaveChunk(worldName, renderedChunks.get(i));
				
				System.out.println("Saved chunk " + i);
				renderedChunks.remove(i, renderedChunks.get(i));
			}
		}
	}
	
	public String getWorldName() {
		return worldName;
	}
	
//	public Chunk getChunk(int chunkX) {
//		int leftMost 
//	}
	public Collection<Chunk> getRenderedChunks(){
		return renderedChunks.values();
	}
}
