package nz.ac.wgtn.swen225.lc.domain.gameObject.Moveable;

import javafx.geometry.Pos;
import nz.ac.wgtn.swen225.lc.domain.Board;
import nz.ac.wgtn.swen225.lc.domain.InformationPacket;
import nz.ac.wgtn.swen225.lc.domain.Position;
import nz.ac.wgtn.swen225.lc.domain.gameObject.GameObject;
import nz.ac.wgtn.swen225.lc.domain.gameObject.item.Item;
import nz.ac.wgtn.swen225.lc.domain.gameObject.item.Key;
import nz.ac.wgtn.swen225.lc.domain.gameObject.tile.Door;
import nz.ac.wgtn.swen225.lc.domain.gameObject.tile.ExitDoor;
import nz.ac.wgtn.swen225.lc.domain.gameObject.tile.Tile;
import nz.ac.wgtn.swen225.lc.domain.gameObject.tile.Wall;
import nz.ac.wgtn.swen225.lc.domain.gameObject.tile.walkableTile.Free;
import nz.ac.wgtn.swen225.lc.domain.gameObject.tile.walkableTile.WalkableTile;

import java.util.List;

/**
 * The type Player.
 *
 * @author Alexander_Galloway 300611406.
 */
public class Player implements GameObject {
    private Position position;
    private List<Item> inventory;
    private int treasuresLeft;

    /**
     * Instantiates a new Player.
     *
     * @param position the position.
     * @param inventory the items carried.
     * @param treasuresLeft treasuresLeft to collect.
     */
    public Player(Position position, List<Item> inventory, int treasuresLeft) {
        if (position==null) throw new IllegalArgumentException();
        if (treasuresLeft==0) throw new IllegalArgumentException();

        this.position = position;
        this.inventory = inventory;
        this.treasuresLeft = treasuresLeft;
    }

    /**
     * This code implements the movement functionality of any actors/enemy's on the board.
     * This however is not very robust, it checks if a tile is walkable, if so it'll attempt to walk over it.
     * Thus, it does not take into account that a tile could contain another object and will not check for it.
     * Only checking if it's crashing into the player.
     *
     * @param direction includes moving direction.
     * @param board includes non-updated board.
     * @return Board that has been updated.
     */
    public InformationPacket move (Board board, Direction direction) {
        if (board==null) throw new IllegalArgumentException();
        if (direction==null) throw new IllegalArgumentException();

        int[] space2D = convertIntTo2Dspace(direction);

        Tile[][] newBoard = board.getBoard();
        Tile moveToTile = newBoard[position.x()+space2D[0]][position.y()+space2D[1]];


        if (!(moveToTile instanceof WalkableTile) && !(moveToTile instanceof Wall)) {
            // Will need to add check for if the player was killed

            if (moveToTile instanceof Door) {
                int keys = inventory.stream()
                        .filter(item-> item instanceof Key && ((Door) moveToTile).keyMatch((Key) item))
                        .toList().size();

                if (keys>0) {
                    Position pos = new Position(position.x()+space2D[0], position.y()+space2D[1]);
                    newBoard[position.x()+space2D[0]][position.y()+space2D[1]] = new Free(null, pos);
                }
            } else if (moveToTile instanceof ExitDoor) {
                if (treasuresLeft==0) {
                    Position pos = new Position(position.x()+space2D[0], position.y()+space2D[1]);
                    newBoard[position.x()+space2D[0]][position.y()+space2D[1]] = new Free(null, pos);
                }
            }
        } else if (!(moveToTile instanceof WalkableTile))
            {return new InformationPacket(board, false, true);}


        // It likes this asserts to make itself happy, already confirmed it was walkableTile
        assert moveToTile instanceof WalkableTile;
        if (((WalkableTile) moveToTile).getGameObject()!=null) {

            if (((WalkableTile) moveToTile).getGameObject().getName().equals("Key")) {
                inventory.add((Item) ((WalkableTile) moveToTile).getGameObject());
                ((WalkableTile) newBoard[position.x()+space2D[0]][position.y()+space2D[1]]).setGameObject(null);

            } else if (((WalkableTile) moveToTile).getGameObject().getName().equals("Treasure")) {
                treasuresLeft--;
                ((WalkableTile) newBoard[position.x()+space2D[0]][position.y()+space2D[1]]).setGameObject(null);

            } else if (((WalkableTile) moveToTile).getGameObject().getName().equals("Actor")) {
                ((WalkableTile) newBoard[position.x()][position.y()]).setGameObject(null);
                board.setBoard(newBoard);
                return new InformationPacket(board, false, false);
            }
        }

        // Using full newBoard as there is no second step, compared to moveToTile.
        ((WalkableTile) newBoard[position.x()+space2D[0]][position.y()+space2D[1]]).setGameObject(this);
        // We are currently alive, so it is assumed we did the check beforehand.
        ((WalkableTile) newBoard[position.x()][position.y()]).setGameObject(null);


        board.setBoard(newBoard);
        return new InformationPacket(board, false, true);
    }

    public int[] convertIntTo2Dspace (Direction direction) {
        int[] space2D = new int[2];
        switch (direction) {
            case UP -> {
                space2D[1] = 1;
            }
            case RIGHT -> {
                space2D[0] = 1;
            }
            case DOWN -> {
                space2D[1] = -1;
            }
            case LEFT -> {
                space2D[0] = -1;
            }
            default -> throw new IllegalArgumentException();
        }
        return space2D;
    }

    public List<Item> getInventory() {
        return inventory;
    }

    public int getTreasuresLeft() {
        return treasuresLeft;
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
