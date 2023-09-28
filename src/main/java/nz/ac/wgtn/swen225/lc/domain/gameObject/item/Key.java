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
    private final int key;

    public Key(int key, Position position) {
        if (position == null) throw new IllegalArgumentException();

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
     * Returns the name of the class.
     *
     * @return String .
     */
    @Override
    public String getName() {
        return "Key";
    }
}
