package entities;
import gamestates.Game;

public class Enemy extends Entity {
	
	protected int contactDmg;
	
	protected Player target;
	
	public Enemy(float x, float y) {
		super(x,y); 
		
		contactDmg = 2;
		
	}
	
	
		
	
}
