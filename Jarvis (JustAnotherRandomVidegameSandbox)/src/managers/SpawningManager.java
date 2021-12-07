package managers;

import org.newdawn.slick.SlickException;

import core.Coordinate;
import core.Engine;
import core.Values;
import entities.Entity.EntType;
import entities.living.*;
import gamestates.Game;
import structures.Block;
import support.Utility;

public class SpawningManager {
	// Variables for easier accessing
	private Game game;
	
	private Player player;
	
	public SpawningManager(Game game) {
		this.game = game;
		
		this.player = game.getPlayer();
	}
	
	public void update() {
		spawnEnemy(game, Values.Spawn_Rate);
	}
	public void spawnEnemy (Game g, float prob) { //expand on parameters like what kind of enemies to spawn in later
		// prob is the percent chance that you want a new enemy to spawn; scale it to how often the update method gets called
 		if(g.getEntities(EntType.Living).size() < 7) {
 			if(!safeZone(g) && Utility.random(0.0, 100.0) <= prob) {
 				//for now it just drops a new enemy on the player's head
 				float x = g.getPlayer().getPosition().getX();
 				float y = g.getPlayer().getPosition().getY();
 				Coordinate coord = getOpenArea(g, 15, 25, 20, 3, 3, prob);
 				if(coord != null) {
 					g.getEntities(EntType.Living).add(new Scorpian(coord.getX(), coord.getY()));
 				}
			
 			}
 		}
	}
	
	//for now just checks if coordinates of player is near the spawn point, later change this to biomes
	public boolean safeZone(Game g) {
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
	
	public Coordinate getOpenArea(Game g, int minDistance, int maxDistance, int elevationDiff, int w, int h, float prob) {
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
				
			coord.setXPos(playerX + i * direction);
			
			//so that it doesn't check out of world
			if(coord.getX() > Values.World_X_Size * Values.Chunk_Size_X - w
					|| coord.getX() < w) {
				return null;
			}
			if(coord.getY() <= elevationDiff + h || coord.getY() >= Values.Chunk_Size_Y-elevationDiff-h) {
				return null;
			}
			
			coord.setYPos(playerY);
			
			int indexX = Engine.game.getWorld().getBlockIndex(coord)[0];
			int indexY = Engine.game.getWorld().getBlockIndex(coord)[1];
			
			float indexChunk = coord.getChunk();
			Block[][] blocks = Engine.game.getWorld().getChunk((int) indexChunk).getBlocks();
			
			//if coord is block, check upwards
			if(blocks[indexX][indexY].getID() != 0) {
				return checkVertical(coord, (int)playerY, elevationDiff, w, h, 1);

			}
			//if it is open air, check downwards
			else if(blocks[indexX][indexY].getID() == 0) { 
				return checkVertical(coord, (int)playerY, elevationDiff, w, h, -1);

			}

			}
		}
		
		return null;
	}
	//checks up/down for an open area given by the openArea method in World
	public static Coordinate checkVertical(Coordinate coord, int playerY, int eleDiff, int w, int h, int direction) {
		Block[][] block = Engine.game.getWorld().getChunk((int) coord.getChunk()).getBlocks();
		int indX = Engine.game.getWorld().getBlockIndex(coord)[0];
		if(indX > w && indX < Values.Chunk_Size_X-w) {
		Coordinate tempCoord = new Coordinate(coord.getX(),playerY);
		for(int j = 0; j < eleDiff; j++) {
			tempCoord.setYPos(playerY + direction*j);
			if(Engine.game.getWorld().openArea(tempCoord, w, h) /*&& block[indX][(int)tempCoord.getY()-w].getID() != 0*/) {
				return tempCoord;

			}
			
		}
		}
		return null;
	}
}
