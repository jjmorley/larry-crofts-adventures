package nz.ac.wgtn.swen225.lc.domain.gameObject.tile.walkableTile;

import javafx.geometry.Pos;
import nz.ac.wgtn.swen225.lc.domain.Position;
import nz.ac.wgtn.swen225.lc.domain.gameObject.GameObject;
import nz.ac.wgtn.swen225.lc.domain.gameObject.tile.Tile;

/**
 * The type Free.
 *
 * @author Alexander_Galloway 300611406.
 */
public class Free implements WalkableTile {

  private Position position;
  private GameObject gameObject;

  /**
   * Instantiates a new Free.
   *
   * @param gameObject the game object
   * @param position   the position
   */
  public Free(GameObject gameObject, Position position) {
    if (position == null) {
      throw new IllegalArgumentException();
    }

    this.gameObject = gameObject;
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
    return "Free";
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
