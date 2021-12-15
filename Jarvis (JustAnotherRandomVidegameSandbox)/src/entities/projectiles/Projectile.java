package entities.projectiles;

import java.util.ArrayList;

import org.newdawn.slick.Image;

import core.Engine;
import entities.core.Coordinate;
import entities.core.Entity;
import entities.living.Living;
import entities.living.Living.Team;
import managers.ImageManager;

public class Projectile extends Entity {
	protected Team team;
	
	protected float damage;
	
	public Projectile(Living origin, Coordinate target){
		super(
				origin.getPosition().getX(), 
				origin.getPosition().getY()
			);
		
		// Default Size
		this.width = 0.5f;
		this.height = 0.5f;
		
		// Default Damage
		this.damage = 1;
		
		// Default Speed
		this.xSpeed = 0f;
		this.ySpeed = 0f;
		
		// Default Sprite 
		sprite = ImageManager.getPlaceholder();
		
		// Setting Team
		this.team = origin.getTeam();
	}
	
	

	@Override // Overwritten update method
	public void update() {
		// Collision detection 
		checkCollisions();
		
		// Position updating
		position.update(xSpeed, ySpeed);
	}
	
	@Override
	protected void entityCollisions() {
		ArrayList<Entity> entities = game.getEntities(EntType.Living);
		
		for(Entity e: entities) {
			Living living = (Living) e;
			
			if(team != living.getTeam() && entityCollision(living)) {
				living.takeDamage((int) damage);
				this.remove = true;
			}
		}
		
	}; 
	
	@Override
	protected void onBlockCollision() {
		// Die when hitting a block
		this.remove = true;
//		// Blow up blocks
//		int centerX = (int) position.getX();
//		int centerY = (int) position.getY();
//		
//		for(int i = -4; i < 5; i++)
//		{
//			for(int j = -4; j < 5; j++)
//			{
//				Engine.game.getWorld().destroyBlock(centerX + i, centerY + j);
//			}
//		}
//		
//		this.markForRemoval();
	}
}