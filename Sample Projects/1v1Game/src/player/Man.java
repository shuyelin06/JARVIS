package player;

import org.newdawn.slick.Graphics;

import core.Game;

public class Man extends Player{
	
	private int kickTimer, doubleKickTimer, heavenlyKickTimer;
	
	public Man(float x, float y, int player) {
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
		//ySpeed is 5
		ySpeed = (float) ((float) Game.gc.getHeight()/153.6);
		//jumpHeight is 20
		jumpHeight = (int) (Game.gc.getHeight()/38.4);
		curHealth = 500;
		maxHealth = 500;
		initDirection();
		setImage("res/man1.png");
	}

	//updates all man
	public void update() {
		super.update();
		updatePercentage();
		runAbilities();
		updateTimers();
	}
	
	//draws man based on direction facing
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
		kickTimer--;
		doubleKickTimer--;
		heavenlyKickTimer--;
		if (kickTimer <= 0) {
			kickTimer = 0;
		}
		if (doubleKickTimer <= 0) {
			doubleKickTimer = 0;
		}
		if (heavenlyKickTimer <= 0) {
			heavenlyKickTimer = 0;
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
				
			}
			if(Game.gc.getInput().isKeyDown(Game.gc.getInput().KEY_V)) {
				
			}
			if(Game.gc.getInput().isKeyDown(Game.gc.getInput().KEY_B)) {
				
			}
		} else if (player == 2) {
			if(Game.gc.getInput().isKeyDown(Game.gc.getInput().KEY_COMMA)) {
				
			}
			if(Game.gc.getInput().isKeyDown(Game.gc.getInput().KEY_PERIOD)) {
				
			}
			if(Game.gc.getInput().isKeyDown(Game.gc.getInput().KEY_SLASH)) {
				
			}
		}
	}
}
