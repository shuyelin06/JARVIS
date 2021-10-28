package entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import core.Engine;
import entities.Entity.EntType;

public class EItem extends Entity{
	//
	protected int count;
	protected int itemID;
	
	public EItem(float x, float y) {
		super(x,y);
		
		
	}
	
	public void collisions() {
		super.collisions();
		
		System.out.println("Checking block-block collisions");
		
		// Check collisions with other blocks of the same ID
		ArrayList<Entity> items = Engine.game.getEntities(EntType.Items); 
		
		int index = items.indexOf(this);
		for(int i = 0;; i++) {
			if(i >= items.size() || i == index) break;
			EItem e2 = (EItem) items.get(i);
			
			if(this.itemID != e2.itemID) continue;
			if(this.getPosition().magDisplacement(e2.getPosition()) < this.magSize() + e2.magSize()) {
				System.out.println("Two blocks are stacked");
				this.count++;
				items.remove(i);
			}
		}
		
//		for(int i = 0;; i++) {
//			
//			
//			EItem e1 = (EItem) items.get(i);
//			
//			for(int j = i+1; j < items.size(); j++) {
//				EItem e2 = (EItem) items.get(j);
//				
//				
//			}
//		}
		
//		ArrayList<Entity> temp = new ArrayList<Entity>(items); // Temporary list to iterate through
//		
//		Iterator<Entity> iterator = items.iterator();
//		int index =
//		while(iterator.hasNext()) {
//			EItem e1 = (EItem) iterator.next();
//			
//			System.out.println(temp.size());
//			Iterator<Entity> second = temp.iterator();
//			if(second.hasNext()) {
//				second.next();
//				second.remove();
//			}
//			while(second.hasNext()) {
//				EItem e2 = (EItem) second.next();
//
//				
//			}
//		}
		
		System.out.println("Finished block-block collisions");
	}
}