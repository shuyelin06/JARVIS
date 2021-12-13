package entities.living;

import managers.ImageManager;
import support.Utility;

//supposed to be spelled with an "a"
public class Scorpian extends Enemy{

	int jumpTimer;
	int jumpCD;
//	boolean onGround;
	boolean inSupaJump;
//	boolean xCollision;
	
	public Scorpian (float x, float y) {
		super(x,y);
		sizeX = 3;
		sizeY = 3;
		
		this.maxHealth = 50;
		this.curHealth = maxHealth;
		
		contactDmg = 25;
		aggroRange = 15;
		
		jumpTimer = 0;
		jumpCD = 120; //should be 2 seconds
		
//		onGround = false;
		inSupaJump = false;
//		xCollision = false;
		
		try {
			this.sprite = ImageManager.getImage("rockEnemy");
		} catch(Exception e) {}
		
		
	}
	
	public void ai(Player p) {
		if(Utility.getDistance(this, p) <= aggroRange) {
			moveToPlayerX(p, 5f);
			supaJump(p, 40f);
			
		}
	}
	
//	public void update() {
//		xCollision = false;
//		super.update();
//	}
	
	protected void onBlockYCollision() {
		super.onBlockYCollision();
//		onGround = true;
		inSupaJump = false;
	}
	
//	protected void onBlockXCollision() {
//		xCollision = true;
//	}
	
	public void supaJump(Player p, float speed) {
		jumpTimer++;
		
		if(jumpTimer > jumpCD) {
			if(Utility.random(0,100) < 2) {
				jump(speed);
				jumpTimer = 0;
				inSupaJump = true;
			}
		}
//		else if(xCollision) {
//			jump(10f);
//		}
		if(inSupaJump) {
			if(this.ySpeed < 0) {
				this.setSpeedY(-75f);
			}
		}
		
		
	}
	
}
