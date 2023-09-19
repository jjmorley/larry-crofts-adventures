package nz.ac.wgtn.swen225.lc.domain.gameObject.item;

import nz.ac.wgtn.swen225.lc.domain.Position;
import nz.ac.wgtn.swen225.lc.domain.gameObject.GameObject;

/**
 * The type Treasure.
 *
 * @author Alexander_Galloway 300611406.
 */
public class Treasure implements Item, GameObject {

    private Position position;
    private GameObject replaceWith;

    public Treasure(GameObject replaceWith, Position position) {
        if (replaceWith==null) throw new IllegalArgumentException();
        if (position==null) throw new IllegalArgumentException();

        this.replaceWith = replaceWith;
        this.position = position;
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
        return true;
    }

    /**
     * Returns the name of the class.
     *
     * @return String .
     */
    @Override
    public String getName() {
        return "Treasure";
    }

    /**
     * Returns the GameObject the Item should be replaced with when picked up.
     *
     * @return GameObject Changes if walkable.
     */
    @Override
    public GameObject replaceWith() {
        return replaceWith;
    }
}
