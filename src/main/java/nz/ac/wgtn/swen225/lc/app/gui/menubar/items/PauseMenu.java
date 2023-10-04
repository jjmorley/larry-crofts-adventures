package nz.ac.wgtn.swen225.lc.app.gui.menubar.items;

import nz.ac.wgtn.swen225.lc.app.Game;
import nz.ac.wgtn.swen225.lc.app.gui.GameWindow;
import nz.ac.wgtn.swen225.lc.app.gui.MenuButton;

/**
 * The pause button on the menu bar.
 *
 * @author Trent Shailer 300602354.
 */
public class PauseMenu extends MenuButton {
  public PauseMenu(Game game, GameWindow window) {
    super("Pause", event -> game.pauseGame(true));
  }
}
