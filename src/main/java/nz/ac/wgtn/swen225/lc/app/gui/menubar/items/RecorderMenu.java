package nz.ac.wgtn.swen225.lc.app.gui.menubar.items;

import java.io.File;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import nz.ac.wgtn.swen225.lc.app.Game;
import nz.ac.wgtn.swen225.lc.app.gui.GameWindow;
import nz.ac.wgtn.swen225.lc.recorder.Playback;

/**
 * The recorder menu on the menu bar.
 *
 * @author Trent Shailer 300602354.
 */
public class RecorderMenu extends Menu {
  /**
   * Create a recorder menu.
   *
   * @param game The game that the recorder belongs to.
   */
  public RecorderMenu(Game game, GameWindow window) {
    super("Recorder");

    MenuItem saveRecording = new MenuItem("Save Recording");
    saveRecording.setOnAction(event -> {
      if (game.recorder == null) {
        return;
      }

      File file = window.openFileSaveDialog("Recording");
      if (file != null) {
        game.recorder.saveRecordedGameToFile(file);
      }
    });

    MenuItem playRecording = new MenuItem("Playback a Recording");
    playRecording.setOnAction(event -> {
      File file = window.openFileSelectorDialog("Recording");
      if (file != null) {
        new Playback(file, game);
      }
    });

    this.getItems().addAll(saveRecording, playRecording);
  }
}
