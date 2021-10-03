package support;

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
}
