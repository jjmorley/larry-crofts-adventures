package nz.ac.wgtn.swen225.lc.app.gui;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

/**
 * The UI for displaying the game info.
 *
 * @author Trent Shailer 300602354.
 */
public class GameInfo extends HBox {

  /**
   * Initialises the game info pane.
   */
  public GameInfo() {
    this.setBackground(
        new Background(
            new BackgroundFill(
                new Color(0.000, 0.000, 0.000, 0.2),
                CornerRadii.EMPTY,
                Insets.EMPTY
            )
        )
    );
  }

  @Override
  protected double computePrefHeight(double v) {
    // 100px - height of menu bar
    return 100 - 25;
  }
}
