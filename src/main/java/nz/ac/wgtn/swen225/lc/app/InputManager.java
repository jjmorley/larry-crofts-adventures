package nz.ac.wgtn.swen225.lc.app;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import nz.ac.wgtn.swen225.lc.app.gui.GameWindow;
import nz.ac.wgtn.swen225.lc.domain.gameObject.Moveable.Direction;


/**
 * Manage keyboard inputs for the game.
 *
 * @author Trent Shailer 300602354.
 */
public class InputManager {
  public static int MOVEMENT_TIMEOUT = 250;
  private final Game game;
  private final GameWindow gameWindow;
  private long lastMoved = 0;

  private boolean movementLocked = false;


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
   * Convert an arrow key to a direction.
   *
   * @param key the key pressed.
   */
  public static Direction keyToDirection(KeyCode key) {
    Direction out;
    switch (key) {
      case UP -> out = Direction.UP;
      case DOWN -> out = Direction.DOWN;
      case LEFT -> out = Direction.LEFT;
      case RIGHT -> out = Direction.RIGHT;
      default -> out = null;
    }
    return out;
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

    if (movementLocked) {
      return;
    }

    long now = System.currentTimeMillis();

    // If the timeout on the movement is active, don't let the player move
    if (now - lastMoved < MOVEMENT_TIMEOUT) {
      return;
    }

    Direction dir = keyToDirection(key);
    assert dir != null;
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

    try {
      URL fileUrl = getClass().getResource("/levels/level" + levelNum + ".json");
      if (fileUrl != null) {
        File f = new File(fileUrl.toURI());
        game.loadGame(f);
      }
    } catch (URISyntaxException ex) {
      System.out.println("Failed to load level " + levelNum + ", URI Syntax error: ");
      System.out.println(ex.getMessage());
    }
  }

  private void handleGame(KeyCode key) {
    assert key.isLetterKey();

    if (key == KeyCode.X) {
      game.exitGame(false);
    } else if (key == KeyCode.S) {
      game.exitGame(true);
    } else if (key == KeyCode.R) {
      File file = gameWindow.openFileSelectorDialog("Save");
      if (file == null) {
        return;
      }

      game.loadGame(file);
    }
  }

  public boolean isMovementLocked() {
    return movementLocked;
  }

  public void setMovementLocked(boolean movementLocked) {
    this.movementLocked = movementLocked;
  }
}
