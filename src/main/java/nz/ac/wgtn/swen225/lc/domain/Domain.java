package nz.ac.wgtn.swen225.lc.domain;

import nz.ac.wgtn.swen225.lc.domain.gameObject.Actor;
import nz.ac.wgtn.swen225.lc.domain.gameObject.Player;

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

    public Domain (Board board, Player player, List<Actor> actors) {
        this.board = board;
        this.player = player;
        this.actors = actors;
    }

    public void advanceClock () {
        for (Actor actor : actors) {
            board = actor.move(board);
        }
    }

    public void movePlayer () {
        board = player;
    }

    public Player getPlayer() {
        return player;
    }

    public Board getBoard() {
        return board;
    }
}
