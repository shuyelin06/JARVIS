package entities.other;

import org.newdawn.slick.Image;

import core.Engine;
import entities.Entity;

public class Projectile extends Entity{
	
	private float speed = 50f;
	
	public Projectile(float x, float y, double theta){
		super(x, y);
		
		this.sizeX = 0.5f;
		this.sizeY = 0.5f;
		
		try {
			sprite = new Image("res/placeholder.png");
		} catch(Exception e) {}
		
		this.xSpeed = (float) Math.cos(theta) * speed;
		this.ySpeed = (float) Math.sin(theta) * speed;
	}

	// Overwriting the update method
	public void update() {
		// Collision detection 
		collisions();
		
		// Position updating
		position.update(xSpeed, ySpeed);
		
		// Direction updating
		if (xSpeed < 0) direction = false;
		if (xSpeed > 0) direction = true;
	}
	protected void onCollision() {
		int centerX = (int) position.getX();
		int centerY = (int) position.getY();
		
		for(int i = -4; i < 5; i++)
		{
			for(int j = -4; j < 5; j++)
			{
				Engine.game.getWorld().destroyBlock(centerX + i, centerY + j);
			}
		}
		
		this.markForRemoval();
	}
}