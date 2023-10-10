package nz.ac.wgtn.swen225.lc.domain.gameObject.Moveable;

import nz.ac.wgtn.swen225.lc.domain.Board;
import nz.ac.wgtn.swen225.lc.domain.InformationPacket;
import nz.ac.wgtn.swen225.lc.domain.Position;
import nz.ac.wgtn.swen225.lc.domain.gameObject.GameObject;
import nz.ac.wgtn.swen225.lc.domain.gameObject.tile.Tile;
import nz.ac.wgtn.swen225.lc.domain.gameObject.tile.walkableTile.WalkableTile;
import nz.ac.wgtn.swen225.lc.renderer.Renderer;

import java.util.Arrays;
import java.util.List;

/**
 * The type Actor.
 *
 * @author Alexander_Galloway 300611406.
 */
public class Actor implements GameObject {

  private final List<Position> route;
  private int positionIndex;

  /**
   * Instantiates a new Actor.
   *
   * @param positionList the position list
   */
  public Actor(List<Position> positionList) {
    if (positionList == null) {
      throw new IllegalArgumentException();
    }

    this.route = positionList;
    positionIndex = 0;
  }

  /**
   * This code implements the movement functionality of any actors/enemy's on the board.
   * This however is not very robust, it checks if a tile is walkable,
   * if so it'll attempt to walk over it. Thus, it does not take into account that a tile
   * could contain another object and will not check for it.
   * Only checking if it's crashing into the player.
   *
   * @param board includes non-updated board.
   * @return Board that has been updated.
   */
  public InformationPacket move(Board board) {
    if (board == null) {
      throw new IllegalArgumentException();
    }
    if (positionIndex + 1 >= route.size()) {
      positionIndex = -1;
    }
    int newIndex = positionIndex + 1;

    Tile[][] newBoard = board.getBoard();
    Tile moveToTile = newBoard[route.get(newIndex).x()][route.get(newIndex).y()];

    // If not a walkableTile something has gone wrong with the map making,
    // throws IllegalArgumentException.
    if (!(moveToTile instanceof WalkableTile walkTile)) {
      throw new IllegalArgumentException();
    }

    // If walkableTile contains a player, the player has been killed.
    // as the actor is stepping on it.
    if (walkTile.getGameObject() instanceof Player) {
      Renderer.playSound("Lose");
      return new InformationPacket(board, false, false, false, null);
    }


    // Using full newBoard as there is no second step, compared to moveToTile.
    ((WalkableTile) newBoard[route.get(newIndex).x()][route.get(newIndex).y()]).setGameObject(this);

    // Work Around but I am ver tired
    int oldIndex = newIndex - 1;
    if (oldIndex == -1) {
      oldIndex = route.size() - 1;
    }
    assert oldIndex !=  newIndex : "Actor must move";

    // We are currently alive, so it is assumed we did the check beforehand.
    ((WalkableTile) newBoard[route.get(oldIndex).x()][route.get(oldIndex).y()]).setGameObject(null);

    // Increase the index of actor.
    positionIndex++;

    board.setBoard(newBoard);
    assert Arrays.deepEquals(board.getBoard(), newBoard) : "Board failed to update";

    return new InformationPacket(board, false, true, false, null);
  }

  /**
   * does as described on the label.
   *
   * @return route returns all positions used in route.
   */
  public List<Position> getRoute() {
    return route;
  }

  /**
   * does as described on the label.
   *
   * @return index returns current number of route.
   */
  public int getPositionIndex() {
    return positionIndex;
  }

  @Override
  public Position getPosition() {
    return route.get(positionIndex);
  }

  @Override
  public void setPosition(Position position) {
    positionIndex++;
  }

  @Override
  public String getName() {
    return "Actor";
  }
}
