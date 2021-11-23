package items;

import java.util.HashMap;

import core.Coordinate;
import core.Engine;
import entities.Entity.EntType;
import entities.living.Player;
import entities.other.EBlock;
import entities.other.EItem;

public class Inventory{
	public static final int Inventory_Size = 10;
	
	private Coordinate playerPos;
	private HashMap<Integer, Integer> idIndexMapping; // First entry: id, second entry: index
	
	private Item[] items;
	// private HashMap<Integer,Integer> items;
	
	public Inventory(Player p) {
		this.playerPos = p.getPosition();
		this.items = new Item[Inventory_Size];
		this.idIndexMapping = new HashMap<Integer, Integer>();
		
		this.items[0] = Item.GetItem(-1, 1);
	}
		
	public Item[] getItems() { return items; }
	public int getIndexMap(int id) { return idIndexMapping.get(id); }
	
	// Filter out items whose counts are 0
	public void filter() {
		for(int i = 0; i < Inventory.Inventory_Size; i++) {
			Item item = items[i];
			
			if(item == null) continue;
			if(item.getCount() == 0) {
				idIndexMapping.remove(item.getID());
				items[i] = null;
			}
		}
	}
	
	// Returns if the inventory has space for a given Integer ID
	public boolean hasSpace(Integer id) {
		if(idIndexMapping.containsKey(id)) return true;
		else if (idIndexMapping.size() < Inventory_Size + 1) return true;
		
		return false;
	}
	
	// Pick Up an EItem
	public void pickUp(EItem item) {
		int id = item.getID();
		int count = item.getCount();
		
		if(idIndexMapping.containsKey(id)) {
			int index = idIndexMapping.get(id);
			items[index].increaseCount(count);
		} else {
			for(int i = 0; i < Inventory_Size; i++) { // Find an empty spot
				if(items[i] == null) {
					idIndexMapping.put(id, i);
					items[i] = Item.GetItem(id, count);
					break;
				}
			}
		}
	}
	
	// Drop an Item by turning it into an EItem
	public void drop(int index) {
		// To drop, reduce count - if the count is 0, remove from hashmap and reset the array
		Item item = items[index];
		
		if(item == null) return;
		else if(item.getCount() > 0) {
			item.decreaseCount(1);
			
			EBlock eblock = new EBlock(item.getID(), playerPos.getX(), playerPos.getY());
			eblock.setSpeedY(7.5f);
			Engine.game.addEntity(EntType.Items, eblock);
		}	
	}
		

}