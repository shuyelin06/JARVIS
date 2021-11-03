package world;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import core.Engine;
import gamestates.Game;

public class Background
{
	private Day day;
	private Underground underground;
	private Night night;
	
	public Background() throws SlickException
	{
		day = new Day();
		underground = new Underground();
		night = new Night();
		
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
	
		if(Engine.game.getWorld().getTime() > 0) 
		{
			night.render(g, Engine.game.getWorld().getTime());
		}
	}
}
