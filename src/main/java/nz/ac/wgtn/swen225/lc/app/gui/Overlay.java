package nz.ac.wgtn.swen225.lc.app.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import nz.ac.wgtn.swen225.lc.app.Game;
import nz.ac.wgtn.swen225.lc.app.gui.overlay.HelpOverlay;
import nz.ac.wgtn.swen225.lc.app.gui.overlay.PauseOverlay;

/**
 * Pane containing the overlay for the game showing help and pause status.
 *
 * @author Trent Shailer 300602354.
 */
public class Overlay extends StackPane {

  private Game game;
  private HelpOverlay helpOverlay;
  private PauseOverlay pauseOverlay;

  /**
   * Creates a new pane to display the overlay.
   */
  public Overlay(Game game) {
    this.game = game;

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

    pauseOverlay = new PauseOverlay(game);
    helpOverlay = new HelpOverlay(game);
    this.getChildren().addAll(pauseOverlay, helpOverlay);
  }


  /**
   * Close the overlay.
   */
  public void close() {
    this.setVisible(false);
    pauseOverlay.setVisible(false);
    helpOverlay.setVisible(false);
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
}
