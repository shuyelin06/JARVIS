package gamestates;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import core.Engine;
import core.Values;

public class Loading extends BasicGameState
{
	// Loading screen 
	private BasicGameState previous; // If loading fails, return to previous screen
	private BasicGameState next; // If loading succeeds, move to next screen
	
	// Variables for the loading bar 
	private int totalTasks;
	private int tasksDone;
	
	private StateBasedGame sbg;
	int id;
	
	public Loading(int id) 
	{
		this.id = id;
	}

	// Initializer, first time
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException 
	{
		this.sbg = sbg;
	}
	
	//render, all visuals
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException 
	{
		drawLoadingBars(gc, g);
	}

	public void drawLoadingBars(GameContainer gc, Graphics g) {
		//image settings
		// 100 width: (int) (0.05208333333*gc.getWidth());
		// 100 height: (int) (0.09259259259*gc.getHeight());
		final float BAR_WIDTH = gc.getWidth() - (int) (0.05208333333*gc.getWidth());
		
		//height 300
		final float BAR_HEIGHT = 3* (int) (0.09259259259*gc.getHeight());
		
		final float BAR_X = gc.getWidth() / 2;
		final float BAR_Y = gc.getHeight() / 2;
		final float PERCENT_LOADED = tasksDone / totalTasks;
		
		// max loading bar
		g.setColor(new Color(0, 100, 0, 150));
		g.fillRect(BAR_X - (BAR_HEIGHT / 2), BAR_Y - (BAR_WIDTH / 2), BAR_WIDTH, BAR_HEIGHT);
		
		// current loaded
		g.setColor(new Color(0, 255, 0, 150));
		g.fillRect(BAR_X - (BAR_HEIGHT / 2), BAR_Y - (BAR_WIDTH / 2), BAR_WIDTH*PERCENT_LOADED, BAR_HEIGHT);
		
		// white outline
		g.setColor(new Color(255, 255, 255));
		g.fillRect(BAR_X - (BAR_HEIGHT / 2), BAR_Y - (BAR_WIDTH / 2), BAR_WIDTH, BAR_HEIGHT);
	}
	
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {}

	public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException 
	{
		
	}

	public void leave(GameContainer gc, StateBasedGame sbg) 
	{
		
	}


	public void keyPressed(int key, char c)
	{
	}
	
	public void mousePressed(int button, int x, int y)
	{
		// sbg.enterState(2);
	}
	
	
	// Returns the ID code for this game state
	public int getID() 
	{
		return id;
	}


}
