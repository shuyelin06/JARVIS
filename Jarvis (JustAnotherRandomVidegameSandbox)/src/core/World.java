package core;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import support.SimplexNoise;

public class World 
{
	SimplexNoise noise;
	
	//long seed = Utility.random(10000);
	
	ArrayList<Integer> blocks;
	int x;
	int y;
	int blockWidth;
	
	public World(int x, int y, int blockWidth)
	{
		noise = new SimplexNoise(0);
		blocks = new ArrayList<Integer>();
		this.x = x;
		this.y = y;
		this.blockWidth = blockWidth;
		
		for(int i = 0; i < x; i++)
		{
			blocks.add( (int) (y * noise.eval(i, 0) / 5) % blockWidth );
			
			System.out.println(noise.eval(i, 0));
		}
	}
	
	public void render(Graphics g)	throws SlickException // will eventually be done on camera class
	{
		g.setColor(new Color(55, 55, 55));
		
		for(int i = 0; i < x; i++)
		{
			g.fillRect(i * blockWidth, 1000, blockWidth, -blocks.get(i) * blockWidth - 500);
		}
	}
}
