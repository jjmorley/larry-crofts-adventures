package nz.ac.wgtn.swen225.lc.domain.gameObject.tile;

import nz.ac.wgtn.swen225.lc.domain.Position;

/**
 * The type Info tile.
 *
 * @author Alexander_Galloway.
 */
public class InfoTile implements Tile {

    private Position position;

    public InfoTile(Position position) {
        assert position!=null;

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
}
