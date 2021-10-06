package support;

import java.util.ArrayList;

import entities.Enemy;
import entities.Player;

public class Spawning {
	
	public static void spawnEnemy (Player p, ArrayList<Enemy> enemies, float prob) { //expand on parameters like what kind of enemies to spawn in later
		//prob is the percent chance that you want a new enemy to spawn; scale it to how often the update method gets called
		if(Utility.random(0.0, 100.0) <= prob) {
			//for now it just drops a new enemy on the player's head
			enemies.add( new Enemy( p.getPosition().getX() , (p.getPosition().getY() + 20) ) );
		}
		
	}
	
	//loops from end to beginning of ArrayList and removes all the dead enemies from the list
	public static void clearDead (ArrayList<Enemy> enemies) {
		for(int i = enemies.size()-1; i > -1; i--) {  //using > -1 instead of >= 0 cause learning c++ is just cool like that
			if(!enemies.get(i).isAlive()) {
				enemies.remove(i);
			}
			
		}
	}

}
