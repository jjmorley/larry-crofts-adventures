package nz.ac.wgtn.swen225.lc.renderer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.animation.*;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
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
  private static final int SPRITE_SIZE = 64;
  private static final int NUM_FRAMES = 4;
  private ImageView playerImageView;
  private final List<ImageView> actorImageViews = new ArrayList<>();
  private ParallelTransition currentParallelTransition;
  private TranslateTransition currentTransition;
  private final Map<ImageView, Integer> frameMap = new HashMap<>();

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
    if (domain.getActors() != null && !domain.getActors().isEmpty()) {
      renderActors(domain);
    }

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
    scrollPane.setFocusTraversable(false); //Ensure input doesn't move scroll pane
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
    playerImageView.setLayoutX(pos.y() * cellSize); // Initial X coordinate
    playerImageView.setLayoutY(pos.x() * cellSize); // Initial Y coordinate
    frameMap.put(playerImageView, 0);

    //start the animation
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
    frameMap.put(currentImageView, ((frameMap.get(currentImageView) + 1) % NUM_FRAMES));
    int x = frameMap.get(currentImageView) * SPRITE_SIZE;
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
      // Set the initial position of the sprite
      Position pos = actor.getPosition();
      actorImageView.setLayoutX(pos.y() * cellSize); // Initial X coordinate
      actorImageView.setLayoutY(pos.x() * cellSize); // Initial Y coordinate
      actorImageViews.add(actorImageView);
      root.getChildren().add(actorImageView);
      frameMap.put(actorImageView, 0);

      //start animation
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
   * @param direction        direction at which the player will move
   * @param translationSpeed speed at which the player will move, matching the game timeout
   * @throws IllegalArgumentException if translationSpeed is less than 0
   */
  public void movePlayer(Direction direction, int translationSpeed) {
    if (translationSpeed < 0) { //precondition: do not allow a negative speed
      throw new IllegalArgumentException("translationSpeed cannot be a negative integer");
    }
    //ensure no animation are already in progress
    if (currentTransition != null && currentTransition.getStatus() == Animation.Status.RUNNING) {
      currentTransition.stop();
    }
    canvas.renderGameBoard();
    currentTransition = new TranslateTransition(Duration.millis(translationSpeed), playerImageView);
    double xchange = 0;
    double ychange = 0;
    if (direction == Direction.LEFT) {
      xchange = cellSize;
    } else if (direction == Direction.RIGHT) {
      xchange = -cellSize;
    } else if (direction == Direction.DOWN) {
      ychange = -cellSize;
    } else {
      ychange = cellSize;
    }
    Position pos = domain.getPlayer().getPosition();
    // Set the initial position of the sprite
    playerImageView.setLayoutX(0.0); // Initial X coordinate
    playerImageView.setLayoutY(0.0); // Initial Y coordinate
    //set the start of the transition
    currentTransition.setFromX(pos.y() * cellSize + xchange);
    currentTransition.setFromY(pos.x() * cellSize + ychange);
    //set the target destination of the transition
    currentTransition.setToX(pos.y() * cellSize);
    currentTransition.setToY(pos.x() * cellSize);

    currentTransition.play();
    // Center the viewport on the player
    centerViewportOnPlayer(domain.getPlayer().getPosition());

  }

  /**
   * Moves all actors at the same time to the next cell - according to the route in the Actor
   * object class. Needs to be called before the actor position gets updated.
   *
   * @param translationSpeed speed at which the player will move, matching the game timeout
   * @throws IllegalArgumentException if translationSpeed is less than 0
   */
  public void moveActors(int translationSpeed) {
    if (translationSpeed < 0) { //precondition: do not allow negative transition speeds
      throw new IllegalArgumentException("translationSpeed cannot be a negative integer");
    }
    //ensure no animations are already in progress
    if (currentParallelTransition != null
            && currentParallelTransition.getStatus() == javafx.animation.Animation.Status.RUNNING) {
      currentParallelTransition.stop();
    }
    canvas.renderGameBoard();
    currentParallelTransition = new ParallelTransition();
    int count = 0; //used to retrieve the corresponding actor from list of actor images
    //add each actor transition to the parallel transition
    for (Actor actor : domain.getActors()) {
      TranslateTransition transition =
              new TranslateTransition(Duration.millis(translationSpeed),
                      actorImageViews.get(count));

      List<Position> route = actor.getRoute();

      // Set the initial position of the sprite
      actorImageViews.get(count).setLayoutX(0.0); // Initial X coordinate
      actorImageViews.get(count).setLayoutY(0.0); // Initial Y coordinate

      //set the start of the transition
      int currentPos = actor.getPositionIndex();
      transition.setFromX(route.get(currentPos).y() * cellSize);
      transition.setFromY(route.get(currentPos).x() * cellSize);

      //set the target destination of the transition
      int nextPos = (currentPos + 1) % route.size();
      transition.setToX(route.get(nextPos).y() * cellSize);
      transition.setToY(route.get(nextPos).x() * cellSize);

      currentParallelTransition.getChildren().add(transition);
      count++;
    }

    currentParallelTransition.play();
  }

  /**
   * gets the scrollPane which provides a focus area on all the root children.
   *
   * @return scrollPane
   */
  public ScrollPane getDisplay() {
    return scrollPane;
  }

}

