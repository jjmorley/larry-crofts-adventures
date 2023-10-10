package nz.ac.wgtn.swen225.lc.domain.gameObject.Moveable;

import nz.ac.wgtn.swen225.lc.domain.Board;
import nz.ac.wgtn.swen225.lc.domain.InformationPacket;
import nz.ac.wgtn.swen225.lc.domain.Position;
import nz.ac.wgtn.swen225.lc.domain.gameObject.GameObject;
import nz.ac.wgtn.swen225.lc.domain.gameObject.item.Item;
import nz.ac.wgtn.swen225.lc.domain.gameObject.item.Key;
import nz.ac.wgtn.swen225.lc.domain.gameObject.item.Treasure;
import nz.ac.wgtn.swen225.lc.domain.gameObject.tile.Door;
import nz.ac.wgtn.swen225.lc.domain.gameObject.tile.ExitDoor;
import nz.ac.wgtn.swen225.lc.domain.gameObject.tile.Tile;
import nz.ac.wgtn.swen225.lc.domain.gameObject.tile.Wall;
import nz.ac.wgtn.swen225.lc.domain.gameObject.tile.walkableTile.*;
import nz.ac.wgtn.swen225.lc.renderer.Renderer;

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
     * @param position      the position.
     * @param inventory     the items carried.
     * @param treasuresLeft treasuresLeft to collect.
     */
    public Player(Position position, List<Item> inventory, int treasuresLeft) {
        if (position == null) throw new IllegalArgumentException();
        if (treasuresLeft == 0) throw new IllegalArgumentException();

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
     * @param board     includes non-updated board.
     * @return Board that has been updated.
     */
    public InformationPacket move(Board board, Direction direction) {
        InformationPacket infoPacket = null;

        if (board == null) throw new IllegalArgumentException();
        if (direction == null) throw new IllegalArgumentException();

        int[] directionOffset = convertIntTo2Dspace(direction);

        Tile[][] newBoard = board.getBoard();
        if (position.x() + directionOffset[0] >= newBoard.length || position.x() + directionOffset[0] < 0 || position.y() + directionOffset[1] >= newBoard[0].length || position.y() + directionOffset[1] < 0) {
            return new InformationPacket(board, false, true, false, null);
        }
        Tile moveToTile = newBoard[position.x() + directionOffset[0]][position.y() + directionOffset[1]];


        if (!(moveToTile instanceof WalkableTile) && !(moveToTile instanceof Wall)) {
            infoPacket = tryWalkThroughNonWalkableTile(moveToTile, newBoard, board, directionOffset);

            if (infoPacket == null) {
                return new InformationPacket(board, false, true, false, null);
            }
            board.setBoard(infoPacket.getBoard().getBoard());

        } else if ((moveToTile instanceof WalkableTile targetTile)) {
            infoPacket = getContentsOfNextTile(targetTile, newBoard, board);


            board.setBoard(infoPacket.getBoard().getBoard());
        } else {
            return new InformationPacket(board, false, true, false, null);
        }


        // Using full newBoard as there is no second step, compared to moveToTile.
        ((WalkableTile) newBoard[position.x() + directionOffset[0]][position.y() + directionOffset[1]]).setGameObject(this);
        // We are currently alive, so it is assumed we did the check beforehand.
        ((WalkableTile) newBoard[position.x()][position.y()]).setGameObject(null);

        position = new Position(position.x()+directionOffset[0], position.y()+directionOffset[1]);

        board.setBoard(newBoard);
        return new InformationPacket(board, true, infoPacket.isPlayerAlive(), infoPacket.hasPlayerWon(), infoPacket.getTileInformation());
    }

    private InformationPacket tryWalkThroughNonWalkableTile(Tile targetTile, Tile[][] newBoard, Board board, int[] directionOffset) {
        boolean validMove = false;

        if (targetTile instanceof Door doorTile && inventory.size()>0) {
            Item validKey = null;

            int i = 0;
            for (Item item : inventory) {
                if (item instanceof Key key && doorTile.keyMatch(key)) {
                    validKey = key;
                    break;
                }
                i++;

                if (i==inventory.size()) i=-1;
            }
            if (i!=-1) inventory.remove(i);

            if (validKey!=null) {
                validMove = true;
                Renderer.playSound("Door");
            }
        } else if (targetTile instanceof ExitDoor) {
            if (treasuresLeft == 0) {
                validMove = true;
                Renderer.playSound("Exit_Door");
            }
        }

        if (validMove) {
            Position pos = new Position(position.x() + directionOffset[0], position.y() + directionOffset[1]);
            newBoard[position.x() + directionOffset[0]][position.y() + directionOffset[1]] = new Free(null, pos);

            board.setBoard(newBoard);
            return new InformationPacket(board, true, true, false, null);
        }

        return null;
    }

    private InformationPacket getContentsOfNextTile(WalkableTile targetTile, Tile[][] newBoard, Board board) {
        String tileInfomation = null;
        boolean playerSurvived = true;
        boolean playerWin = false;

        ((WalkableTile) newBoard[position.x()][position.y()]).setGameObject(null);
        board.setBoard(newBoard);

        if (targetTile.getGameObject() != null) {
            if (targetTile.getGameObject() instanceof Key key) {
                inventory.add(key);
                Renderer.playSound("Key");
            } else if (targetTile.getGameObject() instanceof Treasure) {
                treasuresLeft--;
                Renderer.playSound("Treasure");
            } else if (targetTile.getGameObject() instanceof Actor) {
                playerSurvived = false;
                Renderer.playSound("Lose");
            }
        }

        if (targetTile instanceof Exit) {
            playerWin = true;
            Renderer.playSound("Win");
        } else if (targetTile instanceof Lava || targetTile instanceof Water) {
            playerSurvived = false;
            Renderer.playSound("Splash");
        } else if (targetTile instanceof InfoTile infoTile) {
            tileInfomation = infoTile.getInformation();
        }
        return new InformationPacket(board, false, playerSurvived, playerWin, tileInfomation);
    }

    private int[] convertIntTo2Dspace(Direction direction) {
        int[] space2D = new int[2];
        switch (direction) {
            case UP -> {
                space2D[0] = -1;
            }
            case RIGHT -> {
                space2D[1] = 1;
            }
            case DOWN -> {
                space2D[0] = 1;
            }
            case LEFT -> {
                space2D[1] = -1;
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
