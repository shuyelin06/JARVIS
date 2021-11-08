package world;

import settings.Values;
import structures.Block;
import structures.Tree;
import support.FileLoader;
import support.SimplexNoise;

public class WorldGen{
	// World-Specific Variables 
	private SimplexNoise noise; //noise for the rest
	//might add other noises for other things
	
	private String worldName;
	
	// Variables used in world generation
	
	public WorldGen(String worldName, int seed) 
	{
		this.worldName = worldName;
		
		noise = new SimplexNoise(seed); // Later will be used with the noise parameter for custom seeds
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
	
	
	// Returns a 2D array of blocks for a chunk.
	public Block[][] generate(int x, Block[][] blocks)
	{
		double[] terrain = new double[Values.Chunk_Size_X];
		
		for(int i = 0; i < Values.Chunk_Size_X; i++) {
			
			double temp = noise.eval((x + i) * 0.03125, 0);
			
			terrain[i] = temp * 32 + Values.Surface;
			
			//step 2: block placement (will move to separate method or class)
			for(int j = 0; j < Values.Chunk_Size_Y; j++)
			{
				if(j < terrain[i] - (Math.random() * 2 + 5))
				{
					blocks[i][j] = new Block(3); //stone
				}
				else if(j < terrain[i])
				{
					blocks[i][j] = new Block(1); //dirt;
				} 
				else
				{
					blocks[i][j] = new Block(0); //air
				}			
			}

		}
		
		populate(blocks, x);
		
		return(blocks);
	}
	
	public Block[][] populate(Block[][] blocks, int x)
	{

		float[][] caves = new float[Values.Chunk_Size_X][Values.Chunk_Size_Y];
		
		float[][] ores = new float[Values.Chunk_Size_X][Values.Chunk_Size_Y];
		
		for(int i = 0; i < Values.Chunk_Size_X; i++)
		{
			for(int j = 0; j < Values.Chunk_Size_Y; j++)
			{
				float smoothGen = 0.1f; //adjust to smooth generation;
				
				caves[i][j] = (float) noise.eval((i + x) * smoothGen * .2, j * smoothGen * .5);
				caves[i][j] *= caves[i][j];
				caves[i][j] *= caves[i][j];
				
				ores[i][j] = (float) noise.eval((i + x) * smoothGen, j * smoothGen);
				
				if(blocks[i][j].getID() == 3)
				{
					/*
					 Note about the noise: make the threshold closer to 1 (array[i][j] >= x) for smaller veins
					 Make smoothGen larger along with adjusting the threshold to be higher to make the ores more rare
					 
					 trying to get it to work with just one noise pattern to save power
					 */
					if(ores[(i + 10) % Values.Chunk_Size_X][Math.abs(Values.Chunk_Size_Y - j - 1) ] >= 0.88) //diamonds?
					{
						//System.out.println(j * -1 % Values.Chunk_Size_Y + ", " + j % Values.Chunk_Size_Y);
						blocks[i][j].setID(6);
					} 
					else if(ores[i][j] >= .85) //gold for now
					{
						blocks[i][j].setID(5);
					} 
					else if(ores[(i + 5) % Values.Chunk_Size_X][(j + 50) % Values.Chunk_Size_Y] >= 0.75) //coal or some other ore
					{
						blocks[i][j].setID(4);
					} 
				}	
				
				if(caves[i][j] > 0.2) //rudimentary caves
				{
					blocks[i][j].setID(0);
				}
				
				if(blocks[i][j].getID() == 1) //populating dirt
				{					
					if(adjacentTo(i, j, 0, blocks) > 0) //grasssss
					{
						blocks[i][j].setID(2);
					}
				}
				
				if(blocks[i][j].getID() == 2 && adjacentTo(i, j, 'n', 0, blocks) 
						&& adjacentTo(i, j + 1, 'e', 0, blocks) && adjacentTo(i, j + 1, 'w', 0, blocks)) // tree generation bruhhhh
				{
					if(i % (4 + (int)Math.random() * 3) == 0)
					{
						blocks[i][j + 1].setID(3);
					}
					
				}	
			}
		}
		
		return(blocks);
	}
	
	public Block[][] structures(Block[][] blocks)
	{
		for(int i = 0; i < Values.Chunk_Size_X; i++)
		{
			
		}
		return(blocks);
	}
	
	public boolean adjacentTo(int x, int y, char direction, int id, Block[][] blocks)
	{
		if(direction == 'n')
		{
			if(inBounds(x, y + 1))
			{
				return(blocks[x][y + 1].getID() == id);
			}
		} 
		else if(direction == 's')
		{
			if(inBounds(x, y - 1))
			{
				return(blocks[x][y - 1].getID() == id);
			}
		}
		else if(direction == 'e')
		{
			if(inBounds(x + 1, y))
			{
				return(blocks[x + 1][y].getID() == id);
			}
		}
		else if(direction == 'w')
		{
			if(inBounds(x - 1, y))
			{
				return(blocks[x - 1][y].getID() == id);
			}
		}
		
		return(false);
	}
	
	public int adjacentTo(int x, int y, int id, Block[][] blocks) //method for seeing if a certain kind of block is next to it
	//still need to fix
	{
		int count = 0;
		for(int i = -1; i < 2; i++)
		{
			for(int j = -1; j < 2; j++)
			{
				if(inBounds(x + i, y + j)) // hmmm
				{
					if(blocks[x + i][y + j].getID() == id) 
					{
						count++;					
					}
				}
			}
		}
		
		return(count);
	}
	
	private boolean inBounds(int x, int y) //to avoid the out of bounds error
	{
		return(x > -1 && x < Values.Chunk_Size_X
		&& y > -1 && y < Values.Chunk_Size_Y);
	}
}