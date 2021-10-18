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
	private static final float SpawnY = Chunk.Chunk_Size_Y / 2.5f;
	private final World world;
	
	private static final int Player_Size = 20;
	// Player constructor
	public Player(World world) {
		super(SpawnX, SpawnY); 
		this.world = world;
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
			Chunk c = world.getChunk((int) this.getPosition().getChunk());
			Block[][] blocks = c.getBlocks();
			float chunk = this.getPosition().getChunk();
			//trying to check all blocks in chunk not working
			
			
			for (int i = 0; i < Chunk.Chunk_Size_X; i++) {
				for (int j = 0; j < Chunk.Chunk_Size_Y; j++) {
					if (collidesWithX(blocks[i][j], i, j)) {
						xSpeed = 0;
					}
					if (collidesWithY(blocks[i][j], i, j)) {
						ySpeed = 0;
					}
				}
			}
		}
		
		//collision between player horizontally
		public boolean collidesWithX(Block other, int i, int j)
		{			
			int chunkX = (int) this.getPosition().getChunk();
			return  collidesWithX(other.getBlockX(chunkX, i),     	 		  j          	 ) || 
					collidesWithX(other.getBlockX(chunkX, i) + Coordinate.ConversionFactor, j     		     ) ||
					collidesWithX(other.getBlockX(chunkX, i),                j + Coordinate.ConversionFactor) ||
					collidesWithX(other.getBlockX(chunkX, i) + Coordinate.ConversionFactor, j + Coordinate.ConversionFactor) ||
					collidesWithX(other.getBlockX(chunkX, i) + Coordinate.ConversionFactor/2, j + Coordinate.ConversionFactor/2);
		}
		//collision between player vertically
		public boolean collidesWithY(Block other, int i, int j)
		{			
			int chunkX = (int) this.getPosition().getChunk();
			return  collidesWithY(other.getBlockX(chunkX, i),     	 		  j          	 ) || 
					collidesWithY(other.getBlockX(chunkX, i) + Coordinate.ConversionFactor, j     		     ) ||
					collidesWithY(other.getBlockX(chunkX, i),                j + Coordinate.ConversionFactor) ||
					collidesWithY(other.getBlockX(chunkX, i) + Coordinate.ConversionFactor, j + Coordinate.ConversionFactor) ||
					collidesWithY(other.getBlockX(chunkX, i) + Coordinate.ConversionFactor/2, j + Coordinate.ConversionFactor/2);
		}

		//collision mechanism for Y
		public boolean collidesWithX(float x, float y)
		{
			return 	x >= this.x && 
					x <= this.x + this.w;
//					&& 
//					y >= this.y && 
//					y <= this.y + this.h;
		}
		//collision mechanism for Y
		public boolean collidesWithY(float x, float y)
		{
			return 	
//					x >= this.x && 
//					x <= this.x + this.w && 
					y >= this.y && 
					y <= this.y + this.h;
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
		this.xSpeed = 20f;	
	}
	
	public void moveLeft() {
		this.xSpeed = -20f;
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