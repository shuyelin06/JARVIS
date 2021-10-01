package enemy;

import core.Game;
import core.Utility;

public class BurstSentry extends Enemy{
	private int secondTimer;
	//initialize sentry
	public BurstSentry () {
		super();
		secondTimer = 0;
		setImage("res/burstSentry.png");
		x = ((float)Math.random())*Game.gc.getWidth();
		y = 0;
		xSpeed = Utility.random(-4, 4);
		curHealth = 3 + (Game.level);
		maxHealth = 3 + (Game.level);
		value = 15;
	}
	//shoots lasers in bursts, moves across top of screen
	public void update() {
		super.update();
		damage = curHealth;
		secondTimer--;
		if (x > Game.gc.getWidth()) {
			xSpeed = -xSpeed;
		}
		if (x < (0 + w)) {
			xSpeed = -xSpeed;
		}
		if (time % 100 == 0) {
			secondTimer = 30;
		}
		if (secondTimer >= 0 && secondTimer % 10 == 0) {
			Game.actors.add(new RedLaser(x+(w/2), y+(h/2), 0, 7));
		}
	}
}