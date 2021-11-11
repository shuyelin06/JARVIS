package gamestates;

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
