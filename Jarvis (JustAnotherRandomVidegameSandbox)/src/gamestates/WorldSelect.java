package gamestates;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import core.Engine;
import settings.Values;
import structures.Particle;
import world.Background;
import world.FileLoader;
import world.WorldGen;

public class WorldSelect extends BasicGameState 
{
	//Gamestate ID
	int id;
	
	private boolean createNewWorld = false;
	
	//World selected ID
	private int worldID, worldIDMax, worldIDMin;
	
	//ready to start boolean
	private boolean readyStart;
	private boolean readySettings;
	
	//firework code
	public static ArrayList<Particle> particles = new ArrayList<Particle>();
	public static int arraySize = 50;
	public static int fireworkType = 0;
	
	//background
	private Background bg;
	//image variables
	private Image mainButton;
	private Image w1Button;
	private Image w2Button;
	private Image worldImage;
	private Image s1Button;
	private Image newWorldButton;
	private int mainButtonX, mainButtonY, mainButtonW, mainButtonH;
	private int w1ButtonX, w1ButtonY, w1ButtonW, w1ButtonH;
	private int w2ButtonX, w2ButtonY, w2ButtonW, w2ButtonH;
	private int worldImageX, worldImageY, worldImageW, worldImageH;
	private int s1ButtonX, s1ButtonY, s1ButtonW, s1ButtonH;
	private int newWorldButtonX, newWorldButtonY, newWorldButtonW, newWorldButtonH;
	
	public WorldSelect(int id) 
	{
		this.id = id;
	}

	// Initializer, first time
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException 
	{
		//worldID settings
		worldID = 1;
		worldIDMax = 3;
		worldIDMin = 1;
		
		//background
		bg = new Background();
		
		//image settings
		setImage("res/placeholder.png");
		mainButtonX = gc.getWidth()/2;
		mainButtonY = gc.getHeight()/3;
		mainButtonW = 300;
		mainButtonH = 100;
		w1ButtonX = 2*gc.getWidth()/3;
		w1ButtonY = 2*gc.getHeight()/3;
		w1ButtonW = 100;
		w1ButtonH = 100;
		w2ButtonX = gc.getWidth()/3;
		w2ButtonY = 2*gc.getHeight()/3;
		w2ButtonW = 100;
		w2ButtonH = 100;
		worldImageX = gc.getWidth()/2;
		worldImageY = 2*gc.getHeight()/3;
		worldImageW = 100;
		worldImageH = 100;
		s1ButtonX = gc.getWidth()/5;
		s1ButtonY = gc.getHeight()/5;
		s1ButtonW = 100;
		s1ButtonH = 100;
		newWorldButtonX = 4*gc.getWidth()/5;
		newWorldButtonY = gc.getHeight()/5;
		newWorldButtonW = 100;
		newWorldButtonH = 100;
		
		readyStart = false;
		readySettings = false;
		
	}
	
	//render, all visuals
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException 
	{
		// Render the list of worlds
		String[] worldList = FileLoader.getWorldList();
		g.setBackground(new Color(100, 100, 100));
		
		
		
		bg.render(g, 0, 0);
		
		//temporary string graphics, will be replaced

//		g.drawString("Press a number to change world", gc.getWidth() / 2, (gc.getHeight() / 2) - 20);
//		g.drawString("Press Q to enter world", gc.getWidth() / 2, gc.getHeight() / 2);
//		g.drawString("World: " + worldID, gc.getWidth() / 2, (gc.getHeight() / 2) + 20);

		//draws all buttons and world number image
		drawImages(g);
		
		//draws fireworks
		for (int i = 0; i < particles.size(); i++) {
			particles.get(i).render(g);
		}
	}

	//update, runs consistently
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException
	{	
		if (readyStart) {
			if (worldID == 1) {
				Engine.game.getWorld().changeName("1");
			} else if (worldID == 2) {
				Engine.game.getWorld().changeName("2");
			} else if (worldID == 3) {
				Engine.game.getWorld().changeName("3");
			}
			
			// Check if we want to create a new world or not
			if(createNewWorld) {
				WorldGen gen = new WorldGen(Engine.game.getWorld().getWorldName(), (int) (Math.random() * 10000));
				gen.generateWorld();
			}
			
			// Enter Game gamestate
			Values.LastState = Engine.WorldSelect_ID;
			sbg.enterState(Engine.Game_ID);
			Engine.sound.startMusic();
		}
		
		if (readySettings) {
			Values.LastState = Engine.WorldSelect_ID;
			sbg.enterState(Engine.Settings_ID);
			//settings gamestate
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
		
		//check main button
		if ((x > mainButtonX - (mainButtonW / 2))
				&& (x < mainButtonX + (mainButtonW / 2))
				&& (y > mainButtonY - (mainButtonH / 2))
				&& (y < mainButtonY + (mainButtonH / 2))
				) {
			readyStart = true;
			return;
		}
		
		//settings
		if ((x > s1ButtonX - (s1ButtonW / 2))
				&& (x < s1ButtonX + (s1ButtonW / 2))
				&& (y > s1ButtonY - (s1ButtonH / 2))
				&& (y < s1ButtonY + (s1ButtonH / 2))
				) {
			readySettings = true;
			return;
		}
		
		//toggle new world
		if ((x > newWorldButtonX - (newWorldButtonW / 2))
				&& (x < newWorldButtonX + (newWorldButtonW / 2))
				&& (y > newWorldButtonY - (newWorldButtonH / 2))
				&& (y < newWorldButtonY + (newWorldButtonH / 2))
				) {
			createNewWorld = !createNewWorld;
			return;
		}
		
		//change world ID when clicking on buttons
		if ((x > w1ButtonX - (w1ButtonW / 2))
				&& (x < w1ButtonX + (w1ButtonW / 2))
				&& (y > w1ButtonY - (w1ButtonH / 2))
				&& (y < w1ButtonY + (w1ButtonH / 2))
				) {
			worldID++;
			//max world ID
			if (worldID > worldIDMax) {
				worldID = worldIDMin;
			}
		}
		if ((x > w2ButtonX - (w2ButtonW / 2))
				&& (x < w2ButtonX + (w2ButtonW / 2))
				&& (y > w2ButtonY - (w2ButtonH / 2))
				&& (y < w2ButtonY + (w2ButtonH / 2))
				) {
			worldID--;
			//min world ID
			if (worldID < worldIDMin) {
				worldID = worldIDMax;
			}
		}
		
		//check for type of firework
		if(button == 0) {
			for (int i = 0; i < arraySize; i++) {
				fireworkType = 0;
				particles.add(new Particle(x, y, fireworkType));
			}
		}
		if(button == 1) {
			for (int i = 0; i < arraySize; i++) {
				fireworkType = 1;
				particles.add(new Particle(x, y, fireworkType));
			}
		}
		if(button == 2) {
			for (int i = 0; i < arraySize; i++) {
				fireworkType = 2;
				particles.add(new Particle(x, y, fireworkType));
			}
		}
		
		
		
	}
	
	public void drawImages(Graphics g) {
		//image drawing
		
		setImage("res/startButton.png");
		mainButton.setFilter(Image.FILTER_NEAREST);
		mainButton.draw(mainButtonX - (mainButtonW / 2), mainButtonY - (mainButtonH / 2), mainButtonW, mainButtonH);
		
		setImage("res/placeholder.png");
		w1Button.draw(w1ButtonX - (w1ButtonW / 2), w1ButtonY - (w1ButtonH / 2), w1ButtonW, w1ButtonH);
		w2Button.draw(w2ButtonX - (w2ButtonW / 2), w2ButtonY - (w2ButtonH / 2), w2ButtonW, w2ButtonH);
		
		setImage("res/placeholder.png");
		s1Button.draw(s1ButtonX - (s1ButtonW / 2), s1ButtonY - (s1ButtonH / 2), s1ButtonW, s1ButtonH);
		newWorldButton.draw(newWorldButtonX - (newWorldButtonW / 2), newWorldButtonY - (newWorldButtonH / 2), newWorldButtonW, newWorldButtonH);
		
		//draws based on world number
		if (worldID == 1) {
			setImage("res/1.png");
		} else if (worldID == 2) {
			setImage("res/2.png");
		} else if (worldID == 3) {
			setImage("res/3.png");
		} else if (worldID == 4) {
			setImage("res/4.png");
		} else if (worldID == 5) {
			setImage("res/5.png");
		}
		worldImage.draw(worldImageX - (worldImageW / 2), worldImageY - (worldImageH / 2), worldImageW, worldImageH);
			
	}
	
	public void setImage(String filepath)
	{
		try
		{
			mainButton = new Image(filepath);
			w1Button = new Image(filepath);
			w2Button = new Image(filepath);
			worldImage = new Image(filepath);
			s1Button = new Image(filepath);
			newWorldButton = new Image(filepath);
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
