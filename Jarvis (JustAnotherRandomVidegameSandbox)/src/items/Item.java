package items;

import core.Engine;
import gamestates.Game;

public class Item{
	protected static Game game = Engine.game; // Here so we can refer to it in the item class
	
	protected int id;
	protected int count;
	
	public enum ItemType{ Block, Consumable, Tool }
	
	public Item(int id, int count) {
		this.id = id;
		this.count = count;
	}
	
	// Accessor Methods
	public int getID() { return id; }
	public int getCount() { return count; }
	
	// Mutator Methods
	public void setID(int id) { this.id = id; }
	public void setCount(int count) { this.count = count; }
	public void increaseCount(int i) { count += i; }
	public void decreaseCount(int i) { count -= i; }
	
	// Main method all items will follow
	public void use(float x, float y) {} // Absolute Coordinates of the Mouse in Game
	
	public static Item GetItem(int id, int count) {
		// For now, + numbers = Block Item
		if(id > 0) {
			return new IBlock(id, count);
		} else {
			// - Numbers = Other Items
			return null;
		}
	}
}