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
			sprite = new Image("res/redEnemy.png");
		} catch(Exception e) {}
		
		contactDmg = 1;
		aggroRange = 18;
		sizeX = 1;
		sizeY = 1;
		jumpsLeft = 1;
		healthRegen = false;
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
	
	public void playerCollision(Player p) {
		float x = Utility.changeX(p, this);
		float y = Utility.changeY(p, this);
		if(xCollision(x, p) && yCollision(y, p)) {
			p.takeDamage(1, true);
		}
//		if(Math.abs(p.getPosition().getX() - this.getPosition().getX()) <= this.sizeX) { //still need to implement hitboxes
//			if(Math.abs(p.getPosition().getY() - this.getPosition().getY()) <= 1) {
//				p.takeDamage(1, true);
//			}
//		}
	}
	
	public boolean xCollision(float changeX, Player p) {
		if(changeX <= 0 && Math.abs(changeX) <= this.sizeX) {
			return true;
		}else if(changeX > 0 && Math.abs(changeX) <= p.getSizeX()) {
			return true;
		}
		return false;
	}
	
	public boolean yCollision(float changeY, Player p) {
		if(changeY <= 0 && Math.abs(changeY) <= this.sizeY) {
			return true;
		}else if(changeY > 0 && Math.abs(changeY) <= p.getSizeY()) {
			return true;
		}
		return false;
	}
		
	public void update() {
		super.update();
		
		if(Utility.random(0.0, 100.0) <= 0.2) { //random chance to die just to test stuff
			alive = false;
			remove = true;
		}
		
		playerCollision(target);
		ai(target);
	}
}
