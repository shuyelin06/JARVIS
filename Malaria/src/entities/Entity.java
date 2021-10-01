package entities;

import core.Coordinate;

import org.newdawn.slick.Image;

public class Entity{
	// Will later be moved to some platform code so different platforms can have different frictions.
	protected final static float friction = 0.1f;
	protected final static float gravity = 0.25f;
	
	protected boolean onPlatform; // If the entity is on a platform (determines the forces of friction and gravity)
	protected int jumpsLeft;
	
	protected Image sprite; // The sprite rendered in for the Entity
	protected int sizeX, sizeY; // The size of the Entity
	
	protected Coordinate position;
	protected float xSpeed, ySpeed; // Entity velocity (pixels per second)
	
	// Every entity will have some initial starting position
	public Entity(float InitX, float InitY) {
		this.onPlatform = true;
	
		this.position = new Coordinate(InitX, InitY);
		
		this.xSpeed = 5;
		this.ySpeed = 0;
	}
	
	// Methods returning the position of an object
	public Coordinate getPosition() {
		return position;
	}
	
	// Set Speeds
	public void setXSpeed(float newSpeed) {
		this.xSpeed = newSpeed;
	}
	public void setYSpeed(float newSpeed){
		this.ySpeed = newSpeed;
	}
	
	
	// Updates the entity's position given its velocity
	public void update() {
		if(onPlatform) {
			jumpsLeft = 2;
		}
		
		// Update the entity's position
		position.update(xSpeed, ySpeed);
		
		// Update the entity's velocities
		if(onPlatform) { // If on a platform, friction works on the entity
			if(xSpeed > 0) xSpeed -= friction; // If the entity is moving to the right, friction works to the left
			else xSpeed += friction; // If the entity is moving to the left, friction works to the right
		} 
		else { // If not on a platform, gravity works on the entity
			ySpeed -= gravity;
		}
	}
}