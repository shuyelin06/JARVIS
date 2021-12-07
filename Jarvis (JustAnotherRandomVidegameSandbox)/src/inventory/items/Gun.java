package inventory.items;

import java.lang.Math;
import java.util.HashMap;

import org.lwjgl.Sys;

import core.Coordinate;
import core.Engine;
import entities.Entity.EntType;
import entities.projectiles.BlockBomb;
import entities.projectiles.BlockBullet;
import entities.projectiles.Projectile;
import inventory.Inventory;
import inventory.Item;
import managers.ImageManager;

public class Gun extends Item {
	private static HashMap<Integer, Float> IdScalingFactors = new HashMap<Integer, Float>();
	private static float Cooldown = 0.5f;
	
	private float lastShot;
	
	public Gun(){
		super(-1, 1);
    
		this.sprite = ImageManager.getImage("desert eagle");
		
		IdScalingFactors.put(1, 0.2f);
		IdScalingFactors.put(2, 0.2f);
		IdScalingFactors.put(3, 1f); // Stone
		IdScalingFactors.put(4, 1.2f); // Coal
		IdScalingFactors.put(5, 1.7f);
		IdScalingFactors.put(6, 2.5f);
		IdScalingFactors.put(7, 0.2f);
		IdScalingFactors.put(8, 1f);
		
		lastShot = Sys.getTime();
	}
  
  @Override
  public void use(float x, float y){
	Inventory inv = game.getPlayer().getInventory();
	
	for(Item item: inv.getItems()) {
		if(item == null) continue;
		
		if(IdScalingFactors .containsKey(item.getID()) && Sys.getTime() - lastShot > Cooldown * 1000) {
		    // Spawn new blockbullet
		    BlockBullet bullet = new BlockBullet(game.getPlayer(),
		    		new Coordinate(x,y),
		    		IdScalingFactors.get(item.getID()),
		    		item.getID()
		    		);
		    game.addEntity(EntType.Projectiles, bullet);
			
			inv.removeItem(item.getID());
			
			this.lastShot = Sys.getTime();
			break;
		}
	}
  }
  
  @Override
  public void use2(float x, float y) {
		Inventory inv = game.getPlayer().getInventory();
		
		for(Item item: inv.getItems()) {
			if(item == null) continue;
			
			if(IdScalingFactors .containsKey(item.getID()) && Sys.getTime() - lastShot > Cooldown * 1000) {
			    // Spawn new blockbullet
			    BlockBomb bomb = new BlockBomb(game.getPlayer(),
			    		new Coordinate(x,y),
			    		IdScalingFactors.get(item.getID()),
			    		item.getID()
			    		);
			    game.addEntity(EntType.Projectiles, bomb);
				
				inv.removeItem(item.getID());
				this.lastShot = Sys.getTime();
				break;
			}
		}
  }
  
}
