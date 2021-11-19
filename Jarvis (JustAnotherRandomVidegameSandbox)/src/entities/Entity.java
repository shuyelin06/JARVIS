package entities;

import core.Coordinate;
import core.Engine;
import core.Values;
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
	public enum EntType{ Player, Hostiles, Items, Projectiles }
	
	// Keeping track of the entity
	protected long time;
	protected boolean remove;
	
	// Render Variables
	protected Image sprite; // The sprite rendered in for the Entity
	protected float sizeX, sizeY; // The size of the Entity
	
	// Physics Variables
	protected Coordinate position; // Entity position
	protected float xSpeed, ySpeed; // Entity velocity (pixels per second)
	protected float mass; // Entity mass
	
	// Every entity will have some initial starting position
	public Entity(float InitX, float InitY)
	{
		// Management Variables
		this.time = Sys.getTime();
		this.remove = false;
		
		// Rendering Variables
		try {
			sprite = new Image("res/placeholder.png");
		} catch(Exception e) {}
		this.sizeX = 1f;
		this.sizeY = 1f;

		
		// Physics Variables
		this.position = new Coordinate(InitX, InitY); // Initial position
		this.xSpeed = this.ySpeed = 0f; // Default speeds
		this.mass = 1f; // Default Mass
	}
	
	// Accessor Methods
	public float timeAlive() { return (float) (Sys.getTime() - time) / 1000; }
	
	public Image getSprite() { return sprite; }
	public boolean getDirection() { return xSpeed > 0; } // False - Left, True - Right
	public float getSizeX() { return sizeX; }
	public float getSizeY() { return sizeY; }
	
	public Coordinate getPosition() { return position; }
	public float getXSpeed() { return xSpeed; }
	public float getYSpeed() { return ySpeed; }
	
	public boolean isMarked() { return remove; }
	
	// Mutator Methods
	public void markForRemoval() { this.remove = true; }
	public void setSpeedX(float xSpeed) { this.xSpeed = xSpeed; }
	public void setSpeedY(float ySpeed) { this.ySpeed = ySpeed; }
	
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
	
	
	/*
	 * Collision Detection
	 */
	private static final float collisionError = 0.001f; // Prevents the object from sticking
	
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
		// Defining Memory Used
		Chunk c;
		Block[][] blocks;
		
		float temp;
		int x, y;
		
		// Check for horizontal collisions
		try {
			checkHorizontalCollision();
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
				Block b = Engine.game.getWorld().getChunk(x / Values.Chunk_Size_X).getBlocks()[x % Values.Chunk_Size_X][y];
				
				if(!Block.Passable_Blocks.contains(b.getID())){
					// Interpolate the new y position
					if(ySpeed < 0) position.setYPos(y + this.sizeY + collisionError);
					else if(ySpeed > 0) position.setYPos(y - 1f - collisionError);
					
					onBlockYCollision();
					onBlockCollision();
					
					ySpeed = 0f;
					break;
				}
			} 
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
	
	// Used in debugging (to be moved)
	public void debug() {
		// Adjust x position based on player movement direction
		float LRAdjustment = sizeX / 2f + Math.signum(xSpeed) * sizeX / 2f; 
		
		// Find x next frame
		float xBorder = position.getX() + xSpeed / Engine.FRAMES_PER_SECOND;
		xBorder += LRAdjustment;
		
		int topY = (int) Math.ceil(position.getY() - collisionError);
		int bottomY = (int) Math.ceil(position.getY() - sizeY);
		
		Engine.game.displaymanager.pinpoint(xBorder, position.getY() - sizeY / 2f);
		
		if(xSpeed > 0) {
			for(int i = (int) position.getX(); i <= (int) xBorder; i++) {
				for(int j = bottomY; j <= topY; j++) {
					// if(topY - bottomY == 1) System.out.println("TRUE");
					Engine.game.displaymanager.highlightBlock(i, j);
				}
			}
		} else if (xSpeed < 0) {
			for(int i = (int) position.getX(); i >= (int) xBorder; i--) {
				for(int j = bottomY; j <= topY; j++) {
					// if(topY - bottomY == 1) System.out.println("TRUE");
					Engine.game.displaymanager.highlightBlock(i, j);
				}
			}
		}

//				int x = (int) i;
//				int y = (int) j;
//				
//				Engine.game.displaymanager.highlightBlock(x, y);
//				Block b = Engine.game.getWorld().getChunk(x / Values.Chunk_Size_X).getBlocks()[x % Values.Chunk_Size_X][y];
//				
//				if(!Block.Passable_Blocks.contains(b.getID())) {
//					// Collision detected
//					if(xSpeed < 0) position.setXPos(x + 1f + collisionError);
//					else if(xSpeed > 0) position.setXPos(x - this.sizeX - collisionError);
//					
//					onBlockXCollision();
//					onBlockCollision();
//					
//					xSpeed = 0;
//				}
//			}
		
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
					if(!Block.Passable_Blocks.contains(b.getID())) {
						onBlockXCollision();
						onBlockCollision();
						
						xSpeed = 0f;
					}
					
				}
			}
		} else if (xSpeed < 0) {
			for(int i = (int) position.getX(); i >= (int) xBorder; i--) {
				for(int j = bottomY; j <= topY; j++) {
					Block b = Engine.game.getWorld().getChunk(i / Values.Chunk_Size_X).getBlocks()[i % Values.Chunk_Size_X][j];
					if(!Block.Passable_Blocks.contains(b.getID())) {
						onBlockXCollision();
						onBlockCollision();
						
						xSpeed = 0f;
					}
				}
			}
		}
		
//		float leftRight = sizeX / 2f + Math.signum(xSpeed) * sizeX / 2f;
//		// Find x next frame
//		float nextX = position.getX() + xSpeed / Engine.FRAMES_PER_SECOND;
//		
//		Engine.game.displaymanager.pinpoint(nextX, position.getY() - sizeY / 2f);
//		
//		float topY = (float) Math.ceil(position.getY());
//		float bottomY = (float) Math.floor(position.getY() - sizeY + 1);
//		
//		for(float i = position.getX() + leftRight; i < nextX + leftRight; i++) {
//			for(float j = bottomY; j < topY; j++) {
//				int x = (int) i;
//				int y = (int) j;
//				
//				Engine.game.displaymanager.highlightBlock(x, y);
//				Block b = Engine.game.getWorld().getChunk(x / Values.Chunk_Size_X).getBlocks()[x % Values.Chunk_Size_X][y];
//				
//				if(!Block.Passable_Blocks.contains(b.getID())) {
//					// Collision detected
//					if(xSpeed < 0) position.setXPos(x + 1f + collisionError);
//					else if(xSpeed > 0) position.setXPos(x - this.sizeX - collisionError);
//					
//					onBlockXCollision();
//					onBlockCollision();
//					
//					xSpeed = 0;
//				}
//			}
//		}

	}
}
