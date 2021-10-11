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
		
		double d1 = 0;
		double d2 = 0.015625; //Wtf!!!! Calculus!!!! (its the 2nd derivative, to make things smoooooth)
		
		
		seedBlocks = new int[ (width / f) + 1];
		terrain = new double[width];
		
		
		for(int i = 0; i < (width / f) + 1; i++)
		{
			seedBlocks[i] = (int) ( noise.eval(x  + (i * f), 0) * (90 / 4) ) + 10;
		}
		
		for(int i = 0; i < width; i++) {
			if(i % f == 0) //smooths blocks
			{
				terrain[i] = seedBlocks[i / f];
				
				seedDiff = seedBlocks[hold + 1] - seedBlocks[hold];
				
				if(i <= width - f) //uhhh
				{
					hold = i / f;
				}
				
				if(i / f >= 1) //flips the derivative for the inflection point
				{
					d2 = -d2;
				}
			} 
			else
			{
				d1 = d1 + (d2 * seedDiff * (i % f));
				
				terrain[i] = (float) (seedBlocks[hold] + ( d1 + terrain[i - 1] / f));
				System.out.println(terrain);
			}
			
			for(int j = 0; j < height; j++)
			{
				if(j < terrain[i])
				{
					blocks[i][j] = new Block(0);
				} else
				{
					blocks[i][j] = new Block(1);
				}			
			}

		}
		return(blocks);
	}
}