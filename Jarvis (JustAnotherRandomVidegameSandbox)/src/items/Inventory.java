package items;

import java.util.HashMap;

import entities.other.EItem;

public class Inventory{
	private static final int Inventory_Size = 10;
	
	private HashMap<Integer,Integer> items;
	
	public Inventory() {
		this.items = new HashMap<Integer, Integer>();
	}
	
	public boolean hasSpace(Integer id) {
		if(items.containsKey(id) || items.size() < Inventory_Size + 1) {
			return true;
		}
		return false;
	}
	
	public void pickUp(EItem item) {
		int id = item.getID();
		
		if(items.containsKey(id)) {
			items.replace(id, items.get(id) + 1);
		} else {
			if(items.size() < Inventory_Size + 1) {
				items.put(id, 1);
			}
		}
	}
	public void drop(Item item) {
		
	}
}