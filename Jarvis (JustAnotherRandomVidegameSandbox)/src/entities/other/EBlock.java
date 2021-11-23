package entities.other;

import core.Engine;
import core.Values;

public class EBlock extends EItem {
	final static private float EBlock_Size = 0.75f;
	
	public EBlock(int id, float x, float y) {
		super(x + 0.15f, y);

		this.sprite = Engine.game.displaymanager.getSpriteSheet().getSubImage(
				0, Engine.game.displaymanager.getSpriteHash().get(id));
		
		this.sizeX = EBlock_Size;
		this.sizeY = EBlock_Size;
		
		this.itemID = id;
	}
}