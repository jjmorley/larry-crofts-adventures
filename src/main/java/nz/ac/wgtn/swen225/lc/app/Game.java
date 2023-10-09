package nz.ac.wgtn.swen225.lc.app;

import javafx.application.Platform;
import javafx.stage.Stage;
import nz.ac.wgtn.swen225.lc.app.gui.GameWindow;
import nz.ac.wgtn.swen225.lc.app.gui.RecorderPlaybackWindow;
import nz.ac.wgtn.swen225.lc.domain.Domain;
import nz.ac.wgtn.swen225.lc.domain.InformationPacket;
import nz.ac.wgtn.swen225.lc.domain.Position;
import nz.ac.wgtn.swen225.lc.domain.gameObject.Moveable.Direction;
import nz.ac.wgtn.swen225.lc.persistency.Load;
import nz.ac.wgtn.swen225.lc.recorder.Playback;
import nz.ac.wgtn.swen225.lc.recorder.Recorder;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;


/**
 * This class is the core class of the whole game, it creates and manages all subcomponents.
 *
 * @author Trent Shailer 300602354.
 */
public class Game {
  public Recorder recorder;
  private boolean isPaused = false;
  private GameTimer gameTimer;
  private GameTimer actorTimer;
  private int currentLevel;
  private GameWindow gameWindow;
  private Domain domain;
  private Stage stage;
  private boolean isGameOver = false;

  /**
   * Creates a new instance of the game.
   * Loads the newest incomplete save if one exists, else level 1.
   *
   * @param stage The stage created by the application window.
   */
  public Game(Stage stage) {
    this.stage = stage;
    gameWindow = new GameWindow(stage, this);

    loadLevel(1, true);
  }

  /**
   * Create the game with a specific level on startup, used only by Fuzzing and Testing modules.
   *
   * @param level the number of the level to load;
   */
  public Game(int level) {
    loadLevel(level, true);
  }

  /**
   * Called by recorder when a recording should be played back.
   *
   * @param level the level that was recorded.
   */
  public void startRecordingPlayback(int level, Playback playback) {
    // Lock out user controls
    if (gameWindow != null) {
      gameWindow.inputManager.setMovementLocked(true);
    }

    // Load level
    loadLevel(level, false);

    // Create control window
    new RecorderPlaybackWindow(stage, playback, this);
  }

  /**
   * Stop the recording playback and resume normal game control.
   *
   * @param playback the Playback instance that has stopped.
   */
  public void stopRecordingPlayback(Playback playback) {
    // Resume player controls
    if (gameWindow != null) {
      gameWindow.inputManager.setMovementLocked(false);
    }
  }

  /**
   * Exit the game.
   *
   * @param save Should the current game be saved before exiting.
   */
  public void exitGame(boolean save) {
    if (save) {
      // wait for save to finish then exit game
    }

    System.exit(0);
  }

  private void onTimeout() {
    gameOver("ran out of time.");
  }

  private void onTimerUpdate(long timeRemaining) {
    if (gameWindow == null || gameWindow.gameInfoController == null || domain == null) {
      return;
    }

    Platform.runLater(() -> gameWindow.gameInfoController.updateUi(domain, this));
  }

  /**
   * Load a level.
   *
   * @param level The level number to load.
   */
  public void loadLevel(int level, boolean autoUpdateActors) {
    try {
      URL fileUrl = getClass().getResource("/levels/level" + level + ".json");
      if (fileUrl != null) {
        File f = new File(fileUrl.toURI());
        loadGame(f, autoUpdateActors);
      }
    } catch (URISyntaxException ex) {
      System.out.println("Failed to load level" + level + ", URI Syntax error: " + ex.toString());
    }
  }

  /**
   * Try to load a game from a given file.
   *
   * @param file the save/level file to load from.
   */
  public void loadGame(File file, boolean autoUpdateActors) {
    Domain domain = Load.loadAsDomain(file);
    this.domain = domain;

    isGameOver = false;

    // TODO Get level number from persistence
    // TODO Get time remaining from persistence

    currentLevel = 1; // TODO temporary

    this.recorder = new Recorder(currentLevel, this);

    if (gameTimer != null) {
      gameTimer.pauseTimer();
      gameTimer = null;
    }

    gameTimer = new GameTimer(60, 1000, this::onTimeout, this::onTimerUpdate);

    if (autoUpdateActors) {
      actorTimer = new GameTimer(
          Long.MAX_VALUE,
          1000,
          () -> {
            // This should never happen
          },
          (Long timeRemaining) -> this.updateActors());
    } else if (actorTimer != null) {
      // Kill any actor timer
      actorTimer.pauseTimer();
      actorTimer = null;
    }

    if (gameWindow != null) {
      gameWindow.createGame(domain, currentLevel);
    }
  }

  /**
   * Save the current game.
   */
  public void saveGame() {

  }

  /**
   * Pause the game.
   */
  public void pauseGame(boolean showOverlay) {
    if (gameTimer == null) {
      return;
    }

    isPaused = true;
    gameTimer.setPaused(true);

    if (actorTimer != null) {
      actorTimer.pauseTimer();
    }

    if (showOverlay) {
      gameWindow.overlay.displayPause();
    }
  }

  /**
   * Resume a paused game.
   */
  public void resumeGame() {
    if (gameTimer == null || isGameOver) {
      return;
    }

    isPaused = false;
    gameTimer.setPaused(false);

    if (actorTimer != null) {
      actorTimer.resumeTimer();
    }

    if (gameWindow != null) {
      gameWindow.overlay.close();
    }
  }

  /**
   * Update the actors and propagate the event.
   */
  public void updateActors() {
    if (domain == null) {
      return;
    }

    InformationPacket result = null;

    try {
      result = domain.advanceClock();
    } catch (IllegalArgumentException exception) {
      // Throw away exception when there are no actors present.
    }

    if (result == null) {
      return;
    }

    if (gameWindow != null) {
      //gameWindow.renderer.moveActors(InputManager.MOVEMENT_TIMEOUT);
    }

    if (recorder != null) {
      recorder.addActorMove();
    }

    if (!result.isPlayerAlive()) {
      gameOver("died");
    }
  }

  /**
   * Try a move the player in a given direction.
   *
   * @param direction the direction the player should move in.
   * @return If the movement was successful.
   */
  public boolean movePlayer(Direction direction) {
    if (domain == null) {
      return false;
    }

    Position playerStartPos = domain.getPlayer().getPosition();

    InformationPacket moveResult = domain.movePlayer(direction);

    if (!moveResult.hasPlayerMoved()) {
      return false;
    }

    // Movement was successful

    if (gameWindow != null) {
      gameWindow.renderer.movePlayer(direction, InputManager.MOVEMENT_TIMEOUT);
      Platform.runLater(() -> gameWindow.gameInfoController.updateUi(domain, this));
    }

    if (recorder != null) {
      recorder.addPlayerMove(direction);
    }

    if (!moveResult.isPlayerAlive()) {
      gameOver("died");
    }

    return true;
  }

  private void gameOver(String reason) {
    isGameOver = true;
    pauseGame(false);

    if (gameWindow == null) {
      return;
    }

    Platform.runLater(() -> gameWindow.overlay.displayGameOver(reason));
  }

  private void gameWin() {
    isGameOver = true;
    pauseGame(false);

    if (gameWindow == null) {
      return;
    }

    Platform.runLater(() -> gameWindow.overlay.displayNextLevel());
  }

  public int getCurrentLevel() {
    return currentLevel;
  }

  /**
   * Get the time left in the current game timer.
   *
   * @return The time remaining in the current game timer.
   */
  public long getTimeLeft() {
    if (gameTimer == null) {
      return 0;
    }

    return gameTimer.getTimeRemaining();
  }
}
