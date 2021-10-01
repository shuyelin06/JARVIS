package attack;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import core.Game;

public class MageIceball extends Attack {
	private String direction;
	private float moveSpeed;
	//constructor
	public MageIceball(float x, float y, String faceDirection, int player) {
		super();
		this.x = x;
		this.y = y;
		//w is 10
		this.w = (float) (Game.gc.getWidth()/136.6);
		//h is 10
		this.h = (float) (Game.gc.getHeight()/76.8);
		damage = 1;
		expiry = 500;
		//moveSpeed is 10
		moveSpeed = (float) (Game.gc.getWidth()/136.6);
		this.player = player;
		direction = faceDirection;
		setImage("res/blueShot.png");
		image.setFilter(Image.FILTER_NEAREST);
		//sets direction facing
		if (faceDirection == "left") {
			xSpeed = -moveSpeed;
		} else if (faceDirection == "right") {
			xSpeed = moveSpeed;
		}
		
		effectType = "slow";
	}
	//draw direction of bullet
	public void render(Graphics g) {
		super.render(g);
		if (direction == "left") {
			image.draw((x + w), y, -w, h);
		} else if (direction == "right") {
			image.draw(x, y, w, h);
		}
	}
}
