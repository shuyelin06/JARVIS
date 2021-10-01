package player;

import org.newdawn.slick.Graphics;

import attack.RangerBullet;
import core.Game;
import player.Player;

public class Ranger extends Player{

	private int dashTimer, shotTimer, burstTimer;
	private int dashSubTimer, xSubSpeed, burstSubTimer, dashSpeed;
	
	//constructor
	public Ranger(float x, float y, int player) {
		super();
		this.player = player;
		this.x = x;
		this.y = y;
		//w is 50
		w = (float) ((float) Game.gc.getWidth()/27.32);
		//h is 100
		h = (float) ((float) Game.gc.getHeight()/7.68);
		//xSpeed is 10
		xSpeed = (float) ((float) Game.gc.getWidth()/136.6);
		dashSpeed = (int) xSpeed * 2;
		//ySpeed is 5
		ySpeed = (float) ((float) Game.gc.getHeight()/153.6);
		//jumpHeight is 20
		jumpHeight = (int) (Game.gc.getHeight()/38.4);
		curHealth = 40;
		maxHealth = 40;
		dashTimer = 0;
		shotTimer = 0;
		burstTimer = 0;
		dashSubTimer = 0;
		xSubSpeed = 0;
		initDirection();
		setImage("res/rangerIdle1.png");
	}

	//updates all ranger
	public void update() {
		super.update();
		updatePercentage();
		runAbilities();
		updateTimers();
		updateBurst();
		updateDash();
	}
	
	//draws ranger based on direction facing
	public void render(Graphics g) {
		super.render(g);
		if (faceDirection == "left") {
			image.draw(x, y, w, h);
		} else if (faceDirection == "right"){
			image.draw((x + w), y, -w, h);
		}
	}

	//updates the timers of abilities
	public void updateTimers() {
		dashTimer--;
		shotTimer--;
		burstTimer--;
		dashSubTimer--;
		burstSubTimer--;
		if (dashTimer <= 0) {
			dashTimer = 0;
		}
		if (shotTimer <= 0) {
			shotTimer = 0;
		}
		if (burstTimer <= 0) {
			burstTimer = 0;
		}
		if (dashSubTimer <= 0) {
			dashSubTimer = 0;
			xSubSpeed = 0;
		}
		if (burstSubTimer <= 0) {
			burstSubTimer = 0;
		}
	}
	
	//updates the percentage health and energy of player
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

	//bind keys
	public void runAbilities() {
		if (player == 1) {
			if(Game.gc.getInput().isKeyDown(Game.gc.getInput().KEY_C)) {
				shoot(player);
			}
			if(Game.gc.getInput().isKeyDown(Game.gc.getInput().KEY_V)) {
				burst(player);
			}
			if(Game.gc.getInput().isKeyDown(Game.gc.getInput().KEY_B)) {
				dash();
			}
		} else if (player == 2) {
			if(Game.gc.getInput().isKeyDown(Game.gc.getInput().KEY_COMMA)) {
				shoot(player);
			}
			if(Game.gc.getInput().isKeyDown(Game.gc.getInput().KEY_PERIOD)) {
				burst(player);
			}
			if(Game.gc.getInput().isKeyDown(Game.gc.getInput().KEY_SLASH)) {
				dash();
			}
		}
	}

	//single shot
	public void shoot(int player) {
		if (shotTimer == 0) {
			Game.attacks.add(new RangerBullet(x, y + (h/2), faceDirection, player));
			shotTimer = 40;
		}
	}
	
	//multi shot
	public void burst(int player) {
		if (shotTimer == 0 && curEnergy >= 70) {
			Game.attacks.add(new RangerBullet(x, y + (h/2), faceDirection, player));
			burstSubTimer = 30;
			curEnergy -= 70;
		}
	}
	
	//subtimer to shoot multiple times for burst
	public void updateBurst() {
		if (burstSubTimer > 0 && burstSubTimer % 5 == 0) {
			Game.attacks.add(new RangerBullet(x, y + (h/2), faceDirection, player));
		}
	}
	
	//dash
	public void dash() {
		if (curEnergy >= 20 && dashTimer == 0) {
			if (faceDirection == "right") {
				xSubSpeed = dashSpeed;
				dashSubTimer = 5;
				if (x >= (Game.gc.getWidth() - w)) {
					x = Game.gc.getWidth() - w;
				}
				curEnergy -= 20;
				dashTimer = 10;
			} else if (faceDirection == "left") {
				xSubSpeed = -dashSpeed;
				dashSubTimer = 5;
				if (x <= 0) {
					x = 0;
				}
				curEnergy -= 20;
				dashTimer = 10;
			}
		}
	}
	//makes dash invulnerable
	public void updateDash() {
		if (dashSubTimer != 0) {
			canTakeDamage = false;
		} else {
			canTakeDamage = true;
		}
		x += xSubSpeed;
	}
}
