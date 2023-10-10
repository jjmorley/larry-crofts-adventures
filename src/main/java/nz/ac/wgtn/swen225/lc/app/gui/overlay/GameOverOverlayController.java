package nz.ac.wgtn.swen225.lc.app.gui.overlay;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import nz.ac.wgtn.swen225.lc.app.Game;
import nz.ac.wgtn.swen225.lc.app.gui.GameWindow;

import java.io.File;

/**
 * Controller for the Game Over Overlay.
 *
 * @author Trent Shailer 300602354.
 */
public class GameOverOverlayController {
  public Label reasonLabel;

  private Game game;
  private GameWindow window;

  /**
   * Set up the game over overlay.
   *
   * @param reason The reason the game is over.
   * @param game   The game that should be interacted with.
   * @param window The window this belongs to.
   */
  public void onGameOver(String reason, Game game, GameWindow window) {
    this.game = game;
    this.window = window;

    reasonLabel.setText("You " + reason);
  }

  public void restart(ActionEvent event) {
    window.overlay.close();
    game.loadLevel(game.getCurrentLevel(), true);
  }

  /**
   * Event handler for the load button.
   */
  public void load(ActionEvent event) {
    File saveFile = window.openFileSelectorDialog("Save");
    if (saveFile == null) {
      return;
    }

    window.overlay.close();
    game.loadGameFromFile(saveFile, true);
  }

  public void quit(ActionEvent event) {
    game.exitGame(false);
  }
}
