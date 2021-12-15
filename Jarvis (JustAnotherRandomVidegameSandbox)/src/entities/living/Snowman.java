package entities.living;

import core.Engine;
import entities.projectiles.Carrot;
import entities.projectiles.Snowball;
import managers.ImageManager;
import support.Utility;

public class Snowman extends Enemy {
	int shotCooldown;
	int groupCooldown;
	int carrotCooldown;
	
	public Snowman (float x, float y) {
		super(x, y);
		sizeX = 1;
		sizeY = 2;
		
		this.maxHealth = 20;
		this.curHealth = maxHealth;
		
		shotCooldown = 0;
		groupCooldown = 0;
		carrotCooldown = 0;
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
		carrotCooldown++;
	}
	
	public boolean getPastDirection() {
		if (Engine.game.getPlayer().getPosition().getX() > this.getPosition().getX()) {
			return true;
		}
		if (Engine.game.getPlayer().getPosition().getX() < this.getPosition().getX()) {
			return false;
		}
		return false;
	}
	
	public void ai (Player p) {
		if(Utility.getDistance(this, p) <= aggroRange) {
			
			if (groupCooldown % 80 == 0) {
				shotCooldown = 0;
			}
			
			if (shotCooldown == 0 || shotCooldown == 4 || shotCooldown == 8) {
				Engine.game.addProjectile(new Snowball(this, Engine.game.getPlayer().getPosition()));
			}
			
			if (carrotCooldown % 200 == 0) {
				Engine.game.addProjectile(new Carrot(this, Engine.game.getPlayer().getPosition()));
			}
			
		}
	}
}
