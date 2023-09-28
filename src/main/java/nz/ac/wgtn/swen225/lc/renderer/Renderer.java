package nz.ac.wgtn.swen225.lc.renderer;

import javafx.event.EventHandler;
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
import nz.ac.wgtn.swen225.lc.domain.gameObject.Moveable.Actor;
import nz.ac.wgtn.swen225.lc.domain.gameObject.Moveable.Direction;
import nz.ac.wgtn.swen225.lc.domain.gameObject.Moveable.Player;
import nz.ac.wgtn.swen225.lc.domain.gameObject.item.Item;
import nz.ac.wgtn.swen225.lc.domain.gameObject.item.Key;
import nz.ac.wgtn.swen225.lc.domain.gameObject.tile.Door;
import nz.ac.wgtn.swen225.lc.domain.gameObject.tile.Tile;
import nz.ac.wgtn.swen225.lc.domain.gameObject.tile.walkableTile.WalkableTile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This is the main class on the Renderer Package. It is responsible for update
 * the board display when it receives the appropriate events.
 * 
 * @author morleyjose
 */
public class Renderer {
	private Pane root;
	private static SoundManager soundManager;
	private final SpriteManager spriteManager;
	private final int cellSize;

	//animation variables
	private ImageView playerSprite;
	private int currentFrame = 0;
	private static final int NUM_FRAMES = 4;
	private ImageView playerImageView;
	private List<ImageView> actorImageViews = new ArrayList<>();
	private Timeline playerAnimation;
	private static final Duration MOVE_DURATION = Duration.millis(500); // Adjust as needed

	//canvas variables
	private Canvas canvas;
	Tile[][] tiles;


	public Renderer(Domain domain, int canvasSize){
		this.root = new Pane();
		this.tiles = domain.getBoard().getBoard();
		this.cellSize = canvasSize/ tiles.length;
		soundManager = new SoundManager();
		spriteManager = new SpriteManager();
		canvas = new Canvas(cellSize*tiles.length,cellSize*tiles.length);
		renderGameBoard();
		root.getChildren().add(canvas);
		//setup player animation
		renderPlayer(domain.getPlayer().getPosition());
		//setup actor animation
		/**if(domain.getActors() != null && !domain.getActors().isEmpty()) {
			renderActors(domain);
		}**/
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
	private void renderGameBoard() {
		//map of chars to strings which allows us to determine what colour the doors and keys should be
		Map<Character, String> colourMap = Map.of(
				'r', "Red",
				'b', "Yellow",
				'g', "Green"
		);
		initialiseGameBoard(); //draw an entire board of free tiles
		GraphicsContext gc = canvas.getGraphicsContext2D();
		// Initialize tiles and items and add them to the gridPane
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[i].length; j++) {
				// Load the tile image
				Image tileImage;
				if(tiles[i][j] instanceof Door door){//if it's a door, we need to get its associated colour
					String doorColour = colourMap.get((char)door.getKeyID());
					tileImage = spriteManager.getSprite("Door_"+doorColour);
				}
				else{
					tileImage = spriteManager.getSprite(tiles[i][j].getName());
				}
				//draw the tile onto the canvas
				gc.drawImage(tileImage, j*cellSize, i*cellSize);

				//It the tile is a WalkableTile check to see it has an item
				if(tiles[i][j] instanceof WalkableTile tile){
					if(tile.getGameObject() != null) {
						//load the item sprite
						Image itemImage;
						if(tile.getGameObject() instanceof Key key){//if it's a key, check what colour it is
							String keyColour  = colourMap.get((char)key.getKey());
							itemImage = spriteManager.getSprite("Key_"+keyColour);
						}
						else {
							itemImage = spriteManager.getSprite(tile.getGameObject().getName());
						}
						//draw the item onto the canvas
						gc.drawImage(itemImage, j*cellSize, i*cellSize);
					}
				}
			}
		}
	}

	/**
	 * Responsible for animating the player.
	 *
     */
	private void renderPlayer(Position pos) {
		Image spriteSheet = spriteManager.getSprite("Player");
		playerImageView = new ImageView(spriteSheet);
		playerImageView.setFitWidth(cellSize);
		playerImageView.setFitHeight(cellSize);
		root.getChildren().add(playerImageView);

		// Set the initial position of the sprite
		playerImageView.setLayoutX(pos.x()*cellSize); // Initial X coordinate
		playerImageView.setLayoutY(pos.y()*cellSize); // Initial Y coordinate
		playerAnimation = createAnimation(playerImageView);
		playerAnimation.setCycleCount(Animation.INDEFINITE);
		playerAnimation.play();
	}
	private Timeline createAnimation(ImageView currentImageView) {
		Duration frameDuration = Duration.millis(100); // Adjust frame duration as needed
		KeyFrame keyFrame = new KeyFrame(
				frameDuration,
				event -> {
					updateFrame(currentImageView);
				}
		);

		return new Timeline(keyFrame);
	}
	private void updateFrame(ImageView currentImageView) {
		currentFrame = (currentFrame + 1) % NUM_FRAMES;
		int x = currentFrame * cellSize;
		currentImageView.setViewport(new javafx.geometry.Rectangle2D(x, 0, cellSize, cellSize));
	}

	/**
	 * Responsible for animating the actors.
	 */
	private void renderActors(Domain domain){
		Image spriteSheet = spriteManager.getSprite("Actor");
		for(Actor actor : domain.getActors()) {
			Position pos = actor.getPosition();
			ImageView actorImageView = new ImageView(spriteSheet);
			actorImageView.setFitWidth(cellSize);
			actorImageView.setFitHeight(cellSize);
			root.getChildren().add(playerImageView);
			// Set the initial position of the sprite
			actorImageView.setLayoutX(pos.x() * cellSize); // Initial X coordinate
			actorImageView.setLayoutY(pos.y() * cellSize); // Initial Y coordinate
			actorImageViews.add(actorImageView);

			Timeline actorAnimation = createAnimation(actorImageView);
			actorAnimation.setCycleCount(Animation.INDEFINITE);
			actorAnimation.play();
		}
	}

	/**
	 * allows any package to play a sound statically.
	 * @param soundFilePath
	 */
	public static void playSound(String soundFilePath) {
		soundManager.playSoundEffect(soundFilePath);
	}

	/**
	 * Moves player in the direction given by app package.
     */
	public void movePlayer(Direction direction) {
		TranslateTransition transition = new TranslateTransition(MOVE_DURATION, playerImageView);
		if(direction == Direction.LEFT){
			transition.setByX(-cellSize);
		} else if (direction == Direction.RIGHT) {
			transition.setByX(cellSize);
		} else if (direction == Direction.DOWN) {
			transition.setByY(cellSize);
		}
		else {
			transition.setByY(-cellSize);
		}
		transition.play();
	}

	/**
	 * Moves actor in the direction given by app package.
	 * Requires an integer of which actor needs to be moved - this assumes imageView list is the same order as actor list in domain
	 */
	public void moveActor(int actor, Direction direction) {
		TranslateTransition transition = new TranslateTransition(MOVE_DURATION, actorImageViews.get(actor));
		if(direction == Direction.LEFT){
			transition.setByX(-cellSize);
		} else if (direction == Direction.RIGHT) {
			transition.setByX(cellSize);
		} else if (direction == Direction.DOWN) {
			transition.setByY(cellSize);
		}
		else {
			transition.setByY(-cellSize);
		}
		transition.play();
	}

	/**
	 * gets the main renderer panel which contains the board canvas and player and actor panels.
	 * @return root
	 */
	public Pane getDisplay() {
		return root;
	}
}
