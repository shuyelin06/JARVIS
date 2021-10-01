package entities;

import org.newdawn.slick.Image;
import core.Engine;

public class Entity{
	// Will later be moved to some platform code so different platforms can have different frictions.
	protected final static float friction = 25f;
	
	protected Image sprite; // The sprite rendered in for the Entity
	protected int sizeX, sizeY; // The size of the Entity
	
	protected float x, y; // Entity position
	protected float xSpeed, ySpeed; // Entity velocity (pixels per second)
	
	protected boolean falling; // If gravity will act on the entity
	
	// Every entity will have some initial starting position
	public Entity(float xPos, float yPos) {
		this.falling = true;
		
		this.x = xPos;
		this.y = yPos;
		
		this.xSpeed = 50;
		this.ySpeed = 50;
	}
	
	// Methods returning the position of an object
	public float getX() {
		return x;
	}
	public float getY() { // Flipped the y coordinate so that the screen follows a proper x,y coordinate system
		return Engine.RESOLUTION_Y - y; 
	}
	
	
	// Updates the entity's position given its velocity
	public void update() {
		// Update the entity's position
		x += xSpeed / Engine.FRAMES_PER_SECOND;
		y += ySpeed / Engine.FRAMES_PER_SECOND;
		
		// Update the entity's velocities based on its location
		if(falling) ySpeed -= 25f / Engine.FRAMES_PER_SECOND; // If falling, accelerate downwards
		else { // If not falling (AKA on a platform)
			if(xSpeed < 0) xSpeed += friction / Engine.FRAMES_PER_SECOND;
			else xSpeed -= friction / Engine.FRAMES_PER_SECOND;
		}
		
		
	}
}