package support;

import org.newdawn.slick.SlickException;

import core.Coordinate;
import core.Engine;
import core.Values;
import entities.Entity.EntType;
import entities.living.*;
import gamestates.Game;
import structures.Block;

public class Spawning {
	public static void spawnEnemy (Game g, float prob) throws SlickException { //expand on parameters like what kind of enemies to spawn in later

		//prob is the percent chance that you want a new enemy to spawn; scale it to how often the update method gets called
 		if(g.getEntities(EntType.Hostiles).size() < 5) {
 			if(!safeZone(g) && Utility.random(0.0, 100.0) <= prob) {
 				//for now it just drops a new enemy on the player's head
 				float x = g.getPlayer().getPosition().getX();
 				float y = g.getPlayer().getPosition().getY();
 				Coordinate coord = getOpenArea(g, 15, 25, 20, 1, 1, prob);
 				if(coord != null) {
 					g.getEntities(EntType.Hostiles).add(new Enemy(coord.getX(), coord.getY()));
 				}
 				
 				//System.out.println("New Enemy: " + x + ", " + y);
			
 			}
 		}
	}
	
	//for now just checks if coordinates of player is near the spawn point, later change this to biomes
	public static boolean safeZone(Game g) {
		float x = g.getPlayer().getPosition().getX();
		float y = g.getPlayer().getPosition().getY();
		int rangeX = 32;
		int rangeY = 128;
		if(x > Values.SpawnX-rangeX && x < Values.SpawnX+rangeX) {
			if(y > Values.SpawnY-rangeY && y < Values.SpawnY+rangeY) {
				return true;
			}
		}
		return false;
	}
	
	public static Coordinate getOpenArea(Game g, int minDistance, int maxDistance, int elevationDiff, int w, int h, float prob) {
		float playerX = g.getPlayer().getPosition().getX();
		float playerY = g.getPlayer().getPosition().getY();
		Coordinate coord = new Coordinate(playerX, playerY);
		int direction = 1;
		if(Utility.random(0, 1) == 1) {
			direction = -1;
		}
		int[] blockIndex = Engine.game.getWorld().getBlockIndex(g.getPlayer().getPosition());
		
		for(int i = minDistance; i < maxDistance; i ++) {
			if(Utility.random(0.0, 100.0) <= prob) {
				
			coord.setX(playerX + i * direction);
			
			//so that it doesn't check out of world
			if(coord.getX() > Values.World_X_Size * Values.Chunk_Size_X - w
					|| coord.getX() < w) {
				return null;
			}
			if(coord.getY() <= elevationDiff + h || coord.getY() >= Values.Chunk_Size_Y-elevationDiff-h) {
				return null;
			}
			
			coord.setY(playerY);
			int indexX = Engine.game.getWorld().getBlockIndex(coord)[0];
			//System.out.println("indexX: " + indexX);
			
			int indexY = Engine.game.getWorld().getBlockIndex(coord)[1];
			float indexChunk = coord.getChunk();
			Block[][] blocks = Engine.game.getWorld().getChunk((int) indexChunk).getBlocks();
			if(blocks[indexX][indexY].getID() != 0) {
				for(int j = (int)playerY; j < (int)playerY + elevationDiff; j++) {
					Coordinate tempCoord = new Coordinate(coord.getX(),j);
					if(Engine.game.getWorld().openArea(tempCoord, w, h) && blocks[indexX][j-2].getID() != 0) {
						coord.setY(j);						
						return coord;
					}
				}
			}
			//System.out.println("block ID getting checked: " + blocks[indexX][indexY].getID());
			//if it is open air, check downwards
			else if(blocks[indexX][indexY].getID() == 0) { 
				for(int j = (int)playerY; j > (int)playerY - elevationDiff; j--) {
					//System.out.println(blocks[indexX][j-1].getID());
					Coordinate tempCoord = new Coordinate(coord.getX(),j);
					if(Engine.game.getWorld().openArea(tempCoord, w, h) && blocks[indexX][j-1].getID() != 0) {
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
}
