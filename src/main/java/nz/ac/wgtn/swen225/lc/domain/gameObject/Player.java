package nz.ac.wgtn.swen225.lc.domain.gameObject;

import javafx.geometry.Pos;
import nz.ac.wgtn.swen225.lc.domain.Position;

/**
 * The type Player.
 *
 * @author Alexander_Galloway 300611406.
 */
public class Player implements GameObject{
    private Position position;

    /**
     * Instantiates a new Player.
     *
     * @param position the position
     */
    public Player(Position position) {
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
     * Returns if the gameObject can be walked through.
     *
     * @return boolean Changes if walkable.
     */
    @Override
    public boolean isWalkable() {
        return false;
    }

    /**
     * Returns the name of the class.
     *
     * @return String .
     */
    @Override
    public String getName() {
        return "Player";
    }
}
