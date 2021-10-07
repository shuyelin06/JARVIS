package entities;
import gamestates.Game;
import support.Utility;

public class Enemy extends Entity {
	
	protected int contactDmg;
	
	protected Player target;
	protected int aggroRange;
	
	public Enemy(float x, float y) {
		super(x,y); 
		
		contactDmg = 1;
		aggroRange = 100;
	}
	
	public void ai(Player p) {
		if(Utility.getDistance(this, p) <= aggroRange) {
			
		}
	}
		
//	public void update() {
//		
//	}
	
}
