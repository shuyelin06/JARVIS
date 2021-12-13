package core;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BlockHash {
	final public static Map<Integer, BlockHash> BlockHashing = Stream.of(
			new BlockHash(0, -1, true, 0, 0f), // ID 0 - Air,
			new BlockHash(1, 1, false, 100, 0.2f), // ID 1 - Dirt
			new BlockHash(2, 0, false, 100, 0.2f), // ID 2 - Grass
			new BlockHash(3, 2, false, 200, 1f), // ID 3 - Stone
			new BlockHash(4, 3, false, 250, 1.2f), // ID 4 - Coal
			new BlockHash(5, 4, false, 300, 1.7f), // ID 5 - Gold
			new BlockHash(6, 5, false, 400, 2.5f), // ID 6 - Diamond
			new BlockHash(7, 6, false, 100, 0.2f), // ID 7 - Sand
			new BlockHash(8, 7, false, 200, 1f) // ID 8 - Sandstone
		).collect(Collectors.toMap(BlockHash::getID, BlockHash::getHash));
	
	// Instance Variables for the BlockHash
	private int id; // ID of the Block
	
	private int spriteLocation; // Location of the Image in the SpriteSheet
	
	private boolean passable;
	
	private int baseDurability; // Base Durability of the Block
	private float strengthScaling; // Block Scaling (for the gun)
	
	// Constructor
	public BlockHash(int id, int location, boolean passable, int baseDurability, float strengthScaling) {
		this.id = id;
		
		this.spriteLocation = location;
		
		this.passable = passable;
		
		this.baseDurability = baseDurability;
		this.strengthScaling = strengthScaling;
	}
	
	// Methods for Initializing the Map
	private BlockHash getHash() { return this; }
	private int getID() { return id; }
	
	// Methods to Obtain the Values
	public static boolean hasBlock(int id) { return BlockHashing.containsKey(id); }
	
	public static int getLocation(int id) { return BlockHashing.get(id).spriteLocation; }
	
	public static boolean isPassable(int id) { return BlockHashing.get(id).passable; }

	public static int getBaseDurability(int id) { return BlockHashing.get(id).baseDurability; }
	public static float getStrengthScaling(int id) { return BlockHashing.get(id).strengthScaling; }	
}