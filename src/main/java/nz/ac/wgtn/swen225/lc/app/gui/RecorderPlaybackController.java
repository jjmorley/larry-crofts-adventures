package nz.ac.wgtn.swen225.lc.app.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The window that pops up to control the recorder playback.
 *
 * @author Trent Shailer 300602354.
 */
public class RecorderPlaybackController {
  public Label sliderValue;
  public Slider slider;

  public void initialize() {
    sliderValue.setText(Double.toString(slider.getValue()));

    slider.valueProperty().addListener((observable, oldValue, newValue) -> {
      // we want the double to only have 1dp
      double roundedValue = (double) Math.round(newValue.doubleValue() * 10) / 10;
      slider.setValue(roundedValue);
      sliderValue.setText(Double.toString(roundedValue));
    });

  }

  @FXML
  private void pause(ActionEvent event) {
    event.consume();
    System.out.println("Pause");
  }

  @FXML
  private void play(ActionEvent event) {
    event.consume();
    System.out.println("Play");
  }

  @FXML
  private void nextFrame(ActionEvent event) {
    event.consume();
    System.out.println("Next");
  }
}
