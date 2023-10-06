package nz.ac.wgtn.swen225.lc.app.gui;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import nz.ac.wgtn.swen225.lc.app.Game;
import nz.ac.wgtn.swen225.lc.app.gui.overlay.GameOverOverlayController;
import nz.ac.wgtn.swen225.lc.app.gui.overlay.PauseOverlayController;

import java.io.IOException;
import java.net.URL;

/**
 * Pane containing the overlay for the game showing help and pause status.
 *
 * @author Trent Shailer 300602354.
 */
public class Overlay extends StackPane {

  private final Pane helpOverlay;
  private final Pane pauseOverlay;
  private final Pane gameOverOverlay;
  private final GameOverOverlayController gameOverOverlayController;
  private final Game game;
  private final GameWindow window;

  /**
   * Creates a new pane to display the overlay.
   */
  public Overlay(Game game, GameWindow window) {
    this.game = game;
    this.window = window;

    this.prefHeight(720);
    this.prefWidth(820);

    this.setBackground(
        new Background(
            new BackgroundFill(
                new Color(0.000, 0.000, 0.000, 0.8),
                CornerRadii.EMPTY,
                Insets.EMPTY
            )
        )
    );

    this.setVisible(false);

    try {
      URL fxmlFile = getClass().getResource("/UI/Overlays/PauseOverlay.fxml");
      if (fxmlFile == null) {
        throw new IOException("URL for PauseOverlay was null.");
      }

      FXMLLoader loader = new FXMLLoader(fxmlFile);
      pauseOverlay = loader.load();
      PauseOverlayController controller = loader.getController();
      controller.initializeGame(game);
    } catch (IOException exception) {
      throw new RuntimeException(exception);
    }

    try {
      URL fxmlFile = getClass().getResource("/UI/Overlays/HelpOverlay.fxml");
      if (fxmlFile == null) {
        throw new IOException("URL for HelpOverlay was null.");
      }

      FXMLLoader loader = new FXMLLoader(fxmlFile);
      helpOverlay = loader.load();
      PauseOverlayController controller = loader.getController();
      controller.initializeGame(game);
    } catch (IOException exception) {
      throw new RuntimeException(exception);
    }

    try {
      URL fxmlFile = getClass().getResource("/UI/Overlays/GameOverOverlay.fxml");
      if (fxmlFile == null) {
        throw new IOException("URL for GameOverOverlay was null.");
      }

      FXMLLoader loader = new FXMLLoader(fxmlFile);
      gameOverOverlay = loader.load();
      gameOverOverlayController = loader.getController();
    } catch (IOException exception) {
      throw new RuntimeException(exception);
    }

    this.getChildren().addAll(pauseOverlay, helpOverlay, gameOverOverlay);
  }


  /**
   * Close the overlay.
   */
  public void close() {
    this.setVisible(false);
    pauseOverlay.setVisible(false);
    helpOverlay.setVisible(false);
    gameOverOverlay.setVisible(false);
  }

  /**
   * Display the pause screen on the overlay.
   */
  public void displayPause() {
    pauseOverlay.setVisible(true);
    this.setVisible(true);
  }

  /**
   * Display the help screen on the overlay.
   */
  public void displayHelp() {
    helpOverlay.setVisible(true);
    this.setVisible(true);
  }

  /**
   * Display the game over overlay.
   *
   * @param reason The reason the game over screen is being displayed.
   */
  public void displayGameOver(String reason) {
    gameOverOverlayController.onGameOver(reason, game, window);
    gameOverOverlay.setVisible(true);
    this.setVisible(true);
  }
}
