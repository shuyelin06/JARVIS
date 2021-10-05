package core;

import core.Engine;
import world.Chunk;

public class Coordinate{
	public static final int ConversionFactor = 30; // Conversion: 1 Block = 20 Pixels
	float x, y;
	
	public Coordinate(float InitX, float InitY) {
		this.x = InitX;
		this.y = InitY;
	}
	
	public void update(float xSpeed, float ySpeed) {
		x += xSpeed / Engine.FRAMES_PER_SECOND;
		y += ySpeed / Engine.FRAMES_PER_SECOND;
	}
	
	// Get Positions
	public float getX() {
		return x;
	}
	public float getY() {
		return y;
	}
	
	// Finds the chunk the coordinate is located in
	public int getChunk() {
		return (int) x / Chunk.Chunk_Size_X;
	}
	
	// Determine displacement from some coordinate
	public float[] displacement(float x2, float y2) {
		float[] displacement = new float[2];
		
		displacement[0] = x2 - x;
		displacement[1] = y2 - y;
		
		return displacement;
	}
	
}