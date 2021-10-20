package support;

import java.util.ArrayList;

import core.Coordinate;
import entities.Enemy;
import entities.Player;
import gamestates.Game;
import world.World;

public class Spawning {
	static World world;
	
	public static void spawnEnemy (Game g, float prob) { //expand on parameters like what kind of enemies to spawn in later

  //prob is the percent chance that you want a new enemy to spawn; scale it to how often the update method gets called
 		if(g.getEnemies().size() < 5) {
 			if(Utility.random(0.0, 100.0) <= prob) {
 				//for now it just drops a new enemy on the player's head
 				float x = g.getP().getPosition().getX();
 				float y = g.getP().getPosition().getY();
 				g.getEnemies().add(new Enemy(x, y, g.getWorld()));
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
		
		for(int i = minDistance; i < maxDistance; i ++) {
			coord.setX(playerX + i * direction);
			coord.setY(playerY);
			int indexX = g.getWorld().getBlockIndex(coord)[0];
			int indexY = g.getWorld().getBlockIndex(coord)[1];
			float indexChunk = coord.getChunk();
//			if(/* if block at indexX, indexY of the indexChunkth chunk is open air*/) {
//				//then start checking down to find the ground until it is too far away
					//within this, have a method that checks like a 3x3 area or something
//			} /* else, continue in the loop to check farther to the sides*/
			
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
