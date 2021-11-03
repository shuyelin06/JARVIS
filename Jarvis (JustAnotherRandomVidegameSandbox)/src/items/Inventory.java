package items;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import entities.other.EItem;

public class Inventory{
	public static final int Inventory_Size = 10;
	
	HashMap<Integer, Integer> idIndexMapping; // First entry: id, second entry: index
	int[][] items; // 
	// private HashMap<Integer,Integer> items;
	
	public Inventory() {
		this.items = new int[Inventory_Size][2];
		
		for(int i = 0; i < items.length; i++) {
			System.out.println(items[i][0]);
			System.out.println(items[i][1]);
		}
		
		this.idIndexMapping = new HashMap<Integer, Integer>();
	}
	
	public int[][] getItems() {
		return items;
	}
	public int getIndexMap(int id) {
		return idIndexMapping.get(id);
	}
	
	public boolean hasSpace(Integer id) {
		if(idIndexMapping.containsKey(id)) return true;
		else if (idIndexMapping.size() < Inventory_Size + 1) return true;
		
		return false;
	}
	
	public void pickUp(EItem item) {
		int id = item.getID(); // Get the item ID
		
		if(idIndexMapping.containsKey(id)) {
			items[idIndexMapping.get(id)][1]++;
		} else {
			for(int i = 0; i < Inventory_Size; i++) {
				if(items[i][0] == 0) {
					idIndexMapping.put(id, i);
					items[i][0] = id;
					items[i][1] = 1;
					break;
				}
			}
		}
	}
	public void drop(Item item) {
		// To drop, reduce count - if the count is 0, remove from hashmap and reset the array
	}
}