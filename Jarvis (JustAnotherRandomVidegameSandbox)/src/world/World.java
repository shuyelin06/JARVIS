package world;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import core.Coordinate;
import entities.Player;
import structures.Block;
import support.SimplexNoise;

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
	ArrayList<Chunk> renderedChunks;
	
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
	
	// Copy a World
	public World(World world) {
		
	}
	
	public String getWorldName() {
		return worldName;
	}
	public ArrayList<Chunk> getRenderedChunks(){
		return renderedChunks;
	}
}
