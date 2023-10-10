package nz.ac.wgtn.swen225.lc.domain;

import nz.ac.wgtn.swen225.lc.domain.gameObject.tile.Tile;
import nz.ac.wgtn.swen225.lc.domain.gameObject.tile.walkableTile.WalkableTile;

import java.util.Arrays;

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

    //debugging
    @Override
    public String toString() {
        String s = "";

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] instanceof WalkableTile walkableTile) {
                    if (walkableTile.getGameObject()==null) {
                        s+= walkableTile.getName().split("")[0] + " , ";
                    } else {
                        s+= walkableTile.getGameObject().getName().split("")[0] + " , ";
                    }
                } else {
                    s+= board[i][j].getName().split("")[0] + " , ";
                }
            }
            s+="\n";
        }
        return s;
    }
    //debugging
}
