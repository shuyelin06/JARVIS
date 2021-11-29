package items;

import java.lang.Math;

import core.Coordinate;
import core.Engine;
import entities.Entity.EntType;
import entities.other.Projectile;
import managers.ImageManager;

public class Gun extends Item{
  public Gun(){
    super(-1, 1, 
    		ImageManager.getImage("desert eagle"));
  }
  
  public void use(float x, float y){
	Inventory inv = game.getPlayer().getInventory();
	
	// Check if the player has gold in their inventory
	if(inv.hasItem(5)) {
		// Find the angle the player is aiming
	    Coordinate pos = game.getPlayer().getPosition();
	    
	    double theta = Math.atan2(y - pos.getY(), x - pos.getX()); 
	    
	    // Spawn new projectile 
	    Projectile p = new Projectile(pos.getX(), pos.getY(), theta);
	    p.updateSprite(Engine.game.displaymanager.getSpriteSheet().getSubImage(
				0, Engine.game.displaymanager.getSpriteHash().get(5)));
	    game.addEntity(EntType.Projectiles, p);
	    
	    // Decrement the amount of gold 
		inv.removeItem(5);
	}
	
    
  }
}
