package core;

import org.newdawn.slick.Color;

public class Beach extends Terrain
{
	
	public Beach(Cell owner, double percent)
	{
		super(owner);
		color = new Color(255, 200, (int)(380 - percent * 450));
	}
	
	public void update()
	{
		
	}

}
