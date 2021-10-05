package entities;

import core.Coordinate;

import org.newdawn.slick.Image;

public class Entity{
	/*
	 * Physics Variables
	 */
	protected final static float friction = 0.2f; // We will later move friction to platforms, so diff platforms have different frictions (ex. ice)
	protected final static float gravity = 0.5f;
	
	protected boolean onPlatform; // If the entity is on a platform (determines the forces of friction and gravity)
	protected int jumpsLeft; // Determines how many jumps are left
	
	protected Coordinate position;
	protected float xSpeed, ySpeed; // Entity velocity (pixels per second)
	
	/*
	 * Render Variables
	 */
	protected Image sprite; // The sprite rendered in for the Entity
	protected int sizeX, sizeY; // The size of the Entity
	
	/*
	 * Stat Variables - Unused, but we can implement them later
	 */
	protected int health;
	
	protected int attack;
	protected int defense;
	
	protected int iFrames;
	protected int iDuration;
	
	// Every entity will have some initial starting position
	public Entity(float InitX, float InitY) {
		this.onPlatform = true;
	
		this.position = new Coordinate(InitX, InitY);
		
		this.xSpeed = 0;
		this.ySpeed = 0f;
		
		this.iFrames = 0;
		this.iDuration = 30; //how long invulnerability will last after taking damage
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
	
	public void takeDamage(int dmg, boolean i) { //boolean for iFrames cause for certain piercing attacks that don't trigger them
		//this mimics the mechanics in Terraria
		if(iFrames == 0) {
			dmg -= defense;
			if(dmg <= 0) { //if defense is higher than dmg taken you will just take 1 dmg
				health -= 1;
			}else {
				health -= dmg;
			}
			if(i) {
				setIFrames(iDuration);
			}
			
		}
	}
	
	public void setIFrames(int frames) {
		iFrames = frames;
	}
	
	// Updates the entity's position given its velocity
	public void update() {
		// Reset the entity's jumps
		if(onPlatform) {
			jumpsLeft = 300;
		}
		
		// Update the entity's position
		position.update(xSpeed, ySpeed);
		
		// Update the entity's velocities
		if(onPlatform) { // If on a platform, friction works on the entity
			if(friction > Math.abs(xSpeed)) xSpeed = 0; // If friction is greater than the speed, set speed to 0 (ensures that our player will always stop)
			else if(xSpeed > 0) xSpeed -= friction; // If the entity is moving to the right, friction works to the left
			else xSpeed += friction; // If the entity is moving to the left, friction works to the right
		} 
		else { // If not on a platform, gravity works on the entity
			ySpeed -= gravity;
		}
		
		if(iFrames > 0) { //timer that ticks down iFrames
			iFrames --;
		}
	}
}