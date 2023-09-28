package nz.ac.wgtn.swen225.lc.domain.gameObject.tile.walkableTile;

import javafx.geometry.Pos;
import nz.ac.wgtn.swen225.lc.domain.Position;
import nz.ac.wgtn.swen225.lc.domain.gameObject.GameObject;
import nz.ac.wgtn.swen225.lc.domain.gameObject.tile.Tile;

/**
 * The type Exit.
 *
 * @author Alexander_Galloway 300611406.
 */
public class Exit implements WalkableTile {

    private Position position;
    private GameObject gameObject;

    public Exit(GameObject gameObject, Position position) {
        if (position == null) throw new IllegalArgumentException();

        this.gameObject = gameObject;
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
        return "Exit";
    }

    /**
     * Returns GameObject that a tile may be holding
     *
     * @return GameObject Can be altered.
     */
    @Override
    public GameObject getGameObject() {
        return gameObject;
    }

    /**
     * Sets new gameObject to a tile.
     *
     * @param gameObject New item on gameObject.
     */
    @Override
    public void setGameObject(GameObject gameObject) {
        this.gameObject = gameObject;
    }
}
