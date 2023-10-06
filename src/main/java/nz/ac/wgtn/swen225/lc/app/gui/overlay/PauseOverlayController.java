package nz.ac.wgtn.swen225.lc.app.gui.overlay;

import javafx.event.ActionEvent;
import javafx.scene.layout.BorderPane;
import nz.ac.wgtn.swen225.lc.app.Game;

public class PauseOverlayController {
  private Game game;

  public void initializeGame(Game game) {
    this.game = game;
  }

  public void resume(ActionEvent event) {
    game.resumeGame();
  }
}
