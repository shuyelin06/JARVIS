package player;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import core.Actor;
import core.Game;
import core.Utility;
import enemy.RedShot;

public class Player extends Friendly
{
	private int shotTimer;
	private int maxTimer;
	
	public Player()
	{
		//initialize player
		super();
		setImage("res/yellowPlayer.png");
		w = 72;
		h = 50;
		x = (Game.gc.getWidth() / 2) - ((float) 0.5*h);
		y = Game.gc.getHeight() - 200;
		xSpeed = 7;
		ySpeed = 7;
		curHealth = 50;
		maxHealth = 50;
		maxTimer = 20;
		shotTimer = 0;
		Game.lives = 3;
		value = 0;
	}
	
	//accessor for lives
	public int getLives() {
		return Game.lives;
	}
	
	//accessor for X and Y
	public float getX() {
		return x;
	}
	public float getY() {
		return y;
	}
	
	//accessor for health and maxHealth
	public float getHealth() {
		return curHealth;
	}
	public float getMaxHealth() {
		return maxHealth;
	}
	
	//adds lives to player
	public void addLives(int amount) {
		Game.lives = Game.lives + amount;
	}
	
	//updates player
	public void update()
	{
		controls();
		//runs shotTimer
		if (shotTimer > 0) {
			shotTimer --;
		}
		
		//ends game when done, end screen
		if ((curHealth <= 0) && (Game.lives == 1)) {
			Game.paused = true;
			Game.screen = 3;
			Game.end = true;
		}
	}
	
	//adds health to player, caps at maxHealth
	public void addHealth(int health) {
		curHealth += health;
		if (curHealth > maxHealth) {
			curHealth = maxHealth;
		}
	}
	
	//renders health bar for player
	public void render(Graphics g) throws SlickException {
		super.render(g);
		displayHealthBar(g);
	}
	
	//takes damage for player
	//removes lives when out of health
	//respawn when life decrease
	public void takeDamage(float amount)
	{
		curHealth -= amount;
		if (curHealth <= 0) {
			curHealth = 0;
			if (Game.lives == 1) {
				canRemove = true;
			} else {
				Game.lives--;
				System.out.println("Respawned Player");
				System.out.println("Lives Left: " + Game.lives);
				Game.paused = true;
				Game.screen = 4;
				Game.canClear = true;
				resetLocation();
				curHealth = 50;
			}
		}
	}

	//reset location of player
	public void resetLocation() {
		x = (Game.gc.getWidth() / 2) - ((float) 0.5*h);
		y = Game.gc.getHeight() - 200;
	}
	
	//player dynamic health bar
	public void displayHealthBar(Graphics g) {
		float percentage = curHealth/maxHealth;
		final float BAR_WIDTH = 400;
		final float BAR_HEIGHT = 30;
		g.setColor(new Color(0, 100, 0));
		g.fillRect((Game.gc.getWidth()/2) - (BAR_WIDTH/2), Game.gc.getHeight() - 50, BAR_WIDTH, BAR_HEIGHT);
		g.setColor(new Color(0, 255, 0));
		g.fillRect((Game.gc.getWidth()/2) - (BAR_WIDTH/2), Game.gc.getHeight() - 50, BAR_WIDTH*percentage, BAR_HEIGHT);
		g.setColor(new Color(255, 255, 255));
		g.drawRect((Game.gc.getWidth()/2) - (BAR_WIDTH/2), Game.gc.getHeight() - 50, BAR_WIDTH, BAR_HEIGHT);
	}
	
	//moves player, shoots bullets on key press
	public void controls()
	{
		//movement
		//up
		if(Game.gc.getInput().isKeyDown(Game.gc.getInput().KEY_W))
		{
			y -= ySpeed;
			//does not allow player to reach top of screen in hard mode
			if (Game.difficulty == "hard") {
				if (y < 50) {
					y = 50;
				}
			}
			if (y < 0) {
				y = 0;
			}
		}
		//left
		if(Game.gc.getInput().isKeyDown(Game.gc.getInput().KEY_A))
		{
			x -= xSpeed;
			if (x < 0) {
				x = 0;
			}
		}
		//down
		if(Game.gc.getInput().isKeyDown(Game.gc.getInput().KEY_S))
		{
			y += ySpeed;
			if (y > (Game.gc.getHeight()-h)) {
				y = Game.gc.getHeight()-h;
			}
		}
		//right
		if(Game.gc.getInput().isKeyDown(Game.gc.getInput().KEY_D))
		{
			x += xSpeed;
			if (x > (Game.gc.getWidth()-w)) {
				x = Game.gc.getWidth()-w;
			}
		}
		
		//weapons
		//basic gun
		if(shotTimer == 0 && Game.gc.getInput().isKeyDown(Game.gc.getInput().KEY_SPACE))
		{
			Game.actors.add(new BasicShot(x + (w/3), y+(h/2), 0, -5, Game.shotDamage));
			Game.actors.add(new BasicShot(x + ((2*w)/3), y+(h/2), 0, -5, Game.shotDamage));
			shotTimer = maxTimer;
		}
		//shotgun
		if(shotTimer == 0 && Game.gc.getInput().isKeyDown(Game.gc.getInput().KEY_B)) {
			if (Game.hasShotgun) {
				for (int i = 0; i < 5; i++) {
					Game.actors.add(new BasicShot(x+(w/2), y+(h/2), Utility.random((double)-5, (double)5), Utility.random((double)-7, (double)-3), Game.shotDamage));
				}
				shotTimer = maxTimer;
			}
		}
		//circle gun
		if(shotTimer == 0 && Game.gc.getInput().isKeyDown(Game.gc.getInput().KEY_N)) {
			if (Game.hasCircleGun) {
				for (int i = 0; i < 15; i++) {
					Game.actors.add(new BasicShot(x+(w/2), y+(h/2), Utility.random((double)-5, (double)5), Utility.random((double)-5, (double)5), Game.shotDamage));
				}
				shotTimer = maxTimer;
			}
		}
	}
}
