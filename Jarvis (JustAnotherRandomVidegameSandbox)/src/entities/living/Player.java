package entities.living;

import java.util.ArrayList;
import java.util.Set;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import core.Coordinate;
import core.Engine;
import entities.Entity;
import entities.other.EItem;
import items.Inventory;
import settings.Values;

public class Player extends Living{	
	private Inventory inventory;
	
	// Player constructor
	private int inventorySelected;
	
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
	
	public int selectedItem() { return inventory.getItems()[inventorySelected][0]; }
	public Inventory getInventory() { return inventory; }

	//render
	public void render(Graphics g, float x, float y) {
		super.render(g, x, y);
		drawHealthBars(g);
		drawInventory(g);
	}
	
	//health bars
	public void drawHealthBars(Graphics g) {
		// Defining bar variables
		final float BAR_WIDTH = (float) ((Engine.game.getGC().getWidth()/2) - (0.15625 * Engine.game.getGC().getWidth()));
		final float BAR_HEIGHT = (float) (30f / 1080f) * Engine.game.getGC().getHeight();
		
		// Player max health bar
		g.setColor(new Color(0, 100, 0, 150));
		g.fillRect((float) (Engine.game.getGC().getWidth() - (0.05208333333 * Engine.game.getGC().getWidth())), (float) (0.03703703703 * Engine.game.getGC().getHeight()), -BAR_WIDTH, BAR_HEIGHT);
		
		// Player health bar
		g.setColor(new Color(0, 255, 0, 150));
		g.fillRect((float) (Engine.game.getGC().getWidth() - (0.05208333333 * Engine.game.getGC().getWidth())), (float) (0.03703703703 * Engine.game.getGC().getHeight()), -BAR_WIDTH*percentageHealth, BAR_HEIGHT);
		
		// Health bar white outline
		g.setColor(new Color(255, 255, 255));
		g.drawRect((float) (Engine.game.getGC().getWidth() - (0.05208333333 * Engine.game.getGC().getWidth())), (float) (0.03703703703 * Engine.game.getGC().getHeight()), -BAR_WIDTH, BAR_HEIGHT);
	}
	
	public void drawInventory(Graphics g) {
		// Defining bar variables
		final float BAR_WIDTH = (float) ((Engine.game.getGC().getWidth()/2) - (0.15625 * Engine.game.getGC().getWidth()));
		final float BAR_HEIGHT = (float) ((60f / 1080f) * Engine.game.getGC().getHeight());
		
		// Inventory grey coloration 
		g.setColor(new Color(150, 150, 150, 150));
		g.fillRect((float) (0.050208333333 * Engine.game.getGC().getWidth()), (float) (0.03703703703 * Engine.game.getGC().getHeight()), BAR_WIDTH, BAR_HEIGHT);
		
		// Inventory outline
		g.setColor(new Color(255, 255, 255));
		g.drawRect((float) (0.050208333333 * Engine.game.getGC().getWidth()), (float) (0.03703703703f * Engine.game.getGC().getHeight()), BAR_WIDTH, BAR_HEIGHT);
		
		// Draw every item in the player's inventory
		final float boxSize = BAR_WIDTH / (float) Inventory.Inventory_Size;
		int[][] list = inventory.getItems();
		final float center = (boxSize - (float) Coordinate.ConversionFactor) / 2f;
		
		for(int i = 0; i < list.length; i++) {
			Integer id = list[i][0];
			if(id == 0) continue;
			Integer count = list[i][1];
			
			float barDisp = i * boxSize;
			
			
			Engine.game.displaymanager.getSpriteSheet().getSubImage(0, Engine.game.displaymanager.getSpriteHash().get(id)).draw(
					barDisp + 0.050208333333f * Engine.RESOLUTION_X + center, 
					0.03703703703f * Engine.game.getGC().getHeight() + center // 5 pixel displacement downwards - block centering will later be automatically done.
					);
			g.drawString(count.toString(), barDisp + 0.050208333333f * Engine.RESOLUTION_X,  0.03703703703f * Engine.game.getGC().getHeight()); // Text	
		}
		
		// Draw a box around the selected item
		g.setColor(Color.black);
		g.drawRect(inventorySelected * boxSize + 0.050208333333f * Engine.RESOLUTION_X, 0.03703703703f * Engine.game.getGC().getHeight(), boxSize, BAR_HEIGHT);
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
	
	public void changeInventorySlot(int inventorySelected) {
		this.inventorySelected = inventorySelected;
	}
	
	// Overwritten Collisions Method
	public void collisions() {
		ArrayList<Entity> items = Engine.game.getEntities(EntType.Items);
		
		for(Entity e: items) {
			EItem item = (EItem) e;
			
			if(inventory.hasSpace(item.getID()) && this.entityCollision(e)) {
				inventory.pickUp(item.getID());
				e.markForRemoval();
			}
		}
		
		super.collisions();
	}
	
	
}