package attack;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import core.Game;

public class TankSlam extends Attack {

	//constructor
	public TankSlam(float x, float y, float w, float h, int player) {
		super();
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;

		xSpeed = 0;
		ySpeed = 0;
		damage = 10;
		expiry = 300;
		this.player = player;
	}
	
	//update slam hitbox based on player position
	public void update() {
		super.update();
		if (player == 1) {
			x = Game.player1.getX();
			y = Game.player1.getY();
			if (Game.player1.getTankSlamTimer() == 0) {
				expiry = 0;
			}
		}
		if (player == 2) {
			x = Game.player2.getX();
			y = Game.player2.getY();
			if (Game.player2.getTankSlamTimer() == 0) {
				expiry = 0;
			}
		}
	}
	//draws only hitbox in debug
	public void render(Graphics g) {
		//write health of actor underneath
		if (Game.debugMode) {
			g.setColor(new Color(255, 255, 255));
			g.drawString(""+(int) curHealth, x, y - 15);
			g.setColor(new Color(0, 0, 0));
		}
		//debug for hitbox of actor
		if (Game.debugMode) {
			g.setColor(new Color(255, 255, 255));
			g.drawRect(x, y, w, h);
			g.setColor(new Color(0, 0, 0));
		}
	}
}
