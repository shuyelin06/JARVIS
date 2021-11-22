package entities.living;

import org.newdawn.slick.Image;

import core.Engine;
import core.Values;
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
			this.sprite = Values.Images.get("mushroom");
		} catch(Exception e) {}
		
		contactDmg = 5;
		aggroRange = 20;
		sizeX = 1f;
		sizeY = 1f;
		healthRegen = false;
		
		this.jumps = 0;
	}
	// Overwritten update method
	public void update() {
		super.update();
		
		ai(target);
		if (!alive) {
			this.markForRemoval();
		}
	}

	// Overwritten entity collision method
	protected void entityCollisions() {
		if(this.entityCollision(target)) {
			target.takeDamage(contactDmg, true);
			this.takeDamage(1, true);
		}
	}
	
	public void ai(Player p) {
		if(Utility.getDistance(this, p) <= aggroRange) {
			if(Utility.changeX(this, p) > 0) {
				setXSpeed(6.5f);
			}else if(Utility.changeX(this, p) < 0) {
				setXSpeed(-6.5f);
			}
			if(Utility.random(0,100) < 25) {
				jump(15f);
			}
			
		}
	}
	

}
