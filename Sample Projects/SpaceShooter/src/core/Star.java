package core;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class Star {
	private float w;
	private float h;
	private float x;
	private float y;
	private float xSpeed;
	private float ySpeed;
	private float depth;
	
	//initialize star settings (depth = depth of field)
	public Star() {
		depth = (float) (Math.random()*9)+1;
		w = 4-(depth/2);
		h = 4-(depth/2);
		x = (int)(Math.random()*Game.gc.getWidth());
		y = (int)(Math.random()*Game.gc.getHeight());
		xSpeed = 0;
		ySpeed = 1/depth;
	}
	
	//draws star
	public void render(Graphics g) {
		g.setColor(new Color(255, 255, 255));
		g.fillOval(x, y, w, h);
	}
	
	//updates star movement
	public void update() {
		x += xSpeed;
		y += ySpeed;
		if (y >= Game.gc.getHeight()) {
			y = -h;
		}
	}
	
}
