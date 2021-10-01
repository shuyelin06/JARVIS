package actors;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import actors.Actor.Direction;
import core.Cell;
import core.Game;
import core.Water;
import support.Utility;
import support.Values;

//will need pathfinding
public class Animal extends Actor 
{
	protected float food = 100;
	protected float water = 100;
	protected int wanderTime = 42;
	protected int vision;
	
	private boolean isDrinking;
	
	public Animal(Cell owner)
	{
		super(owner);
		dir = Direction.getRandomDir();
	}
	
	public void randomWander(int time)
	{
		if(time % wanderTime == 0)
		{
			dir = Direction.getRandomDir();
		}
	}
	
	//finds the closest water cell
	public void findWater()
	{
		int bestDistance = 99999;
		int distance = 99999;
		Cell closestWater = owner;
		ArrayList<Cell> fov = getVision();
		
		for(Cell a : fov)
		{
			if(a.getTerrain() instanceof Water)
			{
				distance = Utility.getDistance(owner, a);
			}
			if(distance < bestDistance)
			{
				bestDistance = distance;
				closestWater = a;
			}
		}
		if(closestWater == owner)
		{
			randomWander(Game.time);
		}
		else
		{
			basicPathFinding(closestWater);
		}
	}
	
	public ArrayList<Cell> getVision()
	{
		ArrayList<Cell> result = new ArrayList<Cell>();
		for(int i = owner.getX() - vision; i != owner.getX() + vision; i++)
		{
			//System.out.println("i: " + i);
			if(i > 0 && i < Values.WORLD_WIDTH)
			{
				for(int j = owner.getY() - vision; j != owner.getY() + vision; j++)
				{
					//System.out.println("j: " + j);
					if(j > 0 && j < Values.WORLD_HEIGHT)
					{
						result.add(Game.world.cells[i][j]);
					}
				}
			}
		}
		return result;
	}
	
	public void render(Graphics g)
	{
		super.render(g);
		if(Game.debugMode)
		{
			g.setColor(Color.white);
			g.drawString("\n\nfood: " + food + "\nwater: " + water, 
					    (owner.getX() - Game.world.getCameraX()) * Values.CELL_SIZE,
					    (owner.getY() - Game.world.getCameraY()) * Values.CELL_SIZE);
		}
	}
	
	public void update()
	{
		water -= .04f;
		if(water <= 0) 
		{
			health = 0;
			water = 0;
		}
		if(Utility.getNearWater(owner)) { water = 100; }
//		if(water > 0 && !isDrinking)
//		{
//			water--;
//		}
//		if(isDrinking)
//		{
//			water += 10;
//		}
//		if(water == 0)
//		{
//			health--;
//		}
//		food--;
		if(water < 42)
		{
			findWater();
			//getVision();
		}
		else{ randomWander(Game.time); }
		super.update();
	}
}
