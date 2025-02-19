package nz.ac.wgtn.swen225.lc.app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import nz.ac.wgtn.swen225.lc.domain.Domain;

import java.io.File;


/**
 * Entrypoint for Larry Crofts Adventures.
 *
 * @author Trent Shailer 300602354.
 * */
public class Main extends Application {
  @Override
  public void start(Stage stage) {
    Font.loadFont(getClass().getResourceAsStream("/UI/Silkscreen-Regular.ttf"), 24);
    Font.loadFont(getClass().getResourceAsStream("/UI/Silkscreen-Bold.ttf"), 24);
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
