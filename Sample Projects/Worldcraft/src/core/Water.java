package core;

import org.newdawn.slick.Color;

public class Water extends Terrain
{
	double depth;
	
	public Water(Cell owner, double percent)
	{
		super(owner);
		depth = percent;
		if(depth > 0.48)
		{
			color = new Color(0 , (int)(1 / (0.56 - depth) * 7), (int)(1 / (depth - 0.47) * 9));
		} 
		else if(depth < 0.4)
		{
			color = new Color(0, 30, 150);
		}
		else 
		{
			color = new Color(0, (int) (int)(1 / (0.56 - depth) * 5), (int) (1 / (0.56 - depth) * 25));
		}
	}
	
	public void update()
	{
		
	}

}
