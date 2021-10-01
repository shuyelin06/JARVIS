package player;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class BasicShot extends Friendly
{	
	//initialize shot
	public BasicShot(float x, float y, float xSpeed, float ySpeed, int damage) 
	{
		super();
		setImage("res/basicShot.png");
		isProjectile = true;
		this.x = x;
		this.y = y;
		this.w = 8;
		this.h = 8;
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
		this.damage = damage;
	}
	
	//rotates shot image
	public void render (Graphics g) throws SlickException {
		g.rotate(x, y, (float) xSpeed);
		super.render(g);
		g.rotate(x, y, (float) -xSpeed);
	}
}
