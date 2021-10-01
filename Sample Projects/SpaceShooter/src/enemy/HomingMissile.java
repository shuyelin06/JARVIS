package enemy;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import core.Game;
import core.Utility;
import player.Player;

public class HomingMissile extends Enemy {
	private float rotation;
	private float diagonal;
	
	//initialize missile
	public HomingMissile(float x, float y) {
		super();
		setImage("res/homingMissile.png");
		
		isProjectile = true;
		this.x = x;
		this.y = y;
		w = 10;
		h = 30;
		
		curHealth = 1;
		maxHealth = 1;
		
		damage = 1 + (Game.level);
		expiry = Utility.random(250, 300);
	}
	//moves missile towards player
	public void update() {
		super.update();
		diagonal = (float) Math.sqrt(((x - Game.player.getX()) * (x - Game.player.getX())) + ((y - Game.player.getY()) * (y - Game.player.getY())));
		xSpeed = (float) (5 * Math.sin((Game.player.getX() - x) / diagonal));
		ySpeed = (float) (5 * Math.sin((Game.player.getY() - y) / diagonal));
		rotation = -(float) (Math.atan((Game.player.getX() - x) / (Game.player.getY() - y)) * 57.3);
		if (y < Game.player.getY()) {
			rotation += 180;
		}
	}
	//rotates image
	public void render (Graphics g) throws SlickException {
		g.rotate(x, y, rotation);
		super.render(g);
		g.rotate(x, y, -rotation);
	}
}
