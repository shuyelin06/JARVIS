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
import platform.Background;
import platform.Ground;
import platform.Platform;
import player.Assassin;
import player.DeadPlayer;
import player.Mage;
import player.Player;
import player.Ranger;
import player.Tank;

public class Game extends BasicGameState 
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
	public static Background background;
	public static int stage;
	public static float percentageHealth1, percentageHealth2;
	public static float percentageEnergy1, percentageEnergy2;
	public boolean goBack;
	public static boolean hitPause = false;
	public static boolean hitPauseAllowed = false;
	public static short hitTime = 0;

	
	Game(int id) 
	{
		this.id = id;
	}

	//initializer, first time
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException 
	{
		gc.setShowFPS(true);
		this.gc = gc;
		
		//stage and background settings
		backgroundColor = 1;
		stage = 1;
		
		timeStart = System.currentTimeMillis();
		attacks = new ArrayList<Attack>();
		platforms = new ArrayList<Platform>();
		makePlatforms();
		
		ground = (new Ground(0, gc.getHeight()-122, 2000, 122));
		background = new Background();
		//initialize each character to each player
		goBack = false;
	}
	
	//render, all visuals
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException 
	{
//		g.setBackground(new Color(backgroundColor1, backgroundColor2, backgroundColor3));
		background.render(g);
		for (Platform p: platforms) {
			p.render(g);
		}
		for (Attack a: attacks) {
			a.render(g);
		}
		player1.render(g);
		player2.render(g);
		ground.render(g);
	}

	//update, runs consistently
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException
	{	
		if (!hitPause || !hitPauseAllowed) {
			time++;
			cleanUp();
			updateBackground();
			timeSeconds = (System.currentTimeMillis() - timeStart) / 1000;
			for (Attack a: attacks) {
				a.update();
			}
			player1.update();
			player2.update();
			if (goBack) {
				sbg.enterState(0);
			}
		} else
		if (hitPause && hitPauseAllowed) {
			for(int i = 0; i < attacks.size(); i++)
			{
				if(attacks.get(i).isRemovable())
				{
					attacks.remove(attacks.get(i));
					i--;
				}
			}
			if (hitTime >= 5) {
				hitPause = false;
				hitTime = 0;
			} else {
				hitTime++;
			}
		}
	}


	//enter gamestate
	public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException 
	{
		gc.setShowFPS(true);
		this.gc = gc;
		
		//stage and background settings
		backgroundColor = 1;
		stage = 1;
		
		timeStart = System.currentTimeMillis();
		attacks = new ArrayList<Attack>();
		platforms = new ArrayList<Platform>();
		makePlatforms();
		
		ground = (new Ground(0, gc.getHeight()-122, 2000, 122));
		//initialize each character to each player
		identifyCharacter();
		goBack = false;
	}

	public void leave(GameContainer gc, StateBasedGame sbg) 
	{
		
	}


	//pressed key for player, jumping mechanic
	public void keyPressed(int key, char c)
	{
		if (key == Input.KEY_W) {
			player1.jump();
		} else if (key == Input.KEY_UP) {
			player2.jump();
		}
		
		if (key == Input.KEY_DELETE) {
			goBack = true;
		}
	}
	
	//mouse position clicks
	public void mousePressed(int button, int x, int y)
	{
		
	}
	
	//updates colors, changes dynamically
	public void updateBackground() {
		if (backgroundColor == 1) {
			//pink and light blue
			backgroundColor1 = (int) Math.abs((time % 510) - 255);
			backgroundColor2 = 120;
			backgroundColor3 = 120;
		} else if (backgroundColor == 2) {
			//green and purple
			backgroundColor1 = 120;
			backgroundColor2 = (int) Math.abs((time % 510) - 255);
			backgroundColor3 = 120;
		} else if (backgroundColor == 3) {
			//blue and yellow
			backgroundColor1 = 120;
			backgroundColor2 = 120;
			backgroundColor3 = (int) Math.abs((time % 510) - 255);
		}
	}
	
	//changes the platform formation depending on the stage
	public void makePlatforms() {
		if (stage == 1) {
			platforms.add(new Platform(gc.getWidth()/6, 2*gc.getHeight()/((float) 3.4), gc.getWidth()/6, gc.getHeight()/45));
			platforms.add(new Platform(4*gc.getWidth()/6, 2*gc.getHeight()/((float) 3.4), gc.getWidth()/6, gc.getHeight()/45));
			platforms.add(new Platform((float) 2.5*gc.getWidth()/6, 2*gc.getHeight()/(float) 4.5, gc.getWidth()/6, gc.getHeight()/45));
		} else if (stage == 2) {
			platforms.add(new Platform(gc.getWidth()/8, 2*gc.getHeight()/((float) 3.4), gc.getWidth()/8, gc.getHeight()/45));
			platforms.add(new Platform(6*gc.getWidth()/8, 2*gc.getHeight()/((float) 3.4), gc.getWidth()/8, gc.getHeight()/45));
			platforms.add(new Platform((float) 3.5*gc.getWidth()/8, 2*gc.getHeight()/(float) 4.5, gc.getWidth()/8, gc.getHeight()/45));
		}
	}
	
	//removes removable objects
	public void cleanUp() {
		for(int i = 0; i < attacks.size(); i++)
		{
			if(attacks.get(i).isRemovable())
			{
				attacks.remove(attacks.get(i));
				i--;
			}
		}
		if(player1.canRemove()) {
			player1 = new DeadPlayer();
		}
		if(player2.canRemove()) {
			player2 = new DeadPlayer();
		}
	}
	
	//selects character from CharacterSelect
	public void identifyCharacter() {
		if (CharacterSelect.p1Select == "ranger") {
			player1 = new Ranger(gc.getWidth()/3, 500, 1);
		} else if (CharacterSelect.p1Select == "tank") {
			player1 = new Tank(gc.getWidth()/3, 500, 1);
		} else if (CharacterSelect.p1Select == "assassin") {
			player1 = new Assassin(gc.getWidth()/3, 500, 1);
		} else if (CharacterSelect.p1Select == "mage") {
			player1 = new Mage(gc.getWidth()/3, 500, 1);
		} else if (CharacterSelect.p1Select == "man") {
			
		}
		if (CharacterSelect.p2Select == "ranger") {
			player2 = new Ranger(2*gc.getWidth()/3 - (float) ((float) Game.gc.getWidth()/27.32), 500, 2);
		} else if (CharacterSelect.p2Select == "tank") {
			player2 = new Tank(2*gc.getWidth()/3 - (float) ((float) Game.gc.getWidth()/21.34375), 500, 2);
		} else if (CharacterSelect.p2Select == "assassin") {
			player2 = new Assassin(2*gc.getWidth()/3 - (float) ((float) Game.gc.getWidth()/27.32), 500, 2);
		} else if (CharacterSelect.p2Select == "mage") {
			player2 = new Mage(2*gc.getWidth()/3, 500, 2);
		} else if (CharacterSelect.p2Select == "man") {
			
		}
	}
	
	// Returns the ID code for this game state
	public int getID() 
	{
		return id;
	}


}
