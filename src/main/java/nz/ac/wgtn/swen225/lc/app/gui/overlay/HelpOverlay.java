package nz.ac.wgtn.swen225.lc.app.gui.overlay;

import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import nz.ac.wgtn.swen225.lc.app.Game;

/**
 * Is the pause part of the overlay.
 *
 * @author Trent Shailer 300602354.
 */
public class HelpOverlay extends BorderPane {

  /**
   * Create the pause overlay.
   *
   * @param game The game to call the resume function in.
   */
  public HelpOverlay(Game game) {
    setPadding(new Insets(50));

    createTop();
    createMiddle();
    createBottom(game);

    this.setVisible(false);
  }

  private void createTop() {
    Label title = new Label("Help");
    title.setFont(new Font(48));
    title.setTextFill(Color.WHITE);

    VBox topPane = new VBox();
    topPane.setAlignment(Pos.CENTER);
    topPane.getChildren().add(title);

    this.setTop(topPane);
  }

  private void createMiddle() {
    VBox middlePane = new VBox();
    middlePane.setAlignment(Pos.TOP_CENTER);
    middlePane.setSpacing(25);


    middlePane.getChildren().addAll(createGoal(), createControls());
    this.setCenter(middlePane);
  }

  private Pane createGoal() {
    Label title = new Label("Your Goal");
    title.setFont(new Font(36));
    title.setTextFill(Color.WHITE);

    TextFlow content = new TextFlow();
    Text text = new Text("Larry Croft, a brave knight, has fallen down a well to discover an old dungeon. Venture deeper into the dungeon by collecting treasures :img: to unlock the exit door :img:, then descend to the next floor :img:.");
    text.setFont(new Font(20));
    text.setFill(Color.WHITE);

    ImageView treasureImage = new ImageView("sprites/Treasure.png");
    ImageView exitDoorImage = new ImageView("sprites/Door_Exit_Closed.png");
    ImageView exitImage = new ImageView("sprites/Exit.png");

    content.setTextAlignment(TextAlignment.JUSTIFY);

    Node[] processedText = insertImagesToText(text, treasureImage, exitDoorImage, exitImage);

    content.getChildren().addAll(processedText);

    VBox pane = new VBox();
    pane.setAlignment(Pos.TOP_CENTER);
    pane.getChildren().addAll(title, content);

    return pane;
  }

  private Node[] insertImagesToText(Text source, ImageView... images) {
    List<Node> parts = new ArrayList<>();

    String[] sourceParts = source.getText().split(":img:");
    for (int i = 0; i < sourceParts.length; i++) {
      String sourcePart = sourceParts[i];
      Text text = new Text(sourcePart);
      text.setFont(source.getFont());
      text.setFill(source.getFill());

      parts.add(text);

      if (i < images.length) {
        ImageView image = images[i];
        image.setFitHeight(text.getFont().getSize());
        image.setFitWidth(text.getFont().getSize());
        parts.add(image);
      }
    }

    return parts.toArray(new Node[0]);
  }

  private Pane createControls() {
    Label title = new Label("Controls");
    title.setFont(new Font(36));
    title.setTextFill(Color.WHITE);

    String[] controls = new String[]{
        "Arrow Keys",
        "Space",
        "Escape",
        "CTRL + X",
        "CTRL + S",
        "CTRL + R",
        "CTRL + #"
    };

    String[] actions = new String[]{
        "Movement",
        "Pause game",
        "Resume game",
        "Quit without saving",
        "Save and quit",
        "Load save",
        "Load level #"
    };

    HBox contentPane = new HBox();
    contentPane.setSpacing(100);
    contentPane.setAlignment(Pos.TOP_CENTER);

    VBox keysPane = createControlStack(controls, Pos.TOP_RIGHT);
    VBox actionsPane = createControlStack(actions, Pos.TOP_LEFT);

    contentPane.getChildren().addAll(keysPane, actionsPane);

    VBox pane = new VBox();
    pane.setAlignment(Pos.TOP_CENTER);
    pane.getChildren().addAll(title, contentPane);

    return pane;
  }

  private VBox createControlStack(String[] controls, Pos alignment) {
    VBox pane = new VBox();
    pane.setSpacing(5);
    pane.setAlignment(alignment);
    pane.setPrefWidth(300);

    for (String s : controls) {
      Label l = new Label(s);
      l.setTextFill(Color.WHITE);
      l.setFont(new Font(24));
      pane.getChildren().add(l);
    }

    return pane;
  }

  private void createBottom(Game game) {
    Button button = new Button("Close Help");
    button.setFont(new Font(20));
    button.setOnAction(event -> game.resumeGame());

    VBox bottomPane = new VBox();
    bottomPane.setAlignment(Pos.CENTER);
    bottomPane.getChildren().add(button);

    this.setBottom(bottomPane);
  }
}