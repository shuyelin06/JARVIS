package entities;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;

import core.Coordinate;
import core.Engine;
import gamestates.Game;
import support.Utility;
import world.World;

public class Enemy extends Entity {
	
	protected int contactDmg;
	
	protected Player target;
	protected int aggroRange;
	protected float moveSpeed;
	
	public Enemy(float x, float y) throws SlickException
	{
		super(x,y); 
		target = Engine.game.getPlayer();

		sprite = new Image("res/redEnemy.png");
		
		contactDmg = 1;
		aggroRange = 18;
		sizeX = 1;
		sizeY = 1;
		jumpsLeft = 1;
	}
	
	public void ai(Player p) {
		if(Utility.getDistance(this, p) <= aggroRange) {
			if(Utility.changeX(this, p) > 0) {
				moveRight(10, 5);
			}else if(Utility.changeX(this, p) < 0) {
				moveLeft(10, 5);
			}
			if(Utility.random(0,100) < 25) {
				jump(10);
			}
			
		}
	}
	
	public void playerCollision(Player p) {
		if(Math.abs(p.getPosition().getX() - this.getPosition().getX()) <= 1) { //still need to implement hitboxes
			if(Math.abs(p.getPosition().getY() - this.getPosition().getY()) <= 1) {
				p.takeDamage(1, true);
			}
		}
	}
		
	public void update() {
		super.update();
		
		if(Utility.random(0.0, 100.0) <= 0.2) { //random chance to die just to test stuff
			alive = false;
		}
		
		playerCollision(target);
		ai(target);
	}
}
