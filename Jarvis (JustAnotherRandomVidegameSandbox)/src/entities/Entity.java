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
	protected boolean direction; // Direction facing variable; false = left; true = right
	
	// Physics Variables
	protected Coordinate position; // Entity position
	protected float xSpeed, ySpeed; // Entity velocity (pixels per second)
	protected float mass; // Entity mass
	
	// Every entity will have some initial starting position
	public Entity(float InitX, float InitY)
	{
		// Default tracking variables
		this.time = Sys.getTime();
		this.remove = false;
		
		// Default Variables
		try {
			sprite = new Image("res/placeholder.png");
		} catch(Exception e) {}
		this.direction = false;
		this.sizeX = 1f;
		this.sizeY = 1f;

		
		// Physics Variables
		this.position = new Coordinate(InitX, InitY); // Position
		
		this.xSpeed = this.ySpeed = 0f; // Speeds
		this.mass = 1f; // Default Mass
	}
	
	// Accessor Methods
	public int timeAlive() { return (int) (Sys.getTime() - time) / 1000; }
	
	public Image getSprite() { return sprite; }
	public boolean getDirection() { return direction; }
	public float getSizeX() { return sizeX; }
	public float getSizeY() { return sizeY; }
	
	public Coordinate getPosition() { return position; }
	public float getXSpeed() { return xSpeed; }
	public float getYSpeed() { return ySpeed; }
	
	public boolean isMarked() { return remove; }
	
	// Mutator Methods
	public void markForRemoval() { this.remove = true; }
	
	// Updates the entity's position given its velocity
	public void update() {
		// Forces Acting on X Velocity: Drag, Friction
		xSpeed -= xSpeed * Values.Drag_Coefficient / this.mass;
		
		// Velocity Updating - Y Velocity
		ySpeed -= Values.Acceleration_of_Gravity;
		
		// Collision detection 
		collisions();
		
		// Position updating
		position.update(xSpeed, ySpeed);
		
		// Direction updating
		if (xSpeed < 0) direction = false;
		if (xSpeed > 0) direction = true;
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
					if(xSpeed < 0) position.setXPos(x + 1f + collisionError);
					else if(xSpeed > 0) position.setXPos(x - this.sizeX - collisionError);
					
					xSpeed = 0f;
					
					onCollision();
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
					// Interpolate the new y position
					if(ySpeed < 0) position.setYPos(y + this.sizeY + collisionError);
					else if(ySpeed > 0) position.setYPos(y - 1f - collisionError);
					
					ySpeed = 0f;
					
					onCollision();
					break;
				}
			} 
		} catch(Exception e) {}
	}
	
	protected void onCollision() {} // Empty collision method that can be used in other classes
	
	// Returns true if this entity will collide with another entity e.
	public boolean entityCollision(Entity e) {
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
	
	public void debug(Graphics g, float x, float y) {
		float[] render;
		float temp;
		
		temp = position.getX() + xSpeed / Engine.FRAMES_PER_SECOND;
		if(xSpeed > 0) temp += sizeX;
		
		x = (int) temp; // Furthest x away
		for(int j = 0; j < Math.ceil(sizeY); j++) {
			render = Engine.game.getDisplayManager().positionOnScreen(x, position.getY() - j);
			g.setColor(Color.white);
			g.draw(new Rectangle(render[0], render[1], Coordinate.ConversionFactor, Coordinate.ConversionFactor));
		}
		
		temp = position.getY() + ySpeed / Engine.FRAMES_PER_SECOND;
		if(ySpeed < 0) temp -= sizeY;
		
		y = (int) Math.ceil(temp);
		
		int max = (int) (Math.ceil(sizeX) + 1);
		if(position.getX() - Math.floor(position.getX()) < Math.ceil(sizeX) - sizeX) max--;
		
		for(int i = 0; i < max; i++) {
			x = (int) position.getX() + i; // Get the absolute x coordinate

			render = Engine.game.getDisplayManager().positionOnScreen(x, position.getY() - y);
			g.setColor(Color.cyan);
			g.draw(new Rectangle(render[0], render[1], Coordinate.ConversionFactor, Coordinate.ConversionFactor));
		} 
	}
}
