package nz.ac.wgtn.swen225.lc.app.gui;

import java.io.File;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import nz.ac.wgtn.swen225.lc.app.Game;
import nz.ac.wgtn.swen225.lc.app.InputManager;
import nz.ac.wgtn.swen225.lc.domain.Domain;
import nz.ac.wgtn.swen225.lc.renderer.Renderer;

/**
 * This class creates the game window and all subcomponents including input capture.
 *
 * @author Trent Shailer 300602354.
 */
public class GameWindow {

  private final Game game;
  private final InputManager inputManager;
  private final Stage stage;
  public Overlay overlay;

  public Renderer renderer;
  private Pane gamePane;

  /**
   * Sets up the game window and manages all game window related functionality.
   *
   * @param stage The stage from the application.
   * @param game  The parent Game object.
   */
  public GameWindow(Stage stage, Game game) {
    this.stage = stage;
    this.game = game;

    Scene scene = setupWindow(stage);
    inputManager = new InputManager(scene, game, this);
  }

  private Scene setupWindow(Stage stage) {
    // Pane to stack the overlay on top of the main pane
    StackPane stackPane = new StackPane();

    overlay = new Overlay(game);
    VBox mainPane = createMainPane();

    stackPane.getChildren().addAll(mainPane, overlay);

    // Setup window
    Scene scene = new Scene(stackPane, 720, 820);
    stage.setScene(scene);
    stage.setTitle("Larry Croft's Adventures");
    stage.getIcons().add(new Image("windowIcon.png"));
    stage.setResizable(false);
    stage.show();

    return scene;
  }

  private VBox createMainPane() {
    // Pane to contain the Renderer
    gamePane = new HBox();
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

    MenuBar menuBar = new MenuBar(game, this);
    GameInfo gameInfo = new GameInfo();

    // VBox to stack menu bar, game info, and game window vertically
    return new VBox(menuBar, gameInfo, gamePane);
  }

  /**
   * Set up the UI when a new game is started/loaded.
   *
   * @param domain The domain of the new game.
   * */
  public void createGame(Domain domain) {
    gamePane.getChildren().clear();
    renderer = new Renderer(domain, (int) gamePane.getWidth());
    gamePane.getChildren().add(renderer.getDisplay());
  }

  /**
   * Opens a file picker and returns the file the user picks.
   *
   * @return The file the user picks or null.
   */
  public File openSaveSelectorDialog() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open a Save File");
    fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));

    FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Save File", "*.json");
    fileChooser.getExtensionFilters().add(filter);

    File file = fileChooser.showOpenDialog(stage);

    // TODO processing if it is a valid file?

    return file;
  }

  public Stage getStage() {
    return stage;
  }
}
