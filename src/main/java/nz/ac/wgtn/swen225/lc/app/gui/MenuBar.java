package nz.ac.wgtn.swen225.lc.app.gui;

import java.io.File;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import nz.ac.wgtn.swen225.lc.app.Game;

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
    Menu gameMenu = setupGameMenu(game, window);
    Menu levelMenu = setupLevelMenu(game, window);
    Menu pauseMenu = setupPauseMenu(game, window);
    Menu recorderMenu = setupRecorderMenu(game, window);
    Menu helpMenu = setupHelpMenu(game, window);

    this.getMenus().addAll(gameMenu, levelMenu, recorderMenu, pauseMenu, helpMenu);
  }

  private Menu setupGameMenu(Game game, GameWindow window) {
    MenuItem gameSave = new MenuItem("Save Game");
    gameSave.setOnAction(event -> game.saveGame());

    MenuItem gameLoad = new MenuItem("Load Game");
    gameLoad.setOnAction(event -> {
      File file = window.openSaveSelectorDialog();
      if (file != null) {
        game.loadGame(file);
      }
    });

    MenuItem gameQuit = new MenuItem("Quit Game");
    gameQuit.setOnAction(event -> game.exitGame(false));

    Menu gameMenu = new Menu("Game");
    gameMenu.getItems().addAll(gameSave, gameLoad, gameQuit);

    return gameMenu;
  }

  private Menu setupLevelMenu(Game game, GameWindow window) {
    Menu level = new Menu("Level");

    MenuItem levelLoad1 = new MenuItem("Load Level 1");
    levelLoad1.setOnAction(event -> {
      File file = new File("levels/level1.json");
      game.loadGame(file);
    });

    MenuItem levelLoad2 = new MenuItem("Load Level 2");
    levelLoad2.setOnAction(event -> {
      File file = new File("levels/level2.json");
      game.loadGame(file);
    });

    level.getItems().addAll(levelLoad1, levelLoad2);

    return level;
  }

  private Menu setupPauseMenu(Game game, GameWindow window) {
    Menu pause = new MenuButton("Pause", event -> game.pauseGame(true));

    return pause;
  }

  private Menu setupHelpMenu(Game game, GameWindow window) {
    Menu help = new MenuButton("Help", event -> {
      game.pauseGame(false);
      window.overlay.displayHelp();
    });

    return help;
  }

  private Menu setupRecorderMenu(Game game, GameWindow window) {
    Menu recorder = new Menu("Recorder");
    // TODO add recorder controls

    return recorder;
  }
}
