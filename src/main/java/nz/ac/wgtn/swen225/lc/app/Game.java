package nz.ac.wgtn.swen225.lc.app;

import javafx.application.Platform;
import javafx.stage.Stage;
import nz.ac.wgtn.swen225.lc.app.gui.GameWindow;
import nz.ac.wgtn.swen225.lc.app.gui.RecorderPlaybackWindow;
import nz.ac.wgtn.swen225.lc.domain.Domain;
import nz.ac.wgtn.swen225.lc.domain.InformationPacket;
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
  private int currentLevel;
  private GameWindow gameWindow;
  private Domain domain;
  private Stage stage;

  /**
   * Creates a new instance of the game.
   * Loads the newest incomplete save if one exists, else level 1.
   *
   * @param stage The stage created by the application window.
   */
  public Game(Stage stage) {
    this.stage = stage;
    gameWindow = new GameWindow(stage, this);

    loadLevel(1);
  }

  /**
   * Create the game with a specific level on startup, used only by Fuzzing.
   *
   * @param level the number of the level to load;
   */
  public Game(int level) {
    loadLevel(level);
  }

  /**
   * Called by recorder when a recording should be played back.
   *
   * @param level the level that was recorded.
   */
  public void startRecordingPlayback(int level, Playback playback) {
    // Lock out user controls
    gameWindow.inputManager.setMovementLocked(true);

    // Load level
    try {
      URL fileUrl = getClass().getResource("/levels/level" + level + ".json");
      if (fileUrl != null) {
        File f = new File(fileUrl.toURI());
        this.loadGame(f);
      }
    } catch (URISyntaxException ex) {
      System.out.println("Failed to load level " + level + ", URI Syntax error: " + ex.toString());
      return;
    }

    // Create control window
    new RecorderPlaybackWindow(stage, playback, this);
  }

  public void stopRecordingPlayback(Playback playback) {
    // Resume player controls
    gameWindow.inputManager.setMovementLocked(false);
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

  private void onTimerUpdate(int timeRemaining) {
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
  public void loadLevel(int level) {
    try {
      URL fileUrl = getClass().getResource("/levels/level" + level + ".json");
      if (fileUrl != null) {
        File f = new File(fileUrl.toURI());
        loadGame(f);
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
  public void loadGame(File file) {
    Domain domain = Load.loadAsDomain(file);
    this.domain = domain;
    this.recorder = new Recorder(-1, this); // TODO Get level number from persistence
    // TODO Get time remaining from persistence
    gameTimer = new GameTimer(60, this::onTimeout, this::onTimerUpdate);

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

    if (showOverlay) {
      gameWindow.overlay.displayPause();
    }
  }

  /**
   * Resume a paused game.
   */
  public void resumeGame() {
    if (gameTimer == null) {
      return;
    }

    isPaused = false;
    gameTimer.setPaused(false);

    gameWindow.overlay.close();
  }

  /**
   * Update the actors and propagate the event.
   */
  public void updateActors() {
    if (domain == null) {
      return;
    }

    InformationPacket result = domain.advanceClock();

    if (recorder != null) {
      recorder.addActorMove(null); // TODO update to remove direction input
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
    InformationPacket moveResult = domain.movePlayer(direction);

    if (!moveResult.hasPlayerMoved()) {
      return false;
    }

    // Movement was successful

    if (gameWindow != null) {
      gameWindow.renderer.movePlayer(direction);
      gameWindow.gameInfoController.updateUi(domain, this);
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
    pauseGame(false);

    if (gameWindow == null) {
      return;
    }

    Platform.runLater(() -> gameWindow.overlay.displayGameOver(reason));
  }

  public int getCurrentLevel() {
    return currentLevel;
  }

  /**
   * Get the time left in the current game timer.
   *
   * @return The time remaining in the current game timer.
   */
  public int getTimeLeft() {
    if (gameTimer == null) {
      return 0;
    }

    return gameTimer.getTimeRemaining();
  }
}
