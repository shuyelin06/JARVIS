package entities.living;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import core.Engine;
import entities.Entity;
import entities.other.EItem;
import items.Inventory;
import settings.Values;

public class Player extends Living{	
	private Inventory inventory;
	
	// Player constructor
	public Player() 
	{
		super(Values.SpawnX, Values.SpawnY); 
		
		try {
			sprite = new Image("res/pratt.png");
		} catch(Exception e) {}
		
		this.inventory = new Inventory();
		
		this.sizeY = 2f;
		this.sizeX = 1.3f; // Only use sizes to the 10th PLACE 
		curHealth = 10;
		maxHealth = 10;
		healthRegen = true;
	}

	//render
	public void render(Graphics g, float x, float y) {
		super.render(g, x, y);
		drawHealthBars(g);
	}
	
	//health bars
	public void drawHealthBars(Graphics g) {
		final float BAR_WIDTH = (float) ((Engine.game.getGC().getWidth()/2) - (0.15625 * Engine.game.getGC().getWidth()));
		final float BAR_HEIGHT = 30;
		g.setColor(new Color(0, 100, 0, 150));
		g.fillRect((float) (Engine.game.getGC().getWidth() - (0.05208333333 * Engine.game.getGC().getWidth())), (float) (0.03703703703 * Engine.game.getGC().getHeight()), -BAR_WIDTH, BAR_HEIGHT);
		g.setColor(new Color(0, 255, 0, 150));
		g.fillRect((float) (Engine.game.getGC().getWidth() - (0.05208333333 * Engine.game.getGC().getWidth())), (float) (0.03703703703 * Engine.game.getGC().getHeight()), -BAR_WIDTH*percentageHealth, BAR_HEIGHT);
		g.setColor(new Color(255, 255, 255));
		g.drawRect((float) (Engine.game.getGC().getWidth() - (0.05208333333 * Engine.game.getGC().getWidth())), (float) (0.03703703703 * Engine.game.getGC().getHeight()), -BAR_WIDTH, BAR_HEIGHT);
	}
	
	public void drawInventory(Graphics g) {
		
	}
	// Key Press Mappings
	public void moveRight() {
		if(!this.alive) return; // We will find a better way to do this check later
		
		if(xSpeed + 5f > Values.Terminal_X_Velocity) xSpeed = Values.Terminal_X_Velocity;
		else xSpeed += 5f;	
	}
	
	public void moveLeft() {
		if(!this.alive) return; // We will find a better way to do this check later
		
		if(xSpeed - 5f < 0 - Values.Terminal_X_Velocity) xSpeed = 0 - Values.Terminal_X_Velocity;
		else xSpeed -= 5f;
	}
	
	public void jump() {
		if(!this.alive) return;
		
		if(jumpsLeft > 0) {
			this.onPlatform = false;
			this.ySpeed = 20f;
			
			jumpsLeft--;
		}
	}
	public void fall() {
		if(!this.alive) return;
		
		this.ySpeed -= Entity.gravity;
	}
	
	// Overwritten Collisions Method
	public void collisions() {
		ArrayList<Entity> items = Engine.game.getEntities(EntType.Items);
		
		for(Entity e: items) {
			EItem item = (EItem) e;
			
			if(inventory.hasSpace(item.getID()) && this.entityCollision(e)) {
				e.markForRemoval();
				
				inventory.pickUp(item);
			}
		}
		
		super.collisions();
	}
	
	
	
	
}