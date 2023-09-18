package nz.ac.wgtn.swen225.lc.app;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class GameMenuBar extends MenuBar {

    public GameMenuBar() {
        Menu game = new Menu("Game");
        Menu pause = new Menu("Pause");
        Menu level = new Menu("Level");
        Menu help = new Menu("Help");

        MenuItem gameSave = new MenuItem("Save Game");
        MenuItem gameLoad = new MenuItem("Load Game");
        MenuItem gameQuit = new MenuItem("Quit Game");

        game.getItems().addAll(gameSave, gameLoad, gameQuit);

        MenuItem levelLoad1 = new MenuItem("Load Level 1");
        MenuItem levelLoad2 = new MenuItem("Load Level 2");

        level.getItems().addAll(levelLoad1, levelLoad2);

        this.getMenus().addAll(game, pause, level, help);
    }
}
