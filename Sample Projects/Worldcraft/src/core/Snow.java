package core;

import org.newdawn.slick.Color;

import support.Utility;

public class Snow extends Terrain
{
	
	int colorInt;
	public Snow(Cell owner, double percent)
	{
		super(owner);
		colorInt = Utility.random(245, 255);
		color = new Color(colorInt, colorInt, colorInt);
	}
	
	public void update()
	{
		
	}

}
