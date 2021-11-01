package sound;

import org.newdawn.slick.Music;

public class SoundManager {
	// Audio
	Music BackGroundMusic;
		
	public void music(int biome) { } // To be used later
	public void startMusic() {
		// Music
		try{
			BackGroundMusic = new Music("res/Sound/Morning.ogg");
			BackGroundMusic.loop();
			System.out.println("Sound being played");
		} catch(Exception e) {}
	}
}