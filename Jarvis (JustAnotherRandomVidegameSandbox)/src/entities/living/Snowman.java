package entities.living;

import core.Engine;
import entities.projectiles.Snowball;
import managers.ImageManager;
import support.Utility;

public class Snowman extends Enemy {
	int shotCooldown;
	int groupCooldown;
	
	public Snowman (float x, float y) {
		super(x, y);
		sizeX = 1;
		sizeY = 2;
		
		this.maxHealth = 20;
		this.curHealth = maxHealth;
		
		shotCooldown = 0;
		groupCooldown = 0;
		contactDmg = 10;
		aggroRange = 40;
	
		try {
			this.sprite = ImageManager.getImage("snowmanRight");
		} catch (Exception e) {}
	
	}
	
	public void update() {
		super.update();
		shotCooldown++;
		groupCooldown++;
	}
	
//	public boolean getDirection() {
//		if (Engine.game.getPlayer().getPosition().getX() > this.getPosition().getX()) {
//			return true;
//		}
//		if (Engine.game.getPlayer().getPosition().getX() < this.getPosition().getX()) {
//			return false;
//		}
//		return false;
//	}
	
	public void ai (Player p) {
		if (Engine.game.getPlayer().getPosition().getX() > this.getPosition().getX()) {
			try {
				this.sprite = ImageManager.getImage("snowmanLeft");
			} catch (Exception e) {}
		} else {
			try {
				this.sprite = ImageManager.getImage("snowmanRight");
			} catch (Exception e) {}

		}
		
		if(Utility.getDistance(this, p) <= aggroRange) {
			
			if (groupCooldown % 80 == 0) {
				shotCooldown = 0;
			}
			if (shotCooldown == 0 || shotCooldown == 5 || shotCooldown == 10) {
				Engine.game.addProjectile(new Snowball(this, Engine.game.getPlayer().getPosition()));
			}
			
			
		}
	}
}
