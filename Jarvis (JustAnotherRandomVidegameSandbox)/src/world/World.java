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
	final public static int World_X_Size = 3;
	final public static int World_Y_Size = 3;
	
	// All chunks rendered (into memory - we will have something loading and unloading chunks)
	ArrayList<Chunk> renderedChunks;
	
	public World(int x, int y, Player mainPlayer)
	{
		// Chunk Generation 
		renderedChunks = new ArrayList<Chunk>();
		
		for(int i = 0; i < World_X_Size; i++) {
			for(int j = 0; j < World_Y_Size; j++) {
				Chunk c = new Chunk(i, j);
				renderedChunks.add(c);
			}
		}

	}
	
	public ArrayList<Chunk> getRenderedChunks(){
		return renderedChunks;
	}
}
