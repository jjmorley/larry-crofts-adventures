package nz.ac.wgtn.swen225.lc.domain;

import nz.ac.wgtn.swen225.lc.domain.gameObject.tile.Tile;
import nz.ac.wgtn.swen225.lc.domain.gameObject.tile.walkableTile.WalkableTile;

/**
 * The type Board.
 *
 * @author Alexander_Galloway 300611406.
 */
public class Board {
  private Tile[][] board;
  private final Boolean playerAlive;

  /**
   * Instantiates a new Board.
   *
   * @param board       the board
   * @param playerAlive the player alive
   */
  public Board(Tile[][] board, boolean playerAlive) {
    this.board = board;
    this.playerAlive = playerAlive;
  }

  /**
   * Get board tile [ ] [ ].
   *
   * @return the tile [ ] [ ]
   */
  public Tile[][] getBoard() {
    return board;
  }

  /**
   * Sets board.
   *
   * @param board the board
   */
  public void setBoard(Tile[][] board) {
    this.board = board;
  }

  //debugging
  @Override
  public String toString() {
    //Used mainly for debugging.
    StringBuilder s = new StringBuilder();

    for (Tile[] tiles : board) {
      for (int j = 0; j < board[0].length; j++) {
        if (tiles[j] instanceof WalkableTile walkableTile) {
          if (walkableTile.getGameObject() == null) {
            s.append(walkableTile.getName().split("")[0]).append(" , ");
          } else {
            s.append(walkableTile.getGameObject().getName().split("")[0]).append(" , ");
          }
        } else {
          s.append(tiles[j].getName().split("")[0]).append(" , ");
        }
      }
      s.append("\n");
    }
    return s.toString();
  }
  //debugging
}
