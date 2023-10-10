package nz.ac.wgtn.swen225.lc.domain.gameObject.tile;

import nz.ac.wgtn.swen225.lc.domain.Position;
import nz.ac.wgtn.swen225.lc.domain.gameObject.item.Key;

/**
 * The type Door.
 *
 * @author Alexander_Galloway 300611406.
 */
public class Door implements Tile {

  private Position position;
  private final Key key;

  /**
   * Instantiates a new Door.
   *
   * @param key      the key
   * @param position the position
   */
  public Door(Key key, Position position) {
    if (key == null) {
      throw new IllegalArgumentException();
    }
    if (position == null) {
      throw new IllegalArgumentException();
    }

    this.key = key;
    this.position = position;
  }

  /**
   * Returns true or false, if key matches the doors key.
   *
   * @param key the key
   * @return boolean. boolean
   */
  public boolean keyMatch(Key key) {
    return this.key.getKey() == key.getKey();
  }

  /**
   * Returns an ID that matches a number for a door.
   *
   * @return int Unique ID.
   */
  public int getKeyID() {
    return key.getKey();
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
    return "Door";
  }
}
