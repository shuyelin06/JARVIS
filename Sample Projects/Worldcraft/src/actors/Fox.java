package actors;

import core.Cell;
import support.Values;

public class Fox extends Animal
{
	public Fox(Cell owner)
	{
		super(owner);
		setImage("res/defaultImage.png");
		w = 1;
		h = 1;
		vision = Values.FOX_VISION;
		vel = 3;
	}
}
