package attack;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import core.Game;

public class AssassinSwing extends Attack {
	//constructor
	public AssassinSwing(float x, float y, String faceDirection, int player, float w) {
		super();
		
		this.y = y;
		//w is 60
		this.w = (float) (Game.gc.getWidth()/22.766666666);
		//h is 30
		this.h = (float) (Game.gc.getHeight()/25.6);
		
		this.faceDirection = faceDirection;
		
		if (faceDirection == "left") {
			this.x = x - this.w; 
		} else if (faceDirection == "right") {
			this.x = x + w;
		}
		
		xSpeed = 0;
		ySpeed = 0;
		damage = 5;
		expiry = 1;
		this.player = player;
		setImage("res/dagger.png");
		image.setFilter(Image.FILTER_NEAREST);
	}
	//draw direction of punch
	public void render(Graphics g) {
		super.render(g);
		if (faceDirection == "left") {
			image.draw((x + w), y, -w, h);
		} else if (faceDirection == "right") {
			image.draw(x, y, w, h);
		}
	}
}

