package nz.ac.wgtn.swen225.lc.renderer;

import javafx.scene.image.Image;

/**
 * Manages importing the sprites for renderer use.
 * @author morleyjose
 *
 */
public class SpriteManager {
	
	private Map<String, Image> spriteCache;

    public SpriteManager() {
        spriteCache = new HashMap<>();
        // Load and cache sprites during initialization
        loadSprites();
    }

    private void loadSprites() {
        // Load and cache sprite images here
        spriteCache.put("player", new Image("player.png"));
        spriteCache.put("enemy", new Image("enemy.png"));
        // Add more sprites as needed
    }

    public Image getSprite(String spriteName) {
        return spriteCache.get(spriteName);
    }

}
