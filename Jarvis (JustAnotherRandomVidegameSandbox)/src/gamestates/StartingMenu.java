package gamestates;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import core.Engine;
import structures.Particle;
import world.Background;

public class StartingMenu extends BasicGameState 
{
	int id;
	
	//ready to start boolean
	private boolean readyStart;
	
	//image background variables
	private Background bg;
	private Image mainButton;
	private int mainButtonX, mainButtonY, mainButtonW, mainButtonH;
	
	
	public StartingMenu(int id) 
	{
		this.id = id;
	}

	// Initializer, first time
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException 
	{		
		bg = new Background();
		//image settings
		setImage("res/placeholder.png");
		mainButtonX = gc.getWidth()/2;
		mainButtonY = gc.getHeight()/3;
		mainButtonW = 300;
		mainButtonH = 100;
	}
	
	//render, all visuals
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException 
	{
		//temporary string graphics, will be replaced
		bg.render(g, 0, 0);
		drawImages(g);
		
		//draws fireworks
		for (int i = 0; i < WorldSelect.particles.size(); i++) {
			WorldSelect.particles.get(i).render(g);
		}
		
	}

	//update, runs consistently
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException
	{	
		//starts world selection if start button pressed or ready
		if (readyStart) {
			sbg.enterState(Engine.WorldSelect_ID);
		}
		
		//fireworks
		for (int i = 0; i < WorldSelect.particles.size(); i++) {
			WorldSelect.particles.get(i).update(gc);
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
		
		//check for type of firework
		if(button == 0) {
			for (int i = 0; i < WorldSelect.arraySize; i++) {
				WorldSelect.fireworkType = 0;
				WorldSelect.particles.add(new Particle(x, y, WorldSelect.fireworkType));
			}
		}
		if(button == 1) {
			for (int i = 0; i < WorldSelect.arraySize; i++) {
				WorldSelect.fireworkType = 1;
				WorldSelect.particles.add(new Particle(x, y, WorldSelect.fireworkType));
			}
		}
		if(button == 2) {
			for (int i = 0; i < WorldSelect.arraySize; i++) {
				WorldSelect.fireworkType = 3;
				WorldSelect.particles.add(new Particle(x, y, WorldSelect.fireworkType));
			}
		}
	}
	
	public void drawImages(Graphics g) {
		//image drawing
		
		setImage("res/startButton.png");
		mainButton.setFilter(Image.FILTER_NEAREST);
		mainButton.draw(mainButtonX - (mainButtonW / 2), mainButtonY - (mainButtonH / 2), mainButtonW, mainButtonH);
		
	}
	
	public void setImage(String filepath)
	{
		try
		{
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
