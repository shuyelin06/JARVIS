package structures;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;

import gamestates.WorldSelect;

import org.newdawn.slick.Graphics;

public class Particle {
	
	private float x;
	private float y;
	private float xSpeed;
	private float ySpeed;
	private float gravity = (float) 0;
	private int color1 = 255;
	private int color2 = 255;
	private int color3 = 255;
	private int size = 0;
	
	public Particle (int x, int y) {
		this.x = (float) x;
		xSpeed = (float) (Math.random()*30 - 15);
		ySpeed = (float) (Math.random()*30 - 15);
		size = (int) (Math.random()*10 + 1);
		if (WorldSelect.fireworkType == 0) {
			color1 = 255;
			color2 = (int) (Math.random()*255);
			color3 = (int) (Math.random()*255);
			this.y = y - 10;
			gravity = (float) 0.5;
		} else if (WorldSelect.fireworkType == 1) {
			color1 = (int) (Math.random()*255);
			color2 = (int) (Math.random()*255);
			color3 = 255;
			this.y = y + 10;
			gravity = (float) -0.5;
		} else if (WorldSelect.fireworkType == 2) {
			color1 = (int) (Math.random()*255);
			color2 = 255;
			color3 = (int) (Math.random()*255);
			this.y = y;
			gravity = (float) (Math.random()*1 - 0.5);
		} else if (WorldSelect.fireworkType == 3) {
			color1 = (int) (Math.random()*255);
			color2 = (int) (Math.random()*255);
			color3 = (int) (Math.random()*255);
			this.y = y;
			gravity = (float) (Math.random()*1 - 0.5);
			size = (int) (Math.random()*100 + 1);
		}
	}
	
	public void update(GameContainer gc) {
		y += ySpeed;
		ySpeed += gravity;
		x += xSpeed;
		if (y >= gc.getHeight()) {
			WorldSelect.particles.remove(this);
		}
		if (y <= 0) {
			WorldSelect.particles.remove(this);
		}
		if (x >= gc.getWidth()) {
			WorldSelect.particles.remove(this);
		}
		if (x <= 0) {
			WorldSelect.particles.remove(this);
		}
	}
	
	public void render(Graphics g) {
		g.setColor(new Color(color1, color2, color3));
		g.fillOval(x - size, y - size, (size*2), (size*2));
	}
}