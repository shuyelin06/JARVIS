package entities;

import core.Coordinate;
import core.Engine;
import gamestates.Game;
import structures.Block;
import world.Chunk;
import world.World;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Entity{
	protected World world;
	/*
	 * Physics Variables
	 */
	protected final static float friction = 0.8f; // We will later move friction to platforms, so diff platforms have different frictions (ex. ice)
	protected final static float gravity = 0.6f;
	
	protected boolean onPlatform; // If the entity is on a platform (determines the forces of friction and gravity)
	protected int jumpsLeft; // Determines how many jumps are left
	
	protected Coordinate position;
	protected float xSpeed, ySpeed; // Entity velocity (pixels per second)
	
	
	/*
	 * Render Variables
	 */
	protected Image sprite; // The sprite rendered in for the Entity
	protected float sizeX, sizeY; // The size of the Entity
	
	/*
	 * Stat Variables - Unused, but we can implement them later
	 */
	protected int curHealth, maxHealth;
	protected float percentageHealth;
	
	protected boolean alive;
	
	protected int attack;
	protected int defense;
	
	protected int iFrames;
	protected int iDuration;
	protected int regenTimer;
	
	// Every entity will have some initial starting position
	public Entity(float InitX, float InitY, World world) throws SlickException
	{
		sprite = new Image("res/placeholder.png");
		
		this.world = Game.world;
		this.onPlatform = true;
	
		this.position = new Coordinate(InitX, InitY);
		
		this.xSpeed = 0f;
		this.ySpeed = 0f;
    
		this.iFrames = 0;
		this.iDuration = 30; //how long invulnerability will last after taking damage
		
		alive = true;
		
		sizeX = 20;
		sizeY = 30;
		
		curHealth = 1;
		maxHealth = 1;
		percentageHealth = 1f;
		regenTimer = 120;
    
		maxHealth = 2;
		percentageHealth = 0;
		
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
				curHealth -= 1;
			}else {
				curHealth -= dmg;
			}
			
			if(curHealth <= 0) {
				alive = false;
			}
			else if(i) {
				setIFrames(iDuration);
			}
			
		}
	}
	//gives entity number of iframs that will automatically start ticking down each frame in update()
	public void setIFrames(int frames) {
		iFrames = frames;
	}
	
	public boolean isAlive() {
		return alive;
	}
	
	public void takeDamage(int damage) {
		curHealth -= damage;
		if (curHealth < 0) {
			curHealth = 0;
			alive = false;
		}
	}
	
	// Updates the entity's position given its velocity
	public void update() {
		// Update Stats
		percentageHealth = ((float) curHealth) / ((float) maxHealth); // Update health
		
		if(iFrames > 0) { //timer that ticks down invincibility frames
			iFrames --;
		}
		
		// Velocity Updating
		ySpeed -= gravity; // Gravity (will be cancelled out of a y-collision occurs)
		
		if(onPlatform) { // If on a platform, friction works on the entity (eventually will be changed)
			if(friction > Math.abs(xSpeed)) xSpeed = 0; // If friction is greater than the speed, set speed to 0 (ensures that our player will always stop)
			else if(xSpeed > 0) xSpeed -= friction; // If the entity is moving to the right, friction works to the left
			else xSpeed += friction; // If the entity is moving to the left, friction works to the right
		}
		
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
	
	// Determine if a collision occurs
	public void collisions() {
		// Checking for horizontal collisions
		int x = (int) (position.getX() + xSpeed / (float) Engine.FRAMES_PER_SECOND);
		if(xSpeed > 0) x += sizeX;
		
		Chunk c = world.getChunk(x / Chunk.Chunk_Size_X);			
		Block[][] blocks = c.getBlocks();
		
		
		for(int j = 0; j < Math.ceil((double) sizeY); j++) {
			if(blocks[x % Chunk.Chunk_Size_X][(int) position.getY() - j].getID() != 0) {
				// Collision detected
				onCollision(Collision.X);
				break;
			}
		}

		
		// Checking for vertical collisions
		int y = (int) Math.ceil(position.getY() + ySpeed / (float) Engine.FRAMES_PER_SECOND);
		if(ySpeed <  0) y -= sizeY;
		
		for(int i = 0; i <= Math.ceil((double) sizeX); i++) {
			x = (int) position.getX() + i; // Get the absolute x coordinate

			c = world.getChunk(x / Chunk.Chunk_Size_X);
			
			if(c.getBlocks()[x % Chunk.Chunk_Size_X][y].getID() != 0){
				onCollision(Collision.Y);
				break;
			}
			
		} 
	}
	
	// Code for what happens on collision
	private void onCollision(Collision collision) {
		switch(collision) {
			case X: // X Collision Code
				// Default X Collision - Stop Horizontal Movement
				xSpeed = 0;
				break;
			case Y: // Y Collision Code
				// Default Y Collision - Stop Vertical Movement
				onPlatform = true;
				jumpsLeft = 3;
				
				ySpeed = 0;
				break;
		
		}
	}
	
	//debug rendering
	public void render(Graphics g) {
		
//		//write health of actor underneath
//		if (Game.debugMode) {
//			g.setColor(new Color(255, 255, 255));
//			g.drawString(""+(int) curHealth, x, y - 15);
//			g.setColor(new Color(0, 0, 0));
//		}
//		//debug for hitbox of actor
//		if (Game.debugMode) {
//			g.setColor(new Color(255, 255, 255));
//			g.drawRect(x, y, w, h);
//			g.setColor(new Color(0, 0, 0));
//		}
	}
	
	
	
}
