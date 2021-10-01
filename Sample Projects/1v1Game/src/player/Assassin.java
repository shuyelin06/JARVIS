package player;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import attack.AssassinSwing;
import attack.AssassinThrow;
import attack.RangerBullet;
import attack.TankPunch;
import core.Game;

public class Assassin extends Player {
	private int swingTimer, throwTimer, boostTimer;
	private int boostSubTimer;
	private float baseXSpeed, doubleXSpeed;

	//constructor
	public Assassin(float x, float y, int player) {
		super();
		this.player = player;
		this.x = x;
		this.y = y;
		//w is 19
		w = (float) ((float) Game.gc.getWidth()/27.32);
		//h is 67
		h = (float) ((float) Game.gc.getHeight()/7.11);
		//xSpeed is 10
		xSpeed = (float) ((float) Game.gc.getWidth()/136.6);
		baseXSpeed = xSpeed;
		doubleXSpeed = xSpeed*2;
		//ySpeed is 5
		ySpeed = (float) ((float) Game.gc.getHeight()/153.6);
		//jumpHeight is 20
		jumpHeight = (int) (Game.gc.getHeight()/38.4);
		curHealth = 40;
		maxHealth = 40;
		curEnergy = 50;
		swingTimer = 0;
		throwTimer = 0;
		boostTimer = 0;
		boostSubTimer = 0;
		initDirection();
		setImage("res/assassinIdle.png");
		image.setFilter(Image.FILTER_NEAREST);
	}

	//updates
	public void update() {
		super.update();
		updatePercentage();
		runAbilities();
		updateTimers();
		updateBoost();
	}

	//draws facing direction
	public void render(Graphics g) {
		super.render(g);
		if (faceDirection == "left") {
			image.draw(x, y, w, h);
			if (boostSubTimer > 0) {
				g.drawLine(x + w, y + 2*(h/6), x + (3*(w/2)), y + 2*(h/6));
				g.drawLine(x + w, y + 3*(h/6), x + (3*(w/2)), y + 3*(h/6));
				g.drawLine(x + w, y + 4*(h/6), x + (3*(w/2)), y + 4*(h/6));
			}
		} else if (faceDirection == "right") {
			image.draw((x + w), y, -w, h);
			if (boostSubTimer > 0) {
				g.drawLine(x, y + 2*(h/6), x - (w/2), y + 2*(h/6));
				g.drawLine(x, y + 3*(h/6), x - (w/2), y + 3*(h/6));
				g.drawLine(x, y + 4*(h/6), x - (w/2), y + 4*(h/6));
			}
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

	//update timers
	public void updateTimers() {
		swingTimer--;
		throwTimer--;
		boostTimer--;
		boostSubTimer--;
		if (swingTimer <= 0) {
			swingTimer = 0;
		}
		if (throwTimer <= 0) {
			throwTimer = 0;
		}
		if (boostTimer <= 0) {
			boostTimer = 0;
		}
		if (boostSubTimer <= 0) {
			boostSubTimer = 0;
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

	//bind controls
	public void runAbilities() {
		if (player == 1) {
			if(Game.gc.getInput().isKeyDown(Game.gc.getInput().KEY_C)) {
				swingDagger(player);
			}
			if(Game.gc.getInput().isKeyDown(Game.gc.getInput().KEY_V)) {
				throwDagger(player);
			}
			if(Game.gc.getInput().isKeyDown(Game.gc.getInput().KEY_B)) {
				boost();
			}
		} else if (player == 2) {
			if(Game.gc.getInput().isKeyDown(Game.gc.getInput().KEY_COMMA)) {
				swingDagger(player);
			}
			if(Game.gc.getInput().isKeyDown(Game.gc.getInput().KEY_PERIOD)) {
				throwDagger(player);
			}
			if(Game.gc.getInput().isKeyDown(Game.gc.getInput().KEY_SLASH)) {
				boost();
			}
		}
	}

	//swings dagger forward
	public void swingDagger(int player) {
		if (swingTimer == 0) {
			Game.attacks.add(new AssassinSwing(x, y + (h/4), faceDirection, player, w));
			swingTimer = 20;
		}
	}

	//throws dagger forward
	public void throwDagger(int player) {
		if (throwTimer == 0 && curEnergy >= 35) {
			if (faceDirection == "left") {
				Game.attacks.add(new AssassinThrow(x, y + (h/3), faceDirection, player));
			} else if (faceDirection == "right") {
				Game.attacks.add(new AssassinThrow((x + w), y + (h/3), faceDirection, player));
			}
			throwTimer = 60;
			curEnergy -= 35;
		}
	}

	//boost
	public void boost() {
		if (boostTimer == 0 && curEnergy >= 50) {
			boostTimer = 200;
			boostSubTimer = 100;
			curEnergy -= 50;
		}
	}
	//updates speed depending on boost
	public void updateBoost() {
		if (boostSubTimer == 0) {
			xSpeed = baseXSpeed;
		}
		if (boostSubTimer > 0) {
			xSpeed = doubleXSpeed;
		}
	}
}