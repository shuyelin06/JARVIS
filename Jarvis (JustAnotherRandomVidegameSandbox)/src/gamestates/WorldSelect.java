package gamestates;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import entities.Entity;
import world.FileLoader;

public class WorldSelect extends BasicGameState 
{
	int id;
	
	public WorldSelect(int id) 
	{
		this.id = id;
	}

	// Initializer, first time
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException 
	{
		gc.setShowFPS(true); // Shows the FPS of the game
		
	}
	
	//render, all visuals
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException 
	{
		// Render the list of worlds
		String[] worldList = FileLoader.getWorldList();
		
		
		
	}

	//update, runs consistently
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException
	{	
		
	}

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
		
	}
	
	
	// Returns the ID code for this game state
	public int getID() 
	{
		return id;
	}


}
