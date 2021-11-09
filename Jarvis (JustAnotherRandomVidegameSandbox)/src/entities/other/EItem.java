package entities.other;

import java.util.ArrayList;

import core.Engine;
import entities.Entity;

public class EItem extends Entity{
	//
	protected int count;
	protected int itemID;
	
	public EItem(float x, float y) {
		super(x,y);
		
		this.count = 1;
	}
	
	public int getID() {
		return itemID;
	}
	public void collisions() {	
		if(this.isMarked()) return;
		
		// Check collisions with other blocks of the same ID
		ArrayList<Entity> items = Engine.game.getEntities(EntType.Items); 
		
		int index = items.indexOf(this);
		for(int i = 0; i < items.size(); i++) {
			if(i == index) continue;
			EItem e2 = (EItem) items.get(i);
			
			if(this.itemID != e2.itemID) continue; // If same ID, no stacking will occur
			if(entityCollision(e2)) {
				this.count++;
				e2.markForRemoval();
			}
		}
		
		super.collisions();
	}
}