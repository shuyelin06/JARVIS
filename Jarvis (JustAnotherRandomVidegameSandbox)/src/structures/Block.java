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
	
	public void render(Graphics g, float positionX, float positionY, float x, float y)
	{
		if(getID() == 0)
		{
			g.setColor(new Color(255f, 255f, 255f, 1f));
		} else if(getID() == 1)
		{
			g.setColor(new Color(255f, 255f, 255f, 0f));
		}
		
		if(x % 8 == 1)
		{

			g.setColor(new Color(255f, 0f, 0f, 1f));
		}
		
		g.fillRect(positionX, positionY, x, y);
	}
}