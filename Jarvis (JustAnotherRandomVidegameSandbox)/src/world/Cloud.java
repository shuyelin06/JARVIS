package world;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import core.Engine;
import support.Utility;

public class Cloud 
{
	private float x;
	private float y;
	private float w;
	private float h;
	
	private float xSpeed;
	
	private int cloudType;
	
	Image sprite;
	
	public Cloud() throws SlickException
	{
		sprite = new Image("res/placeholder.png");
		
		this.x = (float) Math.random() * Engine.RESOLUTION_X;
		this.y = (float) Math.random() * Engine.RESOLUTION_Y * 0.4f;
		
		this.w = 192 + (float)(Math.random() * 96);
		this.h = w * 0.5f + (float)(Math.random() * 32);
		
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
		
		xSpeed = (w * w) * 0.00002f;
	}
	
	
	public void render(Graphics g, float x, float y)
	{
		this.x += this.xSpeed;
		
		sprite.draw((x + this.x) % (Engine.RESOLUTION_X + w) - w, this.y, w, h);
	}
}
