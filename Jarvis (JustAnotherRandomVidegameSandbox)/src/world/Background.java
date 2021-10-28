package world;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import core.Coordinate;
import core.Engine;
import settings.Values;

public class Background 
{
	private Image sky;
	private Image hills;
	private Image mountains;
	public Background() throws SlickException
	{
		sky = new Image("res/daySky.png");
		hills = new Image("res/hills-1.png");
		mountains = new Image("res/mountains.png");
	}
	
	public void render(Graphics g, float x, float y)
	{
		System.out.println(y);
		sky.draw();
		
		g.setColor(new Color(50, 122, 32));
		
		//mountains
		mountains.draw((float) (x * 0.2 % (Engine.RESOLUTION_X) ),
				(float) (Engine.RESOLUTION_Y * 0.35), 
				Engine.RESOLUTION_X, 
				(float) (Engine.RESOLUTION_Y * 0.5));
		
		mountains.draw((float) (x * 0.2 % (Engine.RESOLUTION_X) + Engine.RESOLUTION_X), 
				(float) (Engine.RESOLUTION_Y * 0.35), 
				Engine.RESOLUTION_X, 
				(float) (Engine.RESOLUTION_Y * 0.5));
		
		//hills layer 1
		hills.draw((float) (x * 0.25 % (Engine.RESOLUTION_X) + Engine.RESOLUTION_X), //i should add a new texture
				(float) (Engine.RESOLUTION_Y * 0.45), 
				-Engine.RESOLUTION_X, 
				(float) (Engine.RESOLUTION_Y * 0.35));
		
		hills.draw((float)  (x * 0.25 % (Engine.RESOLUTION_X) + (2 * Engine.RESOLUTION_X)),
				(float) (Engine.RESOLUTION_Y * 0.45),
				-Engine.RESOLUTION_X, 
				(float) (Engine.RESOLUTION_Y * 0.35));
		
		//hills layer 2
		hills.draw((float) (x * 0.3 % (Engine.RESOLUTION_X) ), //first hill, loops back every time it travels its full length
				(float) (Engine.RESOLUTION_Y * 0.5), 
				Engine.RESOLUTION_X, 
				(float) (Engine.RESOLUTION_Y * 0.3));
		
		hills.draw((float)  (x * 0.3 % (Engine.RESOLUTION_X) + Engine.RESOLUTION_X), //second hill texture so it doesn't cut off
				(float) (Engine.RESOLUTION_Y * 0.5),
				Engine.RESOLUTION_X, 
				(float) (Engine.RESOLUTION_Y * 0.3));
		
		g.fillRect(0, (float) (Engine.RESOLUTION_Y * 0.79), Engine.RESOLUTION_X, (float) (Engine.RESOLUTION_Y * 0.5));

	}
}
