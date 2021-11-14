package background;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import core.Engine;
import core.Values;

public class Day extends Scene
{
	private Image sky;
	private Image hillsFront;
	private Image hillsBack;
	private Image mountains;
	private Image night;
	private Image stars;
	private Image sunset;
	
	private float nightAlpha;
	private float sunsetAlpha;
	private int tLength;
	private int dayLength;
	private int nightLength;
	private int total;
	private int resX; //placeholders so this code is actually readable
	private int resY;
	
	private Color filler;
	
	private Cloud[] clouds;
	private Sun sun;
	private Moon moon;
	
	public Day() throws SlickException
	{
		tLength = Values.transitionLength;
		dayLength = Values.dayLength;
		nightLength = Values.nightLength;
		nightAlpha = 0;
		sunsetAlpha = 0;
		total = dayLength + nightLength + (tLength * 2);
		resX = Engine.RESOLUTION_X;
		resY = Engine.RESOLUTION_Y;
		
		sky = new Image("res/Background/daySky.png");
		sunset = new Image("res/Background/sunset.png");
		night = new Image("res/Background/night.png");
		stars = new Image("res/Background/stars.png");
		
		hillsFront = new Image("res/Background/hills1.png");
		hillsBack = new Image("res/Background/hills1-80.png");
		mountains = new Image("res/Background/mountains-60.png");

		filler = new Color(50, 122, 32);
		
		clouds = new Cloud[7];
		sun = new Sun(0, resY, dayLength + tLength);
		moon = new Moon(0, resY, nightLength);
		
		for(int i = 0; i < clouds.length; i++)
		{
			clouds[i] = new Cloud();
		}
	}
	
	public void render(Graphics g, float x, float y)
	{
		y  *= 0.5f; //parallax
		
		sky.draw(0, 0, resX, resY);
		
		night.setAlpha(nightAlpha); //still don't know how to do overlapping stuff w/out using 2 different images
		sunset.setAlpha(sunsetAlpha);
		stars.setAlpha(nightAlpha);
		
		night.draw(0, 0, resX, resY);
		sunset.draw(0, 0, resX, resY);
		stars.draw(0, 0, resX, resY);
		
		sun.render(g);		
		moon.render(g);
		
		for(int i = 0; i < clouds.length; i++) //the clouds
		{
			clouds[i].render(g, x, y);
		}
		
		//i should make different class for all the background layers
		mountains.draw(x * 0.2f % resX,
				resY * 0.35f + (y * 0.2f), 
				resX, 
				resY * 0.5f);
		
		mountains.draw((x * 0.2f % resX) + resX, 
				resY * 0.35f + (y * 0.2f), 
				resX, 
				resY * 0.5f);
		
		g.setColor(filler);
		g.fillRect(0, resY * 0.79f + (y * 0.3f), resX, resY * 0.5f);	
		
		//hills layer 1
		hillsBack.draw( (x * 0.25f % resX) + resX, //i should add a new texture
				resY * 0.45f + (y * 0.25f), 
				-resX, 
				resY * 0.35f);
		
		hillsBack.draw((x * 0.25f % resX) + (2 * resX),
				resY * 0.45f + (y * 0.25f),
				-resX, 
				resY * 0.35f);
		
		//hills layer 2
		hillsFront.draw(x * 0.3f % resX, //first hill, loops back every time it travels its full length
				resY * 0.5f + (y * 0.3f), 
				resX, 
				resY * 0.3f);
		
		hillsFront.draw((x * 0.3f % resX) + resX, //second hill texture so it doesn't cut off
				resY * 0.5f + (y * 0.3f),
				resX, 
				resY * 0.3f);
	}

	public void update(int time)
	{
		int temp = time % total;
		
		if(temp > dayLength &&
			temp < dayLength + tLength)
		{
			sunsetAlpha = (float)Math.sin(3.14159 * (temp - dayLength) / tLength); //https://www.desmos.com/calculator/wxnsmit4pw
			nightAlpha += 0.92 / tLength;
		} else if(temp > total - tLength)
		{
			sunsetAlpha = (float)Math.sin(3.14159 * (temp - (total - tLength)) / tLength);
			nightAlpha -= 0.92 / tLength;
		} else
		{
			sunsetAlpha = 0;
		}
		
		for(int i = 0; i < clouds.length; i++) //the clouds
		{
			clouds[i].update();
		}
		
		if(temp < dayLength + tLength * 0.5) //sun
		{
			sun.update(temp + tLength * 0.5f);
		} else if (temp > total - tLength * 0.5)
		{
			sun.update(temp - (total - tLength * 0.5f));
		}
		
		if(temp > dayLength + tLength) //moon
		{
			moon.update(temp - dayLength - tLength);

		}
	}
}
