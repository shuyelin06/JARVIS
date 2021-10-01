package player;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import attack.Attack;
import core.Actor;
import core.Game;
import platform.Platform;

public class Player extends Actor{
	protected int player;
	protected float curEnergy, maxEnergy;
	protected boolean canRemove, canTakeDamage;
	protected int tankSlamTimer;
	protected String playerString;
	protected boolean isDead;
	protected int burnTimer, slowTimer;
	protected float speedModifier;
	
	//constructor
	public Player() {
		super();
		damage = 0;
		curEnergy = 50;
		maxEnergy = 100;
		canRemove = false;
		isDead = false;
		canTakeDamage = true;
		burnTimer = 0;
		slowTimer = 0;
		speedModifier = 1;
	}

	//updates characters: checks collisions with ground and platforms
	public void update() {
		super.update();
		curEnergy += (float) 0.4;
		collideGround();
		collidePlatform();
		controls();
		checkCollisions();
		updateEffects();
		if (curEnergy >= maxEnergy) {
			curEnergy = maxEnergy;
		}
		isDead = false;
	}

	//draws characters
	public void render(Graphics g) {
		super.render(g);
		drawHealthBars(g);
		drawEnergyBars(g);
		drawTitle(g);
	}

	//draws player name above player
	public void drawTitle(Graphics g) {
		if (!isDead) {
			if (player == 1) {
				playerString = "player 1";
			} else if (player == 2) {
				playerString = "player 2";
			}
			g.setColor(new Color(0, 0, 0));
			g.drawString(playerString, x - 7, y - 20);
			g.setColor(new Color(255,255,255));
			g.drawString(playerString, x - 5, y - 22);
		}
	}

	//makes health bar of each character
	public void drawHealthBars(Graphics g) {
		if (player == 1) {
			final float BAR_WIDTH = ((Game.gc.getWidth()/2) - 300);
			final float BAR_HEIGHT = 30;
			g.setColor(new Color(0, 100, 0, 150));
			g.fillRect(100, 40, BAR_WIDTH, BAR_HEIGHT);
			g.setColor(new Color(0, 255, 0, 150));
			g.fillRect(100, 40, BAR_WIDTH*Game.percentageHealth1, BAR_HEIGHT);
			g.setColor(new Color(255, 255, 255));
			g.drawRect(100, 40, BAR_WIDTH, BAR_HEIGHT);
		} else if (player == 2) {
			final float BAR_WIDTH = ((Game.gc.getWidth()/2) - 300);
			final float BAR_HEIGHT = 30;
			g.setColor(new Color(0, 100, 0, 150));
			g.fillRect(Game.gc.getWidth() - 100, 40, -BAR_WIDTH, BAR_HEIGHT);
			g.setColor(new Color(0, 255, 0, 150));
			g.fillRect(Game.gc.getWidth() - 100, 40, -BAR_WIDTH*Game.percentageHealth2, BAR_HEIGHT);
			g.setColor(new Color(255, 255, 255));
			g.drawRect(Game.gc.getWidth() - 100, 40, -BAR_WIDTH, BAR_HEIGHT);
		}
	}

	//draws energy bars for abilities
	public void drawEnergyBars(Graphics g) {
		if (player == 1) {
			final float BAR_WIDTH = ((Game.gc.getWidth()/2) - 300);
			final float BAR_HEIGHT = 15;
			g.setColor(new Color(0, 0, 100, 150));
			g.fillRect(100, 80, BAR_WIDTH, BAR_HEIGHT);
			g.setColor(new Color(0, 0, 255, 150));
			g.fillRect(100, 80, BAR_WIDTH*Game.percentageEnergy1, BAR_HEIGHT);
			g.setColor(new Color(255, 255, 255));
			g.drawRect(100, 80, BAR_WIDTH, BAR_HEIGHT);
		} else if (player == 2) {
			final float BAR_WIDTH = ((Game.gc.getWidth()/2) - 300);
			final float BAR_HEIGHT = 15;
			g.setColor(new Color(0, 0, 100, 150));
			g.fillRect(Game.gc.getWidth() - 100, 80, -BAR_WIDTH, BAR_HEIGHT);
			g.setColor(new Color(0, 0, 255, 150));
			g.fillRect(Game.gc.getWidth() - 100, 80, -BAR_WIDTH*Game.percentageEnergy2, BAR_HEIGHT);
			g.setColor(new Color(255, 255, 255));
			g.drawRect(Game.gc.getWidth() - 100, 80, -BAR_WIDTH, BAR_HEIGHT);
		}
	}

	//checks if damage can be taken, takes damage
	public void takeDamage(int damage) {
		if (canTakeDamage) {
			curHealth -= damage;
		}
		if (curHealth <= 0) {
			canRemove = true;
		}
	}

	//accessor for canRemove
	public boolean canRemove() {
		return canRemove;
	}

	//checks collision for every attack
	public void checkCollisions() {
		for (int i = 0; i < Game.attacks.size(); i++) {
			if (collidesWith(Game.attacks.get(i))) {
				if (Game.attacks.get(i).player() != player) {
					applyEffects(i);
					takeDamage(Game.attacks.get(i).getDamage());
					Game.attacks.get(i).enableRemoval();
					Game.hitPause = true;
					Game.hitTime = 0;
				}
			}
		}
	}
	
	public void applyEffects(int i) {
		if (Game.attacks.get(i).effect().equals("burn")) {
			burnTimer = 180;
		}
		if (Game.attacks.get(i).effect().equals("slow")) {
			slowTimer = 120;
		}
	}
	
	public void updateEffects() {
		burnTimer--;
		slowTimer--;
		if (burnTimer <= 0) {
			burnTimer = 0;
		}
		if (slowTimer <= 0) {
			slowTimer = 0;
		}
		if (burnTimer % 60 == 0 && burnTimer != 0) {
			takeDamage(2);
		}
		if (slowTimer > 0) {
			speedModifier = (float)0.5;
		} else {
			speedModifier = 1;
		}
	}

	//collision between player and attack
	public boolean collidesWith(Attack other)
	{			
		return  collidesWith(other.getX(),     	 		  other.getY()          	 ) || 
				collidesWith(other.getX() + other.getW(), other.getY()     		     ) ||
				collidesWith(other.getX(),                other.getY() + other.getH()) ||
				collidesWith(other.getX() + other.getW(), other.getY() + other.getH()) ||
				collidesWith(other.getX() + other.getW()/2, other.getY() + other.getH()/2);
	}

	//collision mechanism
	public boolean collidesWith(float x, float y)
	{
		return 	x >= this.x && 
				x <= this.x + this.w && 
				y >= this.y && 
				y <= this.y + this.h;

	}


	//check if the player collides with the ground
	public void collideGround() {
		if ((y + h) >= (Game.ground.getY() + 8)) {
			y = (Game.gc.getHeight() - Game.ground.getH()) - h + 8;
			jumpsLeft = 2;
			touchesGround = true;
		} else {
			touchesGround = false;
		}
	}

	//checks if the platform collides with the player when the player is above the platform
	public void collidePlatform() {

		for (Platform p: Game.platforms) {
			//checks if X is in the right range
			if (((x >= (p.getX() - w)
					&& x <= p.getX() + p.getW()))
					|| ((x + w) >= p.getX()
					&& (x + w) <= p.getX() + p.getW())) {
				//checks if Y is just above the platform and if player is going down
				if (((y + h) <= p.getY() 
						&& (ySpeed >= 0)) && (ySpeed + y + h) >= p.getY()
						) {
					y = p.getY() - h;
					ySpeed = 0;
					jumpsLeft = 2;
					touchPlatformChecker = true;
				}
			}
		}
		//enables touchPlatform variable if any of the platforms were touched
		if (touchPlatformChecker = true) {
			touchesPlatform = true;
		} else {
			touchesPlatform = false;
		}
		touchPlatformChecker = false;

	}

	//makes player jump by jumpHeight
	public void jump() {
		if (jumpsLeft > 0) {
			ySpeed = -jumpHeight;
			jumpsLeft--;
		}
	}

	//player movement
	public void controls() {
		if (player == 1) {
			//left
			if(Game.gc.getInput().isKeyDown(Game.gc.getInput().KEY_A))
			{
				x -= xSpeed * speedModifier;
				faceDirection = "left";
				if (x < 0) {
					x = 0;
				}
			}
			//down
			if(Game.gc.getInput().isKeyDown(Game.gc.getInput().KEY_S))
			{
				if (!touchesGround && !touchesPlatform) {
					if (ySpeed >= 0) {
						y += ySpeed;
					}
				} else if (touchesPlatform) {
					y += 0.01;
				}
			}
			//right
			if(Game.gc.getInput().isKeyDown(Game.gc.getInput().KEY_D))
			{
				x += xSpeed * speedModifier;
				faceDirection = "right";
				if (x > (Game.gc.getWidth()-w)) {
					x = Game.gc.getWidth()-w;
				}
			}
		}
		if (player == 2) {
			//left
			if(Game.gc.getInput().isKeyDown(Game.gc.getInput().KEY_LEFT))
			{
				x -= xSpeed * speedModifier;
				faceDirection = "left";
				if (x < 0) {
					x = 0;
				}
			}
			//down
			if(Game.gc.getInput().isKeyDown(Game.gc.getInput().KEY_DOWN))
			{
				if (!touchesGround && !touchesPlatform) {
					if (ySpeed >= 0) {
						y += ySpeed;
					}
				} else if (touchesPlatform) {
					y += 0.01;
				}
			}
			//right
			if(Game.gc.getInput().isKeyDown(Game.gc.getInput().KEY_RIGHT))
			{
				x += xSpeed * speedModifier;
				faceDirection = "right";
				if (x > (Game.gc.getWidth()-w)) {
					x = Game.gc.getWidth()-w;
				}
			}
		}
		if (x < 0) {
			x = 0;
		}
		if (x > (Game.gc.getWidth()-w)) {
			x = Game.gc.getWidth()-w;
		}
	}

	//position accessor
	public float getX() {
		return x;
	}
	public float getY() {
		return y;
	}
	//tankSlam accessor
	public int getTankSlamTimer() {
		return tankSlamTimer;
	}

}
