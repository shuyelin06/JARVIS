package managers;

import java.util.ArrayList;
import java.util.HashMap;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;

import background.Background;
import background.Tutorial;
import core.Coordinate;
import core.Engine;
import core.Values;
import entities.Entity;
import entities.Entity.EntType;
import entities.living.Player;
import entities.other.EBlock;
import gamestates.Game;
import inventory.Inventory;
import inventory.Item;
import structures.Block;
import world.Chunk;
import world.World;

// Will handle all of the game's graphics / display
public class DisplayManager {
	final private static double Span_Divide = 1.5; // Determines the number of blocks to be rendered
	
	// Variables for easier accessing
	private static Game game = Engine.game;
	private Graphics graphics;

	// Display everything relative to this center
	private Coordinate center; 
	
	//lighting, fun
	private float globalLight; //steven y will probably move all of this stuff to some other class, just here for now
	private float tempLight; //temporary lighting so everything doesn't go black during the night
	private float elevationLight; //gets darker as you go down
	
	// Block Sprites
	private HashMap<Integer, Integer> tileHash;
	private SpriteSheet tileset;
	
	// Rendering Variables
	private Tutorial tutorial;
	private Background background;
	
	public DisplayManager(Graphics graphics, Coordinate center) throws SlickException {
		this.graphics = graphics; // Save Graphics Variable
		
		// Create tutorial and background 
		this.tutorial = new Tutorial();
		this.background = new Background(Engine.Game_ID);
		
		tempLight = 0.8f;
		globalLight = 0;
		elevationLight = 0;
		
		// Save center
		this.center = center;
				
		// Initializing Block Spritesheet
		tileset = new SpriteSheet("res/tileset.png", 30, 30);
		
		// Block Hashing: Key - Block ID,
		tileHash = new HashMap<Integer, Integer>();
		tileHash.put(1, 1); // Block ID 1 = dirt
		tileHash.put(2, 0); // Block ID 2 = grass
		tileHash.put(3, 2); // Block ID 3 = stone
		tileHash.put(4, 3); // Block ID 4 = coal
		tileHash.put(5, 4);	// Block ID 5 = gold
		tileHash.put(6, 5); // Block ID 6 = diamonds
		tileHash.put(7, 6); // Block ID 7 = sand
		tileHash.put(8, 7); // Block ID 8 = sandstone
	};
	
	// Accessor Methods
	public SpriteSheet getSpriteSheet() { return tileset; }
	public HashMap<Integer, Integer> getSpriteHash(){ return tileHash; }
	
	public Background getBackground() { return background; }
	
	public Image getBlockSprite(int id) { return tileset.getSubImage(0, tileHash.get(id)); }
	
	// Helper Methods 
	public float screenX(float gameX) { return (gameX - center.getX()) * Values.Pixels_Per_Block + Values.CenterX; } // Return the pixel position of a game coordinate on screen
	public float screenY(float gameY) { return Engine.RESOLUTION_Y - ((gameY - center.getY()) * Values.Pixels_Per_Block + Values.CenterY); }
	
	public float gameX(float screenX) { return center.getX() + (screenX - Values.CenterX) / Values.Pixels_Per_Block; } // Return the position of a screen coordinate in the game
	public float gameY(float screenY) { return center.getY() + 1 + (Values.CenterY - screenY) / Values.Pixels_Per_Block; }
	
	// Render Methods
	// Main Render Method
	public void render(Graphics g) {
		// Update variables in display manager
		background.update(); // Update background
		tutorial.update(); // Update tutorial
		
		if(screenY(Values.Surface) < 0)
		{
			elevationLight = 1;
		} 
		else if(screenY(Values.Surface) > 500)
		{
			elevationLight = 0;
		}
		else
		{
			elevationLight = (500 - screenY(Values.Surface) ) / 500;
		}
		
		globalLight = 1 - (Engine.game.getDisplayManager().getBackground().getSky().getNightAlpha() * tempLight) - elevationLight; 
		if(globalLight < 0.1) globalLight = 0.1f;

		//????????? LOLLLLLL WTF ARE THOSE ACESSSORS
		
		// Render everything
		renderBackground(g); // Render background first
		renderBlocks(g); // Render blocks on top of background
		renderEntities(g); // Render entities on top of blocks
		renderPlayer(g); // Render player UI on top of entities
		renderTutorial(g); // Render tutorial on top of all else
	}
	
	public void renderBackground(Graphics g) { background.render(g, screenX(0), screenY(Values.Surface)); } 
	public void renderTutorial(Graphics g) { if (tutorial.canRender()) { tutorial.render(g); } }
	
	public void renderBlocks(Graphics g) {
		World world = game.getWorld();

		// X Span
		final int xSpan = (int) (Math.ceil(Engine.RESOLUTION_X / Values.Pixels_Per_Block) / Span_Divide);
		final int ySpan = (int) (Math.ceil(Engine.RESOLUTION_Y / Values.Pixels_Per_Block ) / Span_Divide);
		
		// Iterate through every block that will be displayed on screen
		for(int i = -xSpan; i < xSpan; i++) {
			for(int j = -ySpan; j < ySpan; j++) {
				int blockX = (int) center.getX() + i;
				int blockY = (int) center.getY() + j;
				
				int relChunkX = blockX % Values.Chunk_Size_X;
				
				Chunk c = world.getChunk(blockX / Values.Chunk_Size_X);
				if(c == null) continue;
				 
				// Fail safes
				if(relChunkX < 0) continue;
				if(blockY < 0 || blockY > Values.Chunk_Size_Y - 1) continue;
					
				// Get the block ID
				Block b = c.getBlocks()[relChunkX][blockY];
				int id = b.getID();
				
				float screenX = screenX(blockX);
				float screenY = screenY(blockY);

				if(id == 0) continue;
				switch(id) {
					case 2: // Grass
						int variant = world.getGrassVariant(c.getBlocks(), blockX % Values.Chunk_Size_X, blockY, c.getX());
						if(variant == 7) {
							g.drawImage(setLight(tileset.getSubImage(0, 1)), screenX, screenY);
						}else {
							g.drawImage(setLight(tileset.getSubImage(variant, 0)), screenX, screenY);
						}
						break;
					case 3: // Stone
						g.drawImage(setLight(tileset.getSubImage( c.getBlocks()[relChunkX][blockY].getVariant(), tileHash.get(id) ) ), 
								screenX, screenY);
						break;
					default: // Every other block
						g.drawImage(setLight( tileset.getSubImage(c.getBlocks()[relChunkX][blockY].getVariant(), tileHash.get(id) ) ), 
								screenX, screenY);
						break;
				}
				
				if(b.getDurability() != Block.Max_Durability) {
					Image crack = ImageManager.getImage("crack");
					crack = crack.getScaledCopy(Values.Pixels_Per_Block, Values.Pixels_Per_Block);
					
					crack.setImageColor(0f, 0f, 0f, (float) (Block.Max_Durability - b.getDurability()) / Block.Max_Durability);
					crack.draw(screenX, screenY);
				}
			}
		} 
	}
	public void renderEntities(Graphics g) {
		for(ArrayList<Entity> list: game.getAllEntities().values()) {
			for(Entity e: list) {
				// Render Entities
				renderEntity(g, e);
				
				// Outline EBlocks
				if(e instanceof EBlock) {
					g.setColor(Color.black);
					g.drawRect(
							screenX(e.getPosition().getX()), // X Position
							screenY(e.getPosition().getY()), // Y Position
							e.getSizeX() * Values.Pixels_Per_Block, // Width
							e.getSizeY() * Values.Pixels_Per_Block // Height
						);
				}
			}	
    	}
	}
	public void renderPlayer(Graphics g) {
		Player p = game.getPlayer();
		
		drawPlayerHealth(g, p);
		drawPlayerInventory(g, p);
	}
	
	
	private void renderEntity(Graphics g, Entity e) {
		float screenX = screenX(e.getPosition().getX());
		float screenY = screenY(e.getPosition().getY());
		Image sprite = e.getSprite();
		
		if(e.getDirection()) { // Moving right
			e.setPastDirection(true);
		} else if (e.getLeft()){ // Moving left
			e.setPastDirection(false);
		}
		if (e.getPastDirection()) { // Moving right
			setLight(sprite).draw(screenX, screenY, e.getSizeX() * Values.Pixels_Per_Block, e.getSizeY() * Values.Pixels_Per_Block ); 
		} else { // Moving left
			setLight(sprite).draw(screenX + e.getSizeX() * Values.Pixels_Per_Block , screenY, -e.getSizeX() * Values.Pixels_Per_Block , e.getSizeY() * Values.Pixels_Per_Block); 
		}
	}
	
	private void drawPlayerHealth(Graphics g, Player p) {
		// Defining bar variables
		final float BAR_WIDTH = (float) ((Engine.game.getGC().getWidth()/2) - (0.15625 * Engine.game.getGC().getWidth()));
		final float BAR_HEIGHT = (float) (30f / 1080f) * Engine.game.getGC().getHeight();
		
		// Player max health bar
		g.setColor(new Color(0, 100, 0, 150));
		g.fillRect((float) (Engine.game.getGC().getWidth() - (0.05208333333 * Engine.game.getGC().getWidth())), (float) (0.03703703703 * Engine.game.getGC().getHeight()), -BAR_WIDTH, BAR_HEIGHT);
		
		// Player health bar
		g.setColor(new Color(0, 255, 0, 150));
		g.fillRect((float) (Engine.game.getGC().getWidth() - (0.05208333333 * Engine.game.getGC().getWidth())), (float) (0.03703703703 * Engine.game.getGC().getHeight()), -BAR_WIDTH * Engine.game.getPlayer().getPercentHealth(), BAR_HEIGHT);
		
		// Health bar white outline
		g.setColor(new Color(255, 255, 255));
		g.drawRect((float) (Engine.game.getGC().getWidth() - (0.05208333333 * Engine.game.getGC().getWidth())), (float) (0.03703703703 * Engine.game.getGC().getHeight()), -BAR_WIDTH, BAR_HEIGHT);
	}
	private void drawPlayerInventory(Graphics g, Player p) {
		// Defining bar variables
		final float BAR_WIDTH = (float) ((Engine.game.getGC().getWidth()/2) - (0.15625 * Engine.game.getGC().getWidth()));
		final float BAR_HEIGHT = (float) ((60f / 1080f) * Engine.game.getGC().getHeight());
		
		// Inventory grey coloration 
		g.setColor(new Color(150, 150, 150, 150));
		g.fillRect((float) (0.050208333333 * Engine.game.getGC().getWidth()), (float) (0.03703703703 * Engine.game.getGC().getHeight()), BAR_WIDTH, BAR_HEIGHT);
		
		// Inventory outline
		g.setColor(new Color(255, 255, 255));
		g.drawRect((float) (0.050208333333 * Engine.game.getGC().getWidth()), (float) (0.03703703703f * Engine.game.getGC().getHeight()), BAR_WIDTH, BAR_HEIGHT);
		
		// Draw every item in the player's inventory
		final float boxSize = BAR_WIDTH / (float) Inventory.Inventory_Size;
		Item[] list = p.getInventory().getItems();
		final float center = (boxSize - (float) Values.Pixels_Per_Block) / 2f;
		
		for(int i = 0; i < list.length; i++) {
			if(list[i] == null) continue; // If the item is null, skip
			else if(list[i].getID() == 0) continue; // ID of 0 indicates an empty space
			
			Integer count = list[i].getCount();
			float barDisp = i * boxSize;
			
			list[i].getImage().setImageColor(1f, 1f, 1f);
			list[i].getImage().draw(
					barDisp + 0.050208333333f * Engine.RESOLUTION_X + center, 
					0.03703703703f * Engine.game.getGC().getHeight() + center,
					Values.Pixels_Per_Block,
					Values.Pixels_Per_Block
					);
			g.drawString(count.toString(), barDisp + 0.050208333333f * Engine.RESOLUTION_X,  0.03703703703f * Engine.game.getGC().getHeight()); // Text	
		}
		
		// Draw a box around the selected item
		g.setColor(Color.black);
		g.drawRect(p.inventorySelected() * boxSize + 0.050208333333f * Engine.RESOLUTION_X, 0.03703703703f * Engine.game.getGC().getHeight(), boxSize, BAR_HEIGHT);
	}
	
	private Image setLight(Image image)
	{
		image.setImageColor(globalLight, 
				globalLight, 
				globalLight);
		return(image);
	}
	
	// Debug Methods
	public void pinpoint(float x, float y) {
		graphics.setColor(Color.black);
		graphics.fill(new Circle(screenX(x), screenY(y), 5f));
	}
	public void highlightBlock(int x1, int y1) {
		graphics.setColor(Color.white);
		graphics.draw(new Rectangle(screenX(x1), screenY(y1), Values.Pixels_Per_Block , Values.Pixels_Per_Block ));
	}
	
	
}