package enemy;

import core.Game;
import core.Utility;

public class RedSquare extends Enemy
{
	//initialize red square
	public RedSquare()
	{
		super();
		setImage("res/homingShooter.png");
		x = Utility.random(Game.gc.getWidth());
		y = 0;
		xSpeed = (Utility.random(2)) - 1;;
		ySpeed = 2;
		curHealth = 3 + (Game.level/2);
		maxHealth = 3 + (Game.level/2);
		value = 10;
	}
	
	//movement, shoot homing missiles
	public void update()
	{
		super.update();		
		
		damage = curHealth;
		if(y >= Game.gc.getHeight()) {
			y = -h;
		}
		
		if(x >= Game.gc.getWidth() + w) {
			x = -w;
		}
		
		if(x <= 0) {
			x = Game.gc.getWidth() + w;
		}
		
		if(time % 130 == 0)
		{
			Game.actors.add(new HomingMissile(x+(w/2), y+(h/2)));
		}
	}
	
}
