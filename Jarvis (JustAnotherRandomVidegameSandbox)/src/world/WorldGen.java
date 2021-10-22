package world;

import settings.Values;
import structures.Block;
import support.SimplexNoise;

public class WorldGen{
	// World-Specific Variables 
	private SimplexNoise noise;
	private String worldName;
	
	// Variables used in world generation
	
	public WorldGen(String worldName, SimplexNoise noise) 
	{
		this.worldName = worldName;
		this.noise = new SimplexNoise(0); // Later will be used with the noise parameter for custom seeds
	}
	
	/*
	 * Method to generate an entire world from scratch
	 * 
	 * Use FileLoader.SaveChunk() to save chunks to file.
	 */
	public void generateWorld() {
		// Create the folders for the world
		FileLoader.createWorldFolders(worldName);
		
		// For every chunk 1 - World.World_X_Size, generate it using the generate() function and save it to file.
		for(int chunkX = 0; chunkX < Values.World_X_Size; chunkX++) {
			Block[][] blocks = new Block[Values.Chunk_Size_X][Values.Chunk_Size_Y];
			
			blocks = generate(chunkX * Values.Chunk_Size_X, blocks);
			FileLoader.SaveChunk(worldName, new Chunk(chunkX, blocks));
		}
	}
	
	// These seem to be method - based variables, but moving them causes an error so im not moving them anymore lmao
	private int hold;
	private float seedDiff;
	// Returns a 2D array of blocks for a chunk.
	public Block[][] generate(int x, Block[][] blocks)
	{
		int f = 16; // frequency of seed blocks
		
		double d1 = 0; // 1st derivative, starts at zero
		double d2 = 0.0078125; // Wtf!!! Calculus!!! (its the 2nd derivative, to make things smoooooth)
		
		int[] seedBlocks = new int[ (Values.Chunk_Size_X / f) + 1]; // 3 "seeded" points from the noise
		double[] terrain = new double[Values.Chunk_Size_X];
		
		
		for(int i = 0; i < seedBlocks.length; i++)
		{
			seedBlocks[i] = (int)( noise.eval(x + (i * f), 0) * 32 ) + 20;
			System.out.println(seedBlocks[i] + ", at: " + (x + (i * f)));
		}
		
		for(int i = 0; i < Values.Chunk_Size_X; i++) {
			
			//step 1: terrain forming
			if(i % f == 0) //places the seed blocks
			{
				hold = i / f;
				
				terrain[i] = seedBlocks[hold];
				
				seedDiff = seedBlocks[hold + 1] - seedBlocks[hold];
				
			} 
			else
			{
				/* //debug stuff
				 * terrain[i] = 0; 
				 * if(i % 8 == 0) 
				 * { 
				 * terrain[i] = seedBlocks[hold] + (seedDiff / 2); 
				 * }
				 */
				
				 if(i % 8 == 0) //flips the derivative for the inflection point 
				{ 
					 d2 -= d2; 
				}
				  
				 d1 += d2;
				  
				 terrain[i] = (d1 * seedDiff) + terrain[i - 1];
			}
			
			//step 2: block placement (will move to separate method or class)
			for(int j = 0; j < Values.Chunk_Size_Y; j++)
			{
				if(j < terrain[i])
				{
					blocks[i][j] = new Block(1); //dirt
				} else if(j < terrain[i] + 1)
				{
					blocks[i][j] = new Block(2); //grass
				} else
				{
					blocks[i][j] = new Block(0); //air
				}			
			}

		}
		return(blocks);
	}
}