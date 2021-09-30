package entities;

import org.newdawn.slick.Image;
import core.Engine;

public class Entity{	
	protected Image sprite;
	
	protected double x, y;
	protected double xSpeed, ySpeed; // x and y velocities
	
	// Every entity will have some initial starting position
	public Entity(int xPos, int yPos) {
		this.x = xPos;
		this.y = yPos;
		
		this.xSpeed = 0;
		this.ySpeed = 0;
	}
	
	// Methods returning the position of an object
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	
	
	// Updates the entity's position given its velocity
	public void update() {
		x += xSpeed / Engine.FRAMES_PER_SECOND;
		y += ySpeed / Engine.FRAMES_PER_SECOND;
	}
}