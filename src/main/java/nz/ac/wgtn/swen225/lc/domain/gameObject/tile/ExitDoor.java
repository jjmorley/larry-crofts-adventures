package nz.ac.wgtn.swen225.lc.domain.gameObject.tile;

import nz.ac.wgtn.swen225.lc.domain.Position;

/**
 * The type Exit door.
 *
 * @author Alexander_Galloway 300611406.
 */
public class ExitDoor implements Tile {

    private Position position;

    public ExitDoor(Position position) {
        if (position==null) throw new IllegalArgumentException();
        
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
     * Returns the name of the class.
     *
     * @return String .
     */
    @Override
    public String getName() {
        return "ExitDoor";
    }
}
