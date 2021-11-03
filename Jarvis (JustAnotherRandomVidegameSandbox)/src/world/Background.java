package world;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

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
			day.render(g, x, y);
		}
		if(y < 0)
		{
			underground.render(g, x, y);
		}
	}
}
