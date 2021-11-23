package core;

public class Coordinate{
	private float x, y;
	
	public Coordinate(float InitX, float InitY) {
		this.x = InitX;
		this.y = InitY;
	}
	
	public void setXPos(float x){ this.x = x; }
	public void setYPos(float y) { this.y = y; }
	
	public void update(float xSpeed, float ySpeed) {
		x += xSpeed / (float) Engine.FRAMES_PER_SECOND;
		y += ySpeed / (float) Engine.FRAMES_PER_SECOND;
	}
	
	// Get Positions
	public float getX() { return x; }
	public float getY() { return y; }
	
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
	
	// Determine magnitude of displacement from some coordinate
	public float magDisplacement(Coordinate c) {
		float[] displacement = new float[2];
		
		displacement[0] = c.getX() - x;
		displacement[1] = c.getY() - y;
		
		return (float) Math.sqrt(Math.pow(displacement[0], 2) + Math.pow(displacement[1], 2));
	}
	
	// Determine magnitude of displacement from some coordinate
	public float magDisplacement(float[] pos) {
		float[] displacement = new float[2];
		
		displacement[0] = pos[0] - x;
		displacement[1] = pos[1] - y;
		
		return (float) Math.sqrt(Math.pow(displacement[0], 2) + Math.pow(displacement[1], 2));
	}
	
}