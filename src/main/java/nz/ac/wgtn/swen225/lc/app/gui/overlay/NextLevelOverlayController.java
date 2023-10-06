package nz.ac.wgtn.swen225.lc.app.gui.overlay;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import nz.ac.wgtn.swen225.lc.app.Game;
import nz.ac.wgtn.swen225.lc.app.gui.GameWindow;

/**
 * The controller for the Next Level Overlay.
 *
 * @author Trent Shailer 30002354.
 */
public class NextLevelOverlayController {
  public Label timeRemainingLabel;
  public Button nextButton;

  private Game game;
  private GameWindow window;

  /**
   * Sets up the overlay before it is shown.
   *
   * @param game the game this belongs to.
   */
  public void onWin(Game game, GameWindow window) {
    this.game = game;
    this.window = window;
    timeRemainingLabel.setText("You had " + game.getTimeLeft() + " seconds remaining");

    boolean nextLevelExists = game.getCurrentLevel() < 2;
    nextButton.setDisable(!nextLevelExists);
  }

  public void next(ActionEvent event) {
    window.overlay.close();
    game.loadLevel(game.getCurrentLevel() + 1, true);
  }
}
