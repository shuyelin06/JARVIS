package world;

import java.util.Arrays;

import settings.Values;
import gamestates.Game;
import structures.Block;
import support.SimplexNoise;

public class Chunk{
	// Size of chunk (in blocks)
	final public static int Chunk_Size_X = 25;
	final public static int Chunk_Size_Y = 100;
	
	// All blocks in the chunk
	private Block[][] blocks;
	
	WorldGen generation;
	SimplexNoise noise;
	
	int[] seedBlocks;
	int hold;
	float seedDiff; // difference of 2 seed blocks
	
	//will be used for the height of the column of terrain
	private int terrain;
	
	// Location of the chunk's bottom-left corner
	private int chunkX;
	
	// Default constructor which generates values for every block
	public Chunk(int x) {
		this.chunkX = x;
		
		this.blocks = new Block[Chunk_Size_X][Chunk_Size_Y];
		

		generation = new WorldGen(x * 32, Chunk_Size_X, Chunk_Size_Y, blocks);
		
		blocks = WorldGen.lol();
	}
	public Chunk(int x, Block[][] blocks) {
		this.chunkX = x;
		this.blocks = blocks;
	}
	
	// Returns the chunk x
	public int getX(){
		return chunkX;
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