package platform;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import core.Game;

public class Platform {
	private Image image;
	private float x, y, w, h;
	public Platform(float x, float y, float w, float h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		setImage("res/platform.png");
	}
	
	//draw platform
	public void render(Graphics g) {
		if (Game.debugMode == true) {
			g.setColor(new Color(255, 0, 0));
			g.fillRect(x, y, w, h);
		}
		image.draw(x, y, w, h);
	}
	
	//accessors for platform position
	public float getY() {
		return y;
	}
	public float getX() {
		return x;
	}
	public float getW() {
		return w;
	}
	public float getH() {
		return h;
	}
	
	//setting image of platform
	public void setImage(String filepath)
	{
		try
		{
			image = new Image(filepath);
		}
		catch(SlickException e)		
		{
			System.out.println("Image not found!");
		}
	}
}
