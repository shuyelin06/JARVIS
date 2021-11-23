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
    // Find the angle the player is aiming
    Coordinate pos = game.getPlayer().getPosition();
    
    double theta = Math.atan((y - pos.getY()) / (x - pos.getX())); 
    if(x - pos.getX() < 0) theta = theta + Math.PI;
    
    // Spawn new projectile 
    game.addEntity(EntType.Projectiles, new Projectile(pos.getX(), pos.getY(), theta));
  }
}
