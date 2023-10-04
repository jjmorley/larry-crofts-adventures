package nz.ac.wgtn.swen225.lc.app.gui.menubar.items;

import java.io.File;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import nz.ac.wgtn.swen225.lc.app.Game;
import nz.ac.wgtn.swen225.lc.app.gui.GameWindow;

/**
 * The game menu on the menu bar.
 *
 * @author Trent Shailer 300602354.
 */
public class GameMenu extends Menu {
  /**
   * Create a game menu.
   *
   * @param game   The game that is being controlled.
   * @param window The window to open the save selector dialog in.
   */
  public GameMenu(Game game, GameWindow window) {
    super("Game");

    MenuItem gameSave = new MenuItem("Save Game");
    gameSave.setOnAction(event -> game.saveGame());

    MenuItem gameLoad = new MenuItem("Load Game");
    gameLoad.setOnAction(event -> {
      File file = window.openFileSelectorDialog("Save");
      if (file != null) {
        game.loadGame(file);
      }
    });

    MenuItem gameQuit = new MenuItem("Quit Game");
    gameQuit.setOnAction(event -> game.exitGame(false));

    this.getItems().addAll(gameSave, gameLoad, gameQuit);
  }
}
