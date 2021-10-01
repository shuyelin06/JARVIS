package enemy;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import core.Actor;
import core.Game;

public class Enemy extends Actor
{
	public Enemy()
	{
		super();
	}
	
	//render health bars if not projectile and more than 1 hp
	public void render(Graphics g) throws SlickException {
		super.render(g);
		if (!isProjectile && maxHealth > 1) {
			displayHealthBar(g);
		}
	}
	
	//enemy health bars
	public void displayHealthBar(Graphics g) {
		float percentage = ((float) (curHealth + 1)) / ((float) maxHealth + 1);
		final float BAR_WIDTH = w;
		final float BAR_HEIGHT = h / 8;
		g.setColor(new Color(100, 0, 0));
		g.fillRect(x, y + BAR_HEIGHT + 5 + h, BAR_WIDTH, BAR_HEIGHT);
		g.setColor(new Color(255, 0, 0));
		g.fillRect(x, y + BAR_HEIGHT + 5 + h, BAR_WIDTH*percentage, BAR_HEIGHT);
		g.setColor(new Color(100, 100, 100));
		g.drawRect(x, y + BAR_HEIGHT + 5 + h, BAR_WIDTH, BAR_HEIGHT);
	}
}
