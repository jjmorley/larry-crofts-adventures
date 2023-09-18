package nz.ac.wgtn.swen225.lc.app.gui;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

/**
 * The menu bar for the game window.
 *
 * @author Trent Shailer 300602354.
 */
public class MenuBar extends javafx.scene.control.MenuBar {

  /**
   * Initializes the sub-menus in the menu bar and other configuration.
   */
  public MenuBar() {
    Menu game = new Menu("Game");
    MenuItem gameSave = new MenuItem("Save Game");
    MenuItem gameLoad = new MenuItem("Load Game");
    MenuItem gameQuit = new MenuItem("Quit Game");
    game.getItems().addAll(gameSave, gameLoad, gameQuit);

    Menu level = new Menu("Level");
    MenuItem levelLoad1 = new MenuItem("Load Level 1");
    MenuItem levelLoad2 = new MenuItem("Load Level 2");
    level.getItems().addAll(levelLoad1, levelLoad2);

    Menu pause = new Menu("Pause");
    Menu help = new Menu("Help");

    this.getMenus().addAll(game, pause, level, help);
  }
}
