package support;

import java.util.ArrayList;

import entities.Enemy;
import entities.Player;
import gamestates.Game;
import world.World;

public class Spawning {
	static World world;
	
	public static void spawnEnemy (Game g, float prob) { //expand on parameters like what kind of enemies to spawn in later
//		//prob is the percent chance that you want a new enemy to spawn; scale it to how often the update method gets called
//		if(g.getEnemies().size() < 5) {
//			if(Utility.random(0.0, 100.0) <= prob) {
//				//for now it just drops a new enemy on the player's head
//				g.getEnemies().add( new Enemy( g.getP().getPosition().getX() , (g.getP().getPosition().getY() - 100), world) );
//			}
//		}
	}
	
	//loops from end to beginning of ArrayList and removes all the dead enemies from the list
	public static void clearDead (Game g) {
//		for(int i = g.getEnemies().size()-1; i > -1; i--) {  
//			if(!g.getEnemies().get(i).isAlive()) {
//				g.getEnemies().remove(i);
//			}
//			
//		}
	}

}
