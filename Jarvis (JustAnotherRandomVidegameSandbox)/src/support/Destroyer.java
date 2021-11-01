package support;

import java.util.ArrayList;

import org.lwjgl.Sys;

import entities.Entity;
import gamestates.Game;
import settings.Values;

public class Destroyer{
	final private static float Update_Timer = 3; // Called every __ seconds
	
	final private static float Unload_Time = 10; // If an entity exists for >__ seconds, unload it
	
	private Game game;
	private long lastCalled;
	
	public Destroyer(Game game) {
		this.game = game;
		lastCalled = Sys.getTime();
	};
	
	public void update() {
		if((Sys.getTime() - lastCalled) / 1000 > Update_Timer - 1) {
			lastCalled = Sys.getTime();
			
			for(ArrayList<Entity> list: game.getAllEntities().values()) {
				for(Entity e: list) {
					if(Math.abs(e.getPosition().getX() - game.getPlayer().getPosition().getX()) > (Values.Render_Distance + 1) * Values.Chunk_Size_X
							|| e.timeAlive() > Unload_Time) {
						e.markForRemoval();
					}
				}
			}
		}
	}
	
}