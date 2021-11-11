package background;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import core.Engine;

public class Underground extends Scene
{	
	private Image bg;
	private Image bottom;
	private Image top;
	
	private float alpha;
	
	
	public Underground() throws SlickException 
	{
		bg = new Image("res/Background/grey.png");
		top = new Image("res/Background/caveTop.png");
		bottom = new Image("res/Background/caveBottom.png");
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
		top.setAlpha(alpha);
		bottom.setAlpha(alpha);
		
		bg.draw(0, 0, Engine.RESOLUTION_X, Engine.RESOLUTION_Y);
		
		top.draw(0, 0, Engine.RESOLUTION_X, 256);
		bottom.draw(0, Engine.RESOLUTION_Y - 256, Engine.RESOLUTION_X, 256);
		
	}
}
