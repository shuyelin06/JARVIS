package background;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import core.Engine;
import core.Values;

public class Underground extends Scene
{	
	private Image bg;
	private Image bottom;
	private Image top;
	
	private float alpha;
	
	
	public Underground() throws SlickException 
	{
		bg = Values.Images.get("grey");
		top = Values.Images.get("caveTop");
		bottom = Values.Images.get("caveBottom");
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
		
		top.draw((x * 0.25f % Engine.RESOLUTION_X) + Engine.RESOLUTION_X,
				0, 
				-Engine.RESOLUTION_X, 
				Engine.RESOLUTION_Y * 0.475f);
		top.draw((x * 0.25f % Engine.RESOLUTION_X) + (2 * Engine.RESOLUTION_X),
				0,
				-Engine.RESOLUTION_X, 
				Engine.RESOLUTION_Y * 0.475f);
		
		bottom.draw((x * 0.3f % Engine.RESOLUTION_X) + Engine.RESOLUTION_X,
				Engine.RESOLUTION_Y - (Engine.RESOLUTION_Y * 0.237f), 
				-Engine.RESOLUTION_X, 
				Engine.RESOLUTION_Y * 0.237f);
		bottom.draw((x * 0.3f % Engine.RESOLUTION_X) + (2 * Engine.RESOLUTION_X),
				Engine.RESOLUTION_Y - (Engine.RESOLUTION_Y * 0.237f),
				-Engine.RESOLUTION_X, 
				Engine.RESOLUTION_Y * 0.237f);
		
	}
}
