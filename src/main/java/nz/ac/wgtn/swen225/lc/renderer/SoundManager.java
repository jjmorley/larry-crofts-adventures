package nz.ac.wgtn.swen225.lc.renderer;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javafx.scene.media.AudioClip;


/**
 * Manages importing the sounds files for Renderer use.
 *
 * @author morleyjose
 */
public class SoundManager {
  private final Map<String, AudioClip> soundCache;

  /**
   * Initialises the map of sounds and sound names.
   */
  public SoundManager() {
    soundCache = new HashMap<>();
    loadSounds();
  }

  /**
   * Load all sounds into a map for easy access.
   *
   * @throws IllegalArgumentException if any sound file cannot be loaded
   */
  private void loadSounds() {
    try {
      soundCache.put("Key", new AudioClip(Objects.requireNonNull(this.getClass().getResource(
              "/Sounds/Key.mp3")).toExternalForm()));
      soundCache.put("Door", new AudioClip(Objects.requireNonNull(this.getClass().getResource(
              "/Sounds/Door.mp3")).toExternalForm()));
      soundCache.put("Exit_Door",
              new AudioClip(Objects.requireNonNull(this.getClass().getResource("/Sounds/Exit_Door"
                      + ".mp3")).toExternalForm()));
      soundCache.put("Lose", new AudioClip(Objects.requireNonNull(this.getClass().getResource(
              "/Sounds/Lose.mp3")).toExternalForm()));
      soundCache.put("Win", new AudioClip(Objects.requireNonNull(this.getClass().getResource(
              "/Sounds/Win.mp3")).toExternalForm()));
      soundCache.put("Treasure",
              new AudioClip(Objects.requireNonNull(this.getClass().getResource("/Sounds/Treasure"
                      + ".mp3")).toExternalForm()));
      soundCache.put("Splash", new AudioClip(Objects.requireNonNull(this.getClass().getResource(
              "/Sounds/Splash.mp3")).toExternalForm()));
    } catch (Exception e) {
      System.err.println("Error loading sound file: " + e.getMessage());
    }
  }

  /**
   * Play a sound corresponding to a given string.
   *
   * @param soundName name of sound about to be played
   */
  public void playSoundEffect(String soundName) {
    soundCache.get(soundName).play();
  }
}
