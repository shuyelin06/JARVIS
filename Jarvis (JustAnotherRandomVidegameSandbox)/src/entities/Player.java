package entities;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;

import core.Coordinate;
import core.Engine;
import world.Chunk;
import world.World;

public class Player extends Entity{	
	// Spawnpoint of the player
	private static final float SpawnX = World.World_X_Size * Chunk.Chunk_Size_X / 2;
	private static final float SpawnY = Chunk.Chunk_Size_Y / 2.5f;
	
	private static final int Player_Size = 20;
	// Player constructor
	public Player() {
		super(SpawnX, SpawnY); 
	}
	
	public int getSize() {
		return Player_Size;
	}
	
	
	public void update() {
		super.update();
	}

	//draws characters
	public void render(Graphics g) {
		super.render(g);
	}
	
	
	
	
	
	// Key Press Mappings
	public void moveRight() {
		this.xSpeed = 10f;	
	}
	
	public void moveLeft() {
		this.xSpeed = -10f;
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