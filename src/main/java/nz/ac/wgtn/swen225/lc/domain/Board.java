package nz.ac.wgtn.swen225.lc.domain;

import nz.ac.wgtn.swen225.lc.domain.gameObject.Player;
import nz.ac.wgtn.swen225.lc.domain.gameObject.tile.Tile;

/**
 * The type Board.
 *
 * @author Alexander_Galloway 300611406.
 */
public class Board {
     private Tile[][] board;
     private Boolean playerAlive;

    public Board(Tile[][] board, boolean playerAlive) {
        this.board = board;
        this.playerAlive = playerAlive;
    }

    public Tile[][] getBoard() {
        return board;
    }

    public void setBoard(Tile[][] board) {
        this.board = board;
    }

    public Boolean getPlayerAlive() {
        return playerAlive;
    }

    public void setPlayerAlive(Boolean playerAlive) {
        this.playerAlive = playerAlive;
    }
}
