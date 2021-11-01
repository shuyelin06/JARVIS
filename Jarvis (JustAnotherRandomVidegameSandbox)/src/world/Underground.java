package world;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import core.Engine;

public class Underground 
{	
	private Image bg;
	private float alpha;
	public Underground() throws SlickException 
	{
		bg = new Image("res/grey.png");
	}
	
	public void render(Graphics g, float x, float y)
	{
		if(y / -500 >= 1)
		{
			alpha = 1;
		} else
		{
			alpha = y / -500f;
		}
		bg.setAlpha(alpha);
		bg.draw(0, 0, Engine.RESOLUTION_X, Engine.RESOLUTION_Y);
	}
}
