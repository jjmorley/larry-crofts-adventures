package nz.ac.wgtn.swen225.lc.renderer;

import java.util.Map;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import nz.ac.wgtn.swen225.lc.domain.Domain;
import nz.ac.wgtn.swen225.lc.domain.gameObject.item.Key;
import nz.ac.wgtn.swen225.lc.domain.gameObject.tile.Door;
import nz.ac.wgtn.swen225.lc.domain.gameObject.tile.Tile;
import nz.ac.wgtn.swen225.lc.domain.gameObject.tile.walkableTile.WalkableTile;

/**
 * Extends JavaFX canvas class and is responsible for display the current board state.
 *
 * @author morleyjose
 */
public class BoardCanvas extends Canvas {
  private final Domain domain;
  private final SpriteManager spriteManager;
  private final double cellSize;

  /**
   * Initialises the necessary global variables and updates the superclass.
   *
   * @param width         width of canvas
   * @param height        height of canvas
   * @param domain        current domain being used by Game
   * @param spriteManager current sprite manager being used by Renderer
   * @param cellSize      dimensions of a square cell
   */
  public BoardCanvas(double width, double height, Domain domain, SpriteManager spriteManager,
                     double cellSize) {
    super(width, height);
    this.domain = domain;
    this.spriteManager = spriteManager;
    this.cellSize = cellSize;
  }

  /**
   * Responsible for redrawing the static 2D tiles and items of the board.
   */
  public void renderGameBoard() {
    Tile[][] tiles = domain.getBoard().getBoard();

    //map of chars to strings which allows us to determine what colour the doors and keys should be
    Map<Character, String> colourMap = Map.of(
            'r', "Red",
            'b', "Yellow",
            'g', "Green"
    );

    GraphicsContext gc = this.getGraphicsContext2D();
    // Initialize tiles and items and add them to the gridPane
    for (int i = 0; i < tiles.length; i++) {
      for (int j = 0; j < tiles[i].length; j++) {
        // Load the tile image
        Image tileImage;
        if (tiles[i][j] instanceof Door door) { //if it's a door, we need to get its associated
          // colour
          String doorColour = colourMap.get((char) door.getKeyID());
          tileImage = spriteManager.getSprite("Door_" + doorColour);
        } else {
          tileImage = spriteManager.getSprite(tiles[i][j].getName());
        }
        //draw the tile onto the canvas
        gc.drawImage(tileImage, j * cellSize, i * cellSize, cellSize, cellSize);

        //It the tile is a WalkableTile check to see it has an item
        if (tiles[i][j] instanceof WalkableTile tile) {
          if (tile.getGameObject() != null && !tile.getGameObject().getName().equals("Player") && !tile.getGameObject().getName().equals("Actor")) {
            //load the item sprite
            Image itemImage;
            if (tile.getGameObject() instanceof Key key) { //if it's a key, check what colour it is
              String keyColour = colourMap.get((char) key.getKey());
              itemImage = spriteManager.getSprite("Key_" + keyColour);
            } else {
              itemImage = spriteManager.getSprite(tile.getGameObject().getName());
            }
            //draw the item onto the canvas
            gc.drawImage(itemImage, j * cellSize, i * cellSize, cellSize, cellSize);
          }
        }
      }
    }
  }
}
