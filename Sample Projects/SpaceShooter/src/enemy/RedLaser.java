package enemy;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class RedLaser extends Enemy{
	//initialize laser
	public RedLaser (float x, float y, float xMovement, float yMovement) {
		super();
		setImage("res/bullet.png");
		isProjectile = true;
		this.x = x;
		this.y = y;
		this.w = 8;
		this.h = 40;
		xSpeed = xMovement;
		ySpeed = yMovement;
		damage = 5;
		expiry = 1500;
	}
	
	//rotate image
	public void render (Graphics g) throws SlickException {
		g.rotate(x, y, (float) xSpeed);
		super.render(g);
		g.rotate(x, y, (float) -xSpeed);
	}
}
