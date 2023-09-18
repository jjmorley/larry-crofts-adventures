package nz.ac.wgtn.swen225.lc.app;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        GameMenuBar menuBar = new GameMenuBar();

        VBox pane = new VBox();
        pane.getChildren().add(menuBar);
        pane.setBackground(
                new Background(
                        new BackgroundFill(
                                new Color(0.000, 0.000, 0.000, 0.039),
                                CornerRadii.EMPTY,
                                Insets.EMPTY
                        )
                )
        );

        Scene scene = new Scene(pane, 720, 820);

        stage.setTitle("Larry Croft's Adventures");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
