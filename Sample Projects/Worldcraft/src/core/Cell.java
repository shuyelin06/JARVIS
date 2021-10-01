package core;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import actors.Actor;

public class Cell 
{
	int x;
	int y;

	Terrain terrain;
	Actor actor;
	
	public Cell(int x, int y)
	{
		this.x = x;
		this.y = y;	
	}
	
	public void setActor(Actor a)
	{
		actor = a;
	}
	
	public Actor getActor()
	{
		return actor;
	}
	
	public boolean getAccessible()
	{
		return terrain.getAccessible();
	}
	
	public void setElevation(float percent)
	{
		if(percent < .52)
		{
			terrain = new Water(this, percent);
		}
		else if(percent < .6)
		{
			terrain = new Beach(this, percent);
		}
		else if(percent < .85)
		{
			terrain = new Dirt(this, percent);
		}
		else if(percent < 1)
		{
			terrain = new Stone(this, percent);
		}
		else
		{
			terrain = new Snow(this, percent);
		}
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public Terrain getTerrain()
	{
		return terrain;
	}
	
	public void render(Graphics g)
	{
		terrain.render(g);
	}
	
	public void update()
	{
		terrain.update();
	}
}
