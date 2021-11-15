package background;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import core.Engine;

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
		sprite = new Image("res/placeholder.png");
		
		
		this.x = (float) Math.random() * Engine.RESOLUTION_X;
		this.y = (float) Math.random() * Engine.RESOLUTION_Y * 0.4f;
		
		this.w = 256 + 32 * (int)(Math.random() * 3);
		this.h = w * 0.4375f + 28 * (int)(Math.random() * 2);
		
		cloudType = (int)(Math.random() * 4);
		
		if(cloudType == 0)
		{
			sprite = new Image("res/Background/cloud-1.png");
		} 
		else if(cloudType == 1)
		{
			sprite = new Image("res/Background/cloud-2.png");
		}
		else if(cloudType == 2)
		{
			sprite = new Image("res/Background/cloud-3.png");
		} 
		else if(cloudType == 3)
		{
			sprite = new Image("res/Background/cloud-4.png");
		}
		
		xSpeed = (w * w) * 0.000005f;
		parallax  = w * 0.0003f;
		System.out.println(parallax + "");
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
