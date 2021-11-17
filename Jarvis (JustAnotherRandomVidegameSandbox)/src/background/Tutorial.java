package background;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Tutorial {
	
	private Image wasdImage;
	private float wasdX, wasdY, wasdW, wasdH;
	
	public Tutorial() {
		this.wasdX = 50;
		this.wasdY = 843;
		//288
		this.wasdW = 288f;
		//187
		this.wasdH = 187f;
		setImage("res/wasd.png");
	}
	
	public void render (Graphics g) {
		wasdImage.draw(wasdX, wasdY, wasdW, wasdH);
	}
	
	//acccessors
	public float getY() {
		return wasdY;
	}
	public float getX() {
		return wasdX;
	}
	public float getW() {
		return wasdW;
	}
	public float getH() {
		return wasdH;
	}
	
	//setting images
	public void setImage(String filepath)
	{
		try
		{
			wasdImage = new Image(filepath);
		}
		catch(SlickException e)		
		{
			System.out.println("Image not found!");
		}
	}
}
