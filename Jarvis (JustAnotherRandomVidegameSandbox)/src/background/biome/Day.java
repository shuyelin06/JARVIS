package background.biome;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import background.Layer;
import background.Scene;
import core.Engine;
import core.Values;

public class Day extends Scene
{
	public Day() throws SlickException
	{
		super();
		
		background = new Layer("mountains-60", 0.2f, 0, 0.3f, resX, 0.5f);
		middleground = new Layer("hills1-80", 0.3f, 0, 0.35f, resX, 0.25f);
		foreground  = new Layer("hills1", 0.4f, 0, 0.4f, resX, 0.3f);

		frontFiller = new Color(50, 122, 32);
		backFiller = new Color(43, 132, 69);
	}
}
