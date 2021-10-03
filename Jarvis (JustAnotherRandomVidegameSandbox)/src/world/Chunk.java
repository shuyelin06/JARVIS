package world;

import java.util.Arrays;

import structures.Block;

public class Chunk{
	// Size of chunk (in blocks)
	final public static int Chunk_Size_X = 16; 
	final public static int Chunk_Size_Y = 16;
	
	// All blocks in the chunk
	private Block[][] blocks;
	
	// Location of the chunk's bottom-left corner
	private int chunkX;
	private int chunkY;
	
	public Chunk(int x, int y) {
		this.chunkX = x;
		this.chunkY = y;
		
		this.blocks = new Block[Chunk_Size_X][Chunk_Size_Y];
	}
	
	// Returns the chunk x
	public int getX(){
		return chunkX;
	}
	public int getY() {
		return chunkY;
	}
	public Block[][] getBlocks() {
		return blocks;
	}
	
	// Update a block in the chunk
	public void updateBlock(int x, int y, Block newBlock) {
		blocks[x][y] = newBlock;
		
		System.out.println("Block updated");
	}
	
}