package nz.ac.wgtn.swen225.lc.renderer;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
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
import nz.ac.wgtn.swen225.lc.domain.Domain;
import nz.ac.wgtn.swen225.lc.domain.Position;
import nz.ac.wgtn.swen225.lc.domain.gameObject.GameObject;
import nz.ac.wgtn.swen225.lc.domain.gameObject.Player;
import nz.ac.wgtn.swen225.lc.domain.gameObject.item.Item;
import nz.ac.wgtn.swen225.lc.domain.gameObject.tile.Tile;

/**
 * This is the main class on the Renderer Package. It is responsible for update
 * the board display when it receives the appropriate events.
 * 
 * @author morleyjose
 */
public class Renderer {
	private Canvas canvas;
	private SoundManager soundManager;
	private SpriteManager spriteManager;
	private final Domain domain;
	private ImageView playerSprite;

	// Variables for animation
	private static final int FRAME_WIDTH = 64;
	private static final int FRAME_HEIGHT = 64;
	private static final int FRAME_COUNT = 4;
	private int currentFrame = 0;
	private Timeline frameTimeline;
	private final int cellSize;

	// temp variables for testing purposes
	Tile[][] tiles;
	Item[][] items;
	private static final int NUM_FRAMES = 4;
	private Pane root;

	private ImageView imageView;
	private Timeline animation;
	private static final Duration MOVE_DURATION = Duration.millis(500); // Adjust as needed


	public Renderer(Domain domain, Tile[][] tiles){
		this.root = new Pane();
		cellSize = 64;
		this.tiles = tiles;
		this.domain = domain;
		// soundManager = new SoundManager();
		spriteManager = new SpriteManager();
		canvas = new Canvas(cellSize*tiles.length,cellSize*tiles.length);
		initialiseGameBoard();
		renderGameBoard();
		root.getChildren().add(canvas);
	}

	/**
	 * Adds the Floor tile to every grid cell to ensure tiles with transparency have
	 * a background
	 */
	private void initialiseGameBoard() {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		Image tileImage = spriteManager.getSprite("Free");

		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[i].length; j++) {
				// Calculate the position to draw the tile
				double x = j * cellSize;
				double y = i * cellSize;
				// Draw the tile on the canvas
				gc.drawImage(tileImage, x, y);
			}
		}
	}

	/**
	 * Responsible for redrawing the static 2D tiles and items of the board.
	 */
	public void renderGameBoard() {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		// Initialize tiles and items and add them to the gridPane
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[i].length; j++) {
				// Load the tile image
				Image tileImage = spriteManager.getSprite(tiles[i][j].getName());
				// Load the item images image
				//Image itemImage = spriteManager.getSprite(items[i][j].getName());

				// Add the tile's ImageView and item's ImageView to the gridPane
				gc.drawImage(tileImage, j*cellSize, i*cellSize);
				//gridPane.add(new ImageView(itemImage), j, i);
			}
		}
	}

	/**
	 * Responsible for animating the player.
	 * 
	 * @param pos
	 */
	public void renderPlayer(Position pos) {
		Image spriteSheet = spriteManager.getSprite("Player");
		imageView = new ImageView(spriteSheet);
		imageView.setFitWidth(FRAME_WIDTH);
		imageView.setFitHeight(FRAME_HEIGHT);
		root.getChildren().add(imageView);

		// Set the initial position of the sprite
		imageView.setLayoutX(pos.getX()*cellSize); // Initial X coordinate
		imageView.setLayoutY(pos.getY()*cellSize); // Initial Y coordinate
		animation = createAnimation();
		animation.setCycleCount(Animation.INDEFINITE);
		animation.play();
	}
	private Timeline createAnimation() {
		Duration frameDuration = Duration.millis(100); // Adjust frame duration as needed
		KeyFrame keyFrame = new KeyFrame(
				frameDuration,
				event -> {
					updateFrame();
				}
		);

		return new Timeline(keyFrame);
	}
	private void updateFrame() {
		currentFrame = (currentFrame + 1) % NUM_FRAMES;
		int x = currentFrame * FRAME_WIDTH;
		imageView.setViewport(new javafx.geometry.Rectangle2D(x, 0, FRAME_WIDTH, FRAME_HEIGHT));
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

	/**
	 * Moves player from last coordinate to the current coordinate
     */
	public void movePlayer(Position previous, Position current) {
		TranslateTransition transition = new TranslateTransition(MOVE_DURATION, imageView);
		transition.setByX(cellSize);
		transition.play();

	}

	public Pane getDisplay() {
		return root;
	}

	public void startAnimation(Player player){
		renderPlayer(player.getPosition());
	}
}
