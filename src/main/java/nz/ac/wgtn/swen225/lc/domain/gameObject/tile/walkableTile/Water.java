package nz.ac.wgtn.swen225.lc.domain.gameObject.tile.walkableTile;

import nz.ac.wgtn.swen225.lc.domain.Position;
import nz.ac.wgtn.swen225.lc.domain.gameObject.GameObject;

/**
 * The type Water.
 *
 * @author Alexander_Galloway 300611406.
 */
public class Water implements WalkableTile {

  private Position position;
  private GameObject gameObject;

  /**
   * Instantiates a new Water.
   *
   * @param gameObject the game object
   * @param position   the position
   */
  public Water(GameObject gameObject, Position position) {
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
    return "Water";
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

