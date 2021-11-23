package items;

import managers.ImageManager;

public class Tool extends Item{
	public Tool(int id, int count) {
		super(id, count,
				ImageManager.getImage("placeholder"));
	}
	
	public void use(float x, float y) {
		
	}
}