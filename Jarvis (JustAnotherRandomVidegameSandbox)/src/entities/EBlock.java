package entities;

import java.util.ArrayList;
import java.util.Collection;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import core.Coordinate;
import core.Engine;
import settings.Values;

public class EBlock extends EItem {
	final private float EBlock_Size = 0.75f * Coordinate.ConversionFactor;
	
	public EBlock(int id, float x, float y) {
		super(x + 0.15f, y);
		
		this.sizeX = EBlock_Size / Coordinate.ConversionFactor;
		this.sizeY = EBlock_Size / Coordinate.ConversionFactor;
		
		this.count = 1;
		this.itemID = id;
	}
	
	public void render(Graphics g, float x, float y) {
		g.setColor(Values.BlockHash.get(itemID).darker(0.15f));
		g.fillRect(x, y, EBlock_Size, EBlock_Size);
	}
	

}