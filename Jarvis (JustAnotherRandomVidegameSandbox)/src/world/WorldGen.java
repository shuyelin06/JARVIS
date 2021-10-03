package world;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import core.Coordinate;
import support.SimplexNoise;

public class WorldGen{
	// World Generation Variables (can we move this somewhere else?)
	SimplexNoise noise;
	
	ArrayList<Integer> blocks;
	int x;
	int y;
	private static final int blockWidth = Coordinate.ConversionFactor;
	
	//long seed = Utility.random(10000);
	
	public WorldGen() {
		/*
		noise = new SimplexNoise(0);
		blocks = new ArrayList<Integer>();
		this.x = x;
		this.y = y;
		
		for(int i = 0; i < x; i++)
		{
			blocks.add((int) (y * noise.eval(i, 0) / 5) % blockWidth);
			
			System.out.println(noise.eval(i, 0));
		}*/
		
	}
	
	public void render(Graphics g)	throws SlickException // will eventually be done on camera class
	{
		g.setColor(new Color(55, 55, 55));
		
		for(int i = 0; i < x; i++)
		{
			g.fillRect((i * blockWidth), 1000, blockWidth, -blocks.get(i) * blockWidth - 500);
		}
	}
}