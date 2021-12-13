package entities;

import core.BlockHash;
import core.Coordinate;
import core.Engine;
import core.Values;
import gamestates.Game;
import structures.Block;
import support.Utility;
import world.Chunk;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.lwjgl.Sys;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;

public class Entity{
	protected static Game game = Engine.game;
	
	// Entity Type
	public enum EntType{ Living, Items, Projectiles }
	
	// Management Variables
	protected long time; 				// Tracks how long the entity has been alive
	protected boolean remove; 			// Allows entity to be marked for removal
	
	// Render Variables
	protected Image sprite; 			// The sprite rendered in for the Entity
	protected float sizeX, sizeY; 		// The size of the Entity
	
	// Physics Variables
	protected Coordinate position; 		// Entity position
	protected float xSpeed, ySpeed;		// Entity velocity (pixels per second)
	protected float mass; 				// Entity mass
	protected boolean pastDirection;    // Marker for direction faced
	
	// Every entity will have some initial starting position
	public Entity(float InitX, float InitY) {
		// Management Variables
		this.time = Sys.getTime();
		this.remove = false;
		
		// Rendering Variables
		this.sizeX = 1f;
		this.sizeY = 1f;
		
		// Physics Variables
		this.position = new Coordinate(InitX, InitY); // Initial position
		this.xSpeed = this.ySpeed = 0f; // Default speeds
		this.mass = 1f; // Default Mass
		this.pastDirection = false; // Start facing left
	}
	
	// Accessor Methods
	public float timeAlive() 		{ return (float) (Sys.getTime() - time) / 1000; }
	public Image getSprite() 		{ return sprite; }
	public boolean getDirection() 	{ return xSpeed > 0; } // False - Left, True - Right
	public boolean getLeft()        { return xSpeed < 0; } // if true, then left
	public boolean getPastDirection() { return pastDirection; }
	public float getSizeX() 		{ return sizeX; }
	public float getSizeY() 		{ return sizeY; }
	public Coordinate getPosition() { return position; }
	public float getXSpeed() 		{ return xSpeed; }
	public float getYSpeed() 		{ return ySpeed; }
	public boolean isMarked() 		{ return remove; }
	
	// Mutator Methods
	public void updateSprite(Image image) { this.sprite = image; }
	
	public void markForRemoval() { this.remove = true; }
	public void setSpeedX(float xSpeed) { this.xSpeed = xSpeed; }
	public void setSpeedY(float ySpeed) { this.ySpeed = ySpeed; }
	public void setPastDirection(boolean pastDirection) { this.pastDirection = pastDirection; }
	
	// Main method called for all entities
	public void update() {
		// Update Speeds
		xSpeed -= xSpeed * Values.Drag_Coefficient / this.mass; // Drag acts on the x velocity
		ySpeed -= Values.Acceleration_of_Gravity; // Gravity acts on the y velocity
		
		// Collision detection 
		checkCollisions();
		
		// Update position
		position.update(xSpeed, ySpeed);
	}
	
	
	/* Collision Detection */
	private static final float collisionError = 0.0001f; // Prevents the object from sticking
	
	// Empty block collision method that can be used in other classes
	protected void onBlockXCollision() {} // Specific to x collisions
	protected void onBlockYCollision() {} // Specific to y collisions
	protected void onBlockCollision() {} // Called in any collision, x or y
	
	// Main collision method that is called
	protected void checkCollisions() {
		blockCollisions();
		entityCollisions();
	};
	
	// Empty collision method for us to check for unique entity collisions
	protected void entityCollisions() {}; 
	
	// Checks entity - block collisions
	protected void blockCollisions() {
		try {
			checkHorizontalCollision(); // Check for horizontal collisions
			checkVerticalCollision(); // Check for vertical collisions
		} catch(Exception e) {}
	};
	
	// Returns true if this entity will collide with another entity e.
	protected boolean entityCollision(Entity e) {
		float rec1[] = new float[4];
		rec1[0] = position.getX() + xSpeed / Engine.FRAMES_PER_SECOND; // X1
		rec1[2] = rec1[0] + this.sizeX; // X2
		
		rec1[3] = position.getY() + ySpeed / Engine.FRAMES_PER_SECOND; // Y2
		rec1[1] = rec1[3] - this.sizeY; // Y1
		
		
		float rec2[] = new float[4];
		rec2[0] = e.getPosition().getX() + e.xSpeed / Engine.FRAMES_PER_SECOND; // X3
		rec2[2] = rec2[0] + e.sizeX; // X4
		
		rec2[3] = e.getPosition().getY() + e.ySpeed / Engine.FRAMES_PER_SECOND; // Y3
		rec2[1] = rec2[3] - e.sizeY; // X4
		
		return Utility.rectangleOverlap(rec1, rec2);
	}
		
	private void checkVerticalCollision() {
		float TBAdjustment = sizeY / 2f + Math.signum(-ySpeed) * sizeY / 2f;
		
		float yBorder = position.getY() + ySpeed / Engine.FRAMES_PER_SECOND;
		yBorder -= TBAdjustment;
		
		int leftX = (int) position.getX();
		int rightX = (int) (position.getX() + sizeX);
		
		if(ySpeed > 0) {
			for(int i = (int) Math.ceil(position.getY()); i <= (int) Math.ceil(yBorder); i++) {
				for(int j = leftX; j <= rightX; j++) {
					Block b = Engine.game.getWorld().getChunk(j / Values.Chunk_Size_X).getBlocks()[j % Values.Chunk_Size_X][i];
					
					int id = b.getID();
					if(!BlockHash.isPassable(id)) {
						// Adjust Y Position
						this.position.setYPos(i - 1f - collisionError);
						
						// Call Collision Methods
						onBlockYCollision();
						onBlockCollision();
						
						ySpeed = 0f;
						break;
					}
					
				}
			}
		} else if (ySpeed < 0) {
			for(int i = (int) Math.ceil(position.getY()); i >= (int) Math.ceil(yBorder); i--) {
				for(int j = leftX; j <= rightX; j++) {
					Block b = Engine.game.getWorld().getChunk(j / Values.Chunk_Size_X).getBlocks()[j % Values.Chunk_Size_X][i];
					
					int id = b.getID();
					if(!BlockHash.isPassable(id)) {
						// Adjust Y Position
						this.position.setYPos(i + sizeY + collisionError);
						
						// Call Collision Methods
						onBlockYCollision();
						onBlockCollision();
						
						ySpeed = 0f;
						break;
					}
					
				}
			}
		}
	}
	private void checkHorizontalCollision() {
		// Adjust x position based on player movement direction
		float LRAdjustment = sizeX / 2f + Math.signum(xSpeed) * sizeX / 2f; 
		
		// Find x next frame
		float xBorder = position.getX() + xSpeed / Engine.FRAMES_PER_SECOND;
		xBorder += LRAdjustment;
		
		int topY = (int) Math.ceil(position.getY() - collisionError);
		int bottomY = (int) Math.ceil(position.getY() - sizeY);
		
		if(xSpeed > 0) {
			for(int i = (int) position.getX(); i <= (int) xBorder; i++) {
				for(int j = bottomY; j <= topY; j++) {
					Block b = Engine.game.getWorld().getChunk(i / Values.Chunk_Size_X).getBlocks()[i % Values.Chunk_Size_X][j];
					
					int id = b.getID();
					if(!BlockHash.isPassable(id)) {
						// Adjust X Position
						position.setXPos(i - sizeX - collisionError);
						
						// Call Collision Methods
						onBlockXCollision();
						onBlockCollision();
						
						xSpeed = 0f;
						break;
					}
					
				}
			}
		} else if (xSpeed < 0) {
			for(int i = (int) position.getX(); i >= (int) xBorder; i--) {
				for(int j = bottomY; j <= topY; j++) {
					Block b = Engine.game.getWorld().getChunk(i / Values.Chunk_Size_X).getBlocks()[i % Values.Chunk_Size_X][j];
					
					int id = b.getID();
					if(!BlockHash.isPassable(id)) {
						// Adjust X Position
						position.setXPos(i + 1 + collisionError);
						
						// Call Collision Methods
						onBlockXCollision();
						onBlockCollision();
						
						xSpeed = 0f;
						break;
					}
				}
			}
		}
	}
	
	
	// Used in debugging (to be moved)
	public void debug() {
		// Adjust x position based on player movement direction
		float LRAdjustment = sizeX / 2f + Math.signum(xSpeed) * sizeX / 2f; 
		
		// Find x next frame
		float xBorder = position.getX() + xSpeed / Engine.FRAMES_PER_SECOND;
		xBorder += LRAdjustment;
		
		int topY = (int) Math.ceil(position.getY() - collisionError);
		int bottomY = (int) Math.ceil(position.getY() - sizeY);
		
		Engine.game.displayManager.pinpoint(xBorder, position.getY() - sizeY / 2f);
		
		if(xSpeed > 0) {
			for(int i = (int) position.getX(); i <= (int) xBorder; i++) {
				for(int j = bottomY; j <= topY; j++) {
					// if(topY - bottomY == 1) System.out.println("TRUE");
					Engine.game.displayManager.highlightBlock(i, j);
				}
			}
		} else if (xSpeed < 0) {
			for(int i = (int) position.getX(); i >= (int) xBorder; i--) {
				for(int j = bottomY; j <= topY; j++) {
					// if(topY - bottomY == 1) System.out.println("TRUE");
					Engine.game.displayManager.highlightBlock(i, j);
				}
			}
		}

		float TBAdjustment = sizeY / 2f + Math.signum(-ySpeed) * sizeY / 2f;
		
		float yBorder = position.getY() + ySpeed / Engine.FRAMES_PER_SECOND;
		yBorder -= TBAdjustment;
		
		int leftX = (int) position.getX();
		int rightX = (int) (position.getX() + sizeX);
		
		Engine.game.displayManager.pinpoint(position.getX() + sizeX / 2f, yBorder);
		if(ySpeed > 0) {
			for(int i = (int) position.getY(); i <= (int) yBorder; i++) {
				for(int j = leftX; j <= rightX; j++) {
					Engine.game.displayManager.highlightBlock(j, i);
				}
			}
		} else if (ySpeed < 0) {
			for(int i = (int) position.getY(); i >= (int) yBorder; i--) {
				for(int j = leftX; j <= rightX; j++) {
					Engine.game.displayManager.highlightBlock(j, i);
				}
			}
		}
		
	}
}
