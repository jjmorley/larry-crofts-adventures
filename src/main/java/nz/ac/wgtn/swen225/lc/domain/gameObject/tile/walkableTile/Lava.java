package nz.ac.wgtn.swen225.lc.domain.gameObject.tile.walkableTile;

import nz.ac.wgtn.swen225.lc.domain.Position;
import nz.ac.wgtn.swen225.lc.domain.gameObject.GameObject;

/**
 * The type Lava.
 *
 * @author Alexander_Galloway 300611406.
 */
public class Lava implements WalkableTile {

  private Position position;
  private GameObject gameObject;

  /**
   * Instantiates a new Lava.
   *
   * @param gameObject the game object
   * @param position   the position
   */
  public Lava(GameObject gameObject, Position position) {
    if (position == null) {
      throw new IllegalArgumentException();
    }

    this.position = position;
    this.gameObject = gameObject;
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
    return "Lava";
  }

  @Override
  public GameObject getGameObject() {
    return gameObject;
  }

  @Override
  public void setGameObject(GameObject gameObject) {
    this.gameObject = gameObject;
  }
}

