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

import attack.Attack;
import platform.Ground;
import platform.Platform;
import player.Assassin;
import player.DeadPlayer;
import player.Player;
import player.Ranger;
import player.Tank;

public class LavaFloor extends BasicGameState 
{	
	private int id;
	public static int backgroundColor1, backgroundColor2, backgroundColor3;
	public static int backgroundColor;
	public static boolean debugMode = false;
	public static float time, timeStart, timeSeconds;
	public static ArrayList<Attack> attacks;
	public static Player player1;
	public static Player player2;
	public static GameContainer gc;
	public static ArrayList<Platform> platforms;
	public static Ground ground;
	public static int stage;
	public static float percentageHealth1, percentageHealth2;
	public static float percentageEnergy1, percentageEnergy2;
	public boolean goBack;

	LavaFloor(int id) 
	{
		this.id = id;
	}

	//initializer, first time
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException 
	{
		gc.setShowFPS(true);
		this.gc = gc;


		platforms = new ArrayList<Platform>();
		makePlatforms();

	}

	//render, all visuals
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException 
	{
		g.setBackground(new Color(245,245,245));
		for (Platform p: platforms) {
			p.render(g);
		}


	}

	//update, runs consistently
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException
	{	
		time++;
	}

	//enter gamestate
	public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException 
	{
		gc.setShowFPS(true);
		this.gc = gc;


	}

	public void leave(GameContainer gc, StateBasedGame sbg) 
	{

	}


	//pressed key for player, jumping mechanic
	public void keyPressed(int key, char c)
	{

	}

	public void mousePressed(int button, int x, int y)
	{

	}

	//updates colors, changes dynamically
	public void updateBackground() {

	}

	//changes the platform formation depending on the stage
	public void makePlatforms() {

		platforms.add(new Platform(gc.getWidth()/6, 2*gc.getHeight()/((float) 3.4), gc.getWidth()/6, gc.getHeight()/45));
		platforms.add(new Platform(4*gc.getWidth()/6, 2*gc.getHeight()/((float) 3.4), gc.getWidth()/6, gc.getHeight()/45));
		platforms.add(new Platform((float) 2.5*gc.getWidth()/6, 2*gc.getHeight()/(float) 4.5, gc.getWidth()/6, gc.getHeight()/45));

		platforms.add(new Platform(gc.getWidth()/8, 2*gc.getHeight()/((float) 3.4), gc.getWidth()/8, gc.getHeight()/45));
		platforms.add(new Platform(6*gc.getWidth()/8, 2*gc.getHeight()/((float) 3.4), gc.getWidth()/8, gc.getHeight()/45));
		platforms.add(new Platform((float) 3.5*gc.getWidth()/8, 2*gc.getHeight()/(float) 4.5, gc.getWidth()/8, gc.getHeight()/45));

	}

	//removes removable objects
	public void cleanUp() {

	}

	//selects character from CharacterSelect
	public void identifyCharacter() {

	}

	// Returns the ID code for this game state
	public int getID() 
	{
		return 2;
	}


}
