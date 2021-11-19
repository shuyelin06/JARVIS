package background;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Tutorial {
	
	private Image wasdImage;
	private float wasdX, wasdY, wasdW, wasdH;
	private float time;
	private boolean canRender;
	
	public Tutorial() {
		this.wasdX = 50;
		this.wasdY = 890;
		//288
		this.wasdW = 216f;
		//187
		this.wasdH = 140f;
		time = 0;
		canRender = true;
		setImage("res/wasd.png");
	}
	
	public void render (Graphics g) {
		wasdImage.draw(wasdX, wasdY, wasdW, wasdH);
	}
	
	public void update () {
		if (time < 500) {
			time++;
		} else if(time < 1000) {
			time++;
			setImage("res/mouseLeftRight.jpg");
		} else {
			canRender = false;
		}
	}
	
	public boolean canRender() {
		return canRender;
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
