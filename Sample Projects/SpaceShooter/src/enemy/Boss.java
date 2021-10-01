package enemy;

import core.Game;
import core.Utility;

public class Boss extends Enemy {
	//initialize boss
	public Boss()
	{
		super();
//		setImage("res/homingShooter.png");
		x = 0;
		y = 0;
		w = Game.gc.getWidth();
		h = Game.gc.getHeight()/4;
		xSpeed = 0;
		ySpeed = 0;
		curHealth = 300;
		maxHealth = 300;
		value = 100;
		damage = 300;
	}
	
	//shoots bullets and missiles
	public void update()
	{
		super.update();		
		damage = curHealth;
		
		if(time % 100 == 0)
		{
			Game.actors.add(new HomingMissile(Utility.random(0, w), y+(h/2)));
		}
		if(time % 3 == 0) {
			Game.actors.add(new RedShot(Utility.random(x, x + w), y + h, Utility.random(-3, 3), Utility.random(3, 7)));
		}
	}
}
