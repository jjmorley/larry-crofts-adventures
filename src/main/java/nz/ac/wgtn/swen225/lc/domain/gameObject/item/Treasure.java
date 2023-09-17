package nz.ac.wgtn.swen225.lc.domain.gameObject.item;

import nz.ac.wgtn.swen225.lc.domain.Position;
import nz.ac.wgtn.swen225.lc.domain.gameObject.GameObject;

/**
 * The type Treasure.
 *
 * @author Alexander_Galloway.
 */
public class Treasure implements Item, GameObject {

    private Position position;

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
        return true;
    }
}
