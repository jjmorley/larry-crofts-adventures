package nz.ac.wgtn.swen225.lc.app.gui;

import javafx.event.ActionEvent;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import nz.ac.wgtn.swen225.lc.app.Game;
import nz.ac.wgtn.swen225.lc.recorder.Playback;

import java.io.File;
import java.io.IOException;
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
    getRecordingFile();
  }

  private void getRecordingFile() {
    File file = window.openFileSelectorDialog("Recording");
    if (file == null) {
      return;
    }

    try {
      new Playback(file, game);
    } catch (IOException ex) {
      handleRecordingFileError();
    }
  }

  private void handleRecordingFileError() {
    Dialog<ButtonType> dialog = new Dialog<>();

    ButtonType okButtonType = new ButtonType("Try Again", ButtonBar.ButtonData.OK_DONE);
    dialog.getDialogPane().getButtonTypes().add(okButtonType);
    dialog.setTitle("Error reading file");
    dialog.setContentText("Error reading file, make sure the file is a valid Recording file.");

    dialog.showAndWait();

    getRecordingFile();
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
        game.loadGameFromFile(f, true);
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
        game.loadGameFromFile(f, true);
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
      game.loadGameFromFile(file, true);
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
