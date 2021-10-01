package enemy;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class RedShot extends Enemy
{	
	public RedShot(float x, float y, float xMovement, float yMovement) 
	{
		super();
		setImage("res/smallBullet.png");
		isProjectile = true;
		this.x = x;
		this.y = y;
		this.w = 8;
		this.h = 8;
		xSpeed = xMovement;
		ySpeed = yMovement;
		damage = 1;
		expiry = 500;
	}
	
	public void render (Graphics g) throws SlickException {
		g.rotate(x, y, (float) xSpeed);
		super.render(g);
		g.rotate(x, y, (float) -xSpeed);
	}
}
