package nz.ac.wgtn.swen225.lc.app.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import nz.ac.wgtn.swen225.lc.app.Game;
import nz.ac.wgtn.swen225.lc.recorder.Playback;

import java.io.IOException;
import java.net.URL;


/**
 * Creates a recorder playback window.
 *
 * @author Trent Shailer 300602354.
 */
public class RecorderPlaybackWindow {

  /**
   * Create a recorder playback window.
   *
   * @param parentStage The stage that this window belongs to.
   * @param playback    The playback instance to be controlled.
   * @param game        The game instance managing everything.
   */
  public RecorderPlaybackWindow(Stage parentStage, Playback playback, Game game) {
    Stage stage = new Stage();
    stage.setTitle("Recorder Playback");
    stage.initOwner(parentStage);

    try {
      URL fxmlFile = getClass().getResource("/UI/RecorderPlaybackPane.fxml");
      if (fxmlFile == null) {
        throw new IOException("URL for UI/RecorderPlaybackPane.fxml was null.");
      }

      FXMLLoader loader = new FXMLLoader(fxmlFile);
      RecorderPlaybackController controller = loader.getController();
      controller.initializePlaybackControls(playback);
      Pane mainPane = loader.load();

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

      stage.setOnCloseRequest(windowEvent -> {
        // End recording, resume player control
        game.stopRecordingPlayback(playback);
      });

    } catch (IOException exception) {
      throw new RuntimeException(exception);
    }
  }
}
