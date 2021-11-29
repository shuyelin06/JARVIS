package entities.living;

import managers.ImageManager;
import support.Utility;

//supposed to be spelled with an "a"
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
	
	public void ai(Player p) {
		if(Utility.getDistance(this, p) <= aggroRange) {
			
			
		}
	}
	
}
