package core;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Shop {
	private float x;
	private float y;
	private float w;
	private float h;
	private Image image;
	
	public Shop() {
		//sets shop position
		x = Game.gc.getWidth()/4;
		y = Game.gc.getHeight()/10;
		w = Game.gc.getWidth()/2;
		h = Game.gc.getHeight()/4;
		setImage("res/shopSign.png");
	}
	//draw shop sign
	public void render (Graphics g) throws SlickException {
		image.draw(x, y, w, h);
	}
	//sets shop sign image
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
