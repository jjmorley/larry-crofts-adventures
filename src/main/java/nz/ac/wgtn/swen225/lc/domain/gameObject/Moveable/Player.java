package nz.ac.wgtn.swen225.lc.domain.gameObject.Moveable;

import javafx.geometry.Pos;
import nz.ac.wgtn.swen225.lc.domain.Position;
import nz.ac.wgtn.swen225.lc.domain.gameObject.GameObject;

/**
 * The type Player.
 *
 * @author Alexander_Galloway 300611406.
 */
public class Player implements GameObject {
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
     * Returns the name of the class.
     *
     * @return String .
     */
    @Override
    public String getName() {
        return "Player";
    }
}
