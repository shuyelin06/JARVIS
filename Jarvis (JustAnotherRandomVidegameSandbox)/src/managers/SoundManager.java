package managers;

import java.util.HashMap;

import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import support.FileLoader;

public class SoundManager {
	private HashMap<String, Sound> soundHashing;
	private Sound background;
	
	public SoundManager() {
		soundHashing = FileLoader.LoadSoundFiles();
		background = null;
	}
	
	private static String getName(String s) { return s + ".ogg"; }
	
	public void playSound(String name) {
		try {
			Sound s = soundHashing.get(getName(name));
			if(!s.playing()) s.play(); // This way, the same sound effect will not be played over itself
		} catch(Exception e) { System.out.println("Failure in playing a sound"); }
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