package nz.ac.wgtn.swen225.lc.domain.gameObject;

import nz.ac.wgtn.swen225.lc.domain.Position;

/**
 * The interface Game object.
 *
 * @author Alexander_Galloway.
 */
public interface GameObject {

    /**
     * Returns the current position of the GameObject
     *
     * @return Position Can be altered.
     */
    public Position getPosition();

    /**
     * Set the position of the game-object to be a new position
     *
     * @param position new chosen position for game-object.
     */
    public void setPosition(Position position);

    /**
     * Returns if the gameObject can be walked through.
     *
     * @return boolean Changes if walkable.
     */
    public boolean isWalkable();

}