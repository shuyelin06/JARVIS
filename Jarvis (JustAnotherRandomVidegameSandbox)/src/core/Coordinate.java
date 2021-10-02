package core;

import core.Engine;

public class Coordinate{
	public static final int ConversionFactor = 20; // Conversion: 1 Block = 20 Pixels
	float x, y;
	
	public Coordinate(float InitX, float InitY) {
		this.x = InitX;
		this.y = InitY;
	}
	
	public void update(float xSpeed, float ySpeed) {
		x += xSpeed / Engine.FRAMES_PER_SECOND;
		y += ySpeed / Engine.FRAMES_PER_SECOND;
	}
	
	public float getXInPixels() {
		return x * ConversionFactor;
	}
	public float getYInPixels() {
		return Engine.RESOLUTION_Y - y * ConversionFactor;
	}
}