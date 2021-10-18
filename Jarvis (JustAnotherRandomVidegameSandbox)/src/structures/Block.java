package structures;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class Block{
	private int id; // Id of the block
	
	public Block(int id) {
		this.id = id;
	}
	public int getID() {
		return id;
	}
}