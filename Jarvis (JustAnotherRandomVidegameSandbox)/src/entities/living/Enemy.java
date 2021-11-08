package entities.living;

import org.newdawn.slick.Image;

import core.Engine;
import support.Utility;

public class Enemy extends Living {
	protected int contactDmg;
	
	protected Player target;
	protected int aggroRange;
	protected float moveSpeed;
	
	public Enemy(float x, float y) 
	{
		super(x,y); 
		target = Engine.game.getPlayer();

		try {
			this.sprite = new Image("res/bowser.png");
		} catch(Exception e) {}
		
		contactDmg = 1;
		aggroRange = 18;
		sizeX = 1f;
		sizeY = 1f;
		healthRegen = false;
	}
	// Overwritten update method
	public void update() {
		ai(target);
		
		super.update();
	}
	// Overwritten collisions method
	public void collisions() {
		if(this.entityCollision(target)) {
			target.takeDamage(1, true);
		}
		
		super.collisions();
	}
	
	public void ai(Player p) {
		if(Utility.getDistance(this, p) <= aggroRange) {
			if(Utility.changeX(this, p) > 0) {
				moveRight(10, 5);
			}else if(Utility.changeX(this, p) < 0) {
				moveLeft(10, 5);
			}
			if(Utility.random(0,100) < 25) {
				jump(10);
			}
			
		}
	}
	

}
