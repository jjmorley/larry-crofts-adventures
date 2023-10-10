package nz.ac.wgtn.swen225.lc.domain.gameObject.tile;

import nz.ac.wgtn.swen225.lc.domain.Position;

/**
 * The type Wall.
 *
 * @author Alexander_Galloway 300611406.
 */
public class Wall implements Tile {

  private Position position;

  /**
   * Instantiates a new Wall.
   *
   * @param position the position
   */
  public Wall(Position position) {
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
    return "Wall";
  }
}
