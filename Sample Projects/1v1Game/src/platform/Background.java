package platform;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import core.Game;

public class Background {
	private Image image;
	private int x, y, w, h;
	//constructor
	public Background() {
		x = 0;
		y = 0;
		w = Game.gc.getWidth();
		h = Game.gc.getHeight();
		setImage("res/mountains.png");
		image.setFilter(Image.FILTER_NEAREST);
	}
	//draws background
	public void render(Graphics g) {
		image.draw(x, y, w, h);
	}

	//setting image of background
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
