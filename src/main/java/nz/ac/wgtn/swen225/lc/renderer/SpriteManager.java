package nz.ac.wgtn.swen225.lc.renderer;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.image.Image;

/**
 * Manages importing the sprites for renderer use.
 *
 * @author morleyjose
 */
public class SpriteManager {
  private final Map<String, Image> spriteCache;

  /**
   * Initialises the map of sprite and sprite names.
   */
  public SpriteManager() {
    spriteCache = new HashMap<>();
    loadSprites();
  }

  /**
   * Load all sprites into a map for easy access.
   */
  private void loadSprites() {
    // Load and cache sprite images here
    spriteCache.put("Player", new Image("/Sprites/Player.png"));
    spriteCache.put("Actor", new Image("/Sprites/Actor.png"));
    spriteCache.put("ExitDoor", new Image("/Sprites/Door_Exit_Closed.png"));
    spriteCache.put("Door_Yellow", new Image("/Sprites/Door_Yellow_Closed.png"));
    spriteCache.put("Door_Red", new Image("/Sprites/Door_Red_Closed.png"));
    spriteCache.put("Door_Green", new Image("/Sprites/Door_Green_Closed.png"));
    spriteCache.put("Exit", new Image("/Sprites/Exit.png"));
    spriteCache.put("InfoTile", new Image("/Sprites/Info.png"));
    spriteCache.put("Key_Green", new Image("/Sprites/Key_Green.png"));
    spriteCache.put("Key_Red", new Image("/Sprites/Key_Red.png"));
    spriteCache.put("Key_Yellow", new Image("/Sprites/Key_Yellow.png"));
    spriteCache.put("Treasure", new Image("/Sprites/Treasure.png"));
    spriteCache.put("Wall", new Image("/Sprites/Wall.png"));
    spriteCache.put("Free", new Image("/Sprites/Floor.png"));
    spriteCache.put("Door_Exit_Open", new Image("/Sprites/Door_Exit_Open.png"));
    spriteCache.put("Door_Yellow_Open", new Image("/Sprites/Door_Yellow_Open.png"));
    spriteCache.put("Door_Red_Open", new Image("/Sprites/Door_Red_Open.png"));
    spriteCache.put("Door_Green_Open", new Image("/Sprites/Door_Green_Open.png"));
    spriteCache.put("Water", new Image("/Sprites/Water.png"));
    spriteCache.put("Lava", new Image("/Sprites/Lava.png"));
  }

  /**
   * Retrieve a selected sprite from the map.
   *
   * @param spriteName name of sprite needed
   * @return Image
   */
  public Image getSprite(String spriteName) {
    return spriteCache.get(spriteName);
  }

}
