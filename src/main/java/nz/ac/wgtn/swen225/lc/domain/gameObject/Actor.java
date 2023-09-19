package nz.ac.wgtn.swen225.lc.domain.gameObject;

import javafx.geometry.Pos;
import nz.ac.wgtn.swen225.lc.domain.Position;

import java.util.List;

/**
 * The type Actor.
 *
 * @author Alexander_Galloway 300611406.
 */
public class Actor implements GameObject{

    private List<Position> route;
    private int positionIndex;

    public Actor(List<Position> positionList) {
        if (positionList==null) throw new IllegalArgumentException();

        this.route = positionList;
        positionIndex = 0;
    }

    /**
     * Returns the current position of the GameObject
     *
     * @return Position Can be altered.
     */
    @Override
    public Position getPosition() {
        return route.get(positionIndex);
    }

    /**
     * This no longer can change the position, just move the position by one more along its route.
     *
     * @param position new chosen position for game-object.
     */
    @Override
    public void setPosition(Position position) {
        positionIndex++;
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
        return "Actor";
    }
}
