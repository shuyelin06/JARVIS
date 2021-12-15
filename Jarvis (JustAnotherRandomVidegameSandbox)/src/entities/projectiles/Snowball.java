package entities.projectiles;

import core.Coordinate;
import entities.living.Living;

public class Snowball extends Projectile {
	
	private float baseSpeed = 10f;
	private double theta;
	private Living origin;
	private Coordinate target;
	private int timer;
	
	public Snowball (Living origin, Coordinate target) {
		super(origin, target);
		this.origin = origin;
		this.target = target;
		
		this.damage = 10;
		
		theta = Math.atan2(target.getY() - origin.getPosition().getY(), target.getX() - origin.getPosition().getX());
		this.xSpeed = (float) Math.cos(theta) * baseSpeed;
		this.ySpeed = (float) Math.sin(theta) * baseSpeed;
	}
	
	public void update() {
		super.update();
		timer++;
	}
	
	public void projectileAI() {
		super.projectileAI();
		
		//homing
//		if (timer < 100000) {
//			theta = Math.atan2(target.getY() - origin.getPosition().getY(), target.getX() - origin.getPosition().getX());
//			this.xSpeed = (float) Math.cos(theta) * baseSpeed;
//			this.ySpeed = (float) Math.sin(theta) * baseSpeed;
//		}
		
	}
	
}
