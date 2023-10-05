package nz.ac.wgtn.swen225.lc.app.gui.menubar.items;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import nz.ac.wgtn.swen225.lc.app.Game;

/**
 * The level menu on the menu bar.
 *
 * @author Trent Shailer 300602354.
 */
public class LevelMenu extends Menu {
  /**
   * Create a level menu.
   *
   * @param game The game that the menu should load levels to.
   */
  public LevelMenu(Game game) {
    super("Level");

    MenuItem levelLoad1 = new MenuItem("Load Level 1");
    levelLoad1.setOnAction(event -> {
      try {
        URL fileUrl = getClass().getResource("/levels/level1.json");
        if (fileUrl != null) {
          File f = new File(fileUrl.toURI());
          game.loadGame(f);
        }
      } catch (URISyntaxException ex) {
        System.out.println("Failed to load level1, URI Syntax error: " + ex.toString());
      }
    });

    MenuItem levelLoad2 = new MenuItem("Load Level 2");
    levelLoad2.setOnAction(event -> {
      try {
        URL fileUrl = getClass().getResource("/levels/level2.json");
        if (fileUrl != null) {
          File f = new File(fileUrl.toURI());
          game.loadGame(f);
        }
      } catch (URISyntaxException ex) {
        System.out.println("Failed to load level2, URI Syntax error: " + ex.toString());
      }
    });

    this.getItems().addAll(levelLoad1, levelLoad2);
  }
}
