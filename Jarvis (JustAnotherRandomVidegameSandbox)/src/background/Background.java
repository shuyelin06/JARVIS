package background;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import core.Engine;
import gamestates.Game;

public class Background
{
	private Day day;
	private Underground underground;
	
	public Background() throws SlickException
	{
		day = new Day();
		underground = new Underground();
	}
	
	public void render(Graphics g, float x, float y)
	{
		if(y > -500)
		{
			day.render(g, x, y, Engine.game.getWorld().getTime());
		}
		if(y < 0)
		{
			underground.render(g, x, y);
		}
	}
}
