package world;

import org.lwjgl.Sys;
import org.newdawn.slick.Color;

import settings.Values;
import structures.Block;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

// Save and Load World Information
public class FileLoader{
	final private static String Save_Folder = "saves/"; // Directory where all world information will be saved
	final private static String Hash_File_Path = "src/settings/BlockHashing.txt"; // File where all block hashing will be located
	
  public static void main(String[] args) {		
//		// Chunk Generation 
//		World world = new World("test");
//	
//		
//		long time = Sys.getTime();
//		SaveWorld(world);
//		System.out.println(Sys.getTime() - time);
//		
//		time = Sys.getTime();
//		LoadChunk(world.getWorldName(), 1);
//		System.out.println(Sys.getTime() - time);
	  
	  Integer.parseInt("0");
	}
	
	// Saves an entire world (Save + Quit, and on Initial Creation)
	public static void SaveWorld(World world) {
		// Step 1: Create a folder for the world (with an inventory, entities, chunk folder)
		createWorldFolders(world.getWorldName());
		
		// Step 2: Save every chunk of the world to a file (will later use the WorldGen class, not world)
		String chunkFolder = Save_Folder + world.getWorldName() + "/chunks";
		for(Chunk c: world.getAllChunks()) {
			SaveChunk(world.getWorldName(), c);
		}
	}
    
	/*
	 * Raw Functions - Unoptimized functions
	 */
	// Creates all directories / subdirectories for our world
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
	
	// Saves a chunk for a given world name
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
	
	// Load a chunk for a given world name
	public static Chunk LoadChunk(String worldName, int chunkX) {
		System.out.println("Loading Chunk " + chunkX);
		// Code for chunk retrieval
		Block[][] blocks = new Block[Chunk.Chunk_Size_X][Chunk.Chunk_Size_Y];
		
		File chunkFile = new File(Save_Folder + worldName + "/chunks/" + chunkX + ".chunk");
		if(!chunkFile.exists()) return null; // Exception code in case the chunk doesn't exist
		
		try { // Space is ASCII 32, Newline is ASCII 10
			FileReader reader = new FileReader(chunkFile);
			
			int x = 0, y = Chunk.Chunk_Size_Y - 1;
			String id = "";
			
			int data = reader.read();
			while(data != -1) { // When data == -1, the file reading is complete
				if(data == 10) {
					x = 0;
					y -= 1;
					
					id = "";
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

		return new Chunk(chunkX, blocks);
	}

//	// Adds a block hash
//	public static void AddBlockHashing(int id, float r, float g, float b, float a) {
//		try {
//			System.out.println(new File(Hash_File_Path).getAbsolutePath());
//			
//			FileWriter writer = new FileWriter(Hash_File_Path, true);
//			
//
//			// Write block id
//			writer.write(Integer.toString(id) + " ");
//			
//			writer.write(Float.toString(r) + " ");
//			writer.write(Float.toString(g) + " ");
//			writer.write(Float.toString(b) + " ");
//			writer.write(Float.toString(a) + "\n");
//
//			writer.close();
//		} catch(IOException e) {
//			System.out.println("There was an error in writing a block hash");
//		}
//	}
	
	
	// Load block hashings
	public static void LoadBlockHashings(){
		System.out.println(" --- Loading Block Hashings --- ");
		
		try {
			// Instantiating file reader
			FileReader reader = new FileReader(Hash_File_Path);
			
			// Instantiating variables needed for reading
			String[] values = new String[5];
			Arrays.fill(values, "");
			
			int data = reader.read();
			int index = 0;
			
			// File reading
			while(data != -1) {		
				// I don't know what the ASCII character 13 is, but it shows up so I'm skipping it so it doesn't cause errors
				if(data == 13) {}
				
				// Input character is a number
				else if(47 < data && data < 58) {
					values[index] += (char) data;
				} 
				
				// Input character is a space
				else if(data == 32) {
					index++;
					values[index] = ""; // Clear the preexisting value
				} 
				
				// Input character is a newline
				else if(data == 10){
					System.out.println("Adding New Block Hash: ");
					
					int i = Integer.parseInt(values[0]);
					Color c = new Color(
							Integer.parseInt(values[1]), 
							Integer.parseInt(values[2]), 
							Integer.parseInt(values[3]), 
							Integer.parseInt(values[4]));
					
					Values.BlockHash.put(Integer.parseInt(values[0]), c);
					
					index = 0;
					values[index] = "";
					
					System.out.println("Block ID: " + i);
					System.out.println("Block Color: " + c + "\n");
				}
				
				data = reader.read();
			}
			
			reader.close();
			
			System.out.println(" --- Finished Loading Block Hashings --- ");
		} catch(IOException error) {
			System.out.println("There was an error in loading block hashings");
		}
	}
}