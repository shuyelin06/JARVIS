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
	
	public void render(Graphics g, float positionX, float positionY, float x, float y) // should probably move the 
																						//block types to a bunch of classes
	{
		if(getID() != 0)
		{
			if(getID() == 2)
			{
				g.setColor(new Color(70, 180, 10));
			} else if(getID() == 1)
			{
				g.setColor(new Color(120, 70, 50));
			}
				g.fillRect(positionX, positionY, x, y);
		}
			
	}
}