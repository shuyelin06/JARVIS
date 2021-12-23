package entities.living;

import org.lwjgl.Sys;

import core.Engine;
import entities.core.Coordinate;
import entities.core.Entity.Team;
import entities.projectiles.nonphysical.MissileStrike;
import entities.projectiles.physical.Boomerang;
import managers.ImageManager;

public class BossMan extends Enemy {
	public float attackCooldown;
	
	public BossMan(float x, float y) {
		super(x,y);

		this.sprite = ImageManager.getPlaceholder();
		
		this.contactDmg = 500;
		
		this.maxHealth = 250;
		this.curHealth = maxHealth;
		
		width = 5f;
		height = 5f;
		healthRegen = true;
		
		attackCooldown = Sys.getTime();
	}
	
	public void ai(Player p) {
		position.setXPos(position.getX() + ( (float) Math.random() * 25f - 12.5f));
		position.setYPos(position.getY() + ( (float) Math.random() * 25f - 12.5f));
				
		if(Sys.getTime() - attackCooldown > 3.5f * 1000) {
			new Boomerang(this, p.getPosition());
			new MissileStrike(this, new Coordinate(
					(float) Math.random(),
					(float) Math.random()
					));
			
			attackCooldown = Sys.getTime();
		}
	}
}