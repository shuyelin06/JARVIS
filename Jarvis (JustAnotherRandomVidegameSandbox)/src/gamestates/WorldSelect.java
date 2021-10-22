package gamestates;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import core.Engine;
import entities.Entity;
import structures.Particle;
import world.FileLoader;

public class WorldSelect extends BasicGameState 
{
	//Gamestate ID
	int id;
	
	//World selected ID
	private int worldID;
	
	//ready to start boolean
	private boolean readyStart;
	
	
	//firework code
	public static ArrayList<Particle> particles = new ArrayList<Particle>();
	public static int arraySize = 50;
	public static int fireworkType = 0;
	public static int xLocation, yLocation;
	public static int backgroundColor;
	
	private Image startButton;
	private int mainButtonX, mainButtonY, mainButtonW, mainButtonH;
	
	
	
	
	public WorldSelect(int id) 
	{
		this.id = id;
	}

	// Initializer, first time
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException 
	{
		gc.setShowFPS(true); // Shows the FPS of the game
		worldID = 1;
		
		//image settings
		setImage("res/placeholder.png");
		mainButtonX = gc.getWidth()/2;
		mainButtonY = gc.getHeight()/2;
		mainButtonW = 100;
		mainButtonH = 100;
		
		
		//set center
		xLocation = gc.getWidth()/2;
		yLocation = gc.getHeight()/2;
		//randomize gray
		backgroundColor = (int)(Math.random()*50);
	}
	
	//render, all visuals
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException 
	{
		// Render the list of worlds
		String[] worldList = FileLoader.getWorldList();
		g.setBackground(new Color(100, 100, 100));
		
		
		
		
		//temporary string graphics, will be replaced

		g.drawString("Press a number to change world", gc.getWidth() / 2, (gc.getHeight() / 2) - 20);
		g.drawString("Press Q to enter world", gc.getWidth() / 2, gc.getHeight() / 2);
		
		g.drawString("World: " + worldID, gc.getWidth() / 2, (gc.getHeight() / 2) + 20);

		
		
		//image drawing
//		setImage("res/startButton.png");
		startButton.setFilter(Image.FILTER_NEAREST);
		startButton.draw(mainButtonX - (mainButtonW / 2), mainButtonY - (mainButtonH / 2), mainButtonW, mainButtonH);
		
		//draws fireworks
		for (int i = 0; i < particles.size(); i++) {
			particles.get(i).render(g);
		}
		g.setBackground(new Color(backgroundColor, backgroundColor, backgroundColor));
	}

	//update, runs consistently
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException
	{	
		if (readyStart) {
			if (worldID == 1) {
				Game.world.changeName("1");
			} else if (worldID == 2) {
				Game.world.changeName("2");
			} else if (worldID == 3) {
				Game.world.changeName("3");
			}
			sbg.enterState(Engine.Game_ID);
		}
		
		for (int i = 0; i < particles.size(); i++) {
			particles.get(i).update(gc);
		}
		
	}

	public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException 
	{
		
	}

	public void leave(GameContainer gc, StateBasedGame sbg) 
	{
		
	}


	public void keyPressed(int key, char c)
	{
		
		if (key == Input.KEY_1) {
			worldID = 1;
		} else if (key == Input.KEY_2) {
			worldID = 2;
		} else if (key == Input.KEY_3) {
			worldID = 3;
		}
		
		if (key == Input.KEY_Q) {
			readyStart = true;
		}
		
	}
	
	public void mousePressed(int button, int x, int y)
	{
		xLocation = x;
		yLocation = y;
		
		//check main button
		if ((x > mainButtonX - (mainButtonW / 2))
				&& (x < mainButtonX + (mainButtonW / 2))
				&& (y > mainButtonY - (mainButtonH / 2))
				&& (y < mainButtonY + (mainButtonH / 2))
				) {
			readyStart = true;
			return;
		}
		
		
		//check for type of firework
		if(button == 0) {
			System.out.println("left click");
			for (int i = 0; i < arraySize; i++) {
				fireworkType = 0;
				particles.add(new Particle(x, y));
			}
		}
		if(button == 1) {
			System.out.println("button 1");
			for (int i = 0; i < arraySize; i++) {
				fireworkType = 1;
				particles.add(new Particle(x, y));
			}
		}
		if(button == 2) {
			System.out.println("middle click");
			for (int i = 0; i < arraySize; i++) {
				fireworkType = 2;
				particles.add(new Particle(x, y));
			}
		}
		
		
		
	}
	
	
	public void setImage(String filepath)
	{
		try
		{
			startButton = new Image(filepath);
		}
		catch(SlickException e)		
		{
			System.out.println("Image not found!");
		}
	}
	
	// Returns the ID code for this game state
	public int getID() 
	{
		return id;
	}


}
