package gamestates;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import core.Engine;
import world.WorldGen;

public class Loading extends BasicGameState
{
	// Loading screen 
	private int next; // If loading succeeds, move to next screen
	
	Command command;
	
	private int totalTasks;
	private int tasksDone;
	
	private StateBasedGame sbg;
	int id;
	
	public enum Command {
		GenerateWorld
	}
	
	public Loading(int id) 
	{
		this.id = id;

		this.totalTasks = 0;
		this.tasksDone = 0;
	}

	// Returns the ID code for this game state
	public int getID() 
	{
		return id;
	}
	
	public void finishedTask() { this.tasksDone++; }
	public void load(Command command, int previous, int next, int totalTasks) {
		// Initialize the variables in the loading screen
		this.command = command;
		this.next = next;
		
		this.tasksDone = 0;
		this.totalTasks = totalTasks;
		
		sbg.enterState(this.id);
	}
	
	// Initializer, first time
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException { this.sbg = sbg; }
	
	// Rendering the loading bar
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException { drawLoadingBars(gc, g); }
	public void drawLoadingBars(GameContainer gc, Graphics g) {
		//image settings
		// 100 width: (int) (0.05208333333*gc.getWidth());
		// 100 height: (int) (0.09259259259*gc.getHeight());
		final float BAR_WIDTH = gc.getWidth() - (int) (0.05208333333*gc.getWidth());
		// Height 300
		final float BAR_HEIGHT = 3* (int) (0.09259259259*gc.getHeight());
		
		final float BAR_X = gc.getWidth() / 2;
		final float BAR_Y = gc.getHeight() / 2;
		final float PERCENT_LOADED = tasksDone / (float) totalTasks;
		
		// max loading bar
		g.setColor(new Color(0, 100, 0, 150));
		g.fillRect(BAR_X - (BAR_WIDTH / 2), BAR_Y - (BAR_HEIGHT / 2), BAR_WIDTH, BAR_HEIGHT);
		
		// current loaded
		g.setColor(new Color(0, 255, 0, 150));
		g.fillRect(BAR_X - (BAR_WIDTH / 2), BAR_Y - (BAR_HEIGHT / 2), BAR_WIDTH * PERCENT_LOADED, BAR_HEIGHT);
		
		// white outline
		g.setColor(new Color(255, 255, 255));
		g.drawRect(BAR_X - (BAR_WIDTH / 2), BAR_Y - (BAR_HEIGHT / 2), BAR_WIDTH, BAR_HEIGHT);
	}
	
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException { 
		if(tasksDone > totalTasks - 1) {
			sbg.enterState(Engine.Game_ID);
//			Engine.sound.playBackgroundMusic("Morning"); // Begin game background music
//			
//			sbg.enterState(Engine.Game_ID);
		}
	}

	public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException 
	{
		switch(command) {
			case GenerateWorld: // World Generation
				WorldGen gen = new WorldGen(Engine.game.getWorld().getWorldName(), (int) (Math.random() * 10000));
				gen.start();
				break;
		}
		
	}

	public void leave(GameContainer gc, StateBasedGame sbg)  {}
	public void keyPressed(int key, char c) {}
	public void mousePressed(int button, int x, int y) {}
}
