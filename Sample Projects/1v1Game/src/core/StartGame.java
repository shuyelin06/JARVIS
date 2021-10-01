// added a comment

package core;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
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
import player.Player;
import player.Ranger;
import player.Tank;

public class StartGame extends BasicGameState 
{	
	private int id;
	private Image first;
	private Image second;
	private Image third;
	private Image go;
	private Image p1;
	private Image p2;
	private int time;
	private float x, y, w, h;


	public static GameContainer gc;
	public static ArrayList<Platform> platforms;
	public static Ground ground;
	public static Background background;
	public static int stage;

	public boolean goBack;



	StartGame(int id) 
	{
		this.id = id;
	}

	//initializer, first time
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException 
	{
		gc.setShowFPS(true);
		this.gc = gc;

		//stage and background settings

		stage = 1;

		w = 200;
		h = 500;

		time = 0;

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
		if (CharacterSelect.p1Select == "ranger") {
			if (time%60 <= 30) {
				setImage("res/rangerIdle1.png");
				p1.setFilter(Image.FILTER_NEAREST);
				p1.draw(gc.getWidth()/3 + (float) ((float) Game.gc.getWidth()/27.32), 500, -(float) ((float) Game.gc.getWidth()/27.32), (float) ((float) Game.gc.getHeight()/7.68));
			}
		}
		if (CharacterSelect.p1Select == "tank") {
			if (time%60 <= 30) {
				setImage("res/tankIdle.png");
				p1.setFilter(Image.FILTER_NEAREST);
				p1.draw(gc.getWidth()/3 + (float) ((float) Game.gc.getWidth()/21.34375), 500, -(float) ((float) Game.gc.getWidth()/21.34375), (float) ((float) Game.gc.getHeight()/6.736842105));
			}
		}
		if (CharacterSelect.p1Select == "assassin") {
			if (time%60 <= 30) {
				setImage("res/assassinIdle.png");
				p1.setFilter(Image.FILTER_NEAREST);
				p1.draw(gc.getWidth()/3 + (float) ((float) Game.gc.getWidth()/27.32), 500, -(float) ((float) Game.gc.getWidth()/27.32), (float) ((float) Game.gc.getHeight()/7.11));
			}
		}
		if (CharacterSelect.p1Select == "mage") {

		}
		if (CharacterSelect.p2Select == "ranger") {
			if (time%60 <= 30) {
				setImage("res/rangerIdle1.png");
				p2.setFilter(Image.FILTER_NEAREST);
				p2.draw(2*gc.getWidth()/3 - (float) ((float) Game.gc.getWidth()/27.32), 500, (float) ((float) Game.gc.getWidth()/27.32), (float) ((float) Game.gc.getHeight()/7.68));
			}
		}
		if (CharacterSelect.p2Select == "tank") {
			if (time%60 <= 30) {
				setImage("res/tankIdle.png");
				p2.setFilter(Image.FILTER_NEAREST);
				p2.draw((2*gc.getWidth()/3) - (float) ((float) Game.gc.getWidth()/21.34375), 500, (float) ((float) Game.gc.getWidth()/21.34375), (float) ((float) Game.gc.getHeight()/6.736842105));
			}
		}
		if (CharacterSelect.p2Select == "assassin") {
			if (time%60 <= 30) {
				setImage("res/assassinIdle.png");
				p2.setFilter(Image.FILTER_NEAREST);
				p2.draw((2*gc.getWidth()/3) - (float) ((float) Game.gc.getWidth()/27.32), 500, (float) ((float) Game.gc.getWidth()/27.32), (float) ((float) Game.gc.getHeight()/7.11));
			}
		}
		if (CharacterSelect.p2Select == "mage") {

		}

		ground.render(g);

		if (time > 30 && time <= 90) {
			setImage("res/three.png");
			first.setFilter(Image.FILTER_NEAREST);
			first.draw((Game.gc.getWidth()/2) - w, Game.gc.getHeight()/2 - h, w, h);
		}

		if (time > 90 && time <= 150) {
			setImage("res/two.png");
			first.setFilter(Image.FILTER_NEAREST);
			first.draw((Game.gc.getWidth()/2) - w, Game.gc.getHeight()/2 - h, w, h);
		}

		if (time > 150 && time <= 210) {
			setImage("res/one.png");
			first.setFilter(Image.FILTER_NEAREST);
			first.draw((Game.gc.getWidth()/2) - w, Game.gc.getHeight()/2 - h, w, h);
		}

		if (time > 210 && time <= 240) {
			setImage("res/fight.png");
			first.setFilter(Image.FILTER_NEAREST);
			first.draw((Game.gc.getWidth()/2) - w, Game.gc.getHeight()/2 - h, w, h);
		}
	}

	//update, runs consistently
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException
	{	
		time++;


		if (time - 240 == 0) {
			sbg.enterState(1);
		}

	}


	//enter gamestate
	public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException 
	{
		gc.setShowFPS(true);
		this.gc = gc;

		//stage and background settings
		time = 0;

		stage = 1;

		w = 200;
		h = 500;


		platforms = new ArrayList<Platform>();
		makePlatforms();

		ground = (new Ground(0, gc.getHeight()-122, 2000, 122));
		background = new Background();
		//initialize each character to each player
		goBack = false;


	}

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

	public void leave(GameContainer gc, StateBasedGame sbg) 
	{

	}


	//pressed key for player, jumping mechanic
	public void keyPressed(int key, char c)
	{

	}

	//mouse position clicks
	public void mousePressed(int button, int x, int y)
	{

	}

	//updates colors, changes dynamically


	//changes the platform formation depending on the stage



	public void setImage(String filepath)
	{
		try
		{
			first = new Image(filepath);
			second = new Image(filepath);
			third = new Image(filepath);
			go = new Image(filepath);
			p1 = new Image(filepath);
			p2 = new Image(filepath);
		}
		catch(SlickException e)		
		{
			System.out.println("Image not found!");
		}
	}




	// Returns the ID code for this game state
	public int getID() 
	{
		return 3;
	}


}
