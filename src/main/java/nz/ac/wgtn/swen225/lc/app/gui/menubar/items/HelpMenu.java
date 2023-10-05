package nz.ac.wgtn.swen225.lc.app.gui.menubar.items;

import nz.ac.wgtn.swen225.lc.app.Game;
import nz.ac.wgtn.swen225.lc.app.gui.GameWindow;
import nz.ac.wgtn.swen225.lc.app.gui.MenuButton;

/**
 * The help button on the menu bar.
 *
 * @author Trent Shailer 300602354.
 */
public class HelpMenu extends MenuButton {
  /**
   * Create a help menu button.
   *
   * @param game   The game that should be paused.
   * @param window The window that should display the help.
   */
  public HelpMenu(Game game, GameWindow window) {
    super("Help", event -> {
      game.pauseGame(false);
      window.overlay.displayHelp();
    });
  }
}
