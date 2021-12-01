package structures;

import java.util.HashSet;
import java.util.List;

import support.Utility;

public class Block{
	// List of all block IDs that are passable (entities will not collide)
	public static HashSet<Integer> Passable_Blocks = new HashSet<Integer>();
	
	protected int id; // Id of the block
	protected int variant;
	
	final public static int Max_Durability = 100;
	protected int durability;
	
	public Block(int id) {
		
		this.id = id;
		variant = 0;
		
		if(id == 3) { //stone
			setRockVar();
		} else
		{
			setVariant();
		}
		
		this.durability = Max_Durability;
	}
	public int getID() { return id; }
	public void setID(int id) { this.id = id; }
	
	public int getDurability() { return this.durability; }
	public void hit(int efficiency) { this.durability -= efficiency; }
	
	public int getVariant() { return variant; }
	
	public void setVariant() 
	{
		variant = (int)(Math.random() * 4);
	}
	
	public void setRockVar() {
		if(Utility.random(0, 10000) < 1) {
			variant = 2;
		}
		else if(Math.random() > 0.5)
		{
			variant = 1;
		}
		
	}
}