package support;

public class Values
{	
	public final static int FRAMES_PER_SECOND = 60;
	public final static int RESOLUTION_WIDTH = 1920; 	
	public final static int RESOLUTION_HEIGHT = 1080; 
	public final static int WORLD_WIDTH = 800;
	public final static int WORLD_HEIGHT = 450;
	
	public final static double MIN_WATER = 0;
	public final static double MAX_WATER = .4;
	public final static double MIN_DIRT = .4;
	public final static double MAX_DIRT = 1;
	
	public final static int TREE_AMOUNT = 2400;
	
	public final static int FOX_VISION = 24;
	
	public static int CELL_SIZE = 12;
	public static int CAMERA_WIDTH = RESOLUTION_WIDTH / CELL_SIZE;
	public static int CAMERA_HEIGHT = RESOLUTION_HEIGHT / CELL_SIZE;
}
