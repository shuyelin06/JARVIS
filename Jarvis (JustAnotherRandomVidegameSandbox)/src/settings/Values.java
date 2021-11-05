package settings;

import java.util.HashMap;
import org.newdawn.slick.Color;

import core.Engine;

public class Values // this will be quite useful for organizing stuff later on
{	
	// Rendering Variables
	final public static HashMap<Integer, Color> BlockHash = new HashMap<Integer, Color>(); // Stores our Block - Color Combinations
	final public static int Render_Distance = 2; // Render distance
	
	final public static float CenterX = Engine.RESOLUTION_X / 2; // Player Center X
	final public static float CenterY = Engine.RESOLUTION_Y / 2; // Player Center Y
	
	// Player Variables
	final public static float Player_Reach = 10f;
	
	// Word Generation Variables
	final public static int Surface = 1024; // Surface Mapping
	
	// Spawning Variables
	final public static float SpawnX = Values.World_X_Size * Values.Chunk_Size_X / 2; // Player X Spawn
	final public static float SpawnY = Surface + 32f; // Player Y Spawn
	final public static float Spawn_Rate = 10f; // Entity Spawn Rate
	
	// World Size Variables
	final public static int World_X_Size = 100; // World Size (in chunks)
	final public static int Chunk_Size_X = 16; // Chunk X Size (in blocks)
	final public static int Chunk_Size_Y = 2048; // Chunk Y Size (in blocks)
	
	// Physics Variables
	final public static float Terminal_X_Velocity = 13.5f; // X Terminal Velocity
	
	// File Save Paths
	final public static String Save_Folder = "saves/"; // Directory where all world information will be saved
	final public static String Hash_File_Path = "src/settings/BlockHashing.txt"; // File where all block hashing will be located
}
