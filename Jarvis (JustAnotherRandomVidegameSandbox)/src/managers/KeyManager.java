package managers;

import java.util.List;
import java.util.function.Predicate;

import org.newdawn.slick.Input;

import entities.living.Player;
import gamestates.Game;

// Tracks keys that are pressed down
public class KeyManager implements Predicate<Integer>{
	final public static List<Integer> keyList = List.of(Input.KEY_S, Input.KEY_D, Input.KEY_A);
	
	private Player player;
	private Input input;
	
	public KeyManager(Game game) {
		this.input = game.getGC().getInput();
		this.player = game.getPlayer();
	}
	
	// Predicate required method
	public boolean test(Integer key) {
		return input.isKeyDown(key);
	}
	
	public void keyDown(int key) {
		switch(key) {
			case Input.KEY_A:
				System.out.println("Key A pressed");
				player.setXSpeed(-1000f);
				break;
			case Input.KEY_D:
				System.out.println("Key D pressed");
				player.setXSpeed(1000f);
				break;
			case Input.KEY_S:
				player.fall();
				break;
			
		}
	}
	
}