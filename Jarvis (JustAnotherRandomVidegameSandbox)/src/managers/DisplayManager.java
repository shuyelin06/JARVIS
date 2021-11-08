package managers;

import org.newdawn.slick.state.BasicGameState;

import core.Coordinate;
import core.Engine;
import settings.Values;
import world.Background;

// Will handle all of the game's graphics / display
public class DisplayManager {
	// Everything will be displayed relative to this center (in pixels)
	Coordinate center;
	
	private Background background;
	private BasicGameState game;
	
	public DisplayManager(BasicGameState g) {
		Coordinate center = new Coordinate(Values.CenterX, Values.CenterY);
		
		this.game = g;
	};
	
	public void renderBackground() {
		
	}
	public void renderBlocks() {
		
	}
	
	public float[] positionOnScreen(float x, float y) {
		float[] output = center.displacement(x, y);
		
		output[0] = output[0] * Coordinate.ConversionFactor + Values.CenterX;
		output[1] = Engine.RESOLUTION_Y - (output[1] * Coordinate.ConversionFactor + Values.CenterY);
		
		return output;
	}
	public float[] positionInGame(float x, float y) {
		float[] output = new float[2];
		
		// Find the distance from the pixel center
		output[0] = center.getX() + (x - Values.CenterX) / Coordinate.ConversionFactor;
		output[1] = center.getY() + 1 + (Values.CenterY - y) / Coordinate.ConversionFactor;
		
		return output;
	}
}