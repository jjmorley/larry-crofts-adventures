package nz.ac.wgtn.swen225.lc.app;

import javafx.scene.Scene;

/**
 * Manage keyboard inputs for the game.
 *
 * @author Trent Shailer 300602354.
 */
public class InputManager {
  private Scene scene;

  /**
   * Create a new input manager for the window.
   *
   * @param scene The scene for the input manager to capture inputs through.
   */
  public InputManager(Scene scene) {
    this.scene = scene;
  }
}
