package attack;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import core.Game;

public class AssassinThrow extends Attack {
	private String direction;
	private int hitGroundTimer;
	private float moveSpeed;
	//constructor
	public AssassinThrow(float x, float y, String faceDirection, int player) {
		super();
		this.x = x;
		this.y = y;
		//w is 24
		this.w = (float) (Game.gc.getWidth()/56.9166666666);
		//h is 10
		this.h = (float) (Game.gc.getWidth()/76.8);
		damage = 5;
		expiry = 500;
		direction = faceDirection;
		//moveSpeed is 20
		moveSpeed = (float) (Game.gc.getWidth()/68.3);
		hitGroundTimer = 0;
		this.player = player;
		direction = faceDirection;
		setImage("res/dagger.png");
		image.setFilter(Image.FILTER_NEAREST);
		//gravity is 0.1
		gravity = (float) Game.gc.getHeight()/7680;
		//sets direction facing
		if (faceDirection == "left") {
			xSpeed = -moveSpeed;
		} else if (faceDirection == "right") {
			xSpeed = moveSpeed;
		}
	}
	//draw direction of bullet
	public void update() {
		super.update();
		if (y >= Game.gc.getHeight() - 130) {
			ySpeed = 0;
			xSpeed = 0;
			hitGroundTimer++;
			if (hitGroundTimer >= 30) {
				canRemove = true;
			}
		}
//		image.rotate(6);
	}
	
	public void render(Graphics g) {
		super.render(g);
		if (direction == "left") {
			image.draw((x + w), y, -w, h);
		} else if (direction == "right") {
			image.draw(x, y, w, h);
		}
	}
}
