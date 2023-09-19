package nz.ac.wgtn.swen225.lc.app;

import java.io.File;

import com.sun.glass.ui.Application;
import javafx.stage.Stage;
import nz.ac.wgtn.swen225.lc.app.gui.GameWindow;


/**
 * This class is the core class of the whole game, it creates and manages all subcomponents.
 *
 * @author Trent Shailer 300602354.
 */
public class Game {
  private boolean isPaused = false;
  private int timeLeft;

  private int currentLevel;

  private GameWindow gameWindow;


  /**
   * Creates a new instance of the game.
   * Loads the newest incomplete save if one exists, else level 1.
   *
   * @param stage The stage created by the application window.
   * */
  public Game(Stage stage) {
    gameWindow = new GameWindow(stage, this);
    // Load game
  }

  /**
   * Create the game with a specific level on startup, used only by Fuzzing.
   *
   * @param level the number of the level to load;
   * */
  public Game(int level) {
    // Not implemented yet
  }

  public void exitGame(boolean save) {
    if (save) {
      // wait for save to finish then exit game
    }

    System.exit(0);
  }

  public void loadGame(File f) {
    // This function may change depending on how Persistence works
  }

  public void pauseGame() {
    isPaused = true;
    // Send event to UI to display game paused
  }

  public void resumeGame() {
    isPaused = false;
    // Send event to UI to display game resumed
  }

  public void movePlayer(Direction direction) {
    // Only if domain exists
    // Implement timeout (Needs to work with Recorder, maybe timeout should be in input manager?
  }
}
