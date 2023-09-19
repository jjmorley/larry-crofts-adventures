package nz.ac.wgtn.swen225.lc.app;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * This class launches the game.
 *
 * @author Trent Shailer 300602354.
 */
public class Main extends Application {
  /**
   * Launch the game.
   *
   * @param args Command line args (not used).
   */
  public static void main(String[] args) {
    launch();
  }

  @Override
  public void start(Stage stage) {
    new Game(stage);
  }
}
