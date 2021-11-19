package background;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import core.Engine;

public class Layer 
{
	Image sprite;
	String id;
	float parallax;
	float alpha;
	
	float x, y, w, h, resX, resY;
	
	public Layer(String id, String imagePath, float parallax, float x, float y, float w, float h)
	{
		alpha = 1;
		resX = Engine.RESOLUTION_X;
		resY = Engine.RESOLUTION_Y;
		x = 0;
		this.y = resY * y;
		this.h = resY * h;
		this.w = resX * w;
		
		this.parallax = parallax;
		this.id = id;
		
		try { sprite = new Image(imagePath); } 
		catch (SlickException e) { }
	}
	
	public void render(Graphics g, float x, float y)
	{
		sprite.setFilter(Image.FILTER_NEAREST);
		sprite.setAlpha(alpha);
		g.drawString(alpha + "", 100, 300);
		sprite.draw(x * parallax % w, y * parallax + this.y, w, h);
		sprite.draw(x * parallax % w + w, y * parallax + this.y, w, h);
	}
	
	public void update(float x, float y)
	{
		
	}
	
	public void setAlpha(float alpha)
	{
		this.alpha = alpha;
	}
	
	public void setImage(String imagePath)
	{
		try
		{
			sprite = new Image(imagePath);
		}
		catch (SlickException e) { }
	}
	
	public Color getColor()
	{
		return sprite.getColor(100,100);
	}
	
	public void dim() //still need to figure out how to get the filters to work
	{
		
	}
}
