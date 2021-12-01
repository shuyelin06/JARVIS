package background;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import background.biome.Desert;
import background.biome.Hills;
import background.biome.Underground;
import background.sky.Sky;
import core.Coordinate;
import core.Engine;
import core.Values;

public class Background
{
	private int time;
	private float nightAlpha;
	
	private Sky sky;
	private Hills hills;
	private Desert desert;
	private Underground underground;
	
	private Cloud[] clouds;
	
	public Background() throws SlickException
	{
		nightAlpha = 0;
		time = 0;
		sky = new Sky();
		hills = new Hills();
		desert = new Desert();
		
		underground = new Underground();
		
		clouds = new Cloud[7];
		
		for(int i = 0; i < clouds.length; i++)
		{
			clouds[i] = new Cloud();
		}
	}
	
	public void render(Graphics g, float x, float y)
	{
		float currentChunk = (x - Values.CenterX) / Values.Pixels_Per_Block / Values.Chunk_Size_X * -1;
		
		if(y > -500)
		{
			sky.render(g);
			
			for(int i = 0; i < clouds.length; i++) //the clouds
			{
				clouds[i].render(g, x, y);
			}
			
			if(currentChunk > Values.desertStart
					&& currentChunk < Values.desertEnd)
			{
				desert.render(g, x, y);
			} 
			else 
			{
				hills.render(g, x, y);
			}
			
			if(currentChunk > Values.desertStart - 1
					&& currentChunk < Values.desertStart)
			{
				desert.setSceneAlpha(transition(currentChunk, Values.desertStart - 1, Values.desertStart));
				desert.render(g, x, y);
			}
			
			if(currentChunk < Values.desertEnd + 1
					&& currentChunk > Values.desertEnd)
			{
				desert.setSceneAlpha(transition(currentChunk, Values.desertEnd + 1, Values.desertEnd));
				desert.render(g, x, y);
			}

		}
		
		if(y < 0)
		{
			underground.render(g, x, y);
		}
	}
	
	public void update()
	{
		nightAlpha = sky.getNightAlpha();
		//time = Engine.game.getWorld().getTime();
		time++;
		
		for(int i = 0; i < clouds.length; i++) //the clouds
		{
			clouds[i].update(nightAlpha);
		}
		
		desert.update(nightAlpha);
		hills.update(nightAlpha);
		sky.update(time);
	}
	
	public float transition(float pos, float start, float end) //takes two points
	{
		float alpha = Math.abs((pos - start) / (end - start));
		
		return alpha;
	}
}
