package background;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import core.Engine;
import core.Values;

public class Cloud 
{
	private float x;
	private float y;
	private float w;
	private float h;
	
	private float xSpeed;
	private float parallax;
	
	private int cloudType;
	
	Image sprite;
	
	public Cloud() throws SlickException
	{
		sprite = Values.Images.get("placeholder");
		
		this.x = (float) Math.random() * Engine.RESOLUTION_X;
		this.y = (float) Math.random() * Engine.RESOLUTION_Y * 0.4f;
		
		this.w = 256 + 32 * (int)(Math.random() * 3);
		this.h = w * 0.4375f + 28 * (int)(Math.random() * 2);
		
		cloudType = (int)(Math.random() * 4);
		
		if(cloudType == 0)
		{
			sprite = Values.Images.get("cloud-1");
		} 
		else if(cloudType == 1)
		{
			sprite = Values.Images.get("cloud-2");
		}
		else if(cloudType == 2)
		{
			sprite = Values.Images.get("cloud-3");
		} 
		else if(cloudType == 3)
		{
			sprite = Values.Images.get("cloud-4");
		}
		
		xSpeed = (w * w) * 0.000005f;
		parallax  = w * 0.0003f;
	}
	
	public void render(Graphics g, float x, float y)
	{
		sprite.setFilter(Image.FILTER_NEAREST);
		sprite.draw((x * parallax + this.x) % (Engine.RESOLUTION_X + w) - w, this.y + y * parallax, w, h);
	}
	
	public void update()
	{
		this.x += this.xSpeed;
	}
}
