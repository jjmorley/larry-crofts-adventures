package nz.ac.wgtn.swen225.lc.domain.gameObject.tile;

import nz.ac.wgtn.swen225.lc.domain.Position;

/**
 * The type Exit door.
 *
 * @author Alexander_Galloway 300611406.
 */
public class ExitDoor implements Tile {

  private Position position;

  /**
   * Instantiates a new Exit door.
   *
   * @param position the position
   */
  public ExitDoor(Position position) {
    if (position == null) {
      throw new IllegalArgumentException();
    }

    this.position = position;
  }

  @Override
  public Position getPosition() {
    return position;
  }

  @Override
  public void setPosition(Position position) {
    this.position = position;
  }

  @Override
  public String getName() {
    return "ExitDoor";
  }
}
