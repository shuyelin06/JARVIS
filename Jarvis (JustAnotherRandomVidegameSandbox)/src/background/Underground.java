package background;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import core.Engine;

public class Underground extends Scene
{	
	private Layer bg;
	private Layer bottom;
	private Layer top;
	
	private float alpha;
	
	public Underground() throws SlickException 
	{
		bg = new Layer("caves", "res/Background/grey.png", 0.2f, 0, 0, 4, 1);
		bottom = new Layer("cavesBottom", "res/Background/caveBottom.png", 0.3f, 0, 0.8f, 4, 0.2f);
		top  = new Layer("cavesTop", "res/Background/caveTop.png", 0.4f, 0, 0, 1, 0.3f);
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
		
		bg.render(g, x, 0);
		top.render(g, x, 0);
		bottom.render(g, x, 0);	
	}
}
