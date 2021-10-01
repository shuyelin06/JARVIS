package core;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import support.Settings;
import support.Values;

abstract public class Terrain 
{
	Cell owner;
	Color color;
	int curGrass;
	int maxGrass;
	boolean accessible = true;
	
	public Terrain(Cell owner)
	{
		this.owner = owner;
		color = new Color(255, 100, 255);
	}
	
	public boolean getAccessible()
	{
		return accessible;
	}
	
	abstract void update();
	
	public void render(Graphics g)
	{
		g.setColor(color);
		g.fillRect((owner.getX() - Game.world.getCameraX()) * Values.CELL_SIZE, 	// x
				   (owner.getY() - Game.world.getCameraY()) * Values.CELL_SIZE, 	// y
				   Values.CELL_SIZE, 												// width
				   Values.CELL_SIZE);												// height
		
		if(Settings.showGridlines)
		{
			g.setColor(Color.black);
			g.drawRect(owner.getX() * Values.CELL_SIZE, 	
					   owner.getY() * Values.CELL_SIZE, 	
					   Values.CELL_SIZE, 				
					   Values.CELL_SIZE);				
		}
	}

}
