package support;

import java.util.ArrayList;

import org.newdawn.slick.SlickException;

import core.Coordinate;
import entities.Enemy;
import entities.Player;
import gamestates.Game;
import structures.Block;
import world.Chunk;
import world.World;

public class Spawning {
	//static World world;
	
	public static void spawnEnemy (Game g, float prob) throws SlickException { //expand on parameters like what kind of enemies to spawn in later

  //prob is the percent chance that you want a new enemy to spawn; scale it to how often the update method gets called
 		if(g.getEnemies().size() < 5) {
 			if(Utility.random(0.0, 100.0) <= prob) {
 				//for now it just drops a new enemy on the player's head
 				float x = g.getP().getPosition().getX();
 				float y = g.getP().getPosition().getY();
 				Coordinate coord = getOpenArea(g, 15, 25, 10, 1, 1, prob);
 				if(coord != null) {
 					g.getEnemies().add(new Enemy(coord.getX(), coord.getY(), Game.world));
 				}
 				
 				//System.out.println("New Enemy: " + x + ", " + y);
			
 			}
 		}
	}
	
	public static Coordinate getOpenArea(Game g, int minDistance, int maxDistance, int elevationDiff, int w, int h, float prob) {
		float playerX = g.getP().getPosition().getX();
		float playerY = g.getP().getPosition().getY();
		Coordinate coord = new Coordinate(playerX, playerY);
		int direction = 1;
		if(Utility.random(0, 1) == 1) {
			direction = -1;
		}
		int[] blockIndex = Game.world.getBlockIndex(g.getP().getPosition());
		
		for(int i = minDistance; i < maxDistance; i ++) {
			if(Utility.random(0.0, 100.0) <= prob) {
				
			coord.setX(playerX + i * direction);
			if(coord.getX() > Game.world.World_X_Size * Chunk.Chunk_Size_X - w
					|| coord.getX() < w) {
				return null;
			}
			if(coord.getY() <= elevationDiff + h) {
				return null;
			}
			
			coord.setY(playerY);
			int indexX = Game.world.getBlockIndex(coord)[0];
			//System.out.println("indexX: " + indexX);
			
			int indexY = Game.world.getBlockIndex(coord)[1];
			float indexChunk = coord.getChunk();
			Block[][] blocks = Game.world.getChunk((int) indexChunk).getBlocks();
			//if block at indexX, indexY of the indexChunkth chunk is open air
			//System.out.println("block ID getting checked: " + blocks[indexX][indexY].getID());
			
			if(blocks[indexX][indexY].getID() == 0) { 
				for(int j = (int)playerY; j > (int)playerY - elevationDiff; j--) {
					//System.out.println(blocks[indexX][j-1].getID());
					if(Game.world.openArea(coord, w, h) && blocks[indexX][j-1].getID() != 0) {
						coord.setY(j);						
						return coord;
					}
					
				}
			}
//			if(/* if block at indexX, indexY of the indexChunkth chunk is open air*/) {
//				//then start checking down to find the ground until it is too far away
					//within this, have a method that checks like a 3x3 area or something
//			} /* else, continue in the loop to check farther to the sides*/
			}
		}
		
		
		
		return null;
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
