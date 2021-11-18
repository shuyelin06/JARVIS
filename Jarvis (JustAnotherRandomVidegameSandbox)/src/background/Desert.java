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
		
		background.setImage("res/Background/mountains-60.png");
		middleground.setImage("res/Background/dunes-80.png");
		foreground.setImage("res/Background/dunes.png");
		
		frontFiller = new Color(233, 201, 124);
		backFiller = new Color(182, 136, 80);
	}
}
