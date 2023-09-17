package nz.ac.wgtn.swen225.lc.domain.gameObject.tile;

import nz.ac.wgtn.swen225.lc.domain.Position;
import nz.ac.wgtn.swen225.lc.domain.gameObject.item.Key;

/**
 * The type Door.
 *
 * @author Alexander_Galloway.
 */
public class Door implements Tile{

    private Position position;
    private Key key;

    /**
     * Returns true or false, if key matches the doors key.
     *
     * @return boolean.
     */
    public boolean keyMatch(Key key) {
        return this.key.equals(key);
    }

    /**
     * Returns the current position of the GameObject
     *
     * @return Position Can be altered.
     */
    @Override
    public Position getPosition() {
        return position;
    }

    /**
     * Set the position of the game-object to be a new position
     *
     * @param position new chosen position for game-object.
     */
    @Override
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * Returns if the gameObject can be walked through.
     *
     * @return boolean Changes if walkable.
     */
    @Override
    public boolean isWalkable() {
        return false;
    }
}
