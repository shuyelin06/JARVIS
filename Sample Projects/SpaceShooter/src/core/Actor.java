package core;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import enemy.Enemy;
import player.Friendly;

public class Actor 
{
	protected boolean isProjectile;
	protected int time;
	protected int expiry;
	protected float maxHealth;
	protected float curHealth;
	protected float damage;
	protected float x;
	protected float y;
	protected int w;
	protected int h;
	protected float xSpeed;
	protected float ySpeed;
	protected int value;
	protected boolean hasPaid;
	protected boolean canRemove;
	protected Image image;
	private ArrayList<Actor> collisions;

	//initialize variables
	public Actor()
	{
		curHealth = 1;
		maxHealth = 1;
		damage = 1;
		setImage("res/defaultImage.png");
		w = 32;
		h = 32;
		value = 5;
		collisions = new ArrayList<Actor>();
	}
	
	//sets image of actor
	public void setImage(String filepath)
	{
		try
		{
			image = new Image(filepath);
		}
		catch(SlickException e)		
		{
			System.out.println("Image not found!");
		}
	}

	//accessor for isProjectile
	public boolean isProjectile()
	{
		return isProjectile;
	}
	
	//accessor for damage
	public float getDamage()
	{
		return damage;
	}
	
	public void update()
	{
		collisions.clear();
		//updates time, x, y, and expiry
		time++;
		x += xSpeed;
		y += ySpeed;
		if (time == expiry) {
			die();
		}
	}

	public void render(Graphics g) throws SlickException 
	{
		//draws actor
		image.draw(x, y, w, h);
		//debug for health of actor
		if (Game.debugMode) {
			g.setColor(new Color(255, 255, 255));
			g.drawString(""+(int) curHealth, x, y + h + 4);
			g.setColor(new Color(0, 0, 0));
		}
		//debug for hitbox of actor
		if (Game.debugMode) {
			g.setColor(new Color(255, 255, 255));
			g.drawRect(x, y, w, h);
			g.setColor(new Color(0, 0, 0));
		}
		
	}
	//accessor for if player is dead
	public boolean isDead()
	{
		return curHealth <= 0;
	}
	//accessor for canRemove
	public boolean canRemove()
	{
		return canRemove;
	}
	//kills actor
	public void die()
	{
		curHealth = 0;
		canRemove = true;
	}
	//takes damage, enables removal if at 0 health
	//increases money by value of actor when killed
	public void takeDamage(float amount)
	{
		curHealth -= amount;
		if (curHealth < 0) {
			curHealth = 0;
			canRemove = true;
		}
		if (!isProjectile) {
			if (curHealth <= 0 && !hasPaid) {
				Game.money += value;
				hasPaid = true;
			}
		}
	}
	//check if actor is off screen
	public boolean isOffScreen() 
	{
		return x < 0 || x > Game.gc.getWidth() || y < 0 || y > Game.gc.getHeight();
	}
	//collision between actors
	public boolean collidesWith(Actor other)
	{			
		return  collidesWith(other.x,     		other.y          ) || 
				collidesWith(other.x + other.w, other.y          ) ||
				collidesWith(other.x,           other.y + other.h) ||
				collidesWith(other.x + other.w, other.y + other.h);
	}
	//collision mechanism
	public boolean collidesWith(float x, float y)
	{
		return 	x >= this.x && 
				x <= this.x + this.w && 
				y >= this.y && 
				y <= this.y + this.h;
	}

	public void hitBy(Actor other)
	{
		// If the object already hit us this frame, ignore it.
		if(collisions.contains(other))
		{
			return;
		}
		
		// If on opposite teams and at least one is not a projectile, it's a HIT
		if((this instanceof Friendly && other instanceof Enemy || 
			this instanceof Enemy && other instanceof Friendly) && 
			!(this.isProjectile() && other.isProjectile()))
		{
			// Add it to the collision list
			collisions.add(other);
			
			// Report hit
			if(Game.debugMode)
			{
				System.out.println(Game.gameTime + ": " + this + " hit by " + other);
			}
			
			// Take damage
			takeDamage(other.getDamage());
		}
	}

	public String toString()
	{
		return this.getClass().getSimpleName();
	}

}
