package entities;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import core.Coordinate;
import settings.Values;

public class EBlock extends Entity {
	final private float EBlock_Size = 0.75f * Coordinate.ConversionFactor;
	
	private int blockId;
	private int count;
	
	public EBlock(int id, float x, float y) {
		super(x + 0.15f, y);
		
		this.sizeX = EBlock_Size / Coordinate.ConversionFactor;
		this.sizeY = EBlock_Size / Coordinate.ConversionFactor;
		
		this.count = 1;
		this.blockId = id;
	}
	
	public void render(Graphics g, float x, float y) {
		g.setColor(Values.BlockHash.get(blockId).darker(0.15f));
		g.fillRect(x, y, EBlock_Size, EBlock_Size);
	}
	public void update() {
		super.update();
		
		// Check if there are other EBlocks of the same ID nearby
	}
}