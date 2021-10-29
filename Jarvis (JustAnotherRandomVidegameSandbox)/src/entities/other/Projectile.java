package entities.other;

import entities.Entity;

public class Projectile extends Entity{
	
	public Projectile(float x, float y){
		super(x, y);
	}
	// Must also check Projectile - Player / Enemy Collisions
	public void collisions() {
		super.collisions();
		
//		if(Math.abs(p.getPosition().getX() - this.getPosition().getX()) <= 1) { //still need to implement hitboxes
//			if(Math.abs(p.getPosition().getY() - this.getPosition().getY()) <= 1) {
//				p.takeDamage(1, true);
//			}
//		}
	}
}