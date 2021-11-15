package entities.living;

import java.util.ArrayList;
import java.util.Set;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import core.Coordinate;
import core.Engine;
import core.Values;
import entities.Entity;
import entities.other.EItem;
import items.Inventory;

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
		
		this.inventory = new Inventory(this);
		
		this.sizeY = 2f;
		this.sizeX = 1.3f; // Only use sizes to the 10th PLACE 
		curHealth = 10;
		maxHealth = 10;
		healthRegen = true;
	}
	
	public int inventorySelected() { return this.inventorySelected; }
	public int selectedItem() { return inventory.getItems()[inventorySelected][0]; }
	public Inventory getInventory() { return inventory; }

	public void changeInventorySlot(int inventorySelected) {
		this.inventorySelected = inventorySelected;
	}
	
	public void adjustInventorySlot(int change) {
		inventorySelected += change / 120;
		if (inventorySelected < 0) {
			inventorySelected = Inventory.Inventory_Size - 1;
		} else if (inventorySelected > Inventory.Inventory_Size - 1) {
			inventorySelected = 0;
		}
	}
	
	public void dropItem() {
		inventory.drop(inventorySelected);
	}
	// Overwritten Collisions Method
	protected void entityCollisions() {
		ArrayList<Entity> items = Engine.game.getEntities(EntType.Items);
		
		for(Entity e: items) {
			EItem item = (EItem) e;
			
			// Can only pick up after 0.5 seconds of existing
			if(e.timeAlive() < 0.5) continue;
			
			// Pick up item
			if(inventory.hasSpace(item.getID()) && this.entityCollision(e)) {
				inventory.pickUp(item);
				e.markForRemoval();
			}
		}
	}
	
	public void respawn() {
		position.setX(Values.SpawnX);
		position.setY(Values.SpawnY);
		curHealth = maxHealth;
		alive = true;
	}
}