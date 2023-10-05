package nz.ac.wgtn.swen225.lc.app.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import nz.ac.wgtn.swen225.lc.app.InputManager;
import nz.ac.wgtn.swen225.lc.recorder.Playback;

/**
 * The window that pops up to control the recorder playback.
 *
 * @author Trent Shailer 300602354.
 */
public class RecorderPlaybackController {
  public Label sliderLabel;
  public Slider slider;

  private Playback playback;
  private InputManager inputManager;

  /**
   * Initializes the FXML controller.
   */
  public void initialize() {
    sliderLabel.setText(Double.toString(slider.getValue()));
  }

  /**
   * Initializes the playback controls.
   *
   * @param playback     The playback instance to be controlled.
   */
  public void initializePlaybackControls(Playback playback) {
    this.playback = playback;

    playback.setSpeed((int) Math.round(250.0 * slider.getValue()));

    slider.valueProperty().addListener((observable, oldValue, newValue) -> {
      // we want the double to only have 1dp
      double multiplier = (double) Math.round(newValue.doubleValue() * 10) / 10;
      slider.setValue(multiplier);
      sliderLabel.setText(Double.toString(multiplier));

      int newTimeout = (int) Math.round(250.0 * multiplier);

      playback.setSpeed(newTimeout);
      InputManager.MOVEMENT_TIMEOUT = newTimeout;
    });
  }

  @FXML
  private void pause(ActionEvent event) {
    event.consume();

    if (playback != null) {
      playback.pause();
    }
  }

  @FXML
  private void play(ActionEvent event) {
    event.consume();

    if (playback != null) {
      playback.play();
    }
  }

  @FXML
  private void nextFrame(ActionEvent event) {
    event.consume();

    if (playback != null) {
      playback.playNextFrame();
    }
  }
}
