package nz.ac.wgtn.swen225.lc.app;

import java.io.File;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import nz.ac.wgtn.swen225.lc.app.gui.GameWindow;

/**
 * Manage keyboard inputs for the game.
 *
 * @author Trent Shailer 300602354.
 */
public class InputManager {
  private static final int MOVEMENT_TIMEOUT = 100;
  // This value should be updated dynamically based on renderer
  // Since that needs to support recorder playback speed and
  // there should only be one source of truth for that value

  private final Game game;
  private final GameWindow gameWindow;
  private long lastMoved = 0;


  /**
   * Create a new input manager for the window.
   *
   * @param scene The scene for the input manager to capture inputs through.
   */
  public InputManager(Scene scene, Game game, GameWindow gameWindow) {
    this.game = game;
    this.gameWindow = gameWindow;

    scene.setOnKeyPressed(event -> onKeyPressed(event.getCode(), event.isControlDown()));
  }

  /**
   * Handle user input.
   *
   * @param key           The Keycode of the key pressed by the user.
   * @param isControlDown Is the control key being held down.
   */
  public void onKeyPressed(KeyCode key, boolean isControlDown) {
    if (key.isArrowKey()) {
      handleMovement(key);
    } else if (key.isDigitKey() && isControlDown) {
      handleLoadLevel(key);
    } else if (key.isLetterKey() && isControlDown) {
      handleGame(key);
    } else if (key == KeyCode.SPACE) {
      game.pauseGame(true);
    } else if (key == KeyCode.ESCAPE) {
      game.resumeGame();
    }
  }

  private void handleMovement(KeyCode key) {
    assert key.isArrowKey();

    long now = System.currentTimeMillis();

    // If the timeout on the movement is active, don't let the player move
    if (now - lastMoved < MOVEMENT_TIMEOUT) {
      return;
    }

    Direction dir = Direction.keyToDirection(key);
    assert dir != Direction.NONE;
    boolean success = game.movePlayer(dir);

    // Start timeout
    if (success) {
      lastMoved = now;
    }
  }

  private void handleLoadLevel(KeyCode key) {
    assert key.isDigitKey();

    int levelNum = key.ordinal() - 24;
    if (levelNum < 1 || levelNum > 2) {
      return;
    }

    String path = "levels/level" + levelNum + ".json";
    game.loadGame(new File(path));
  }

  private void handleGame(KeyCode key) {
    assert key.isLetterKey();

    if (key == KeyCode.X) {
      game.exitGame(false);
    } else if (key == KeyCode.S) {
      game.exitGame(true);
    } else if (key == KeyCode.R) {
      File file = gameWindow.openSaveSelectorDialog();
      if (file == null) {
        return;
      }

      game.loadGame(file);
    }
  }
}
