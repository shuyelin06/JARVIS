package items;

import java.lang.Math;

public class Gun extends Item{
  public Gun(){
    super(-1, 1);
  }
  
  public void use(float x, float y){
    // Find the angle the player is aiming
    Player player;
    
    Coordinates pos = player.getPosition();
    
    double theta = Math.atan(y - pos.getY(), x - pos.getX()); 
    if(x - pos.getX() < 0) theta = theta + Math.PI;
    
    // Spawn new projectile 
  }
}
