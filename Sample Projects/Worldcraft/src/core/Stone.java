package core;

import org.newdawn.slick.Color;

public class Stone extends Terrain
{
	double stoneFactor;
	
	public Stone(Cell owner, double percent)
	{
		super(owner);
		stoneFactor = (percent - 0.75) * 650;
		color = new Color((int)stoneFactor, 
				(int) (stoneFactor + (1 - percent * 20)), 
				(int) stoneFactor);
	}
	
	public void update()
	{
		
	}

}
