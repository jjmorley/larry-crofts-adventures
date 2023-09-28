package nz.ac.wgtn.swen225.lc.domain.gameObject.tile.walkableTile;

import nz.ac.wgtn.swen225.lc.domain.Position;
import nz.ac.wgtn.swen225.lc.domain.gameObject.GameObject;

/**
 * The type Info tile.
 *
 * @author Alexander_Galloway 300611406.
 */
public class InfoTile implements WalkableTile {

    private Position position;
    private GameObject gameObject;
    private final String information;

    public InfoTile(GameObject gameObject, Position position, String information) {
        if (position == null) throw new IllegalArgumentException();
        if (information == null) throw new IllegalArgumentException();

        this.position = position;
        this.gameObject = gameObject;
        this.information = information;
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
        return "InfoTile";
    }

    /**
     * Returns the information that we want the user to see when stepped on of the class.
     *
     * @return String .
     */
    public String getInformation() {
        return information;
    }

    /**
     * Returns GameObject that a tile may be holding.
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
