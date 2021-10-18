package core;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import gamestates.Game;

/*
 * Perlin Noise - 
 * Simplex Noise - Both very common algorithms for random world generation 
 * 
 * Random Class in Java - Also allows randomization with seeding 
 */
public class Engine extends StateBasedGame 
{
	/*
	 * Desktop Resolution: 1920 x 1080
	 * Laptop Resolution: 1366 x 768
	 */
	public final static int RESOLUTION_X = 1920; 	
	public final static int RESOLUTION_Y = 1080; 
	public final static int FRAMES_PER_SECOND = 60;
	
	public static final int StartingMenu_ID = 0;
    public static final int WorldSelect_ID = 1;
    public static final int Game_ID = 2;
    
    private BasicGameState game;

	public Engine(String name) 
	{
		super(name);
		
		game = new Game(Game_ID);
	}

	public void initStatesList(GameContainer gc) throws SlickException 
	{
		addState(game);
	}

	public static void main(String[] args) 
	{
		try {
			AppGameContainer appgc = new AppGameContainer(new Engine("pogger"));
			
			System.setProperty("org.lwjgl.opengl.Window.undecorated", "true");
			
			appgc.setDisplayMode(RESOLUTION_X, RESOLUTION_Y, false);
			appgc.setTargetFrameRate(FRAMES_PER_SECOND);
			appgc.start();
			appgc.setVSync(true);

		} catch (SlickException e) 
		{
			e.printStackTrace();
		}

	}
}