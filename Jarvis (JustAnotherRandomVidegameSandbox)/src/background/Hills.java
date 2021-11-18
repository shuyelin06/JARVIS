package background;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import core.Engine;

public class Hills extends Scene
{
	public Hills() throws SlickException
	{
		super();
		resX = Engine.RESOLUTION_X;
		resY = Engine.RESOLUTION_Y;
		
		background.setImage("res/Background/mountains-60.png");
		middleground.setImage("res/Background/hills1-80.png");
		foreground.setImage("res/Background/hills1.png");

		frontFiller = new Color(50, 122, 32);
		backFiller = new Color(43, 132, 69);
	}
}
