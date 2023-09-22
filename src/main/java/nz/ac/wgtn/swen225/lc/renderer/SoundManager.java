package nz.ac.wgtn.swen225.lc.renderer;

import java.util.*;
import javafx.scene.media.*;

/**
 * Manages importing the sounds files for Renderer use. 
 * @author morleyjose
 *
 */
public class SoundManager {

    private static final String BASE_PATH = "file:///C:/Users/joefa/Documents/SWEN225/Project/larry-crofts-adventures/assets/Sounds/";


    private Map<String, AudioClip> soundCache;
    private AudioClip backgroundMusic;//not implemented yet

    public SoundManager() {
    	soundCache = new HashMap<String, AudioClip>();
        //backgroundMusic = new AudioClip(getClass().getResource(BASE_PATH + "background_music.mp3").toExternalForm()); //NOT IMPLEMENTED YET
        loadSounds();
    }

    /**
     * Load all sounds into a map for easy access.
     */
    private void loadSounds() {
    	try {
            soundCache.put("Key",new AudioClip(BASE_PATH + "Key.mp3"));
    	 soundCache.put("Door",new AudioClip(BASE_PATH + "Door.mp3"));
    	 soundCache.put("Exit_Door",new AudioClip(BASE_PATH + "Exit_Door.mp3"));
    	 soundCache.put("Lose",new AudioClip(BASE_PATH + "Lose.mp3"));
    	 soundCache.put("Win",new AudioClip(BASE_PATH + "Win.mp3"));
    	 soundCache.put("Treasure",new AudioClip(BASE_PATH + "Treasure.mp3"));
    	}catch (Exception e) {
    		    System.err.println("Error loading sound file: " + e.getMessage());
    		}
    }
    
    /**
     * Music for game. NOT IMPLEMENTED YET
     */
    public void playBackgroundMusic() {
        backgroundMusic.setCycleCount(AudioClip.INDEFINITE); // Loop the background music indefinitely
        backgroundMusic.play();
    }

    /**
     * Stop the music.
     */
    public void stopBackgroundMusic() {
        backgroundMusic.stop();
    }

    /**
     * Play a sound corresponding to a given string.
     * @param soundName
     */
    public void playSoundEffect(String soundName) {
        soundCache.get(soundName).play();
    }
}
