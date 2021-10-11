package support;

import entities.Entity;

public class Utility //random functions and the such, feel free to add any annoying-to-write lines
{
	public static int random(int max) 
	{
		return (int) (Math.random() * max);
	}

	public static int random(int min, int max) 
	{
		return (int) (Math.random() * (max - min + 1)) + min;

	}

	public static float random(double min, double max) 
	{
		return (float) (min + (Math.random() * (max - min)));
	}
	
	public static float getDistance(Entity a, Entity b) {
		float d = 0f;
		
		d = (float) Math.sqrt((Math.pow(changeX(a, b), 2)) + (Math.pow(changeY(a,b), 2)) );		
		return d;
	}
	
	public static float getAngle(Entity a, Entity b) {
		float theta = 0f;
		theta = (float)Math.atan(changeY(a,b)/changeX(a,b));
	
		
		return theta;
	}
	public static float changeX(Entity a, Entity b) {
		return b.getPosition().getX() - a.getPosition().getX();
	}
	public static float changeY(Entity a, Entity b) {
		return b.getPosition().getY() - a.getPosition().getY();
	}
}
