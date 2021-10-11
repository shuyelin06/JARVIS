package entities;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;

import gamestates.Game;
import support.Utility;

public class Enemy extends Entity {
	
	protected int contactDmg;
	
	protected Player target;
	protected int aggroRange;
	protected int size;
	
	public Enemy(float x, float y) {
		super(x,y); 
		
		contactDmg = 1;
		aggroRange = 100;
		size = 15;
	}
	
	public void ai(Player p) {
		if(Utility.getDistance(this, p) <= aggroRange) {
			
		}
	}
		
//	public void update() {
//		
//	}
	
	//x and y are coordinates of the player
	public void render(Graphics g, float x, float y) {
		super.render(g);
		//if() { //if it is close enough to player and will be on screen
			g.setColor(Color.red);
			g.draw(new Circle((Game.centerX()) + (this.getPosition().getX() - x), Game.centerY() + (this.getPosition().getY() - y), size));	
		//}
	}
	
}
