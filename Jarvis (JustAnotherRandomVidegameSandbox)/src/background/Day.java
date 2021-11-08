package background;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import core.Engine;

public class Day extends Scene
{
	private Image sky;
	private Image hillsFront;
	private Image hillsBack;
	private Image mountains;
	private Image night;
	
	private float alpha;
	
	private Cloud[] clouds;
	
	public Day() throws SlickException
	{
		alpha = 0;
		
		sky = new Image("res/Background/daySky.png");
		
		hillsFront = new Image("res/Background/hills1.png");
		hillsBack = new Image("res/Background/hills1-80.png");
		
		mountains = new Image("res/Background/mountains.png");
		
		night = new Image("res/Background/night.png");
		
		clouds = new Cloud[5];
		
		mountains.setAlpha(.6f);
		
		for(int i = 0; i < clouds.length; i++)
		{
			clouds[i] = new Cloud();
		}
	}
	
	public void render(Graphics g, float x, float y, int time)
	{
		sky.draw();
		
		y  *= 0.5f;
		
		//mountains
		mountains.draw(x * 0.2f % Engine.RESOLUTION_X,
				Engine.RESOLUTION_Y * 0.35f + (y * 0.2f), 
				Engine.RESOLUTION_X, 
				Engine.RESOLUTION_Y * 0.5f);
		
		mountains.draw((x * 0.2f % Engine.RESOLUTION_X) + Engine.RESOLUTION_X, 
				Engine.RESOLUTION_Y * 0.35f + (y * 0.2f), 
				Engine.RESOLUTION_X, 
				Engine.RESOLUTION_Y * 0.5f);
		
		for(int i = 0; i < clouds.length; i++) //the clouds
		{
			clouds[i].render(g, (x  * 0.15f) % Engine.RESOLUTION_X, y * 0.2f);
		}
		
		//hills layer 1
		hillsBack.draw( (x * 0.25f % Engine.RESOLUTION_X) + Engine.RESOLUTION_X, //i should add a new texture
				Engine.RESOLUTION_Y * 0.45f + (y * 0.25f), 
				-Engine.RESOLUTION_X, 
				Engine.RESOLUTION_Y * 0.35f);
		
		hillsBack.draw((x * 0.25f % Engine.RESOLUTION_X) + (2 * Engine.RESOLUTION_X),
				Engine.RESOLUTION_Y * 0.45f + (y * 0.25f),
				-Engine.RESOLUTION_X, 
				Engine.RESOLUTION_Y * 0.35f);
		
		//hills layer 2
		hillsFront.draw(x * 0.3f % Engine.RESOLUTION_X, //first hill, loops back every time it travels its full length
				Engine.RESOLUTION_Y * 0.5f + (y * 0.3f), 
				Engine.RESOLUTION_X, 
				Engine.RESOLUTION_Y * 0.3f);
		
		hillsFront.draw((x * 0.3f % Engine.RESOLUTION_X) + Engine.RESOLUTION_X, //second hill texture so it doesn't cut off
				Engine.RESOLUTION_Y * 0.5f + (y * 0.3f),
				Engine.RESOLUTION_X, 
				Engine.RESOLUTION_Y * 0.3f);
		
		g.setColor(new Color(50, 122, 32));
		g.fillRect(0, Engine.RESOLUTION_Y * 0.79f +  + (y * 0.3f), Engine.RESOLUTION_X, Engine.RESOLUTION_Y * 0.5f);
		
		
		if(time % 600 > 300)
		{
			if(alpha >= 0.8)
			{
				alpha = 0.8f;
			} 
			else
			{
				alpha = (time - 300) * 0.003f;
			}
			
			night.setAlpha(alpha);
			
			night.draw(0, 0, Engine.RESOLUTION_X, Engine.RESOLUTION_Y);
		}

	}
}
