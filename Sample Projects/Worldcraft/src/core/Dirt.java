package core;

import org.newdawn.slick.Color;

public class Dirt extends Terrain
{
	double growthFactor;
	double grassMax;
	
	int grassLevel;
	
	public Dirt(Cell owner, double percent)
	{
		super(owner);
		grassLevel = 0;
		growthFactor = percent;
		color = new Color(0, (int) (130 / growthFactor - 40), 0);
	}
	
	public void update()
	{
		
	}

}
