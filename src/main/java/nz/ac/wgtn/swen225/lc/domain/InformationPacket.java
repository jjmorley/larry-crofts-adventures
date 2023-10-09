package nz.ac.wgtn.swen225.lc.domain;

/**
 * The type InformationPacket.
 *
 * @author Alexander_Galloway 300611406.
 */
public class InformationPacket {
    private final Board board;
    private final boolean playerMoved;
    private final boolean playerAlive;
    private final boolean playerWon;

    public InformationPacket(Board board, boolean playerMoved, boolean playerAlive, boolean playerWon) {
        if (board == null) throw new IllegalArgumentException();

        this.board = board;
        this.playerMoved = playerMoved;
        this.playerAlive = playerAlive;
        this.playerWon = playerWon;
    }

    public Board getBoard() {
        return board;
    }

    public boolean hasPlayerMoved() {
        return playerMoved;
    }

    public boolean isPlayerAlive() {
        return playerAlive;
    }

    public boolean hasPlayerWon() {
        return playerWon;
    }
}
