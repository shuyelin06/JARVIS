package enemy;

import core.Game;
import core.Utility;

public class Sentry extends Enemy{
	//initialize sentry
	public Sentry () {
		super();
		setImage("res/sentry.png");
		x = ((float)Math.random())*Game.gc.getWidth();
		y = 0;
		xSpeed = Utility.random(-4, 4);
		curHealth = 3 + (Game.level/2);
		maxHealth = 3 + (Game.level/2);
		value = 10;
	}
	//movement across top of screen, shoot laser
	public void update() {
		damage = curHealth;
		super.update();
		if (x > Game.gc.getWidth()) {
			xSpeed = -xSpeed;
		}
		if (x < (0 + w)) {
			xSpeed = -xSpeed;
		}
		if (time % 50 == 0) {
			Game.actors.add(new RedLaser(x+(w/2), y+(h/2), 0, 7));
		}
	}
}
