package nz.ac.wgtn.swen225.lc.domain.gameObject.tile;

import nz.ac.wgtn.swen225.lc.domain.Position;
import nz.ac.wgtn.swen225.lc.domain.gameObject.item.Key;

/**
 * The type Door.
 *
 * @author Alexander_Galloway 300611406.
 */
public class Door implements Tile {

    private Position position;
    private final Key key;

    public Door(Key key, Position position) {
        if (key == null) throw new IllegalArgumentException();
        if (position == null) throw new IllegalArgumentException();

        this.key = key;
        this.position = position;
    }

    /**
     * Returns true or false, if key matches the doors key.
     *
     * @return boolean.
     */
    public boolean keyMatch(Key key) {
        return this.key.equals(key);
    }

    /**
     * Returns an ID that matches a number for a door.
     *
     * @return int Unique ID.
     */
    public int getKeyID() {
        return key.getKey();
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
        return "Door";
    }
}
