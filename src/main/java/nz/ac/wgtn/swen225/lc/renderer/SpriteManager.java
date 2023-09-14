package main.java.nz.ac.wgtn.swen225.lc.renderer;

import java.util.*;
import javafx.scene.image.Image;

/**
 * Manages importing the sprites for renderer use.
 * @author morleyjose
 *
 */
public class SpriteManager {
	
	private static final String BASE_PATH = "C:\\Users\\joefa\\git\\larry-crofts-adventures\\assets\\Sprites\\";
	
	private Map<String, Image> spriteCache;

    public SpriteManager() {
    	spriteCache = new HashMap<String, Image>();
        loadSprites();
    }

    /**
     * Load all sprites into a map for easy access.
     */
    private void loadSprites() {
        // Load and cache sprite images here
        spriteCache.put("Player", new Image(BASE_PATH + "Player.png"));
        spriteCache.put("Actor", new Image(BASE_PATH + "Actor.png"));
        spriteCache.put("Door_Exit", new Image(BASE_PATH + "Door_Exit_Closed.png"));
        spriteCache.put("Door_Yellow", new Image(BASE_PATH + "Door_Yellow_Closed.png"));
        spriteCache.put("Door_Red", new Image(BASE_PATH + "Door_Red_Closed.png"));
        spriteCache.put("Door_Green", new Image(BASE_PATH + "Door_Green_Closed.png"));
        spriteCache.put("Exit", new Image(BASE_PATH + "Exit.png"));
        spriteCache.put("Info", new Image(BASE_PATH + "Info.png"));
        spriteCache.put("Key_Green", new Image(BASE_PATH + "Key_Green.png"));
        spriteCache.put("Key_Red", new Image(BASE_PATH + "Key_Red.png"));
        spriteCache.put("Key_Yellow", new Image(BASE_PATH + "Key_Yellow.png"));
        spriteCache.put("Treasure", new Image(BASE_PATH + "Treasure.png"));
        spriteCache.put("Wall", new Image(BASE_PATH + "Wall.png"));
        spriteCache.put("Floor", new Image(BASE_PATH + "Floor.png"));
        spriteCache.put("Door_Exit_Open", new Image(BASE_PATH + "Door_Exit_Open.png"));
        spriteCache.put("Door_Yellow_Open", new Image(BASE_PATH + "Door_Yellow_Open.png"));
        spriteCache.put("Door_Red_Open", new Image(BASE_PATH + "Door_Red_Open.png"));
        spriteCache.put("Door_Green_Open", new Image(BASE_PATH + "Door_Green_Open.png"));
    }

    /**
     * Retrieve a selected sprite from the map.
     * @param spriteName
     * @return
     */
    public Image getSprite(String spriteName) {
        return spriteCache.get(spriteName);
    }

}
