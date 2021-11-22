package managers;

import java.util.HashMap;

import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import core.Values;
import support.FileLoader;

public class SoundManager {
	private Sound background;
	private float pitch, volume;
	
	public SoundManager() {
		background = null;
		pitch = 1f;
		volume = 0.5f;
	}
	
	public void playSound(String name) {
		try {
			Sound s = Values.Sounds.get(name);
			if(!s.playing()) s.play(); // This way, the same sound effect will not be played over itself
		} catch(Exception e) { System.out.println("Failure in playing a sound"); }
	}
	
	public void playBackgroundMusic(String name) {
		if(background != null) background.stop(); // Stop the existing background music
		
		background = Values.Sounds.get(name); // Get the background music desired
		background.loop(pitch, volume); // Loop the background music
	}
	
	public void stopBackgroundMusic() {
		if(background != null) background.stop();
	}
	
	public void increaseVolume() {
		volume += 0.1;
		if (volume > 1) {
			volume = 1;
		}
	}
	public void decreaseVolume() {
		volume -= 0.1;
		if (volume < 0) {
			volume = 0;
		}
	}
}