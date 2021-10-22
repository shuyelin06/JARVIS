package core;

import core.Engine;
import settings.Values;
import world.Chunk;

public class Coordinate{
	public static final int ConversionFactor = 30; // Conversion: 1 Block = 20 Pixels
	private float x, y;
	
	public Coordinate(float InitX, float InitY) {
		this.x = InitX;
		this.y = InitY;
	}
	
	public void setXPos(float x){
		this.x = x;
	}
	public void setYPos(float y) {
		this.y = y;
	}
	public void update(float xSpeed, float ySpeed) {
		x += xSpeed / (float) Engine.FRAMES_PER_SECOND;
		y += ySpeed / (float) Engine.FRAMES_PER_SECOND;
	}
	
	// Get Positions
	public float getX() {
		return x;
	}
	public float getY() {
		return y;
	}
	//set positions directly (these methods should probably be used with caution, i just added them as a way to manipulate coordinates)
	public void setX(float x) {
		this.x = x;
	}
	public void setY(float y) {
		this.y = y;
	}
	
	// Finds the chunk the coordinate is located in
	public float getChunk() {
		return x / (float) Values.Chunk_Size_X;
	}
	
	// Determine displacement from some coordinate
	public float[] displacement(float x2, float y2) {
		float[] displacement = new float[2];
		
		displacement[0] = x2 - x;
		displacement[1] = y2 - y;
		
		return displacement;
	}
	
}