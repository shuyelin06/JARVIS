package gamestates;

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
import managers.FileManager;
import managers.ImageManager;
import structures.Particle;

public class StartingMenu extends BasicGameState 
{
	int id;
	
	private int time;
	//ready to start boolean
	private boolean readyStart;
	
	//image background variables
	public static Background bg;
	private Image mainButton;
	private int mainButtonX, mainButtonY, mainButtonW, mainButtonH;
	
	
	public StartingMenu(int id) 
	{
		this.id = id;
	}

	// Initializer, first time
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException 
	{		
		FileManager.LoadResFiles();

		time = 0;
		bg = new Background();
		//image settings
		setImage("placeholder");
		mainButtonX = gc.getWidth()/2;
		mainButtonY = gc.getHeight()/3;
		
		//image settings
		
		// 100 width: (int) (0.05208333333*gc.getWidth());
		// 100 height: (int) (0.09259259259*gc.getHeight());
		mainButtonW = 3* (int) (0.05208333333*gc.getWidth());
		mainButtonH = (int) (0.09259259259*gc.getHeight());
	}
	
	//render, all visuals
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException 
	{
		//temporary string graphics, will be replaced
		bg.render(g, 0, 0);
		drawImages(g);
		
		time++;
		
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
			Values.LastState = Engine.StartingMenu_ID;
			sbg.enterState(Engine.WorldSelect_ID);
		}
		
		//fireworks
		for (int i = 0; i < WorldSelect.particles.size(); i++) {
			WorldSelect.particles.get(i).update(gc);
		}
		
		bg.update();
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
		
		setImage("startButton");
		mainButton.setFilter(Image.FILTER_NEAREST);
		mainButton.draw(mainButtonX - (mainButtonW / 2), mainButtonY - (mainButtonH / 2), mainButtonW, mainButtonH);
		
	}
	
	public void setImage(String file)
	{
		try
		{
			mainButton = ImageManager.getImage(file);
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
