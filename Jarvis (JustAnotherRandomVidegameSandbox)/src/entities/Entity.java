package entities;

import core.Coordinate;
import core.Engine;
import gamestates.Game;
import settings.Values;
import structures.Block;
import world.Chunk;
import world.FileLoader;
import world.World;

import javax.swing.text.html.HTMLDocument.HTMLReader.BlockAction;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Entity{
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
	public Entity(float InitX, float InitY) throws SlickException
	{
		sprite = new Image("res/placeholder.png");
		
		this.onPlatform = false;
	
		this.position = new Coordinate(InitX, InitY);
		
		this.xSpeed = 0f;
		this.ySpeed = 0f;
    
		this.iFrames = 0;
		this.iDuration = 30; //how long invulnerability will last after taking damage
		
		alive = true;
		
		sizeX = 20;
		sizeY = 30;
		
		curHealth = 1;
		maxHealth = 2;
		percentageHealth = 1f;
		regenTimer = 120;
    
//		maxHealth = 2;
//		percentageHealth = 0;
		
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
	public void takeDamage(int dmg, boolean i) { //boolean for iFrames cause for certain piercing attacks that don't trigger them
		//this mimics the mechanics in Terraria
		if(iFrames == 0) {
			dmg -= defense;
			if(dmg <= curHealth) {
				if(dmg <= 0) { //if defense is higher than dmg taken you will just take 1 dmg
					curHealth -= 1;
				}else {
					curHealth -= dmg;
				}
			}else {
				curHealth = 0;
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
		/*
		 * Defining Memory Used
		 */
		Chunk c;
		Block[][] blocks;
		float temp;
		
		int x;
		int y;
		
		try {
			// Checking for horizontal collisions
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
		} catch(Exception e) {
			System.out.println("Failure checking horizontal collisions");
		}
		
		try {
			// Checking for vertical collisions
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
		} catch(Exception e) {
			System.out.println("Failure checking vertical collisions");
		}
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
	

	public void render(Graphics g, float x, float y) {
		if(xSpeed < 0)
		{
			sprite.draw(x + sizeX * Coordinate.ConversionFactor, y, -sizeX * Coordinate.ConversionFactor, sizeY * Coordinate.ConversionFactor); 
		} else
		{
			sprite.draw(x, y, sizeX * Coordinate.ConversionFactor, sizeY * Coordinate.ConversionFactor); 
		}
	}
	//debug rendering		
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
