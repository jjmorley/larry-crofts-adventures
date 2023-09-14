package nz.ac.wgtn.swen225.lc.renderer;

import javafx.scene.canvas.Canvas;
import javafx.scene.layout.GridPane;

/**
 * This is the main class on the Renderer Package. It is responsible for update the board display
 * when it receives the appropriate events.
 * 
 * @author morleyjose
 */
public class Renderer{
	private GridPane gridPane;
	
	public Renderer() {
		
	}
	
	/**
	 * Responsible for redrawing the static 2D tiles and items of the board.
	 */
	public void renderGameBoard() {
        // Render the game board, tiles, and walls
		// Initialize your tiles and items and add them to the gridPane
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                // Load the tile and item images here (you may need to modify this)
                Image tileImage = new Image("path_to_tile_image.png");
                tiles[i][j] = new Tile(tileImage);

                // Load the item images here and set their coordinates (you may need to modify this)
                Image itemImage = new Image("path_to_item_image.png");
                items[i][j] = new Item(itemImage);

                // Add the tile's ImageView and item's ImageView to the gridPane
                gridPane.add(tiles[i][j].getImageView(), j, i);
                gridPane.add(items[i][j].getImageView(), j, i);
            }
        }
    }

	/**
	 * Responsible for animating the player.
	 * @param player
	 */
    public void renderPlayer(Player player) {
        // Render the player character and animate as needed
    }

    /**
     * Responsible for animating the actor.
     */
    public void renderActors(List<Actor> actors) {
        // Render other game actors (enemies, collectibles, etc.)
    }

    /**
     * Manages playing the sounds at the appropriate times.
     * @param soundFilePath
     */
    public void playSound(String soundFilePath) {
        // Play game sounds
    }
}
