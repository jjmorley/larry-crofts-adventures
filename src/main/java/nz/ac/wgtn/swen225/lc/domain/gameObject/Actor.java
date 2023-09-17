package nz.ac.wgtn.swen225.lc.domain.gameObject;

import javafx.geometry.Pos;
import nz.ac.wgtn.swen225.lc.domain.Position;

import java.util.List;

/**
 * The type Actor.
 *
 * @author Alexander_Galloway.
 */
public class Actor implements GameObject{

    private Position position;
    private List<Position> route;

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
