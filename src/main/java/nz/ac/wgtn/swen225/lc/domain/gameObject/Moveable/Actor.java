package nz.ac.wgtn.swen225.lc.domain.gameObject.Moveable;

import nz.ac.wgtn.swen225.lc.domain.Board;
import nz.ac.wgtn.swen225.lc.domain.InformationPacket;
import nz.ac.wgtn.swen225.lc.domain.Position;
import nz.ac.wgtn.swen225.lc.domain.gameObject.GameObject;
import nz.ac.wgtn.swen225.lc.domain.gameObject.tile.Tile;
import nz.ac.wgtn.swen225.lc.domain.gameObject.tile.walkableTile.WalkableTile;

import java.util.List;

/**
 * The type Actor.
 *
 * @author Alexander_Galloway 300611406.
 */
public class Actor implements GameObject {

    private final List<Position> route;
    private int positionIndex;

    public Actor(List<Position> positionList) {
        if (positionList==null) throw new IllegalArgumentException();

        this.route = positionList;
        positionIndex = 0;
    }


    /**
     * This code implements the movement functionality of any actors/enemy's on the board.
     * This however is not very robust, it checks if a tile is walkable, if so it'll attempt to walk over it.
     * Thus, it does not take into account that a tile could contain another object and will not check for it.
     * Only checking if it's crashing into the player.
     *
     * @param board includes non-updated board.
     * @return Board that has been updated.
     */
    public InformationPacket move (Board board) {
        if (board==null) throw new IllegalArgumentException();
        if (positionIndex++ >= route.size())  positionIndex = -1;

        int newIndex = positionIndex++;
        Tile[][] newBoard = board.getBoard();

        Tile moveToTile = newBoard[route.get(newIndex).x()][route.get(newIndex).y()];

        // If not a walkableTile something has gone wrong with the map making, throws IllegalArgumentException.
        if (!(moveToTile instanceof WalkableTile)) {
            throw new IllegalArgumentException();
        }
        // If walkableTile contains a player, the player has been killed. as the actor is stepping on it.
        if (((WalkableTile) moveToTile).getGameObject().getName().equals("Player")) {
            return new InformationPacket(board, false, false);
        }

        // Using full newBoard as there is no second step, compared to moveToTile.
        ((WalkableTile) newBoard[route.get(newIndex).x()][route.get(newIndex).y()]).setGameObject(this);
        // We are currently alive, so it is assumed we did the check beforehand.
        ((WalkableTile) newBoard[route.get(positionIndex).x()][route.get(positionIndex).y()]).setGameObject(null);

        positionIndex++;

        board.setBoard(newBoard);
        return new InformationPacket(board, false, true);
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
     * Returns the name of the class.
     *
     * @return String .
     */
    @Override
    public String getName() {
        return "Actor";
    }
}
