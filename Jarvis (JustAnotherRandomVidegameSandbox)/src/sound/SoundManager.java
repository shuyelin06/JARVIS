package sound;

import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class SoundManager {
	// Audio
	Music BackGroundMusic;
		
	Sound bruh;
	
	public SoundManager() {
		try {
			bruh = new Sound("res/Sound/Effects/Breh.ogg");
		} catch(SlickException e) {}
	}
	public void music(int biome) { } // To be used later
	public void startMusic() {
		// Music
		try{
			BackGroundMusic = new Music("res/Sound/Morning.ogg");
			BackGroundMusic.loop();
			System.out.println("Sound being played");
		} catch(Exception e) {}
	}
	
	public void breh() {
		bruh.play();
	}
}