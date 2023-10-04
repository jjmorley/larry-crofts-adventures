package nz.ac.wgtn.swen225.lc.app.gui;

import javafx.scene.control.Menu;
import nz.ac.wgtn.swen225.lc.app.Game;
import nz.ac.wgtn.swen225.lc.app.gui.menubar.items.*;

/**
 * The menu bar for the game window.
 *
 * @author Trent Shailer 300602354.
 */
public class MenuBar extends javafx.scene.control.MenuBar {

  /**
   * Initializes the sub-menus in the menu bar and other configuration.
   */
  public MenuBar(Game game, GameWindow window) {
    Menu gameMenu = new GameMenu(game, window);
    Menu levelMenu = new LevelMenu(game);
    Menu pauseMenu = new PauseMenu(game, window);
    Menu recorderMenu = new RecorderMenu(game, window);
    Menu helpMenu = new HelpMenu(game, window);

    this.getMenus().addAll(gameMenu, levelMenu, recorderMenu, pauseMenu, helpMenu);
  }
}
