package attack;

import org.newdawn.slick.Graphics;

import core.Actor;
import core.Game;

public class Attack extends Actor{
	//attack keys: CVB for player 1 and ,./ for player 2
	protected int expiry;
	protected boolean canRemove;
	protected int player;
	protected int personalTimer;
	protected String effectType;
	public Attack() {
		super();
		canRemove = false;
		gravity = 0;
		personalTimer = 0;
		effectType = "";
	}
	
	//draws attacks
	public void render(Graphics g) {
		super.render(g);
	}
	
	//updates attack positions and removal
	public void update() {
		personalTimer++;
		super.update();
		checkRemoval();
		x += xSpeed;
	}
	
	//enables removal
	public void enableRemoval() {
		canRemove = true;
	}
	
	//checks if attacks can be removed
	public void checkRemoval() {
		if (expiry <= personalTimer) {
			canRemove = true;
		}
		if (isOffScreen()) {
			canRemove = true;
		}
	}
	
	//accessor for if the actor is off screen
	public boolean isOffScreen() 
	{
		return (x + w) < 0 || x > Game.gc.getWidth() || (y + h) < 0 || y > Game.gc.getHeight();
	}
	
	//accessor for canRemove
	public boolean isRemovable() {
		return canRemove;
	}
	
	//player accessors (which player to prevent friendly fire from)
	public int player() {
		return player;
	}
	
	//position accessors
	public float getX() {
		return x;
	}
	public float getY() {
		return y;
	}
	public float getW() {
		return w;
	}
	public float getH() {
		return h;
	}
	
	//damage accessor
	public int getDamage() {
		return damage;
	}
	
	public String effect() {
		return effectType;
	}
}
