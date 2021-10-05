package world;

import java.util.Arrays;

import gamestates.Game;
import structures.Block;
import support.SimplexNoise;

public class Chunk{
	// Size of chunk (in blocks)
	final public static int Chunk_Size_X = 32;
	final public static int Chunk_Size_Y = 96;
	
	// All blocks in the chunk
	private Block[][] blocks;
	
	
	
	//noise pattern
	SimplexNoise noise;
	int terrain;
	int[] seedBlocks;
	int hold;
	
	// Location of the chunk's bottom-left corner
	private int chunkX;
	
	// Default constructor which generates values for every block
	public Chunk(int x) {
		this.chunkX = x;
		noise = new SimplexNoise(Game.seed);
		seedBlocks = new int[Chunk_Size_X / 8];
		
		
		this.blocks = new Block[Chunk_Size_X][Chunk_Size_Y];
		
		for(int i = 0; i < (int) (Chunk_Size_X / 8); i++)
		{
			seedBlocks[i] = (int) (noise.eval(x + i, 0) * Chunk_Size_Y);
		}
		
		for(int i = 0; i < Chunk_Size_X; i++) {
			
			if(i % 8 == 0) //smooths blocks
			{
				terrain = seedBlocks[i / 8];
				if(i < Chunk_Size_X - 8)
				{
					hold = i / 8;
				}
			} else
			{
				terrain = (seedBlocks[hold] - seedBlocks[hold + 1] ) * i / 8;
			}
			
			System.out.println(terrain);
			
			for(int j = 0; j < Chunk_Size_Y; j++)
			{
				if(j < terrain)
				{
					blocks[i][j] = new Block(0);
				} else
				{
					blocks[i][j] = new Block(1);
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