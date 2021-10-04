package world;

import org.lwjgl.Sys;

import structures.Block;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

// Save and Load World Information
public class FileLoader{
	/* World Saving:
	 * Saves data into the world Directory
	*/
	final private static String Save_Folder = "saves/";
	
	// Tests the load and retrieve data method times
	private static void TestTimes() {
		// Chunk Generation 
		World world = new World("test");
	
		long averageSaveTime = 0;
		long averageLoadTime = 0;
		
		long tempTime;
		int runs = 5;
		for(int i = 0; i < runs; i++) {
			tempTime = Sys.getTime();
			SaveWorld(world);
			averageSaveTime += Sys.getTime() - tempTime;
			
			tempTime = Sys.getTime();
			LoadChunk(world.getWorldName(), 1);
			averageLoadTime += Sys.getTime() - tempTime;
		}
		
		averageSaveTime /= (long) runs;
		averageLoadTime /= (long) runs;
		
		System.out.println("Average save time: " + averageSaveTime + " milliseconds");
		System.out.println("Average load time: " + averageLoadTime + " milliseconds");
	}
	
	// Saves an entire world (Save + Quit, and on Initial Creation)
	public static void SaveWorld(World world) {
		// Step 1: Create a folder for the world (with an inventory, entities, chunk folder)
		createWorldFolders(world.getWorldName());
		
		// Step 2: Save every chunk of the world to a file (will later use the WorldGen class, not world)
		for(Chunk c: world.getRenderedChunks()) {
			SaveChunk(world.getWorldName(), c);
		}
		
		// Step 3: Inventory, Entities (Later)
		
	}
	
	// Saves a chunk (when playing)
	public static void SaveChunk(String worldName, Chunk c) {
		try {
			FileWriter writer = new FileWriter(new File(Save_Folder + worldName + "/chunks/" + c.getX() + ".chunk"));
			
			Block[][] blocks = c.getBlocks();
			for(int y = Chunk.Chunk_Size_Y - 1; y >= 0 ; y--) {
				for(int x = 0; x < Chunk.Chunk_Size_X; x++) {
					String id = Integer.toString(blocks[x][y].getID());
					
					writer.write(id);
					writer.write(' ');
				}
				writer.write("\n");
			}
			
			writer.close();
		} catch(IOException e) {
			System.out.println("There was an error in saving chunk " + c.getX());
		}
		
	}
	
	// Creates all directories/subdirectories for our world
	public static boolean createWorldFolders(String name) {
		String path = Save_Folder + name;
		if(new File(path).mkdir()) {
			new File(path + "/entities").mkdir();
			new File(path + "/player").mkdir();
			new File(path + "/chunks").mkdir();
			
			return true;
		} 
		else return false;
	}
	
	
	/*
	 * World Loading - Loads data from world directories
	 */
	// public static void LoadWorld() {}
	public static Chunk LoadChunk(String worldName, int chunkX) {
		// Code for chunk retrieval
		Block[][] blocks = new Block[Chunk.Chunk_Size_X][Chunk.Chunk_Size_Y];
		
		String path = Save_Folder + worldName + "/chunks/" + chunkX + ".chunk";
		try { // Space is ASCII 32, Newline is ASCII 10
			FileReader reader = new FileReader(path);
			
			int x = 0, y = Chunk.Chunk_Size_Y - 1;
			String id = "";
			
			int data = reader.read();
			while(data != -1) { // When data == -1, the file reading is complete
				if(data == 10) {
					x = 0;
					y -= 1;
				} else if(data == 32) {
					blocks[x][y] = new Block(Integer.parseInt(id));
					
					x++;
					id = "";
				} else {
					id += (char) data;
				}
				
				data = reader.read();
			}
			
			reader.close();
		} catch(IOException err) {
			System.out.println("There was an error in loading chunk " + chunkX);
		}
		
		
		// Printing (will be deleted later)
		for(int y = Chunk.Chunk_Size_Y - 1; y >= 0 ; y--) {
			for(int x = 0; x < Chunk.Chunk_Size_X; x++) {
				System.out.print(blocks[x][y].getID() + " ");
			}
			System.out.println();
		}

		return new Chunk(chunkX, blocks);
	}
	
	
	/*
	 * Block Hash Loading
	 * Loads the mappings for block IDs
	 */
	final private static String Hash_File_Path = "";
	public static void AddBlockHashing() {
		
	}
	
	public static void LoadBlockHashings(){
		
	}
	
	// 2/3 below below the surface is the ground
	// 1/3 above the surface is the sky
	// Dirt
	// Grass
	// Stone
	// Deepslate
}