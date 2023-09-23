package nz.ac.wgtn.swen225.lc.domain.gameObject;

import nz.ac.wgtn.swen225.lc.domain.Board;
import nz.ac.wgtn.swen225.lc.domain.Position;
import nz.ac.wgtn.swen225.lc.domain.gameObject.tile.Tile;

import java.util.List;

/**
 * The type Actor.
 *
 * @author Alexander_Galloway 300611406.
 */
public class Actor implements GameObject{

    private final List<Position> route;
    private int positionIndex;
    private final Tile replaceWith;

    public Actor(List<Position> positionList, Tile replaceWith) {
        if (positionList==null) throw new IllegalArgumentException();
        if (replaceWith==null) throw new IllegalArgumentException();

        this.route = positionList;
        this.replaceWith =replaceWith;
        positionIndex = 0;
    }

    public Board move (Board board) {
        if (board==null) throw new IllegalArgumentException();
        if (positionIndex++ >= route.size())  positionIndex = -1;

        int newIndex = positionIndex++;
        Tile[][] newBoard = board.getBoard();
        //check if board is not walkable, if not check it's the player. if so return true. else throw error.
        //if the board returns false. increment positionIndex and update board
        if (!newBoard[route.get(newIndex).x()][route.get(newIndex).y()].isWalkable()) {
            if (newBoard[route.get(newIndex).x()][route.get(newIndex).y()] instanceof Player) {
                return null;
            } else throw new IllegalArgumentException();
        }

        newBoard[route.get(newIndex).x()][route.get(newIndex).y()] = replaceWith;
        newBoard[route.get(positionIndex).x()][route.get(positionIndex).y()] = replaceWith;
        positionIndex++;

        board.setBoard(newBoard);
        return board;
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
