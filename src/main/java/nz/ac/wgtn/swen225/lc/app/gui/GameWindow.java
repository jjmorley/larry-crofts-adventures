package nz.ac.wgtn.swen225.lc.app.gui;

import javafx.fxml.FXMLLoader;
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

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * This class creates the game window and all subcomponents including input capture.
 *
 * @author Trent Shailer 300602354.
 */
public class GameWindow {

  public final InputManager inputManager;
  private final Game game;
  private final Stage stage;
  public Overlay overlay;
  public Renderer renderer;
  public GameInfoController gameInfoController;
  private Pane gamePane;
  private Pane gameInfoPane;
  public MenuBarController menuBarController;


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

    overlay = new Overlay(game, this);
    VBox mainPane = createMainPane();

    stackPane.getChildren().addAll(mainPane, overlay);

    // Setup window
    Scene scene = new Scene(stackPane, 720, 820);
    scene.getStylesheets().add(getClass().getResource("/UI/styles.css").toExternalForm());
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

    Pane menuBar = null;

    try {
      URL fxmlFile = getClass().getResource("/UI/MenuBar.fxml");
      if (fxmlFile == null) {
        throw new IOException("URL for UI/MenuBar.fxml was null.");
      }

      FXMLLoader loader = new FXMLLoader(fxmlFile);
      menuBar = loader.load();
      menuBarController = loader.getController();
      menuBarController.initializeOwners(game, this);

    } catch (IOException exception) {
      throw new RuntimeException(exception);
    }

    if (menuBar == null) {
      throw new RuntimeException("Failed to load menuBar from fxml");
    }

    gameInfoPane = new VBox();
    gameInfoPane.setPrefHeight(75);

    // VBox to stack menu bar, game info, and game window vertically
    return new VBox(menuBar, gameInfoPane, gamePane);
  }

  /**
   * Set up the UI when a new game is started/loaded.
   *
   * @param domain The domain of the new game.
   */
  public void createGame(Domain domain, int levelNum) {
    gamePane.getChildren().clear();
    renderer = new Renderer(domain, (int) gamePane.getWidth());
    gamePane.getChildren().add(renderer.getDisplay());

    menuBarController.updateLevelNumber(levelNum);

    try {
      URL fxmlFile = getClass().getResource("/UI/GameInfo.fxml");
      if (fxmlFile == null) {
        throw new IOException("URL for UI/GameInfo.fxml was null.");
      }

      FXMLLoader loader = new FXMLLoader(fxmlFile);
      Pane gameInfo = loader.load();
      gameInfoPane.getChildren().clear();
      gameInfoPane.getChildren().add(gameInfo);

      gameInfoController = loader.getController();
      gameInfoController.updateUi(domain, game);
    } catch (IOException exception) {
      throw new RuntimeException(exception);
    }
  }

  /**
   * Opens a file picker and returns the file the user picks.
   *
   * @param kindOfFile The 'kind' of file that is being selected, e.g., "Save", "Recording"
   * @return The file the user picks or null.
   */
  public File openFileSelectorDialog(String kindOfFile) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open a " + kindOfFile + " File");
    fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));

    FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter(
        kindOfFile + " File",
        "*.json"
    );
    fileChooser.getExtensionFilters().add(filter);

    return fileChooser.showOpenDialog(stage);
  }

  /**
   * Opens a save dialog and returns the file the user picks.
   *
   * @param kindOfFile The 'kind' of file that is being selected, e.g., "Save", "Recording"
   * @return The file the user picks or null.
   */
  public File openFileSaveDialog(String kindOfFile, String initialFileName) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Save as");
    fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
    fileChooser.setInitialFileName(initialFileName);

    FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter(
        kindOfFile + " File",
        "*.json"
    );
    fileChooser.getExtensionFilters().add(filter);

    return fileChooser.showSaveDialog(stage);
  }

  public Stage getStage() {
    return stage;
  }
}
