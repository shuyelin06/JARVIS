package background;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import core.Engine;
import gamestates.Game;

public class Background
{
	private int time;
	private Sky sky;
	private Day day;
	private Underground underground;
	
	public Background() throws SlickException
	{
		time = 0;
		sky = new Sky();
		day = new Day();
		underground = new Underground();
	}
	
	public void render(Graphics g, float x, float y)
	{
		if(y > -500)
		{
			sky.render(g);
			day.render(g, x, y);
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
