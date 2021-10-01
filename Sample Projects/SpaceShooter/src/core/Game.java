package core;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import enemy.Boss;
import enemy.BurstSentry;
import enemy.Enemy;
import enemy.HomingMissile;
import enemy.RedSquare;
import enemy.Sentry;
import enemy.SmallEnemy;
import player.Friendly;
import player.Player;

public class Game extends BasicGameState 
{		
	private int id;
	public static int level;
	public static boolean debugMode = true;
	public static int gameTime;
	public static boolean paused;
	public static GameContainer gc;
	public static Player player;
	public static ArrayList<Actor> actors;
	public static ArrayList<Star> stars;
	public static int screen;
	public static boolean end;
	public static int money;
	public static String difficulty;
	public static boolean hasShotgun;
	public static boolean hasCircleGun;
	public static boolean hasDamage;
	public static boolean canClear;
	public static int shotDamage;
	public static int lives;
	public static Shop shop;
	
	Game(int id) 
	{
		this.id = id;
	}
	
	public int getID() 
	{
		return id;
	}
	
	//initialize all variables, make ArrayLists of actors and stars
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException 
	{
		this.gc = gc;
		gc.setShowFPS(false);
		actors = new ArrayList<Actor>();
		stars = new ArrayList<Star>();
		shop = new Shop();
		
		for(int i = 0; i < 200; i++) {
			stars.add(new Star());
		}
		
		screen = 0;
		paused = true;
		hasShotgun = false;
		hasCircleGun = false;
		hasDamage = false;
		canClear = false;
		shotDamage = 1;
		difficulty = "easy";
		
		player = new Player();
		actors.add(player);
		
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException 
	{
		//background and stars, upper bound
		g.setBackground(new Color(0, 0, 0));
		//shows boundary for hard mode
		if (difficulty == "hard") {
			g.setColor(new Color(255, 0, 0, 100));
			g.fillRect(0, 0, gc.getWidth(), 50);
			g.setColor(new Color(255, 255, 255));
			g.drawLine(0, 50, gc.getWidth(), 50);
		}
		for(int i = 0; i < stars.size(); i++) {
			stars.get(i).render(g);
		}
		//actors
		g.setColor(new Color(0, 0, 0));
		for(Actor a : actors)
		{
			a.render(g);
		}
		
		//top left screen, list of stats
		g.setColor(new Color(255, 255, 255));
		g.drawString("Level: " + level, 10, 10);
		g.drawString("Money: " + money, 10, 30);
		g.drawString("Lives: " + lives, 10, 50);
		g.setColor(new Color(0, 0, 0));
		
		//debug
		if (debugMode) {
			g.drawString(""+actors.size(), 5, 5);
		}
		
		if (paused) {
			if (screen == 0) {
				//starting screen
				g.setColor(new Color(50, 50, 50));
				g.fillRect(0, 0, gc.getWidth(), gc.getHeight());
				g.setColor(new Color(255, 255, 255));
				g.drawString("Press any key to Start", (gc.getWidth()/2)-100, (gc.getHeight()/2) - 30);
				g.drawString("WASD to move, Space to shoot", (gc.getWidth()/2)-120, (gc.getHeight()/2) - 15);
				g.drawString("Press P to go to shop/pause", (gc.getWidth()/2)-115, (gc.getHeight()/2));
				g.drawString("Beat 7 levels to win", (gc.getWidth()/2)-90, (gc.getHeight()/2) + 15);
				g.setColor(new Color(255, 0, 0));
				g.drawString("Press H for hard mode", (gc.getWidth()/2)-95, (gc.getHeight()/2) + 30);
			}
			if (screen == 1) {
				//pause screen
				g.setColor(new Color(50, 50, 50));
				g.fillRect(0, 0, gc.getWidth(), gc.getHeight());
				g.setColor(new Color(255, 255, 255));
				g.drawString("Paused Game", (gc.getWidth()/2)-50, 10);
				g.drawString("P to unpause", (gc.getWidth()/2)-55, 25);
				g.drawString("Level: " + level, 10, 10);
				g.drawString("Money: " + money, 10, 30);
				g.drawString("Lives: " + lives, 10, 50);
				
				//draw shop
				renderShop(gc, sbg, g);
				shop.render(g);
				
				//update player to display health bar in shop
				player.update();
				player.displayHealthBar(g);
			}
			if (screen == 2) {
				//win screen
				g.setColor(new Color(50, 50, 50));
				g.fillRect(0, 0, gc.getWidth(), gc.getHeight());
				g.setColor(new Color(255, 255, 255));
				g.drawString("You Win!", (gc.getWidth()/2)-30, gc.getHeight()/2);
			}
			if (screen == 3) {
				//lose screen
				g.setColor(new Color(50, 50, 50));
				g.fillRect(0, 0, gc.getWidth(), gc.getHeight());
				g.setColor(new Color(255, 255, 255));
				g.drawString("Game Over", (gc.getWidth()/2)-30, gc.getHeight()/2);
				g.drawString(":(", (gc.getWidth()/2)-4, (gc.getHeight()/2) + 15);
			}
			if (screen == 4) {
				//respawn screen
				g.setColor(new Color(50, 50, 50));
				g.fillRect(0, 0, gc.getWidth(), gc.getHeight());
				g.setColor(new Color(255, 255, 255));
				g.drawString("Uh Oh, You Died!", (gc.getWidth()/2)-80, (gc.getHeight()/2) - 15);
				g.drawString("Press any key to respawn", (gc.getWidth()/2)-120, (gc.getHeight()/2));
			}
		}
	}

	public void renderShop(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		//list of items
		g.setColor(new Color(255, 255, 255));
		g.drawString("Items: ", (gc.getWidth() / 3) - 20, (gc.getHeight() / 3));
		g.drawString("+10 Health", (gc.getWidth() / 3) - 20, (gc.getHeight() / 3)+ 15);
		g.drawString("+1 Damage", (gc.getWidth() / 3) - 20, (gc.getHeight() / 3)+ 30);
		g.drawString("Shotgun (B)", (gc.getWidth() / 3) - 20, (gc.getHeight() / 3)+ 45);
		g.drawString("Circle Gun (N)", (gc.getWidth() / 3) - 20, (gc.getHeight() / 3)+ 60);
		
		//show player if items bought
		g.setColor(new Color(0, 255, 0));
		if (hasDamage) {
			g.drawString("Damage: " + shotDamage, (gc.getWidth() / 3) - 150, (gc.getHeight() / 3)+ 30);
		}
		if (hasShotgun) {
			g.drawString("Bought", (gc.getWidth() / 3) - 130, (gc.getHeight() / 3)+ 45);
		}
		if (hasCircleGun) {
			g.drawString("Bought", (gc.getWidth() / 3) - 130, (gc.getHeight() / 3)+ 60);
		}
		g.setColor(new Color(255, 255, 255));
		
		//item cost
		g.drawString("Cost: ", 2*(gc.getWidth() / 3) - 20, (gc.getHeight() / 3));
		g.drawString("10 money", 2*(gc.getWidth() / 3) - 20, (gc.getHeight() / 3)+ 15);
		g.drawString("30 money", 2*(gc.getWidth() / 3) - 20, (gc.getHeight() / 3)+ 30);
		g.drawString("50 money", 2*(gc.getWidth() / 3) - 20, (gc.getHeight() / 3)+ 45);
		g.drawString("100 money", 2*(gc.getWidth() / 3) - 20, (gc.getHeight() / 3)+ 60);
		
		//key to buy item
		g.drawString("Press [key] to buy: ", (gc.getWidth() / 2) - 80, (gc.getHeight() / 3));
		g.drawString("1", (gc.getWidth() / 2), (gc.getHeight() / 3)+ 15);
		g.drawString("2", (gc.getWidth() / 2), (gc.getHeight() / 3)+ 30);
		g.drawString("3", (gc.getWidth() / 2), (gc.getHeight() / 3)+ 45);
		g.drawString("4", (gc.getWidth() / 2), (gc.getHeight() / 3)+ 60);
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException
	{	
		if (!paused) {
			//increase game time as game updates
			gameTime++;
			collisions();
			action();
			cleanUp();
			clearOffScreen();
			spawnEnemiesPeriodic();
			advanceLevel();
			clearBoard();
		}
	}
	
	public void advanceLevel() {
		//advances level if ready, ends game if all levels complete
		if (readyForNextLevel()) {
			if (level < 7) {
				level++;
				updateEnemies();
			} else {
				paused = true;
				screen = 2;
				end = true;
			}
			
		}
	}
	
	public void updateEnemies () {
		//spawns enemies depending on level
		if (level == 1) {
			spawnSmallEnemy(1);
		} else if (level == 2) {
			spawnRedSquare(1);
			spawnSmallEnemy(2);
		} else if (level == 3) {
			spawnSentry(2);
			spawnRedSquare(2);
		} else if (level == 4) {
			spawnBurstSentry(3);
		} else if (level == 5) {
			spawnBurstSentry(2);
			spawnRedSquare(2);
		} else if (level == 6) {
			spawnBurstSentry(3);
			spawnSentry(2);
			spawnRedSquare(4);
		} else if (level == 7) {
			spawnBurstSentry(5);
			spawnRedSquare(5);
		}
	}
	
	public void spawnEnemiesPeriodic() {
		//periodic enemies by level
		if (level == 1) {
			if (gameTime % 300 == 0) {
				spawnSmallEnemy(1);
			}
		} else if (level == 2) {
			if (gameTime % 300 == 0) {
				spawnSmallEnemy(1);
			}
		} else if (level == 3) {
			if (gameTime % 600 == 0) {
				spawnRedSquare(1);
			}
			if (gameTime % 300 == 0) {
				spawnSmallEnemy(1);
			}
		} else if (level == 4) {
			if (gameTime % 300 == 0) {
				spawnSentry(1);
			}
		} else if (level == 5) {
			if (gameTime % 300 == 0) {
				spawnSentry(1);
			}
			if (gameTime % 400 == 0) {
				spawnSmallEnemy(1);
			}
		} else if (level == 6) {
			if (gameTime % 300 == 0) {
				spawnSentry(1);
			}
			if (gameTime % 400 == 0) {
				spawnSmallEnemy(1);
			}
		} else if (level == 7) {
			if ((gameTime - 100) % 200 == 0) {
				spawnBurstSentry(1);
			}
			if (gameTime % 200 == 0) {
				spawnRedSquare(1);
			}
			if (gameTime % 100 == 0) {
				spawnHomingMissile(1);
			}
		}
	}
	
	public void spawnSmallEnemy(int num) {
		//SmallEnemy class
		for (int i = 0; i < num; i++) {
			actors.add(new SmallEnemy());
		}
	}
	
	public void spawnRedSquare(int num) {
		//RedSquare class
		for (int i = 0; i < num; i++) {
			actors.add(new RedSquare());
		}
	}
	
	public void spawnSentry(int num) {
		//Sentry class
		for (int i = 0; i < num; i++) {
			actors.add(new Sentry());
		}
	}
	
	public void spawnBurstSentry(int num) {
		//BurstSentry class
		for (int i = 0; i < num; i++) {
			actors.add(new BurstSentry());
		}
	}
	
	public void spawnBoss(int num) {
		//Boss class (unused)
		for (int i = 0; i < num; i++) {
			actors.add(new Boss());
		}
	}
	
	public void spawnHomingMissile(int num) {
		//HomingMissile class (projectile)
		for (int i = 0; i < num; i++) {
			actors.add(new HomingMissile(Utility.random(0, gc.getWidth()), Utility.random(0, gc.getHeight())));
		}
	}
	
	public boolean readyForNextLevel() {
		//loop through all actors
		//see if any alive
		//see if they are enemies
		for (Actor a: actors) {
			if (a instanceof Enemy && !a.isProjectile()) {
				return false;
			}
		}
		return true;
	}
	
	public void collisions() {
		// Collisions
		for(Actor a : actors)
		{
			for(Actor b : actors)
			{
				if(a.collidesWith(b))
				{
					a.hitBy(b);
					b.hitBy(a);
				}
			}
		}
	}
	
	public void clearBoard() {
		//clears the board if canClear is true
		if (canClear) {
			for(int i = 0; i < actors.size(); i++)
			{
				if(actors.get(i).isProjectile())
				{
					actors.remove(actors.get(i));
					i--;
				}
			}
			canClear = false;
		}
	}
	
	public void action() {
		//update stars
		for(int i = 0; i < stars.size(); i++) {
			stars.get(i).update();
		}
		
		//update actors
		for(int i = 0; i < actors.size(); i++)
		{
			actors.get(i).update();
		}
	}
	
	public void cleanUp() {
		// Clean Up
		for(int i = 0; i < actors.size(); i++)
		{
			if(actors.get(i).canRemove())
			{
				actors.remove(actors.get(i));
				i--;
			}
		}
	}
	
	public void clearOffScreen () {
		//clear off screen projectiles
		for (Actor a : actors) 
		{
			if (a.isProjectile() && a.isOffScreen()) {
				a.die();
			}
		}
	}
	
	public void keyPressed(int key, char c)
	{
		//pauses and unpauses game depending on screen
		if (screen == 0 && key == Input.KEY_H) {
			difficulty = "hard";
		}
		if (key == Input.KEY_P || (screen == 0 || screen == 4)) {
			if (paused) {
				if (!end) {
					paused = false;
					screen = 1;
				}
			} else {
				if (key == Input.KEY_P) {
					paused = true;
				}
			}
		}
		
		keySpawn(key, c);
		shop(key, c);
	}
	public void keySpawn (int key, char c) {
		//spawn enemies on key
		if (!paused) {
			if (key == Input.KEY_1) {
				spawnSmallEnemy(1);
			}
			if (key == Input.KEY_2) {
				spawnRedSquare(1);
			}
			if (key == Input.KEY_3) {
				spawnSentry(1);
			}
			if (key == Input.KEY_4) {
				spawnBurstSentry(1);
			}
			if (key == Input.KEY_5) {
				spawnBoss(1);
			}
			if (key == Input.KEY_9) {
				spawnHomingMissile(10);
			}
			//increase money
			if (key == Input.KEY_8) {
				money += 100;
			}
			//spawn all enemies and double money
			if (key == Input.KEY_0) {
				spawnSmallEnemy(10);
				spawnRedSquare(10);
				spawnSentry(10);
				spawnBurstSentry(10);
				spawnBoss(1);
				money += money;
			}
		}
	}
	public void shop (int key, char c) {
		//shop functions
		if (paused && screen == 1) {
			if (key == Input.KEY_1) {
				buyHealth();
			} else if (key == Input.KEY_2) {
				buyDamage();
			} else if (key == Input.KEY_3){
				buyShotgun();
			} else if (key == Input.KEY_4) {
				buyCircleGun();
			}
		}
	}
	public void buyHealth() {
		//increase health in shop
		if ((money >= 10) && (player.getHealth() < player.getMaxHealth())) {
			money -= 10;
			player.addHealth(10);
		}
	}
	public void buyShotgun() {
		//buy shotgun weapon
		if (money >= 50 && !hasShotgun) {
			money -= 50;
			hasShotgun = true;
		}
	}
	public void buyDamage() {
		//increase damage in shop
		if (money >= 30) {
			money -= 30;
			shotDamage ++;
			hasDamage = true;
		}
	}
	public void buyCircleGun() {
		//buy circle gun weapon
		if (money >= 100 && !hasCircleGun) {
			money -= 100;
			hasCircleGun = true;
		}
	}
}
