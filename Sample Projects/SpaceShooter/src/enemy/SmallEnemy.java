package enemy;

import core.Game;
import core.Utility;

public class SmallEnemy extends Enemy
{
	//initialize small enemy
	public SmallEnemy()
	{
		super();
		setImage("res/smallEnemy.png");
		x = Utility.random(Game.gc.getWidth());
		y = 0;
		xSpeed = 0;
		ySpeed = 2;
		value = 5;
	}
	
	//move straight down, shoot bullets
	public void update()
	{
		super.update();		
		
		damage = curHealth;
		// this works for an enemy, but they're all firing in sync... how could we fix this?
		if(y >= Game.gc.getHeight()) {
			y = -h;
		}
		
		if(x >= Game.gc.getWidth() + w) {
			x = -w;
		}
		
		if(x <= 0) {
			x = Game.gc.getWidth() + w;
		}
		
		if(time % 60 == 0)
		{
			Game.actors.add(new RedShot(x+15, y+16, (float) 0, (float) 5));
		}
	}
	
}