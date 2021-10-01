// added a comment

package core;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import actors.Actor;
import support.SimplexNoise;

public class Game extends BasicGameState 
{	
	public static GameContainer gc;
	private int id;
	public static int time;
	public static boolean debugMode = true;
	
	public static World world;
	
	Game(int id) 
	{
		this.id = id;
	}
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException 
	{
		time = 0;
		this.gc = gc;
		this.gc.setVSync(true);
		world = new World();
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException 
	{
		world.render(g);
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException
	{	
		time++;
		world.update();
	}

	public void keyPressed(int key, char c)
	{
		
	}
	
	public void mousePressed(int button, int x, int y)
	{
		
	}
	
	public int getID() 
	{
		return id;
	}


}
