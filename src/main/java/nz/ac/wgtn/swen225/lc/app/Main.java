package nz.ac.wgtn.swen225.lc.app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Entrypoint for Larry Crofts Adventures.
 *
 * @author Trent Shailer 300602354.
 * */
public class Main extends Application {
  @Override
  public void start(Stage stage) {
    new Game(stage);
    stage.setOnCloseRequest(event -> System.exit(0));
  }

  /**
   * The entry point of application.
   *
   * @param args the input arguments
   */
  public static void main(String[] args) {
    launch();
  }
}
