package background;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import core.Engine;
import core.Values;

public class Day extends Scene
{
	private Layer hillsFront;
	private Layer hillsBack;
	private Layer mountains;
	
	private Sky sky;
	
	private int resX; //placeholders so this code is actually readable
	private int resY;
	
	private Color frontFiller;
	private Color backFiller;
	
	private Cloud[] clouds;

	
	public Day() throws SlickException
	{
		
		resX = Engine.RESOLUTION_X;
		resY = Engine.RESOLUTION_Y;
		
		mountains = new Layer("hillsBack", "res/Background/mountains-60.png", 0.2f, 0, 0.3f, resX, 0.5f);
		hillsBack = new Layer("hillsBack", "res/Background/hills1-80.png", 0.3f, 0, 0.35f, resX, 0.25f);
		hillsFront  = new Layer("hillsFront", "res/Background/hills1.png", 0.4f, 0, 0.4f, resX, 0.3f);
		
		sky = new Sky();

		frontFiller = new Color(50, 122, 32);
		backFiller = new Color(43, 132, 69);
		
		clouds = new Cloud[7];

		
		for(int i = 0; i < clouds.length; i++)
		{
			clouds[i] = new Cloud();
		}
	}
	
	public void render(Graphics g, float x, float y)
	{
		y  *= 0.5f; //parallax
		
		sky.render(g);
		
		for(int i = 0; i < clouds.length; i++) //the clouds
		{
			clouds[i].render(g, x, y);
		}
	
		mountains.render(g, x, y);
		
		g.setColor(backFiller);
		g.fillRect(0, resY * 0.6f + (y * 0.3f), resX, resY * 0.25f);	
		
		hillsBack.render(g, x - 600, y);
		
		g.setColor(frontFiller);
		g.fillRect(0, resY * 0.7f + (y * 0.4f), resX, resY * 0.5f);	
		
		hillsFront.render(g, x, y);
	}

	public void update(int time)
	{
		sky.update(time);
		
		for(int i = 0; i < clouds.length; i++) //the clouds
		{
			clouds[i].update();
		}
	}
}
