package background;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import core.Engine;

public class Sun 
{
	float x;
	float y;
	int dayLength;
	
	Image sprite;
	
	public Sun(float x, float y, int dayLength)
	{
		this.x = x;
		this.y = y;
		this.dayLength = dayLength;
		try {
			sprite = new Image("res/Background/sun.png");
		} catch (SlickException e) {
		}
	}
	
	public void update(int time)
	{
		float temp = dayLength * 0.5f;
		
		x = time * Engine.RESOLUTION_X / dayLength;
		
		if((temp * temp) - ( (time - temp) * (time - temp) ) > 0)
		{
			y = (float) (Engine.RESOLUTION_Y - ((Engine.RESOLUTION_Y / temp) * 
					Math.sqrt( (temp * temp) - ( (time - temp) * (time - temp) ) ) 
					)); //https://www.desmos.com/calculator/ub3lpaewad Lol
		} else
		{
			y = Engine.RESOLUTION_Y;
		}
		
	}
	
	public void render(Graphics g)
	{
		sprite.draw(x, y);
	}
}
