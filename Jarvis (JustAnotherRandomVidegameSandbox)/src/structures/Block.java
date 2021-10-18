package structures;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import world.Chunk;

public class Block{
	private int id; // Id of the block
	
	public Block(int id) {
		this.id = id;
	}
	public int getID() {
		return id;
	}

  public int getBlockX(int chunkX, int relX) {
		return chunkX * Chunk.Chunk_Size_X + relX;
	}
	public int getBlockY(int chunkY, int relY) {
		return chunkY * Chunk.Chunk_Size_Y + relY;
	}
}