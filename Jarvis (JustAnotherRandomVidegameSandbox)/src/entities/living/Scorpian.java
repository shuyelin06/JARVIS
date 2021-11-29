package entities.living;

import managers.ImageManager;

public class Scorpian extends Enemy{

	public Scorpian (float x, float y) {
		super(x,y);
		sizeX = 3;
		sizeY = 3;
		
		contactDmg = 25;
		aggroRange = 15;
		
		try {
			this.sprite = ImageManager.getImage("placeholder");
		} catch(Exception e) {}
	}
	
	
	
}
