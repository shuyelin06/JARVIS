package background;

import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;

import core.Engine;

public class Desert extends Scene
{
	public Desert() throws SlickException
	{
		super();
		resX = Engine.RESOLUTION_X;
		resY = Engine.RESOLUTION_Y;
		
		background.setImage("mountains-60");
		middleground.setImage("dunes-80");
		foreground.setImage("dunes");
		
		frontFiller = new Color(233, 201, 124);
		backFiller = new Color(182, 136, 80);
	}
}
