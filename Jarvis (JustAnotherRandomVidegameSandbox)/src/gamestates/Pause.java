package gamestates;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import core.Engine;
import settings.Values;

public class Pause extends BasicGameState
{
	private StateBasedGame sbg;
	int id;
	
	public Pause(int id) 
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
		// Render the game without updating it
		Engine.game.render(gc, sbg, g);
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
		switch(key) {
			case Input.KEY_P: // Unpause Key Binding
				Values.LastState = Engine.Pause_ID;
				sbg.enterState(Engine.Game_ID);
				break;
		}
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
