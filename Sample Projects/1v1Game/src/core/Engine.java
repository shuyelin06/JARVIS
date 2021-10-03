package core;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Engine extends StateBasedGame 
{
	//desktop: 1920 by 1080
	//laptop: 1366 by 768
	public final static int RESOLUTION_X = 1366; 	
	public final static int RESOLUTION_Y = 768; 
	public final static int FRAMES_PER_SECOND = 60;
	public static final int CHARACTERSELECT_ID = 0;
    public static final int GAME_ID  = 1;
    public static final int characterselect = 0;
    public static final int game = 1;
    public static final int lavaFloor = 2;
    public static final int startGame = 3;
    
    
    

	public Engine(String name) 
	{
		super(name);
		
		this.addState(new CharacterSelect(characterselect));
		this.addState(new Game(game));
		this.addState(new LavaFloor(lavaFloor));
		this.addState(new StartGame(startGame));
		
		
//		game = new characterSelect(CHARACTERSELECT_ID); //changing this to characterSelect runs characterSelect instead, changing to Game runs Game
		
		
//		game = new Game(GAME_ID);
		
		
//		
	}

	public void initStatesList(GameContainer gc) throws SlickException 
	{
//		addState(game);
		
		this.getState(characterselect).init(gc, this);
		this.getState(game).init(gc,  this);
		this.enterState(characterselect);
		this.getState(lavaFloor).init(gc, this);
		this.getState(startGame).init(gc, this);
		
	}

	public static void main(String[] args) 
	{
		try {
			AppGameContainer appgc = new AppGameContainer(new Engine("Sample Slick Game"));
			//true --> fullscreen, false --> windowed
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
