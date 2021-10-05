package world;

import java.util.Arrays;

import gamestates.Game;
import structures.Block;
import support.SimplexNoise;

public class Chunk{
	// Size of chunk (in blocks)
	final public static int Chunk_Size_X = 25;
	final public static int Chunk_Size_Y = 100;
	
	// All blocks in the chunk
	private Block[][] blocks;
	
	//noise pattern
	SimplexNoise noise;
	
	//will be used for the height of the column of terrain
	private int terrain;
	
	// Location of the chunk's bottom-left corner
	private int chunkX;
	
	// Default constructor which generates values for every block
	public Chunk(int x) {
		this.chunkX = x;
		noise = new SimplexNoise(Game.seed);
		
		this.blocks = new Block[Chunk_Size_X][Chunk_Size_Y];
		
		for(int i = 0; i < Chunk_Size_X; i++) {
			terrain = (int) (noise.eval(x + i, 0) * (Chunk_Size_Y / 8) ); 
			//generates the height of the column + the surface.     ^8 over here just makes the changes less extreme
			for(int j = 0; j < Chunk_Size_Y; j++)
			{
				if(j < terrain + Values.surface)
				{
					blocks[i][j] = new Block(1);
				} else
				{
					blocks[i][j] = new Block(0);
				}
				
			}
			

		}
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