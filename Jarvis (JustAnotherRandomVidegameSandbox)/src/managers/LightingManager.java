package managers;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import core.Engine;

public class LightingManager
{
	//lighting based off the time of day
	private float globalLight;
	private float blockLight;
	
	//plan to split it up into block lighting and sky, like how minecraft does it
	public LightingManager()
	{
		globalLight = 1 - 0.2f;//Engine.game.getDisplayManager().getBackground().getSky().getNightAlpha(); //????????? LOLLLLLL
		blockLight = 0;
	}
	
	public Image setLighting(Image image)
	{
		image.setImageColor(globalLight + blockLight, 
				globalLight + blockLight, 
				globalLight + blockLight);
		return(image);
	}
}
