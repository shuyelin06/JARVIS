package support;

import java.util.ArrayList;

import core.Coordinate;
import entities.Enemy;
import entities.Player;
import gamestates.Game;

public class Spawning {
	
	public static void spawnEnemy (Game g, float prob) { //expand on parameters like what kind of enemies to spawn in later
		//prob is the percent chance that you want a new enemy to spawn; scale it to how often the update method gets called
		if(g.getEnemies().size() < 5) {
			if(Utility.random(0.0, 100.0) <= prob) {
				//for now it just drops a new enemy on the player's head
				float x = g.getP().getPosition().getX();
				float y = g.getP().getPosition().getY();
				g.getEnemies().add(new Enemy(x, y));
				//System.out.println("New Enemy: " + x + ", " + y);
				
			}
		}
	}
	
	public static Coordinate getOpenArea(Game g, int minDistance, int maxDistance) {
		float playerX = g.getP().getPosition().getX();
		float playerY = g.getP().getPosition().getY();
		Coordinate coord = new Coordinate(playerX, playerY);
		int direction = 1;
		if(Utility.random(0, 1) == 1) {
			direction = -1;
		}
		int[] blockIndex = g.getWorld().getBlockIndex(g.getP().getPosition());
		float chunkNum = g.getP().getPosition().getChunk();
		
		for(int i = minDistance; i < maxDistance; i ++) {
			coord.setX(playerX + i * direction);
			coord.setY(playerY);
			//if the block at coord is empty, check down until you hit the ground
			//if block at coord has an actual block, keep checking to the sides
			//check the area around the block to make sure it is all open (make a method for this)
		}
		
		
		
		return coord;
	}
	
	//loops from end to beginning of ArrayList and removes all the dead enemies from the list
	public static void clearDead (Game g) {
		for(int i = g.getEnemies().size()-1; i > -1; i--) {  
			if(!g.getEnemies().get(i).isAlive()) {
				g.getEnemies().remove(i);
			}
			
		}
	}

}
