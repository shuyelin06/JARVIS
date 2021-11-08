package world;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import core.Engine;

public class Night extends Scene
{
	private Image nightSky;
	private float alpha;
	
	public Night() throws SlickException
	{
		nightSky = new Image("res/background/night.png");
		alpha = 0;
	}
	
	public void render(Graphics g, int time)
	{
		if(alpha >= 0.8)
		{
			alpha = 0.8f;
		} 
		else
		{
			alpha = time * 0.003f;
		}
		nightSky.setAlpha(alpha);
		nightSky.draw(0, 0, Engine.RESOLUTION_X, Engine.RESOLUTION_Y);

	}
}
