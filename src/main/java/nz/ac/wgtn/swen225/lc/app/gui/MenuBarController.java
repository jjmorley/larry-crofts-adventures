package nz.ac.wgtn.swen225.lc.app.gui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import nz.ac.wgtn.swen225.lc.app.Game;
import nz.ac.wgtn.swen225.lc.recorder.Playback;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * The controller for the custom menu bar for the game.
 *
 * @author Trent Shailer 300602354.
 */
public class MenuBarController {
  public Label levelLabel;
  private Game game;
  private GameWindow window;

  /**
   * Initializes the variables that the menu bar needs to interact with.
   *
   * @param game   The game the menu should interact with.
   * @param window The window the menu should interact with.
   */
  public void initializeOwners(Game game, GameWindow window) {
    this.game = game;
    this.window = window;
  }

  /**
   * Updates the level number in the menu bar.
   *
   * @param levelNum The new level number
   */
  public void updateLevelNumber(int levelNum) {
    levelLabel.setText("Level " + levelNum);
  }

  /**
   * Function called when the pause button is clicked.
   */
  public void onPauseClicked(ActionEvent event) {
    event.consume();
    game.pauseGame(true);
  }

  /**
   * Function called when the help button is clicked.
   */
  public void onHelpClicked(ActionEvent event) {
    event.consume();
    game.pauseGame(false);
    window.overlay.displayHelp();
  }

  /**
   * Function called when the play recording button is clicked.
   */
  public void onPlayRecording(ActionEvent event) {
    event.consume();
    File file = window.openFileSelectorDialog("Recording");
    if (file != null) {
      new Playback(file, game);
    }
  }

  /**
   * Function called when the save recording is clicked.
   */
  public void onSaveRecording(ActionEvent event) {
    event.consume();
    if (game.recorder == null) {
      return;
    }

    File file = window.openFileSaveDialog(
        "Recording",
        "Level-" + game.getCurrentLevel() + "-recording"
    );
    if (file != null) {
      game.recorder.saveRecordedGameToFile(file);
    }
  }

  /**
   * Function called when the load level 2 button is clicked.
   */
  public void onLoadLevel2(ActionEvent event) {
    event.consume();
    try {
      URL fileUrl = getClass().getResource("/levels/level2.json");
      if (fileUrl != null) {
        File f = new File(fileUrl.toURI());
        game.loadGame(f);
      }
    } catch (URISyntaxException ex) {
      System.out.println("Failed to load level2, URI Syntax error: " + ex.toString());
    }
  }

  /**
   * Function called when the load level 1 button is clicked.
   */
  public void onLoadLevel1(ActionEvent event) {
    event.consume();
    try {
      URL fileUrl = getClass().getResource("/levels/level1.json");
      if (fileUrl != null) {
        File f = new File(fileUrl.toURI());
        game.loadGame(f);
      }
    } catch (URISyntaxException ex) {
      System.out.println("Failed to load level1, URI Syntax error: " + ex.toString());
    }
  }

  /**
   * Function called when the quit button is clicked.
   */
  public void onQuitGame(ActionEvent event) {
    event.consume();
    game.exitGame(false);
  }

  /**
   * Function called when the load button is clicked.
   */
  public void onLoadGame(ActionEvent event) {
    event.consume();
    File file = window.openFileSelectorDialog("Save");
    if (file != null) {
      game.loadGame(file);
    }
  }

  /**
   * Function called when the save button is clicked.
   */
  public void onSaveGame(ActionEvent event) {
    event.consume();
    game.saveGame();
  }
}
