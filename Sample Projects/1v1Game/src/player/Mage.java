package player;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import attack.MageFireball;
import attack.MageIceBall;
import core.Game;
import core.Utility;

public class Mage extends Player{

	private int fireTimer, iceTimer, teleportTimer;
	private ArrayList<Float> xPositions;
	private ArrayList<Float> yPositions;
	private ArrayList<String> faceDirections;
	private ArrayList<Integer> healths;
	private Image teleportShadow;
	
	//constructor
	public Mage(float x, float y, int player) {
		super();
		xPositions = new ArrayList<Float>();
		yPositions = new ArrayList<Float>();
		faceDirections = new ArrayList<String>();
		healths = new ArrayList<Integer>();
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
		curHealth = 40;
		maxHealth = 40;
		for (int i = 0; i < 180; i++) {
			xPositions.add(x);
			yPositions.add(y);
			faceDirections.add(faceDirection);
			healths.add(curHealth);
		}
		initDirection();
		setImage("res/mageIdle1.png");
		setTeleportShadow("res/defaultImage.png");
	}

	//updates all mage
	public void update() {
		super.update();
		updatePercentage();
		runAbilities();
		updateTimers();
		updatePreviousPositions();
	}
	
	//draws mage based on direction facing
	public void render(Graphics g) {
		super.render(g);
		if (faceDirection == "left") {
			image.draw(x, y, w, h);
		} else if (faceDirection == "right"){
			image.draw((x + w), y, -w, h);
		}
		if (teleportTimer == 0) {
			if (faceDirections.get(0) == "left") {
				teleportShadow.draw(xPositions.get(0), yPositions.get(0), w, h);
			} else if (faceDirections.get(0) == "right"){
				teleportShadow.draw((xPositions.get(0) + w), yPositions.get(0), -w, h);
			}
		}
	}

	//updates the timers of abilities
	public void updateTimers() {
		fireTimer--;
		iceTimer--;
		teleportTimer--;
		if (fireTimer <= 0) {
			fireTimer = 0;
		}
		if (iceTimer <= 0) {
			iceTimer = 0;
		}
		if (teleportTimer <= 0) {
			teleportTimer = 0;
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

	public void updatePreviousPositions() {
		xPositions.add(x);
		xPositions.remove(0);
		yPositions.add(y);
		yPositions.remove(0);
		faceDirections.add(faceDirection);
		faceDirections.remove(0);
		healths.add(curHealth);
		healths.remove(0);
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
				fireball();
			}
			if(Game.gc.getInput().isKeyDown(Game.gc.getInput().KEY_V)) {
				iceball();
			}
			if(Game.gc.getInput().isKeyDown(Game.gc.getInput().KEY_B)) {
				teleport();
			}
		} else if (player == 2) {
			if(Game.gc.getInput().isKeyDown(Game.gc.getInput().KEY_COMMA)) {
				fireball();
			}
			if(Game.gc.getInput().isKeyDown(Game.gc.getInput().KEY_PERIOD)) {
				iceball();
			}
			if(Game.gc.getInput().isKeyDown(Game.gc.getInput().KEY_SLASH)) {
				teleport();
			}
		}
	}

	public void fireball() {
		if (fireTimer == 0 && curEnergy >= 20) {
			if (faceDirection == "left") {
				Game.attacks.add(new MageFireball(x, y + (h/2), faceDirection, player));
			} else if (faceDirection == "right") {
				Game.attacks.add(new MageFireball((x + w), y + (h/2), faceDirection, player));
			}
			fireTimer = 30;
			curEnergy -= 20;
		}
	}
	
	public void iceball() {
		if (iceTimer == 0 && curEnergy >= 20) {
			if (faceDirection == "left") {
				Game.attacks.add(new MageIceBall(x, y + (h/2), faceDirection, player));
			} else if (faceDirection == "right") {
				Game.attacks.add(new MageIceBall((x + w), y + (h/2), faceDirection, player));
			}
			iceTimer = 30;
			curEnergy -= 20;
		}
	}
		
	public void teleport() {
		if (teleportTimer == 0 && curEnergy >= 30) {
			teleportTimer += 180;
			curEnergy -= 30;
			x = xPositions.get(0);
			y = yPositions.get(0);
			faceDirection = faceDirections.get(0);
			curHealth = healths.get(0);
		}
	}
	
	public void setTeleportShadow(String filepath) {
		try
		{
			teleportShadow = new Image(filepath);
		}
		catch(SlickException e)		
		{
			System.out.println("Image not found!");
		}
	}
	
}
