package entities;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;

import core.Coordinate;
import core.Engine;
import gamestates.Game;
import settings.Values;
import structures.Block;
import world.Chunk;
import world.World;

public class Player extends Entity{	
	// Player constructor
	public Player() throws SlickException 
	{
		super(Values.SpawnX, Values.SpawnY); 
		
		this.sizeY = 2f;
		this.sizeX = 1.3f; // Only use sizes to the 10th PLACE 
		curHealth = 10;
		maxHealth = 10;
	}
	
	//draws characters
	public void render(Graphics g, float x, float y) 
	{
		super.render(g, x, y);
		
		drawHealthBars(g);
	}
	
	//health bars
	public void drawHealthBars(Graphics g) {
		final float BAR_WIDTH = ((Engine.game.getGC().getWidth()/2) - 300);
		final float BAR_HEIGHT = 30;
		g.setColor(new Color(0, 100, 0, 150));
		g.fillRect(Engine.game.getGC().getWidth() - 100, 40, -BAR_WIDTH, BAR_HEIGHT);
		g.setColor(new Color(0, 255, 0, 150));
		g.fillRect(Engine.game.getGC().getWidth() - 100, 40, -BAR_WIDTH*percentageHealth, BAR_HEIGHT);
		g.setColor(new Color(255, 255, 255));
		g.drawRect(Engine.game.getGC().getWidth() - 100, 40, -BAR_WIDTH, BAR_HEIGHT);
	}
	
	// Key Press Mappings
	public void moveRight() {
		if(xSpeed + 5f > Values.Terminal_X_Velocity) xSpeed = Values.Terminal_X_Velocity;
		else xSpeed += 5f;	
	}
	
	public void moveLeft() {
		if(xSpeed - 5f < 0 - Values.Terminal_X_Velocity) xSpeed = 0 - Values.Terminal_X_Velocity;
		else xSpeed -= 5f;
	}
	
	public void jump() {
		if(jumpsLeft > 0) {
			this.onPlatform = false;
			this.ySpeed = 20f;
			
			jumpsLeft--;
		}
	}
	public void fall() {
		this.ySpeed -= Entity.gravity;
	}
	
	
	
	
}