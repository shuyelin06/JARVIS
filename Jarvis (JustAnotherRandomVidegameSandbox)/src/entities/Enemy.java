package entities;
import gamestates.Game;

public class Enemy extends Entity {
	
	protected int contactDmg;
	
	protected Player target;
	
	public Enemy() {
		super(0,0); // implement stuff for deciding where to spawn
		
		contactDmg = 2;
		
	}
	
	public int spawn() {//make a class that takes care of spawning
			
		return 0;
	}
		
	
}
