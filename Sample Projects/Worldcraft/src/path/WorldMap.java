package path;

import org.newdawn.slick.util.pathfinding.PathFindingContext;
import org.newdawn.slick.util.pathfinding.TileBasedMap;

import core.Cell;
import core.Stone;
import core.Water;
import support.Values;

public class WorldMap implements TileBasedMap
{
	public Cell[][] cells;
	
	public WorldMap(Cell[][] cells)
	{
		this.cells = cells;
	}
	
	public boolean blocked(PathFindingContext arg0, int tx, int ty) {
		return cells[tx][ty].getTerrain() instanceof Water ||
			   cells[tx][ty].getTerrain() instanceof Stone;
	}

	public float getCost(PathFindingContext arg0, int arg1, int arg2) {
		return 1;
	}

	public int getHeightInTiles() {
		return Values.WORLD_HEIGHT;
	}

	public int getWidthInTiles() {
		return Values.WORLD_WIDTH;
	}

	public void pathFinderVisited(int arg0, int arg1) {
		
	}
	
}
