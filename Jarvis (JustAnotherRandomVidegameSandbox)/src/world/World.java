package world;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

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
	
	// All chunks rendered into memory
	private HashMap<Integer, Chunk> renderedChunks;
	
	// Generate world from scratch
	public World(String worldName)
	{
		this.worldName = worldName; 
		
		// Chunk Generation 
		renderedChunks = new HashMap<Integer, Chunk>();
	}
	
	public void renderChunks(int playerChunk) {
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
	
	public Chunk getChunk(int i) {
		return renderedChunks.get(i);
	}
	public String getWorldName() {
		return worldName;
	}
  
	public Collection<Chunk> getAllChunks() {

		return renderedChunks.values();
	}
}