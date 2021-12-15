package inventory.items;

import java.lang.Math;
import java.util.HashMap;

import org.lwjgl.Sys;

import core.BlockSettings;
import core.Engine;
import entities.core.Coordinate;
import entities.core.Entity.EntType;
import entities.projectiles.BlockBomb;
import entities.projectiles.BlockBullet;
import entities.projectiles.Projectile;
import inventory.Inventory;
import inventory.Item;
import managers.ImageManager;

public class Gun extends Item {
	private static float Cooldown = 0.5f;
	
	private float lastShot;
	
	public Gun(){
		super(-1, 1);
    
		this.sprite = ImageManager.getImage("desert eagle");
		
		lastShot = Sys.getTime();
	}
	
	public void update()
	{
		super.update();
	}
	
  @Override
  public void use(float x, float y){
	Inventory inv = game.getPlayer().getInventory();
	
	for(Item item: inv.getItems()) {
		if(item == null) continue;
		
		int id = item.getID();
		if(BlockSettings.hasBlock(id) && 
				Sys.getTime() - lastShot > Cooldown * 1000) {
		    // Spawn new blockbullet
		    BlockBullet bullet = new BlockBullet(game.getPlayer(),
		    		new Coordinate(x,y),
		    		BlockSettings.getStrengthScaling(id),
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
			
			int id = item.getID();
			if(BlockSettings.hasBlock(id) 
					&& Sys.getTime() - lastShot > Cooldown * 1000) {
			    // Spawn new blockbullet
			    BlockBomb bomb = new BlockBomb(game.getPlayer(),
			    		new Coordinate(x,y),
			    		BlockSettings.getStrengthScaling(id),
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
