package entities;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;

import core.Coordinate;
import core.Engine;
import gamestates.Game;
import structures.Block;
import world.Chunk;
import world.World;

public class Player extends Entity{	
	// Spawnpoint of the player
	private static final float SpawnX = World.World_X_Size * Chunk.Chunk_Size_X / 2;
	private static final float SpawnY = Chunk.Chunk_Size_Y / 2.5f + 20f;
	
	private static final int Player_Size = 20;
	// Player constructor
	public Player(World world) {
		super(SpawnX, SpawnY, world); 
		
		this.sizeY = 2f;
		this.sizeX = 1f;
	}
	
	public int getSize() {
		return Player_Size;
	}
	
	
	public void update() {
		super.update();
		checkCollisions();
	}

	//draws characters
	public void render(Graphics g) {
		super.render(g);
		drawHealthBars(g);
	}
	
	
	//checking all blocks in the nearest two chunks
	public void checkCollisions() {
		float chunk = this.getPosition().getChunk();
		//trying to check all blocks in chunk not working
		
		
		for (int i = 0; i < Chunk.Chunk_Size_X; i++) {
			for (int j = 0; j < Chunk.Chunk_Size_Y; j++) {
				
			}
		}
	}
	
	
	//health bars
	public void drawHealthBars(Graphics g) {
		final float BAR_WIDTH = ((Game.gc.getWidth()/2) - 300);
		final float BAR_HEIGHT = 30;
		g.setColor(new Color(0, 100, 0, 150));
		g.fillRect(Game.gc.getWidth() - 100, 40, -BAR_WIDTH, BAR_HEIGHT);
		g.setColor(new Color(0, 255, 0, 150));
		g.fillRect(Game.gc.getWidth() - 100, 40, -BAR_WIDTH*percentageHealth, BAR_HEIGHT);
		g.setColor(new Color(255, 255, 255));
		g.drawRect(Game.gc.getWidth() - 100, 40, -BAR_WIDTH, BAR_HEIGHT);
	}
	
	// Key Press Mappings
	public void moveRight() {
		this.xSpeed = 15f;	
	}
	
	public void moveLeft() {
		this.xSpeed = -15f;
	}
	
	public void jump() {
		if(jumpsLeft > 0) {
			this.onPlatform = false;
			this.ySpeed = 20f;
			
			jumpsLeft--;
		}
	}
	public void fall() {
		this.onPlatform = false;
		
		this.ySpeed -= 5f;
	}
	
	
	
	
	
}