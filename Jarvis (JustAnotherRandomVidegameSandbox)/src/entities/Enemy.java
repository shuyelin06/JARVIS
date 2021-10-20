package entities;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;

import gamestates.Game;
import support.Utility;
import world.World;

public class Enemy extends Entity {
	
	protected int contactDmg;
	
	protected Player target;
	protected int aggroRange;
	protected int size;
	
	public Enemy(float x, float y, World world) throws SlickException 
	{
		super(x,y, world); 
		
		contactDmg = 1;
		aggroRange = 100;
		size = 15;
	}
	
	public void ai(Player p) {
		if(Utility.getDistance(this, p) <= aggroRange) {
			
		}
	}
		
	public void update() {
		super.update();
		if(Utility.random(0.0, 100.0) <= 0.2) { //random chance to die just to test stuff
			alive = false;
		}
		setXSpeed(100f);
	}
}
