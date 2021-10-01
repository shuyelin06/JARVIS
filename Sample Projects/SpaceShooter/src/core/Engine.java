
package core;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Engine extends StateBasedGame 
{
	public final static int RESOLUTION_X = 1366; 	// 1366 or 1920
	public final static int RESOLUTION_Y = 768; 	// 768 or 1080
	public final static int FRAMES_PER_SECOND = 60;
	
    public static final int GAME_ID  = 0;
    
    private BasicGameState game;

	public Engine(String name) 
	{
		super(name);
		game = new Game(GAME_ID);
	}

	public void initStatesList(GameContainer gc) throws SlickException 
	{
		addState(game);
	}

	public static void main(String[] args) 
	{
		try 
		{
			AppGameContainer appgc = new AppGameContainer(new Engine("Sample Slick Game"));
			System.setProperty("org.lwjgl.opengl.Window.undecorated", "true");
		
			appgc.setDisplayMode(RESOLUTION_X, RESOLUTION_Y, false);
			appgc.setTargetFrameRate(FRAMES_PER_SECOND);
			appgc.start();
			appgc.setVSync(true);

		} 
		catch (SlickException e) 
		{
			e.printStackTrace();
		}

	}
}