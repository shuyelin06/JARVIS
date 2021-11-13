package background;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Moon extends Sun
{
	int phase;
	
	public Moon(float x, float y, int nightLength) 
	{
		super(x, y, nightLength);
		
		phase = 0;
		
		try {
			sprite = new Image("res/Background/moon.png");
		} catch (SlickException e) {
		}
	}
	
	public void update(int time)
	{
		super.update(time);
	}
	
	public void render(Graphics g)
	{
		super.render(g);
		g.drawString("x: " + x + ", y: " + y, 100, 200);
	}
}
