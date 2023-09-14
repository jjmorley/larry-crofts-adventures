package nz.ac.wgtn.swen225.lc.renderer;

import javafx.scene.media.AudioClip;

/**
 * Manages importing the sounds files for Renderer use. 
 * @author morleyjose
 *
 */
public class SoundManager {
	private static final String BASE_PATH = "path_to_your_sound_folder/"; // Adjust this path to the location of your sound files

    private AudioClip backgroundMusic;
    // You can add more AudioClip variables for different sounds/effects

    public SoundManager() {
        // Load and initialize your audio clips here
        backgroundMusic = new AudioClip(getClass().getResource(BASE_PATH + "background_music.mp3").toExternalForm());
    }

    public void playBackgroundMusic() {
        backgroundMusic.setCycleCount(AudioClip.INDEFINITE); // Loop the background music indefinitely
        backgroundMusic.play();
    }

    public void stopBackgroundMusic() {
        backgroundMusic.stop();
    }

    public void playSoundEffect(String soundFilePath) {
        AudioClip soundEffect = new AudioClip(getClass().getResource(BASE_PATH + soundFilePath).toExternalForm());
        soundEffect.play();
    }
}
