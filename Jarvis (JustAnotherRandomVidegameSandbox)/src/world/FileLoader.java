package world;

import org.lwjgl.Sys;

import structures.Block;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

// Save and Load World Information
public class FileLoader{
	final private static String Save_Folder = "saves/";
	
	public static void main(String[] args) {		
		// Chunk Generation 
		World world = new World("test");
	
		
		long time = Sys.getTime();
		SaveWorld(world);
		System.out.println(Sys.getTime() - time);
		
		time = Sys.getTime();
		LoadChunk(world.getWorldName(), 1);
		System.out.println(Sys.getTime() - time);
	}
	
	// Saves an entire world (Save + Quit, and on Initial Creation)
	public static void SaveWorld(World world) {
		// Step 1: Create a folder for the world (with an inventory, entities, chunk folder)
		createWorldFolders(world.getWorldName());
		
		// Step 2: Save every chunk of the world to a file (will later use the WorldGen class, not world)
		String chunkFolder = Save_Folder + world.getWorldName() + "/chunks";
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
}