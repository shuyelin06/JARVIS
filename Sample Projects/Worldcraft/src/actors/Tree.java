package actors;

import org.newdawn.slick.Graphics;

import core.Cell;
import core.Game;
import support.Values;

public class Tree extends Plant
{
	public Tree(Cell owner)
	{
		super(owner);
		dir = Direction.none;
		setImage("res/tree.png");
		w = 2;
		h = 4;
	}
	
	public void render(Graphics g)
	{
		image.draw((owner.getX() - Game.world.getCameraX()) * Values.CELL_SIZE,
				   (owner.getY() - Game.world.getCameraY()) * Values.CELL_SIZE, w * Values.CELL_SIZE, h * Values.CELL_SIZE);
	}
}
