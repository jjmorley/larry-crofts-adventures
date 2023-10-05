package nz.ac.wgtn.swen225.lc.app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import nz.ac.wgtn.swen225.lc.domain.Domain;

import java.io.File;

import static nz.ac.wgtn.swen225.lc.persistency.Load.loadAsDomain;
import static nz.ac.wgtn.swen225.lc.persistency.Save.saveJSONFromDomain;

/**
 * Entrypoint for Larry Crofts Adventures.
 *
 * @author Trent Shailer 300602354.
 * */
public class Main extends Application {
  @Override
  public void start(Stage stage) {
    new Game(stage);
    Domain d = loadAsDomain(new File("src/main/resources/levels/levels-template.json"));
    saveJSONFromDomain(new File("src/main/resources/levels/saved-level-test.json"), d);
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
