package inventory.items;

import core.Coordinate;
import core.Engine;
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
		// Only break blocks within a certain radius of the player
		float dist = Engine.game.getPlayer().getPosition().magDisplacement(new Coordinate(x,y));
		if(dist > 10) return;
		
		
		int BlockX = (int) x;
		int BlockY = (int) y;
		
		Block b = game.getWorld().getChunk(BlockX / Values.Chunk_Size_X).getBlocks()[BlockX % Values.Chunk_Size_X][BlockY];
		b.hit(strength);
		
		if(b.getDurability() <= 0) {
			game.getWorld().destroyBlock(BlockX, BlockY);
		}
	}
}