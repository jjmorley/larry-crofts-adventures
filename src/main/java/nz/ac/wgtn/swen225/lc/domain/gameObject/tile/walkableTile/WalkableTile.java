package nz.ac.wgtn.swen225.lc.domain.gameObject.tile.walkableTile;

import nz.ac.wgtn.swen225.lc.domain.gameObject.GameObject;
import nz.ac.wgtn.swen225.lc.domain.gameObject.tile.Tile;

/**
 * Interface called WalkableTile
 *
 * @author Alexander_Galloway 300611406.
 */
public interface WalkableTile extends Tile {
    /**
     * Returns GameObject that a tile may be holding.
     *
     * @return GameObject Can be altered.
     */
    public GameObject getGameObject();

    /**
     * Sets new gameObject to a tile.
     *
     * @param gameObject New item on gameObject.
     */
    public void setGameObject(GameObject gameObject);
}
