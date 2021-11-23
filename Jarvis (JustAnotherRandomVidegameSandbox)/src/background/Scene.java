package background;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import core.Engine;

public class Scene
{
	protected Layer foreground;
	protected Layer middleground;
	protected Layer background;
	
	protected int resX; //placeholders so this code is actually readable
	protected int resY;
	
	protected Color frontFiller;
	protected Color backFiller;
	
	float alpha;
	
	public Scene() throws SlickException
	{
		resX = Engine.RESOLUTION_X;
		resY = Engine.RESOLUTION_Y;
		
		foreground = new Layer("placeholder", 0.4f, 0, 0.4f, 1.8f, 0.35f);
		middleground = new Layer("placeholder", 0.3f, 0, 0.4f, 1f, 0.25f);
		background  = new Layer("placeholder", 0.2f, 0, 0.35f, 1f, 0.4f);

		frontFiller = foreground.getColor();
		backFiller = middleground.getColor();
	}
	
	public void setSceneAlpha(float f)
	{
		alpha = f;
		foreground.setAlpha(f);
		middleground.setAlpha(f);
		background.setAlpha(f);
		
		frontFiller.a = f;
		backFiller.a = f;
	}
	
	public void render(Graphics g, float x, float y)
	{
		y  *= 0.5f; //parallax
		
		background.render(g, x, y);
		
		g.setColor(backFiller);
		g.fillRect(0, resY * 0.6f + (y * middleground.getParallax()), resX, resY * 0.25f);	
		
		middleground.render(g, x - 600, y);
		
		g.setColor(frontFiller);
		g.fillRect(0, resY * 0.7f + (y * foreground.getParallax()), resX, resY * 0.5f);	
		
		foreground.render(g, x, y);
	}
}
