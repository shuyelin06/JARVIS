package entities;

import core.Coordinate;
import core.Engine;
import settings.Values;
import structures.Block;
import world.Chunk;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class Entity{
	//
	protected boolean remove;
	/*
	 * Physics Variables
	 */
	protected final static float friction = 1.5f; // We will later move friction to platforms, so diff platforms have different frictions (ex. ice)
	protected final static float drag = 0.75f;
	protected final static float gravity = 0.85f;
	
	protected boolean onPlatform; // If the entity is on a platform (determines the forces of friction and gravity)
	protected int jumpsLeft; // Determines how many jumps are left
	
	protected Coordinate position;
	protected float xSpeed, ySpeed; // Entity velocity (pixels per second)
	
	
	/*
	 * Render Variables
	 */
	protected Image sprite; // The sprite rendered in for the Entity
	protected float sizeX, sizeY; // The size of the Entity

	public enum EntType{
		Player, Hostiles, Items, Projectiles
	}
	// Every entity will have some initial starting position
	public Entity(float InitX, float InitY)
	{
		this.remove = false;
		
		try {
			sprite = new Image("res/placeholder.png");
		} catch(Exception e) {}
		
		this.onPlatform = false;
	
		this.position = new Coordinate(InitX, InitY);
		
		this.xSpeed = 0f;
		this.ySpeed = 0f;
	}
	
	public float getSizeX() {
		return sizeX;
	}
	public float getSizeY() {
		return sizeY;
	}
	// Methods returning the position of an object
	public Coordinate getPosition() {
		return position;
	}
	
	public void markForRemoval() {
		this.remove = true;
	}
	public boolean isMarked() {
		return remove;
	}
	
	// Set Speeds
	public void setXSpeed(float newSpeed) {
		this.xSpeed = newSpeed;
	}
	public void setYSpeed(float newSpeed){
		this.ySpeed = newSpeed;
	}
	public void moveRight(float maxSpeed, float acceleration) {
		if(xSpeed + acceleration > maxSpeed) xSpeed = maxSpeed;
		else xSpeed += acceleration;	
	}
	
	public void moveLeft(float maxSpeed, float acceleration) {
		if(xSpeed - acceleration < 0 - maxSpeed) xSpeed = 0 - maxSpeed;
		else xSpeed -= acceleration;
	}
	
	public void jump(float speed) {
		if(jumpsLeft > 0) {
			this.onPlatform = false;
			this.ySpeed = speed;
			
			jumpsLeft--;
		}
	}
	public void fall() {
		this.ySpeed -= Entity.gravity;
	}
	
	// Updates the entity's position given its velocity
	public void update() {
		// Velocity Updating - X Velocity
		float resistance = drag;
		if(onPlatform) resistance = friction;
		
		if(resistance > Math.abs(xSpeed)) xSpeed = 0; 
		else if(xSpeed > 0) xSpeed -= resistance;
		else xSpeed += resistance;
		
		// Velocity Updating - Y Velocity
		ySpeed -= gravity;
		
		// Collision detection 
		collisions();
		
		// Position updating
		position.update(xSpeed, ySpeed);		
	}
	
	
	/*
	 * Collision Detection
	 */
	// Enumerators describing the type of collisions
	public enum Collision{
		X, Y;
	}
	private static final float collisionError = 0.001f; // Prevents the object from sticking
	
	// Determine if a collision occurs
	public void collisions() {
		// Defining Memory Used
		Chunk c;
		Block[][] blocks;
		
		float temp;
		int x, y;
		
		// Checking for horizontal collisions
		try {
			temp = position.getX() + xSpeed / Engine.FRAMES_PER_SECOND;
			if(xSpeed > 0) temp += sizeX;
			
			x = (int) temp; // Furthest x away
			
			c = Engine.game.getWorld().getChunk(x / Values.Chunk_Size_X);	
			blocks = c.getBlocks();
			
			for(int j = 0; j < Math.ceil(sizeY); j++) {
				if(blocks[x % Values.Chunk_Size_X][(int) position.getY() - j].getID() != 0) {
					// Collision detected
					onCollision(Collision.X, x);
					break;
				}
			}
		} catch(Exception e) {}
		
		// Checking for vertical collisions
		try {
			temp = position.getY() + ySpeed / Engine.FRAMES_PER_SECOND;
			if(ySpeed < 0) temp -= sizeY;
			
			y = (int) Math.ceil(temp);
			
			int max = (int) (Math.ceil(sizeX) + 1);
			if(position.getX() - Math.floor(position.getX()) < Math.ceil(sizeX) - sizeX) max--;
			
			for(int i = 0; i < max; i++) {
				x = (int) position.getX() + i; // Get the absolute x coordinate

				c = Engine.game.getWorld().getChunk(x / Values.Chunk_Size_X);
				
				if(c.getBlocks()[x % Values.Chunk_Size_X][y].getID() != 0){
					onCollision(Collision.Y, y);
					break;
				}
			} 
		} catch(Exception e) {}
	}
	
	// Code for what happens on collision
	private void onCollision(Collision collision, int blockCoord) {
		switch(collision) {
			case X: // X Collision Code
				// Default X Collision - Stop Horizontal Movement
				
				// Interpolate the new x position
				if(xSpeed < 0) position.setXPos(blockCoord + 1f + collisionError);
				else if(xSpeed > 0) position.setXPos(blockCoord - this.sizeX - collisionError);
				
				xSpeed = 0f;
				break;
			case Y: // Y Collision Code
				// Default Y Collision - Stop Vertical Movement
				
				if(ySpeed < 0) {
					jumpsLeft = 3; // Reset Jumps
					onPlatform = true;
				}
				
				// Interpolate the new y position
				if(ySpeed < 0) position.setYPos(blockCoord + this.sizeY + collisionError);
				else if(ySpeed > 0) position.setYPos(blockCoord - 1f - collisionError);
				
				ySpeed = 0f;
				break;
		
		}
	}
	
	// Returns true if this entity will collide with another entity e.
	public boolean entityCollision(Entity e) {
		float inter1 = position.getX() + xSpeed / Engine.FRAMES_PER_SECOND;
		if(xSpeed > 0) inter1 += sizeX;
		
		float inter2 = e.getPosition().getX() + e.xSpeed / Engine.FRAMES_PER_SECOND;
		
		// Check if one will be in the other
		if(inter2 - 0.001f < inter1 && inter1 < inter2 + e.sizeX + 0.001f) {
			inter1 = position.getY() + ySpeed / Engine.FRAMES_PER_SECOND;
			if(ySpeed < 0) inter1 -= sizeY;
			
			inter2 = e.getPosition().getY() + e.ySpeed / Engine.FRAMES_PER_SECOND;
			
			if(inter2 - e.sizeY - 0.001f < inter1 && inter1 < inter2 + 0.001f) return true;
		}
		return false;
	}
	
	public void render(Graphics g, float x, float y) {
		if(xSpeed < 0)
		{
			sprite.draw(x + sizeX * Coordinate.ConversionFactor, y, -sizeX * Coordinate.ConversionFactor, sizeY * Coordinate.ConversionFactor); 
		} else
		{
			sprite.draw(x, y, sizeX * Coordinate.ConversionFactor, sizeY * Coordinate.ConversionFactor); 
		}
	}	
}
