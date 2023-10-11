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

import java.util.Arrays;
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
    if (position == null) {
      throw new IllegalArgumentException();
    }
    if (inventory == null) {
      throw new IllegalArgumentException();
    }

    this.position = position;
    this.inventory = inventory;
    this.treasuresLeft = treasuresLeft;
  }

  /**
   * This code implements the movement functionality of any actors/enemy's on the board.
   * This however is not very robust, it checks if a tile is walkable,
   * if so it'll attempt to walk over it. Thus, it does not take into account.
   * that a tile could contain another object and will not check for it.
   * Only checking if it's crashing into the player.
   *
   * @param board     includes non-updated board.
   * @param direction includes moving direction.
   * @return Board that has been updated.
   */
  public InformationPacket move(Board board, Direction direction) {
    // Holds the infoPacket we will return to Domain
    InformationPacket infoPacket = null;

    // Pre-Checks
    if (board == null) {
      throw new IllegalArgumentException();
    }
    if (direction == null) {
      throw new IllegalArgumentException();
    }

    int[] directionOffset = convertIntTo2Dspace(direction);

    // Get 2d array from board and check that we aren't attempting to move outside it's bounds
    // the if not get the Tile we are attempting to move to
    Tile[][] newBoard = board.getBoard();
    assert  newBoard != null : "getBoard() failed";

    if (position.x() + directionOffset[0] >= newBoard.length || position.x() + directionOffset[0] < 0 || position.y() + directionOffset[1] >= newBoard[0].length || position.y() + directionOffset[1] < 0) {
      return new InformationPacket(board, false, true, false, null);
    }
    Tile moveToTile = newBoard[position.x() + directionOffset[0]][position.y() + directionOffset[1]];


    if (!(moveToTile instanceof WalkableTile) && !(moveToTile instanceof Wall)) {
      // Attempt to walk through a non-walkable tile that isn't wall
      infoPacket = tryWalkThroughNonWalkableTile(moveToTile, newBoard, board, directionOffset);

      // If player wasn't able to walk through the non-walkableTile return
      // the packet saying player didn't move
      if (!infoPacket.hasPlayerMoved()) {
        return new InformationPacket(board, false, true, false, null);
      }
      // Update board with new board given to us if we could walk through
      board.setBoard(infoPacket.getBoard().getBoard());
      assert Arrays.deepEquals(board.getBoard(), newBoard) : "Board has not updated";

    } else if ((moveToTile instanceof WalkableTile targetTile)) {
      // Attempt to walk through walkable tile,
      // and react on what type of tile we are walking through
      // if we cannot walk through continue as normal, but set player alive to false.
      infoPacket = getContentsOfNextTile(targetTile, board);

      board.setBoard(infoPacket.getBoard().getBoard());
      assert Arrays.deepEquals(board.getBoard(), newBoard) : "Board has not updated";

    } else {
      // This only occurs if we are attempting to walk through a wall
      // thus we cannot do this thus we return infoPacket
      return new InformationPacket(board, false, true, false, null);
    }


    // Using full newBoard as there is no second step, compared to moveToTile.
    ((WalkableTile) newBoard[position.x() + directionOffset[0]][position.y() + directionOffset[1]]).setGameObject(this);
    // We are currently alive, so it is assumed we did the check beforehand.
    ((WalkableTile) newBoard[position.x()][position.y()]).setGameObject(null);

    // Update position of player
    Position oldPos = position;
    position = new Position(position.x() + directionOffset[0], position.y() + directionOffset[1]);
    assert position != oldPos;

    // Set new board
    board.setBoard(newBoard);
    assert Arrays.deepEquals(board.getBoard(), newBoard) : "Board has not updated";

    return new InformationPacket(board, true, infoPacket.isPlayerAlive(), infoPacket.hasPlayerWon(), infoPacket.getTileInformation());
  }

  /**
   * Called when we know that the tile we are attempting to walk through is a WalkableTile.
   * this is done to check which walkable tile it is and then pickup items, kill the player.
   * or win the game, all whilst calling the static playSound method.
   *
   * @return information packet
   */
  private InformationPacket tryWalkThroughNonWalkableTile(Tile targetTile, Tile[][] newBoard, Board board, int[] directionOffset) {
    if (targetTile == null) {
      throw new IllegalArgumentException("");
    }
    if (newBoard == null) {
      throw new IllegalArgumentException("");
    }
    if (board == null) {
      throw new IllegalArgumentException("");
    }
    if (directionOffset == null) {
      throw new IllegalArgumentException("");
    }


    // Boolean used to check if any match occurred and doors where opened
    boolean validMove = false;

    if (targetTile instanceof Door doorTile && inventory.size() > 0) {
      // Moves through all inventory spaces to check if any key matches
      // the one held within the door ahead
      int i = 0;
      for (Item item : inventory) {
        if (item instanceof Key key && doorTile.keyMatch(key)) {
          break;
        }
        i++;
        if (i == inventory.size()) {
          i = -1;
        }
      }

      // If match occurs delete key within the inventory
      if (i != -1) {
        inventory.remove(i);
        validMove = true;
        Renderer.playSound("Door");
      }

    } else if (targetTile instanceof ExitDoor) {
      // Check if all treasures have been picked up,
      // if so play Sound and allow validMove to be true
      if (treasuresLeft == 0) {
        validMove = true;
        Renderer.playSound("Exit_Door");
      }
    }

    // If validMove is true then replace the tile ahead of us with a free tile
    // thus allowing us to move through doors if we have the correct requirements
    if (validMove) {
      Position pos
          = new Position(position.x() + directionOffset[0], position.y() + directionOffset[1]);

      newBoard[position.x() + directionOffset[0]][position.y() + directionOffset[1]]
          = new Free(null, pos);

      board.setBoard(newBoard);
      assert Arrays.deepEquals(board.getBoard(), newBoard) : "Board has not updated";

      return new InformationPacket(board, true, true, false, null);
    }

    return new InformationPacket(board, false, true, false, null);
  }

  /**
   * Called when we know that the tile we are attempting to walk through is a WalkableTile.
   * this is done to check which walkable tile it is and then pickup items, kill the player.
   * or win the game, all whilst calling the static playSound method.
   *
   * @return information packet
   */
  private InformationPacket getContentsOfNextTile(WalkableTile targetTile, Board board) {
    if (targetTile == null) {
      throw new IllegalArgumentException("");
    }
    if (board == null) {
      throw new IllegalArgumentException("");
    }

    // Instantiate all needed holding variables for the Information Packet
    String tileInformation = null;
    boolean playerSurvived = true;
    boolean playerWin = false;

    // If target contains an gameObject, Do something based on what type of gameObject it is.
    if (targetTile.getGameObject() != null) {
      if (targetTile.getGameObject() instanceof Key key) {
        inventory.add(key);
        Renderer.playSound("Key");
        assert inventory.contains(key) : "Inventory failed to update";

      } else if (targetTile.getGameObject() instanceof Treasure) {
        treasuresLeft--;
        Renderer.playSound("Treasure");
        assert treasuresLeft >= 0 : "Treasures cannot be less than zero";

      } else if (targetTile.getGameObject() instanceof Actor) {
        playerSurvived = false;
        Renderer.playSound("Lose");
      }
    }

    // If target is any of the below either win if possible, or die
    if (targetTile instanceof Exit) {
      playerWin = true;
      Renderer.playSound("Win");

    } else if (targetTile instanceof Lava || targetTile instanceof Water) {
      playerSurvived = false;
      Renderer.playSound("Splash");

    } else if (targetTile instanceof InfoTile infoTile) {
      tileInformation = infoTile.getInformation();
      assert tileInformation != null : "TileInformation cannot be null";
      assert !tileInformation.equals("") : "TileInformation cannot be empty";
    }
    return new InformationPacket(board, false, playerSurvived, playerWin, tileInformation);
  }

  /**
   * Converts the enum called direction into an array of [y, x] that we can add.
   * to the current position to get next position for tests.
   *
   * @return int[y,x]
   */
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

  /**
   * Gets inventory.
   *
   * @return the inventory
   */
  public List<Item> getInventory() {
    return inventory;
  }

  /**
   * Gets treasures left.
   *
   * @return the treasures left
   */
  public int getTreasuresLeft() {
    return treasuresLeft;
  }

  @Override
  public Position getPosition() {
    return position;
  }

  @Override
  public void setPosition(Position position) {
    this.position = position;
  }

  @Override
  public String getName() {
    return "Player";
  }
}
