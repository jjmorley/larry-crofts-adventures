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
   * Instantiates a new Domain.
   *
   * @param board  the board
   * @param player the player
   * @param actors the actors
   */
  public Domain(Board board, Player player, List<Actor> actors) {
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
    for (Actor actor : actors) {
      infoPacket = actor.move(board);
 
      if (infoPacket == null) {
        throw new IllegalArgumentException();
      }
      if (!infoPacket.isPlayerAlive()) {
        return infoPacket;
      }
      board = infoPacket.getBoard();
    }

    if (infoPacket == null) {
      throw new IllegalArgumentException();
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
    InformationPacket infoPacket = player.move(board, direction);

    if (infoPacket == null) {
      throw new IllegalArgumentException();
    }
    if (!infoPacket.isPlayerAlive() || !infoPacket.hasPlayerMoved() || infoPacket.hasPlayerWon()) {
      return infoPacket;
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
