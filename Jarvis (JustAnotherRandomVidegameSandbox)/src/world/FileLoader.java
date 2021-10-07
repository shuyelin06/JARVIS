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
		World newWorld = new World("Test World");

		createWorldFolders(newWorld.getWorldName());
		for(Chunk c: newWorld.getRenderedChunks()) {
			SaveChunk(newWorld.getWorldName(), c);
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
		System.out.println("Attempting to load chunk " + chunkX);
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
		try {
			FileReader reader = new FileReader(Hash_File_Path);
			
			int data = reader.read();
			
			String[] values = new String[5];
			Arrays.fill(values, "");
			int index = 0;
			while(data != -1) {
				if(data == 10) {
					Values.BlockHash.put(Integer.parseInt(values[0]), 
							new Color(
									Float.parseFloat(values[1]), 
									Float.parseFloat(values[2]), 
									Float.parseFloat(values[3]), 
									Float.parseFloat(values[4]))
							);
					
					index = 0;
					Arrays.fill(values, "");
				} else if(data == 32) {			
					index++;
				} else {
					values[index] += (char) data;
				}
				
				data = reader.read();
			}
			
			reader.close();
			
		} catch(IOException error) {
			System.out.println("There was an error in loading block hashings");
		}
	}
}