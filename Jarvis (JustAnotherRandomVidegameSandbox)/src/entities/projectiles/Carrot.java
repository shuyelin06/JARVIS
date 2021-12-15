package entities.projectiles;

import entities.core.Coordinate;
import entities.living.Living;
import managers.ImageManager;

public class Carrot extends Projectile {
	
	private float baseSpeed = 12f;
	private double theta;
	private Living origin;
	private Coordinate target;
	private int timer;
	
	public Carrot (Living origin, Coordinate target) {
		super(origin, target);
		this.origin = origin;
		this.target = target;
		
		this.damage = 20;
		this.width = 1.5f;
		this.height = 0.5f;
		
		
		theta = Math.atan2(target.getY() - origin.getPosition().getY(), target.getX() - origin.getPosition().getX());
		this.xSpeed = (float) Math.cos(theta) * baseSpeed;
		this.ySpeed = (float) Math.sin(theta) * baseSpeed;
		
		try {
			this.sprite = ImageManager.getImage("carrot");
		} catch (Exception e) {}
	}
	
	public void update() {
		super.update();
		timer++;
	}
	
	public void projectileAI() {
		super.projectileAI();
		
		//homing
		if (timer < 120) {
			theta = Math.atan2(target.getY() - this.getPosition().getY(), target.getX() - this.getPosition().getX());
			this.xSpeed = (float) Math.cos(theta) * baseSpeed;
			this.ySpeed = (float) Math.sin(theta) * baseSpeed;
		}
		
	}
	
}
