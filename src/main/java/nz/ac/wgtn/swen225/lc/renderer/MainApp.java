package main.java.nz.ac.wgtn.swen225.lc.renderer;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Sprite Animation");
        
        // Create a root pane
        Pane root = new Pane();

        // Create a Renderer object
        Renderer renderer = new Renderer();

        // Create a scene and add it to the stage
        Scene scene = new Scene(root, 500, 500);
        primaryStage.setScene(scene);
     // Add key event listener to the scene
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.RIGHT) {
                renderer.movePlayer();
            }
        });
        // Set up the stage and show it
        primaryStage.show();

        // Example: Move the player sprite
        renderer.renderPlayer();
        renderer.startIdleAnimation();
    }
}
