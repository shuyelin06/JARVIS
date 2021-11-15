package entities.other;

import java.util.ArrayList;

import core.Engine;
import entities.Entity;

public class EItem extends Entity{
	protected int count;
	protected int itemID;
	
	public EItem(float x, float y) {
		super(x,y);	
		this.count = 1;
	}
	
	public int getID() { return itemID; }
	public int getCount() { return count; }
	
	// Overwritten entity collision method
	protected void entityCollisions() {
		// Check collisions with other EItems
		ArrayList<Entity> items = Engine.game.getEntities(EntType.Items); 
		
		int index = items.indexOf(this);
		for(int i = 0; i < items.size(); i++) {
			if(i == index) continue;
			EItem e2 = (EItem) items.get(i);
			
			if(e2.isMarked()) continue;
			
			if(this.itemID == e2.itemID && entityCollision(e2)) { // If not the same ID, no stacking will occur
				this.count += e2.count;
				e2.markForRemoval();
			}
		}
	}
}