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

import background.Background;
import core.Engine;
import core.Values;
import structures.Particle;
import world.WorldGen;

public class Settings extends BasicGameState {

	//Gamestate ID
	int id;
	
	//ready to start boolean
	private boolean readyStart;
	
	//firework code
	public static ArrayList<Particle> particles = new ArrayList<Particle>();
	public static int arraySize = 50;
	public static int fireworkType = 0;
	
	//background
	private Background bg;
	//image variables
	private Image mainButton;
	private Image m1Button;
	private Image m2Button;
	private Image volumeImage;
	private int mainButtonX, mainButtonY, mainButtonW, mainButtonH;
	private int m1ButtonX, m1ButtonY, m1ButtonW, m1ButtonH;
	private int m2ButtonX, m2ButtonY, m2ButtonW, m2ButtonH;
	private int volumeImageX, volumeImageY, volumeImageW, volumeImageH;
	
	public Settings(int id) 
	{
		this.id = id;
	}

	// Initializer, first time
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException 
	{
		//background
		bg = new Background();
		
		//image settings
		setImage("placeholder");
		mainButtonX = gc.getWidth()/2;
		mainButtonY = gc.getHeight()/3;
		mainButtonW = 300;
		mainButtonH = 100;
		m1ButtonX = 2*gc.getWidth()/3;
		m1ButtonY = 2*gc.getHeight()/3;
		m1ButtonW = 100;
		m1ButtonH = 100;
		m2ButtonX = gc.getWidth()/3;
		m2ButtonY = 2*gc.getHeight()/3;
		m2ButtonW = 100;
		m2ButtonH = 100;
		volumeImageX = gc.getWidth()/2;
		volumeImageY = 2*gc.getHeight()/3;
		volumeImageW = 100;
		volumeImageH = 100;
		
		readyStart = false;
	}
	
	//render, all visuals
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException 
	{
		//background
		bg.render(g, 0, 0);
		
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
			
			//enter last state
			Values.LastState = Engine.Settings_ID;
			sbg.enterState(Values.LastState);
			
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
		
		//change world ID when clicking on buttons
		if ((x > m1ButtonX - (m1ButtonW / 2))
				&& (x < m1ButtonX + (m1ButtonW / 2))
				&& (y > m1ButtonY - (m1ButtonH / 2))
				&& (y < m1ButtonY + (m1ButtonH / 2))
				) {
			//increase volume
			
		}
		if ((x > m2ButtonX - (m2ButtonW / 2))
				&& (x < m2ButtonX + (m2ButtonW / 2))
				&& (y > m2ButtonY - (m2ButtonH / 2))
				&& (y < m2ButtonY + (m2ButtonH / 2))
				) {
			//decrease volume
			
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
		Values.Images.get("startButton").setFilter(Image.FILTER_NEAREST);
		Values.Images.get("startButton").draw(mainButtonX - (mainButtonW / 2), mainButtonY - (mainButtonH / 2), mainButtonW, mainButtonH);
		
		setImage("placeholder");
		Values.Images.get("placeholder").draw(m1ButtonX - (m1ButtonW / 2), m1ButtonY - (m1ButtonH / 2), m1ButtonW, m1ButtonH);
		Values.Images.get("placeholder").draw(m2ButtonX - (m2ButtonW / 2), m2ButtonY - (m2ButtonH / 2), m2ButtonW, m2ButtonH);
		
		
		volumeImage.draw(volumeImageX - (volumeImageW / 2), volumeImageY - (volumeImageH / 2), volumeImageW, volumeImageH);
			
	}
	
	public void setImage(String file)
	{
		try
		{
			mainButton = Values.Images.get(file);
			m1Button = Values.Images.get(file);
			m2Button = Values.Images.get(file);
			volumeImage = Values.Images.get(file);
		}
		catch(Exception e)		
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
