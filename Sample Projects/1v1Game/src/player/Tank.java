package player;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import attack.RangerBullet;
import attack.TankPunch;
import attack.TankSlam;
import core.Game;
import platform.Platform;
import player.Player;

public class Tank extends Player{
	
	private int punchTimer, shieldTimer, slamTimer;
	private int shieldSubTimer, slamSubTimer;
	private int ySubSpeed;
	
	//constructor
	public Tank(float x, float y, int player) {
		super();
		this.player = player;
		this.x = x;
		this.y = y;
		//w is 64
		w = (float) ((float) Game.gc.getWidth()/21.34375);
		//h is 114
		h = (float) ((float) Game.gc.getHeight()/6.736842105);
		//xSpeed is 7
		xSpeed = (float) ((float) Game.gc.getWidth()/195.14285714);
		//ySpeed is 5
		ySpeed = (float) ((float) Game.gc.getHeight()/153.6);
		//jumpHeight is 16
		jumpHeight = (int) (Game.gc.getHeight()/48);
		curHealth = 50;
		maxHealth = 50;
		punchTimer = 0;
		shieldTimer = 0;
		slamTimer = 0;
		shieldSubTimer = 0;
		initDirection();
		setImage("res/tankIdle.png");
		image.setFilter(Image.FILTER_NEAREST);
	}
	
	//updates
	public void update() {
		super.update();
		//image.rotate(30);
		updatePercentage();
		runAbilities();
		updateTimers();
		updateShield();
		updateSlam();
	}

	//draws facing direction
	public void render(Graphics g) {
		super.render(g);
		if (faceDirection == "left") {
			image.draw(x, y, w, h);
		} else if (faceDirection == "right") {
			image.draw((x + w), y, -w, h);
		}
		if (shieldSubTimer > 0 ) {
			renderShield(g);
		}
	}
	
	//update timers
	public void updateTimers() {
		punchTimer--;
		shieldTimer--;
		slamTimer--;
		shieldSubTimer--;
		slamSubTimer--;
		if (punchTimer <= 0) {
			punchTimer = 0;
		}
		if (shieldTimer <= 0) {
			shieldTimer = 0;
		}
		if (shieldSubTimer <= 0) {
			shieldSubTimer = 0;
		}
		if (slamTimer <= 0) {
			slamTimer = 0;
		}
		if (slamSubTimer <= 0) {
			slamSubTimer = 0;
		}
	}
	
	//updates the percentage health of player
	public void updatePercentage() {
		if (player == 1) {
			Game.percentageHealth1 = (float) curHealth/maxHealth;
			Game.percentageEnergy1 = (float) curEnergy/maxEnergy;
		} else if (player == 2) {
			Game.percentageHealth2 = (float) curHealth/maxHealth;
			Game.percentageEnergy2 = (float) curEnergy/maxEnergy;
		}
	}
	
	//sets starting direction based on player
	public void initDirection() {
		if (player == 1) {
			faceDirection = "right";
		} else if (player == 2) {
			faceDirection = "left";
		}
	}
	
	//bind controls
	public void runAbilities() {
		if (player == 1) {
			if(Game.gc.getInput().isKeyDown(Game.gc.getInput().KEY_C)) {
				punch(player);
			}
			if(Game.gc.getInput().isKeyDown(Game.gc.getInput().KEY_V)) {
				slam();
			}
			if(Game.gc.getInput().isKeyDown(Game.gc.getInput().KEY_B)) {
				shield();
			}
		} else if (player == 2) {
			if(Game.gc.getInput().isKeyDown(Game.gc.getInput().KEY_COMMA)) {
				punch(player);
			}
			if(Game.gc.getInput().isKeyDown(Game.gc.getInput().KEY_PERIOD)) {
				slam();
			}
			if(Game.gc.getInput().isKeyDown(Game.gc.getInput().KEY_SLASH)) {
				shield();
			}
		}
	}
	
	//punch attack
	public void punch(int player) {
		if (punchTimer == 0) {
			Game.attacks.add(new TankPunch(x, y + (h/4), faceDirection, player, w));
			punchTimer = 20;
		}
	}
	
	//shield ability
	public void shield() {
		if (shieldTimer == 0 && curEnergy >= 50) {
			shieldSubTimer = 40;
			shieldTimer = 90;
			curEnergy -= 50;
		}
	}
	
	//updates the shield
	public void updateShield() {
		if (shieldSubTimer == 0) {
			canTakeDamage = true;
		}
		if (shieldSubTimer > 0) {
			canTakeDamage = false;
		}
	}
	//draws shield when enabled
	public void renderShield(Graphics g) {
		if (shieldSubTimer >= 0) {
			g.setColor(new Color(173, 216, 230, 200));
			g.fillOval(x, y, w, h);
			g.setColor(new Color(255, 255, 255));
			g.drawOval(x, y, w, h);
		}
	}
	
	//slam down
	public void slam() {
		if (slamTimer == 0 && curEnergy >= 70) {
			Game.attacks.add(new TankSlam(x, y, w, h, player));
			ySubSpeed += 20; 
			curEnergy -= 70;
			slamSubTimer = 100;
		}
	}
	//controls when slam hits
	public void updateSlam() {
		if (!touchesPlatform && tankSlamTimer > 0) {
			y += ySubSpeed;
		}
		
		collideGround();
		collidePlatform();
		tankSlamTimer = slamSubTimer;
	}
	
	//ground collision
	public void collideGround() {
		if ((y + h) >= (Game.ground.getY() + 8)) {
			y = (Game.gc.getHeight() - Game.ground.getH()) - h + 8;
			jumpsLeft = 2;
			touchesGround = true;
			slamSubTimer = 0;
			ySubSpeed = 0;
		} else {
			touchesGround = false;
		}
	}
	
	//platform collision
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
					slamSubTimer = 0;
					ySubSpeed = 0;
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
}
