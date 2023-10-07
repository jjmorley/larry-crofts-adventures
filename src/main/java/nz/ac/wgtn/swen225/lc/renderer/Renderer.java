package nz.ac.wgtn.swen225.lc.renderer;

import java.util.ArrayList;
import java.util.List;
import javafx.animation.*;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import nz.ac.wgtn.swen225.lc.app.InputManager;
import nz.ac.wgtn.swen225.lc.domain.Domain;
import nz.ac.wgtn.swen225.lc.domain.Position;
import nz.ac.wgtn.swen225.lc.domain.gameObject.Moveable.Actor;
import nz.ac.wgtn.swen225.lc.domain.gameObject.Moveable.Direction;
import nz.ac.wgtn.swen225.lc.domain.gameObject.tile.Tile;

/**
 * This is the main class on the Renderer Package. It is responsible for update
 * the board display when it receives the appropriate events.
 *
 * @author morleyjose
 */
public class Renderer {
  private final Pane root;
  private final Domain domain;
  private static final SoundManager SOUND_MANAGER = new SoundManager();
  private final SpriteManager spriteManager;
  private final double cellSize;

  //animation variables
  private int currentFrame = 0;
  private static final int SPRITE_SIZE = 64;
  private static final int NUM_FRAMES = 4;
  private ImageView playerImageView;
  private final List<ImageView> actorImageViews = new ArrayList<>();

  //canvas variables
  private final BoardCanvas canvas;
  private static final int VIEW_SIZE = 7;
  private final Tile[][] tiles;
  private final ScrollPane scrollPane;
  private final double viewportWidth;
  private final double viewportHeight;

  /**
   * Creates boardCanvas and scrollPane.
   *
   * @param currentDomain the current domain used by Game
   * @param canvasSize    size of the game display
   */
  public Renderer(Domain currentDomain, int canvasSize) {
    this.root = new Pane();
    this.domain = currentDomain;
    this.tiles = domain.getBoard().getBoard();
    this.cellSize = (double) canvasSize / VIEW_SIZE;
    spriteManager = new SpriteManager();

    //create board canvas
    canvas = new BoardCanvas(tiles.length * cellSize, tiles.length * cellSize, currentDomain,
            spriteManager, cellSize);
    canvas.renderGameBoard();
    root.getChildren().add(canvas);

    //setup player animation
    renderPlayer(domain.getPlayer().getPosition());

    //setup actor animation
    /**if (domain.getActors() != null && !domain.getActors().isEmpty()) {
      renderActors(domain);
    }*/

    //create display window
    scrollPane = new ScrollPane();
    // Remove the vertical and horizontal scroll bars
    scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    scrollPane.setContent(root);
    // Calculate the viewport width and height
    viewportWidth = canvasSize;
    viewportHeight = canvasSize;
    scrollPane.setPrefViewportWidth(viewportWidth);
    scrollPane.setPrefViewportHeight(viewportHeight);
    scrollPane.setPannable(false); //Do not allow player panning - NOT WORKING???
    // Center the viewport on the player
    centerViewportOnPlayer(domain.getPlayer().getPosition());
  }

  /**
   * Responsible for calculating a value between 0.0 and 1.0 that represents the location the
   * Player is, relative to the board. This value is used to center the scroll panel on the player.
   *
   * @param playerPosition The Player Object's current coordinate
   */
  private void centerViewportOnPlayer(Position playerPosition) {
    double playerX = playerPosition.y() * cellSize + cellSize / 2;
    double playerY = playerPosition.x() * cellSize + cellSize / 2;

    // Calculate the new viewport position to center it on the player
    double newHvalue = (playerX - (viewportWidth / 2)) / (tiles.length * cellSize - viewportWidth);
    double newVvalue =
            (playerY - (viewportHeight / 2)) / (tiles.length * cellSize - viewportHeight);

    // Ensure the values are within valid bounds [0, 1]
    newHvalue = Math.max(0, Math.min(1, newHvalue));
    newVvalue = Math.max(0, Math.min(1, newVvalue));

    // Set the new viewport position
    scrollPane.setHvalue(newHvalue);
    scrollPane.setVvalue(newVvalue);
  }

  /**
   * Responsible for animating the player.
   *
   * @param pos The Player's current coordinate
   */
  private void renderPlayer(Position pos) {
    Image spriteSheet = spriteManager.getSprite("Player");
    playerImageView = new ImageView(spriteSheet);
    playerImageView.setFitWidth(cellSize);
    playerImageView.setFitHeight(cellSize);
    root.getChildren().add(playerImageView);

    // Set the initial position of the sprite
    playerImageView.setLayoutX(pos.x() * cellSize); // Initial X coordinate
    playerImageView.setLayoutY(pos.y() * cellSize); // Initial Y coordinate
    Timeline playerAnimation = createAnimation(playerImageView);
    playerAnimation.setCycleCount(Animation.INDEFINITE);
    playerAnimation.play();
  }

  /**
   * Creates an animation for a give sprite sheet.
   *
   * @param currentImageView The sprite sheet being used
   * @return Timeline
   */
  private Timeline createAnimation(ImageView currentImageView) {
    Duration frameDuration = Duration.millis(100); // Adjust frame duration as needed
    KeyFrame keyFrame = new KeyFrame(frameDuration, event -> {
      updateFrame(currentImageView);
    });

    return new Timeline(keyFrame);
  }

  /**
   * Updates which frame is visible on a given sprite sheet.
   *
   * @param currentImageView The current sprite sheet being used
   */
  private void updateFrame(ImageView currentImageView) {
    currentFrame = (currentFrame + 1) % NUM_FRAMES;
    int x = currentFrame * SPRITE_SIZE;
    currentImageView.setViewport(new javafx.geometry.Rectangle2D(x, 0, SPRITE_SIZE, SPRITE_SIZE));
  }

  /**
   * Responsible for animating the actors.
   */
  private void renderActors(Domain domain) {
    Image spriteSheet = spriteManager.getSprite("Actor");
    for (Actor actor : domain.getActors()) {
      ImageView actorImageView = new ImageView(spriteSheet);
      actorImageView.setFitWidth(cellSize);
      actorImageView.setFitHeight(cellSize);
      root.getChildren().add(playerImageView);
      // Set the initial position of the sprite
      Position pos = actor.getPosition();
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
   *
   * @param fileName the name of the sound being played
   */
  public static void playSound(String fileName) {
    SOUND_MANAGER.playSoundEffect(fileName);
  }

  /**
   * Moves player in the direction given by app package.
   *
   * @param direction direction at which the player will move
   * @param translationSpeed speed at which the player will move, matching the game timeout
   */
  public void movePlayer(Direction direction, int translationSpeed) {
    canvas.renderGameBoard();
    TranslateTransition transition =
            new TranslateTransition(Duration.millis(translationSpeed),
                    playerImageView);
    if (direction == Direction.LEFT) {
      transition.setByX(-cellSize);
    } else if (direction == Direction.RIGHT) {
      transition.setByX(cellSize);
    } else if (direction == Direction.DOWN) {
      transition.setByY(cellSize);
    } else {
      transition.setByY(-cellSize);
    }
    transition.play();
    // Center the viewport on the player
    centerViewportOnPlayer(domain.getPlayer().getPosition());

  }

  /**
   * Moves all actors at the same time to the next cell - according to the route in the Actor
   * object class.
   *
  public void moveActors(int translationSpeed) {
    ParallelTransition parallelTransition = new ParallelTransition();
    int count = 0; //used to retrieve the corresponding actor from list of actor images
    for (Actor actor : domain.getActors()) {
      TranslateTransition transition =
              new TranslateTransition(Duration.millis(translationSpeed),
                      actorImageViews.get(count));

      // Retrieve the actors current and next position
      List<Position> route = actor.getRoute();
      int currentPos = actor.getPositionIndex();
      int nextPosition = (currentPos + 1) % route.size();

      // Calculate the change in X and Y coordinates
      int deltaX = route.get(nextPosition).x() - route.get(currentPos).x();
      int deltaY = route.get(nextPosition).y() - route.get(currentPos).y();

      // determine the direction
      if (Math.abs(deltaX) == 1) {
        // Player moved horizontally
        if (deltaX > 0) { //Moved right
          transition.setByX(cellSize);
        } else { //Moved left
          transition.setByX(-cellSize);
        }
      } else if (Math.abs(deltaY) == 1) {
        // Player moved vertically
        if (deltaY < 0) { //Moved Up
          transition.setByY(-cellSize);
        } else { //Moved down
          transition.setByY(cellSize);
        }
      }

      parallelTransition.getChildren().add(transition);
      count++;
    }

    parallelTransition.play();
  }*/

  /**
   * gets the scrollPane which provides a focus area on all the root children.
   *
   * @return scrollPane
   */
  public ScrollPane getDisplay() {
    return scrollPane;
  }

}

