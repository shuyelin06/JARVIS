package entities.other;

import core.Engine;
import core.Values;

public class EBlock extends EItem {
	public EBlock(int id, float x, float y) {
		super(x + 0.15f, y);

		this.sprite = game.displayManager.getBlockSprite(id);
		
		this.sizeX = Values.EBlock_Size;
		this.sizeY = Values.EBlock_Size;
		
		this.itemID = id;
	}
}