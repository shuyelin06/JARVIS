package background;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import core.Engine;

public class Background
{
	private boolean test;
	
	private int time;
	private Sky sky;
	private Hills day;
	private Desert desert;
	private Underground underground;
	
	public Background() throws SlickException
	{
		test = true;
		
		time = 0;
		sky = new Sky();
		day = new Hills();
		desert = new Desert();
		
		underground = new Underground();
	}
	
	public void render(Graphics g, float x, float y)
	{
		if(y > -500 && !test)
		{
			sky.render(g);
			day.render(g, x, y);
		} else
		{
			sky.render(g);
			desert.render(g, x, y);
		}

		
		if(y < 0)
		{
			underground.render(g, x, y);
		}
	}
	
	public void update()
	{
		time = Engine.game.getWorld().getTime();
		
		day.update(time);
		sky.update(time);
	}
}
