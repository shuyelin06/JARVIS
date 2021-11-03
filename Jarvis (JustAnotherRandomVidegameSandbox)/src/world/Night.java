package world;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import core.Engine;

public class Night extends Scene
{
	private Image nightSky;
	public Night() throws SlickException
	{
		nightSky = new Image("res/nightTest.png");
	}
	
	public void render(Graphics g, int time)
	{
		nightSky.setAlpha(time / 1000);
		nightSky.draw(0, 0, Engine.RESOLUTION_X, Engine.RESOLUTION_Y);
	}
}
