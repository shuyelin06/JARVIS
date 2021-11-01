package entities.other;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import core.Coordinate;
import core.Engine;
import settings.Values;

public class EBlock extends EItem {
	final private float EBlock_Size = 0.75f;
	
	public EBlock(int id, float x, float y) {
		super(x + 0.15f, y);
		
		this.sizeX = EBlock_Size;
		this.sizeY = EBlock_Size;

		this.itemID = id;
	}
	
	public void render(Graphics g, float x, float y) {
		Engine.game.getSpriteSheet().getSubImage(0, Engine.game.getSpriteHash().get(itemID)).draw(x, y, 0.75f);
		
		float[] renderPos = Engine.game.renderPosition(position.getX(), position.getY());
		g.setColor(Color.black);
		g.drawRect(renderPos[0], renderPos[1], EBlock_Size * Coordinate.ConversionFactor, EBlock_Size * Coordinate.ConversionFactor);
	}

}