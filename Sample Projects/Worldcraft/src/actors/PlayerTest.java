package actors;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import actors.Actor.Direction;
import core.Cell;
import core.Game;
import support.Values;

public class PlayerTest extends Actor 
{
	public PlayerTest(Cell owner)
	{
		super(owner);

		vel = 4;
		dir = Direction.none;
	}
	
	public Cell getPlayerCell()
	{
		return owner;
	}
	
	public void controls()
	{
		if(Game.gc.getInput().isKeyDown(Game.gc.getInput().KEY_UP))
		{
			dir = Direction.up;
		}
		if(Game.gc.getInput().isKeyDown(Game.gc.getInput().KEY_DOWN))
		{
			dir = Direction.down;
		}
		if(Game.gc.getInput().isKeyDown(Game.gc.getInput().KEY_RIGHT))
		{
			dir = Direction.right;
		}
		if(Game.gc.getInput().isKeyDown(Game.gc.getInput().KEY_LEFT))
		{
			dir = Direction.left;
		}
		if(Game.gc.getInput().isKeyDown(Game.gc.getInput().KEY_SPACE))
		{
			dir = Direction.none;
		}
	}
	
	public void render(Graphics g)
	{
		super.render(g);
		g.setColor(Color.white);
		g.drawString(coordinates(), 10, 60);
	}
	
	public void update()
	{
		controls();
		super.update();
	}
	
}
