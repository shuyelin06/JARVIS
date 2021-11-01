package gamestates;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import core.Engine;

public class StartingMenu extends BasicGameState 
{
	int id;
	
	//ready to start boolean
	private boolean readyStart;
	
	//image background variables
	private Image background;
	private Image mainButton;
	private int mainButtonX, mainButtonY, mainButtonW, mainButtonH;
	private int backgroundX, backgroundY, backgroundW, backgroundH;
	
	public StartingMenu(int id) 
	{
		this.id = id;
	}

	// Initializer, first time
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException 
	{
		//image settings
		setImage("res/placeholder.png");
		mainButtonX = gc.getWidth()/2;
		mainButtonY = gc.getHeight()/3;
		mainButtonW = 300;
		mainButtonH = 100;
		//background is whole screen
		backgroundX = gc.getWidth()/2;
		backgroundY = gc.getHeight()/2;
		backgroundW = gc.getWidth();
		backgroundH = gc.getHeight();
	}
	
	//render, all visuals
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException 
	{
		//temporary string graphics, will be replaced
		drawImages(g);
		g.drawString("Press Q to start game", gc.getWidth() / 2, gc.getHeight() / 2);
		
	}

	//update, runs consistently
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException
	{	
		//starts world selection if start button pressed or ready
		if (readyStart) {
			sbg.enterState(Engine.WorldSelect_ID);
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
	}
	
	public void drawImages(Graphics g) {
		//image drawing
		
		setImage("res/placeholder.png");
		background.setFilter(Image.FILTER_NEAREST);
		background.draw(backgroundX - (backgroundW / 2), backgroundY - (backgroundH / 2), backgroundW, backgroundH);
		setImage("res/startButton.png");
		mainButton.setFilter(Image.FILTER_NEAREST);
		mainButton.draw(mainButtonX - (mainButtonW / 2), mainButtonY - (mainButtonH / 2), mainButtonW, mainButtonH);
		
	}
	
	public void setImage(String filepath)
	{
		try
		{
			background = new Image(filepath);
			mainButton = new Image(filepath);
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
