package structures;

import support.Utility;

public class Block{
	protected int id; // Id of the block
	protected int variant;
	
	public Block(int id) {
		this.id = id;
		variant = 0;
		if(id == 2) { //grass
			setGrassVar();
		}else if(id == 3) { //stone
			setRockVar();
		}
	}
	public int getID() {
		return id;
	}
	public void setID(int id)
	{
		this.id = id;
	}
	
	public int getVariant() {
		return variant;
	}
	
	public void setGrassVar() {
		
		
	}
	
	public void setRockVar() {
		if(Utility.random(0, 10000)<1) {
			variant = 1;
		}
		
	}
}