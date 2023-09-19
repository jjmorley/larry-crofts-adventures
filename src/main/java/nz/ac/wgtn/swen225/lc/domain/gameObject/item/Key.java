package nz.ac.wgtn.swen225.lc.domain.gameObject.item;

import nz.ac.wgtn.swen225.lc.domain.Position;
import nz.ac.wgtn.swen225.lc.domain.gameObject.GameObject;

/**
 * The type Key.
 *
 * @author Alexander_Galloway 300611406.
 */
public class Key implements Item, GameObject {

    private Position position;
    private int key;
    private GameObject replaceWith;

    public Key(GameObject replaceWith, int key, Position position) {
        if (replaceWith==null) throw new IllegalArgumentException();
        if (position==null) throw new IllegalArgumentException();

        this.replaceWith = replaceWith;
        this.key = key;
        this.position = position;

    }

    /**
     * Returns an ID that matches a number for a door.
     *
     * @return int Unique ID.
     */
    public int getKey() {
        return key;
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
        return "Key";
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
