package entities.core;

import core.BlockSettings;
import core.Engine;
import core.Values;
import gamestates.Game;
import managers.DisplayManager;
import managers.ImageManager;
import structures.Block;
import support.Utility;

import org.lwjgl.Sys;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class Entity{
	// Reference Variables
	protected static Game game = Engine.game;
	
	// Entity Type
	public enum EntType{ Living, Items, Projectiles }
	
	// Management Variables
	protected long time; 				// Tracks how long the entity has been alive
	protected boolean remove; 			// Allows entity to be marked for removal
	
	// Physics Variables
	protected Coordinate position; 		// Entity position
	
	protected float xSpeed, ySpeed;		// Entity velocity (pixels per second)
	protected float mass; 				// Entity mass
	
	protected float angle; 				// Angle of rotation
		
	// Descriptive Variables
	protected float width, height; 		// The size of the Entity
		
	// Render Variables
	protected Image sprite; 			// The sprite rendered in for the Entity
	protected int pastDirection;    	// Marker for direction last faced
	
	// Entity Hitbox
	protected Rectangle hitbox;		
	
	// Every entity will have some initial starting position
	public Entity(float InitX, float InitY) {
		// Management Variables
		this.time = Sys.getTime();
		this.remove = false;
		
		// Initial Physics Variables
		this.position = new Coordinate(InitX, InitY); // Initial position
		
		this.xSpeed = this.ySpeed = 0f; // Default speeds
		this.mass = 1f; // Default Mass
		
		this.angle = 0f;
		
		// Default Size
		this.width = 1f;
		this.height = 1f;
		
		// Default Rendering Variables
		this.sprite = ImageManager.getPlaceholder();
		
		this.pastDirection = 1; // Left (-1), Right (1)
		
		// Initializing HitBox
		this.hitbox = new Rectangle(this);	
	}
	
	// Accessor Methods
	public float timeAlive() 				{ return (float) (Sys.getTime() - time) / 1000f; }
	public Image getSprite() 				{ return sprite; }
	public boolean movingRight() 			{ return xSpeed > 0; } // False - Left, True - Right
	public boolean movingLeft()        		{ return xSpeed < 0; } // if true, then left
	public float getWidth() 				{ return width; }
	public float getHeight() 				{ return height; }
	public Coordinate getPosition() 		{ return position; }
	public float getXSpeed() 				{ return xSpeed; }
	public float getYSpeed() 				{ return ySpeed; }
	public boolean isMarked() 				{ return remove; }
	
	public float getAngle() 				{ return angle; }
	
	// Mutator Methods
	public void updateSprite(Image image) 	{ this.sprite = image; }
	
	public void markForRemoval()			{ this.remove = true; }
	public void setSpeedX(float xSpeed) 	{ this.xSpeed = xSpeed; }
	public void setSpeedY(float ySpeed) 	{ this.ySpeed = ySpeed; }
	
	/* Entity Updating */
	public void update() {
		// Update Velocities
		gravity();
		drag();
		
		// Collision detection 
		checkCollisions();
		
		// Update position
		position.update(xSpeed, ySpeed);
	}
	
	/* Entity Rendering */
	public void render(Graphics g) {
		renderOther(g); // Draw unique entity graphics
		drawSprite(); // Draw entity sprite
	}
	
	public void debug(Graphics g) {
		hitbox.drawHitBox(g); // Draw hitbox first
	}

	protected void renderOther(Graphics g) {} // Rendering method unique to the entity
	protected void drawSprite() { // Draw the entity sprite
		DisplayManager display = game.displayManager;
		
		// Determine Entity Direction
		int direction = (int) Math.signum(xSpeed);
		if(direction != 0) pastDirection = direction;
		
		// Scale the sprite 
		Image im = sprite.getScaledCopy((int) (width * Values.Pixels_Per_Block * pastDirection), (int) (height * Values.Pixels_Per_Block));

		// Set Sprite Lighting
		float globalLight = display.getGlobalLight();
		im.setImageColor(globalLight, globalLight, globalLight);
		
		// Rotate the sprite
		im.setCenterOfRotation(im.getWidth() / 2, im.getHeight() / 2);
		im.rotate((float) -(angle * 180 / Math.PI)); // Convert to clockwise degrees

		// Draw the sprite
		im.drawCentered(
				display.screenX(position.x), 
				display.screenY(position.y)
				);
	}
	
	/* Velocity Updating */
	protected void gravity() {
		ySpeed -= Values.Acceleration_of_Gravity; // Gravity acts on y velocity
	}
	protected void drag() { // Drag acts on x and y velocity
		xSpeed -= xSpeed * Values.Friction / this.mass;
		ySpeed -= ySpeed * Values.Drag / this.mass;
	}
	
	/* Collision Detection */
	private static final float collisionError = 0.0001f; // Prevents the object from sticking
	
	// Main collision method that is called
	protected void checkCollisions() {
		blockCollisions();
		entityCollisions();
	};
	
	// Empty collision method for us to check for unique entity collisions
	protected void entityCollisions() {}; 
	
	// Helper method that returns true if this entity will collide with another entity e.
	protected boolean entityCollision(Entity e) { return this.hitbox.intersects(e.hitbox); }
	
	// Checks Entity - Block Collisions
	protected void blockCollisions() {
		try {
			checkHorizontalCollision(); // Check for horizontal collisions
			checkVerticalCollision(); // Check for vertical collisions
		} catch(Exception e) {}
	};
	
	// Empty block collision method that can be used in other classes
	protected void onBlockXCollision() {} // Specific to x collisions
	protected void onBlockYCollision() {} // Specific to y collisions
	protected void onBlockCollision() {} // Called in any collision, x or y
		
	// Check Vertical Block Collisions
	private void checkVerticalCollision() {
		float yBorder = position.getY() + ySpeed / Engine.FRAMES_PER_SECOND + height / 2f * Math.signum(ySpeed);
		// yBorder -= TBAdjustment;
		
		int leftX = (int) (position.getX() - width / 2);
		int rightX = (int) (position.getX() + width / 2);
		
		if(ySpeed > 0) {
			for(int i = (int) Math.ceil(position.getY()); i <= (int) Math.ceil(yBorder); i++) {
				for(int j = leftX; j <= rightX; j++) {
					Block b = Engine.game.getWorld().getChunk(j / Values.Chunk_Size_X).getBlocks()[j % Values.Chunk_Size_X][i];
					
					int id = b.getID();
					if(!BlockSettings.isPassable(id)) {
						// Adjust Y Position
						this.position.setYPos(i - 1f - height / 2f - collisionError);
						
						// Call Collision Methods
						onBlockYCollision();
						onBlockCollision();
						
						ySpeed = 0f;
						break;
					}
					
				}
			}
		} else if (ySpeed < 0) {
			for(int i = (int) Math.ceil(position.getY()); i >= (int) Math.ceil(yBorder); i--) {
				for(int j = leftX; j <= rightX; j++) {
					Block b = Engine.game.getWorld().getChunk(j / Values.Chunk_Size_X).getBlocks()[j % Values.Chunk_Size_X][i];
					
					game.displayManager.highlightBlock(j, i);
					int id = b.getID();
					if(!BlockSettings.isPassable(id)) {
						
						// Adjust Y Position
						this.position.setYPos(i + height / 2 + collisionError);
						
						// Call Collision Methods
						onBlockYCollision();
						onBlockCollision();
						
						ySpeed = 0f;
						break;
					}
					
				}
			}
		}
	}
	
	// Check Horizontal Block Collisions
	private void checkHorizontalCollision() {
		float xBorder = position.getX() + xSpeed / Engine.FRAMES_PER_SECOND + width / 2f * Math.signum(xSpeed);
		
		int topY = (int) Math.ceil(position.getY() + height / 2 - collisionError);
		int bottomY = (int) Math.ceil(position.getY() - height / 2);
		
		if(xSpeed > 0) {
			for(int i = (int) position.getX(); i <= (int) xBorder; i++) {
				for(int j = bottomY; j <= topY; j++) {
					Block b = Engine.game.getWorld().getChunk(i / Values.Chunk_Size_X).getBlocks()[i % Values.Chunk_Size_X][j];
					
					int id = b.getID();
					if(!BlockSettings.isPassable(id)) {
						// Adjust X Position
						position.setXPos(i - width / 2 - collisionError);
						
						// Call Collision Methods
						onBlockXCollision();
						onBlockCollision();
						
						xSpeed = 0f;
						break;
					}
					
				}
			}
		} else if (xSpeed < 0) {
			for(int i = (int) position.getX(); i >= (int) xBorder; i--) {
				for(int j = bottomY; j <= topY; j++) {
					Block b = Engine.game.getWorld().getChunk(i / Values.Chunk_Size_X).getBlocks()[i % Values.Chunk_Size_X][j];
					
					int id = b.getID();
					if(!BlockSettings.isPassable(id)) {
						// Adjust X Position
						position.setXPos(i + 1 + width / 2 + collisionError);
						
						// Call Collision Methods
						onBlockXCollision();
						onBlockCollision();
						
						xSpeed = 0f;
						break;
					}
				}
			}
		}
	}
	
}
