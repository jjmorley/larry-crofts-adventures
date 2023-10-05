package nz.ac.wgtn.swen225.lc.app;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import nz.ac.wgtn.swen225.lc.app.gui.GameWindow;
import nz.ac.wgtn.swen225.lc.app.gui.RecorderPlaybackController;
import nz.ac.wgtn.swen225.lc.domain.Domain;
import nz.ac.wgtn.swen225.lc.domain.InformationPacket;
import nz.ac.wgtn.swen225.lc.domain.gameObject.Moveable.Direction;
import nz.ac.wgtn.swen225.lc.persistency.Load;
import nz.ac.wgtn.swen225.lc.recorder.Recorder;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;


/**
 * This class is the core class of the whole game, it creates and manages all subcomponents.
 *
 * @author Trent Shailer 300602354.
 */
public class Game {
  private boolean isPaused = false;
  private int timeLeft;
  private int currentLevel;
  private GameWindow gameWindow;
  private Domain domain;
  public Recorder recorder;

  /**
   * Creates a new instance of the game.
   * Loads the newest incomplete save if one exists, else level 1.
   *
   * @param stage The stage created by the application window.
   */
  public Game(Stage stage) {
    gameWindow = new GameWindow(stage, this);
    this.startRecordingPlayback(-1, stage);

    // Load game
    try {
      URL fileUrl = getClass().getResource("/levels/level1.json");
      if (fileUrl != null) {
        File f = new File(fileUrl.toURI());
        loadGame(f);
      }
    } catch (URISyntaxException ex) {
      System.out.println("Failed to load level1, URI Syntax error: " + ex.toString());
    }
  }

  /**
   * Create the game with a specific level on startup, used only by Fuzzing.
   *
   * @param level the number of the level to load;
   */
  public Game(int level) {
    try {
      URL fileUrl = getClass().getResource("/levels/level" + level + ".json");
      if (fileUrl != null) {
        File f = new File(fileUrl.toURI());
        loadGame(f);
      }
    } catch (URISyntaxException ex) {
      System.out.println("Failed to load level" + level + ", URI Syntax error: " + ex.toString());
    }
  }

  /**
   * Called by recorder when a recording should be played back.
   *
   * @param level the level that was recorded.
   * */
  public void startRecordingPlayback(int level, Stage parentStage) {
    Stage stage = new Stage();
    stage.setTitle("Recorder Playback");
    stage.initOwner(parentStage);

    try {
      URL fxmlFile = getClass().getResource("/UI/RecorderPlaybackPane.fxml");
      if (fxmlFile == null) {
        throw new IOException("URL for UI/RecorderPlaybackPane.fxml was null.");
      }

      Pane mainPane = FXMLLoader.load(fxmlFile);

      Scene scene = new Scene(mainPane);
      URL styleSheet = getClass().getResource("/UI/styles.css");
      if (styleSheet == null) {
        throw new IOException("URL for UI/styles.css was null.");
      }

      scene.getStylesheets().add(styleSheet.toExternalForm());
      stage.setScene(scene);
      stage.getIcons().add(new Image("recorderWindowIcon.png"));
      stage.setResizable(false);
      stage.show();
    } catch (IOException exception) {
      throw new RuntimeException(exception);
    }
  }

  /**
   * Exit the game.
   *
   * @param save Should the current game be saved before exiting.
   */
  public void exitGame(boolean save) {
    if (save) {
      // wait for save to finish then exit game
    }

    System.exit(0);
  }

  /**
   * Try to load a game from a given file.
   *
   * @param file the save/level file to load from.
   */
  public void loadGame(File file) {
    Domain domain = Load.loadAsDomain(file);
    this.domain = domain;
    this.recorder = new Recorder(-1, this); // TODO Get level number from persistence

    if (gameWindow != null) {
      gameWindow.createGame(domain);
    }
  }

  /**
   * Save the current game.
   */
  public void saveGame() {

  }

  /**
   * Pause the game.
   */
  public void pauseGame(boolean showOverlay) {
    isPaused = true;

    // TODO do timer stuff

    if (showOverlay) {
      gameWindow.overlay.displayPause();
    }
  }

  /**
   * Resume a paused game.
   */
  public void resumeGame() {
    isPaused = false;

    // TODO timer stuff

    gameWindow.overlay.close();
  }

  /**
   * Update the actors and propagate the event.
   */
  public void updateActors() {
    if (domain == null) {
      return;
    }

    InformationPacket result = domain.advanceClock();

    if (!result.isPlayerAlive()) {
      System.out.println("Dead");
      // TODO Handle death
    }

    if (recorder != null) {
      recorder.addActorMove(null); // TODO update to remove direction input
    }
  }

  /**
   * Try a move the player in a given direction.
   *
   * @param direction the direction the player should move in.
   * @return If the movement was successful.
   */
  public boolean movePlayer(Direction direction) {
    if (domain == null) {
      return false;
    }
    InformationPacket moveResult = domain.movePlayer(direction);

    if (!moveResult.hasPlayerMoved()) {
      return false;
    }

    if (!moveResult.isPlayerAlive()) {
      System.out.println("Dead");
      // TODO Handle death
    }

    // Movement was successful

    if (gameWindow != null) {
      gameWindow.renderer.movePlayer(direction);
    }

    if (recorder != null) {
      recorder.addPlayerMove(direction);
    }

    return true;
  }
}
