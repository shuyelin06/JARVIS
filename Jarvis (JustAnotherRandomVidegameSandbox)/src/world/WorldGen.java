package world;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import core.Coordinate;
import structures.Block;
import support.SimplexNoise;

public class WorldGen{
	// World Generation Variables (can we move this somewhere else?)
	SimplexNoise noise;

	double[] terrain;
	int divisor;
	
	int[] seedBlocks;
	int hold;
	float seedDiff;
	
	static Block[][] temp;
	
	public WorldGen(int x, int width, int height, Block[][] blocks) 
	{
		
		noise = new SimplexNoise(0); //change to Game.seed, just 0 for control purposes 
		
		temp = generate(x, width, height, blocks);
	}
	
	public static Block[][] lol()
	{
		return(temp);
	}
	
	public Block[][] generate(int x, int width, int height, Block[][] blocks)
	{
		int f = 16; // frequency of seed blocks
		
		double d1 = 0; //1st derivative, starts at zero
		double d2 = 0.015625; //Wtf!!! Calculus!!! (its the 2nd derivative, to make things smoooooth)
		
		seedBlocks = new int[ (width / f) + 1]; //3 "seeded" points from the noise
		terrain = new double[width];
		
		
		for(int i = 0; i < seedBlocks.length; i++)
		{
			seedBlocks[i] = (int) ( noise.eval(x  + (i * f), 0) * 32 ) + 20;
		}
		
		for(int i = 0; i < width; i++) {
			
			//step 1: terrain forming
			if(i % f == 0) //places the seed blocks
			{
				hold = i / f;
				
				terrain[i] = seedBlocks[hold];
				
				seedDiff = seedBlocks[hold + 1] - seedBlocks[hold];
				
				System.out.println(seedDiff + ", " + i);
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
					 d2 = -d2; 
				}
				  
				 d1 = d1 + d2;
				  
				 terrain[i] = (d1 * seedDiff) + terrain[i - 1];
			}
			
			//step 2: block placement (will move to separate method or class)
			for(int j = 0; j < height; j++)
			{
				if(j < terrain[i])
				{
					blocks[i][j] = new Block(1);
				} else if(j < terrain[i] + 1)
				{
					blocks[i][j] = new Block(2);
				} else
				{
					blocks[i][j] = new Block(0);
				}			
			}

		}
		return(blocks);
	}
}