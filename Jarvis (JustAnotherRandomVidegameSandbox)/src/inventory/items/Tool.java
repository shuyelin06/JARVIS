package inventory.items;

import core.Values;
import inventory.Item;
import managers.ImageManager;
import structures.Block;

public class Tool extends Item {
	private int strength;
	
	public Tool() {
		super(-2, 1);
		
		this.sprite = ImageManager.getImage("pickaxe");
		this.strength = 10;
	}
	
	public void use(float x, float y) {
		int BlockX = (int) x;
		int BlockY = (int) y;
		
		Block b = game.getWorld().getChunk(BlockX / Values.Chunk_Size_X).getBlocks()[BlockX % Values.Chunk_Size_X][BlockY];
		b.hit(strength);
		
		if(b.getDurability() <= 0) {
			game.getWorld().destroyBlock(BlockX, BlockY);
		}
	}
}