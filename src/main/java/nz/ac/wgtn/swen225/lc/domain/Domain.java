package nz.ac.wgtn.swen225.lc.domain;

import nz.ac.wgtn.swen225.lc.domain.gameObject.Moveable.Actor;
import nz.ac.wgtn.swen225.lc.domain.gameObject.Moveable.Direction;
import nz.ac.wgtn.swen225.lc.domain.gameObject.Moveable.Player;

import java.util.List;

/**
 * The type Domain.
 *
 * @author Alexander_Galloway 300611406.
 */
public class Domain {
    private final Player player;
    private Board board;
    private final List<Actor> actors;

    public Domain(Board board, Player player, List<Actor> actors) {
        this.board = board;
        this.player = player;
        this.actors = actors;
    }

    public InformationPacket advanceClock() {
        // Could use streams in this case to make the code shorter, But makes it a pain to read.
        // Seems unnecessary.
        InformationPacket infoPacket = null;
        for (Actor actor : actors) {
            infoPacket = actor.move(board);
            if (infoPacket == null) throw new IllegalArgumentException();

            if (!infoPacket.isPlayerAlive()) return infoPacket;
            board = infoPacket.getBoard();
        }

        if (infoPacket == null) throw new IllegalArgumentException();
        return infoPacket;
    }

    public InformationPacket movePlayer(Direction direction) {
        InformationPacket infoPacket = player.move(board, direction);
        if (infoPacket == null) throw new IllegalArgumentException();

        if (!infoPacket.isPlayerAlive() || !infoPacket.hasPlayerMoved() || infoPacket.hasPlayerWon()) return infoPacket;
        board = infoPacket.getBoard();

        return infoPacket;
    }

    public Player getPlayer() {
        return player;
    }

    public Board getBoard() {
        return board;
    }

    public List<Actor> getActors() {
        return actors;
    }
}
