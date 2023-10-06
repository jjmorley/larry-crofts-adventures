package nz.ac.wgtn.swen225.lc.app.gui.overlay;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import nz.ac.wgtn.swen225.lc.app.Game;

/**
 * Is the pause part of the overlay.
 *
 * @author Trent Shailer 300602354.
 * */
public class PauseOverlay extends BorderPane {

  /**
   * Create the pause overlay.
   *
   * @param game The game to call the resume function in.
   * */
  public PauseOverlay(Game game) {
    setPadding(new Insets(50));

    Label title = new Label("Game Paused");
    title.setFont(new Font(48));
    title.setTextFill(Color.WHITE);

    VBox topPane = new VBox();
    topPane.setAlignment(Pos.CENTER);
    topPane.getChildren().add(title);

    this.setTop(topPane);


    Button button = new Button("Resume Game");
    button.getStyleClass().add("OverlayButton");
    button.setOnAction(event -> game.resumeGame());

    VBox bottomPane = new VBox();
    bottomPane.setAlignment(Pos.CENTER);
    bottomPane.getChildren().add(button);

    this.setBottom(bottomPane);
    this.setVisible(false);
  }
}
