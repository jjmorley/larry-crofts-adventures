package nz.ac.wgtn.swen225.lc.app;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import nz.ac.wgtn.swen225.lc.app.gui.GameInfo;
import nz.ac.wgtn.swen225.lc.app.gui.MenuBar;


/**
 * This class creates an instance of the whole game, as in the window and all sub-components.
 *
 * @author Trent Shailer 300602354.
 */
public class Main extends Application {
  private HBox gamePane;

  @Override
  public void start(Stage stage) {
    // Pane to contain the Renderer
    gamePane = new HBox(new Label("GAME PANE"));
    gamePane.setPrefHeight(720);
    gamePane.setBackground(
        new Background(
            new BackgroundFill(
                new Color(0.000, 0.000, 0.000, 0.4),
                CornerRadii.EMPTY,
                Insets.EMPTY
            )
        )
    );

    MenuBar menuBar = new MenuBar();
    GameInfo gameInfo = new GameInfo();

    // VBox to stack menu bar, game info, and game window vertically
    VBox pane = new VBox(menuBar, gameInfo, gamePane);

    // Setup window
    Scene scene = new Scene(pane, 720, 820);
    stage.setScene(scene);
    stage.setTitle("Larry Croft's Adventures");
    stage.getIcons().add(new Image("windowIcon.png"));
    stage.setResizable(false);
    stage.show();
  }

  /**
   * Starts an instance of the game.
   *
   * @param args Command line args (not used).
   */
  public static void main(String[] args) {
    launch();
  }
}
