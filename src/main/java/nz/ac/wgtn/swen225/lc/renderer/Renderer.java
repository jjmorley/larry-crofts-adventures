package main.java.nz.ac.wgtn.swen225.lc.renderer;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.GridPane;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.geometry.Rectangle2D;
import javafx.util.Duration;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

/**
 * This is the main class on the Renderer Package. It is responsible for update
 * the board display when it receives the appropriate events.
 * 
 * @author morleyjose
 */
public class Renderer {
	private GridPane gridPane;
	private SoundManager soundManager;
	private SpriteManager spriteManager;
	private Domain domain;
	private ImageView playerSprite;

	// Variables for animation
	private static final int FRAME_WIDTH = 64;
	private static final int FRAME_HEIGHT = 64;
	private static final int FRAME_COUNT = 4;
	private int currentFrame = 0;
	private Timeline frameTimeline;

	// temp variables for testing purposes
	private int rows = 9;
	private int columns = 9;

	public Renderer(Domain domain) {
		this.domain = domain;
		// soundManager = new SoundManager();
		spriteManager = new SpriteManager();
		gridPane = new GridPane();
		initialiseGameBoard();
	}

	/**
	 * Adds the Floor tile to every grid cell to ensure tiles with transparency have
	 * a background
	 */
	private void initialiseGameBoard() {
		Image tileImage = spriteManager.getSprite("Floor");
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				gridPane.add(new ImageView(tileImage), j, i);
			}
		}
	}

	/**
	 * Responsible for redrawing the static 2D tiles and items of the board.
	 */
	public void renderGameBoard() {
		// Initialize tiles and items and add them to the gridPane
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				// Load the tile image
				Image tileImage = spriteManager.getSprite(domain.getTiles[i][j].getType());
				// Load the item images image
				Image itemImage = spriteManager.getSprite(domain.getItems[i][j].getType());

				// Add the tile's ImageView and item's ImageView to the gridPane
				gridPane.add(new ImageView(tileImage), j, i);
				gridPane.add(new ImageView(itemImage), j, i);
			}
		}
	}

	/**
	 * Responsible for animating the player.
	 * 
	 * @param player
	 */
	public void renderPlayer(Pane root) {
		Image spriteSheet = spriteManager.getSprite("Player");
		playerSprite = new ImageView(spriteSheet);

		frameTimeline = new Timeline(new KeyFrame(Duration.millis(150), event -> {
			// Calculate the next frame's X-coordinate
			double nextFrameX = (currentFrame * FRAME_WIDTH) % (FRAME_WIDTH * FRAME_COUNT);
			playerSprite.setViewport(new Rectangle2D(nextFrameX, 0, FRAME_WIDTH, FRAME_HEIGHT));
			currentFrame = (currentFrame + 1) % FRAME_COUNT;
		}));
		frameTimeline.setCycleCount(Animation.INDEFINITE);

		root.getChildren().add(playerSprite);
	}

	/**
	 * Responsible for animating the actor.
	 *
	 * public void renderActors(List<Actor> actors) { // Render other game actors
	 * (enemies, collectibles, etc.) /
	 * 
	 * /** Manages playing the sounds at the appropriate times.
	 * 
	 * @param soundFilePath
	 */
	public void playSound(String soundFilePath) {
		// Play game sounds
	}

	public void startIdleAnimation() {
		frameTimeline.play();
	}

	public void stopIdleAnimation() {
		frameTimeline.stop();
	}

	/**
	 * Moves player from last coordinate to the current coordinate
	 * @param lastCoord
	 * @param currentCoord
	 */
	public void movePlayer(Coord lastCoord, Coord currentCoord) {
		double currentX = playerSprite.getTranslateX();
		double targetX = currentX + 50; // The target X-coordinate

		// Calculate the animation duration based on the frame rate
		long animationDurationMillis = 100 * FRAME_COUNT; // Total animation duration

		// Create a TranslateTransition to move the playerSprite gradually
		TranslateTransition transition = new TranslateTransition(Duration.millis(animationDurationMillis),
				playerSprite);
		transition.setToX(targetX);

		// Start the translation animation
		transition.play();

	}

	public GridPane getDisplay() {
		return gridPane;
	}
}
