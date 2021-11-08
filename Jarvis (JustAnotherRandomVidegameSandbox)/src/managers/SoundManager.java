package managers;

import java.util.HashMap;

import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import support.FileLoader;

public class SoundManager {
	HashMap<String, Sound> soundHashing;

	Sound background;
	
	public SoundManager() {
		soundHashing = FileLoader.LoadSoundFiles();
		background = null;
	}
	
	private static String getName(String s) { return s + ".ogg"; }
	
	public void playSound(String name) {
		soundHashing.get(getName(name)).play();
	}
	public void playBackgroundMusic(String name) {
		if(background != null) background.stop(); // Stop the existing background music
		
		background = soundHashing.get(getName(name)); // Get the background music desired
		background.loop(); // Loop the background music
	}
	
	public void stopBackgroundMusic() {
		if(background != null) background.stop();
	}
}