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
  private final String tileInformation;

  /**
   * Instantiates a new Information packet.
   *
   * @param board           the board
   * @param playerMoved     the player moved
   * @param playerAlive     the player alive
   * @param playerWon       the player won
   * @param tileInformation the tile information
   */
  public InformationPacket(Board board, boolean playerMoved, boolean playerAlive, boolean playerWon, String tileInformation) {
    if (board == null) {
      throw new IllegalArgumentException();
    }

    this.board = board;
    this.playerMoved = playerMoved;
    this.playerAlive = playerAlive;
    this.playerWon = playerWon;
    this.tileInformation = tileInformation;
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
   * Has player moved boolean.
   *
   * @return the boolean
   */
  public boolean hasPlayerMoved() {
    return playerMoved;
  }

  /**
   * Is player alive boolean.
   *
   * @return the boolean
   */
  public boolean isPlayerAlive() {
    return playerAlive;
  }

  /**
   * Has player won boolean.
   *
   * @return the boolean
   */
  public boolean hasPlayerWon() {
    return playerWon;
  }

  /**
   * Gets tile information.
   *
   * @return the tile information
   */
  public String getTileInformation() {
    return tileInformation;
  }
}
