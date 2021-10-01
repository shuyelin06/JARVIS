package core;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import platform.Platform;

public class Actor {

	protected float x, y, w, h;
	protected float xSpeed, ySpeed, xAcceleration;
	protected int curHealth, maxHealth, damage;
	protected float percentHealth;
	
	protected int jumpsLeft, jumpHeight;
	protected int time;
	protected float attackTimer, abilityTimer1, abilityTimer2;
	protected boolean canAttack, canAbility1, canAbility2;
	protected Image image;
	protected float gravity;
	protected String faceDirection;
	protected boolean touchesPlatform, touchPlatformChecker, touchesGround;

	//constructs actor
	public Actor() {
		setImage("res/defaultImage.png");
		time = 0;
		gravity = (float) 1;
		xSpeed = 0;
		xAcceleration = 0;
		ySpeed = 0;
		curHealth = 10;
		maxHealth = 50;
	}
	//updates movement and position
	public void update() {
		y += ySpeed;
		ySpeed += gravity;
		percentHealth = (float) curHealth;
	}
	
	//draws actor
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
	//setting image of actor
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
}
