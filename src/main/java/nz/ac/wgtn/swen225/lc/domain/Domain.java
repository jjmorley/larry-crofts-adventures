package nz.ac.wgtn.swen225.lc.domain;

import nz.ac.wgtn.swen225.lc.domain.gameObject.Moveable.Actor;
import nz.ac.wgtn.swen225.lc.domain.gameObject.Moveable.Direction;
import nz.ac.wgtn.swen225.lc.domain.gameObject.Moveable.Player;
import nz.ac.wgtn.swen225.lc.domain.gameObject.tile.walkableTile.WalkableTile;

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

  /**
   * Instantiates a new Domain. domain handles all aspects of the game.
   * this moves the actors as well as players. which handle their own
   * movements allowing us to simply to ask them to move themselves.
   *
   * @param board  the board
   * @param player the player
   * @param actors the actors
   */
  public Domain(Board board, Player player, List<Actor> actors) {
    if (board == null) {
      throw new IllegalArgumentException();
    }
    if (player == null) {
      throw new IllegalArgumentException();
    }

    this.board = board;
    this.player = player;
    this.actors = actors;
  }

  /**
   * Advance clock information packet.
   *
   * @return the information packet
   */
  public InformationPacket advanceClock() {
    InformationPacket infoPacket = null;

    // Attempts to move all actors
    for (Actor actor : actors) {
      infoPacket = actor.move(board);

      // if infoPacket is null something has gone wrong,  thus throw IllegalStateException
      if (infoPacket == null) {
        throw new IllegalStateException();
      }
      // if player has died return right away
      if (!infoPacket.isPlayerAlive()) {
        return infoPacket;
      }
      board = infoPacket.getBoard();
    }

    return infoPacket;
  }

  /**
   * Move player information packet.
   *
   * @param direction the direction
   * @return the information packet
   */
  public InformationPacket movePlayer(Direction direction) {
    if (direction == null) {
      throw new IllegalArgumentException();
    }

    // attempts to move the player in specified direction
    InformationPacket infoPacket = player.move(board, direction);

    // if returned null something has gone wrong, thus throw IllegalStateException
    if (infoPacket == null) {
      throw new IllegalStateException();
    }
    board = infoPacket.getBoard();

    return infoPacket;
  }

  /**
   * Gets player.
   *
   * @return the player
   */
  public Player getPlayer() {
    return player;
  }

  /**
   * Gets board.
   *
   * @return the board
   */
  public Board getBoard() {
    return board;
  }

  /**
   * Gets actors.
   *
   * @return the actors
   */
  public List<Actor> getActors() {
    return actors;
  }
}
