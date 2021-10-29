package entities.living;

import entities.Entity;

public class Living extends Entity{
	/*
	 * Stat Variables - Unused, but we can implement them later
	 */
	protected int curHealth, maxHealth;
	protected float percentageHealth;
	
	protected boolean alive;
	
	protected int attack;
	protected int defense;
	
	protected int iFrames;
	protected int iDuration;
	protected boolean healthRegen;
	protected int regenTimer;
	protected int timeLastHit;
	protected int regenRate;
	protected int regenInc;
	
	public Living(float x, float y) {
		super(x,y);
		
		this.iFrames = 0;
		this.iDuration = 30; //how long invulnerability will last after taking damage
		
		alive = true;
		
		curHealth = 1;
		maxHealth = 2;
		percentageHealth = 1f;
		regenTimer = 120;
		
		curHealth = 1;
		maxHealth = 2;
		percentageHealth = 1f;
		healthRegen = false;
		timeLastHit = 0;
		regenRate = 30;
		regenInc = 0;
		
	}
	
	public void moveRight(float maxSpeed, float acceleration) {
		if(xSpeed + acceleration > maxSpeed) xSpeed = maxSpeed;
		else xSpeed += acceleration;	
	}
	
	public void moveLeft(float maxSpeed, float acceleration) {
		if(xSpeed - acceleration < 0 - maxSpeed) xSpeed = 0 - maxSpeed;
		else xSpeed -= acceleration;
	}
	
	public void jump(float speed) {
		if(jumpsLeft > 0) {
			this.onPlatform = false;
			this.ySpeed = speed;
			
			jumpsLeft--;
		}
	}
	public void fall() {
		this.ySpeed -= Entity.gravity;
	}
	public void takeDamage(int dmg, boolean i) { //boolean for iFrames cause for certain piercing attacks that don't trigger them
		//this mimics the mechanics in Terraria
		if(iFrames == 0) {
			if(healthRegen) {
				timeLastHit = 0;
				regenInc = 0;
			}
			
			dmg -= defense;
			if(dmg <= curHealth) {
				if(dmg <= 0) { //if defense is higher than dmg taken you will just take 1 dmg
					curHealth -= 1;
				}else {
					curHealth -= dmg;
				}
			}else {
				curHealth = 0;
			}
			if(curHealth <= 0) {
				alive = false;
			}
			else if(i) {
				setIFrames(iDuration);
			}
			
		}
	}
	public void regen() {
		if(healthRegen && timeLastHit >= regenTimer && curHealth < maxHealth) {
//			if(Utility.random(0, 100) < 5) { //replace this with increment health every x frames
//				curHealth++;
//			}
			regenInc++;
			if(regenInc % regenRate == 0) {
				curHealth++;
			}
		
		}
	}

	//gives entity number of iframs that will automatically start ticking down each frame in update()
	public void setIFrames(int frames) {
		iFrames = frames;
	}
	
	public boolean isAlive() {
		return alive;
	}
	
	public void takeDamage(int damage) {
		curHealth -= damage;
		if (curHealth < 0) {
			curHealth = 0;
			alive = false;
		}
	}
		
	public void update() {
		if(curHealth < 0) {
			this.remove = true;
			return;
		}
		
		// Update Stats
		percentageHealth = ((float) curHealth) / ((float) maxHealth); // Update health
		
		if(iFrames > 0) { //timer that ticks down invincibility frames
			iFrames --;
		}
		if(healthRegen && timeLastHit < 240) {
			timeLastHit++;
		}
		regen();
		
		super.update();
	}
	public void collisions() {
		super.collisions();
	}
}