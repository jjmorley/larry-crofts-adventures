package nz.ac.wgtn.swen225.lc.domain.gameObject.tile.walkableTile;

import nz.ac.wgtn.swen225.lc.domain.Position;
import nz.ac.wgtn.swen225.lc.domain.gameObject.GameObject;

/**
 * The type Info tile.
 *
 * @author Alexander_Galloway 300611406.
 */
public class InfoTile implements WalkableTile {

  private Position position;
  private GameObject gameObject;
  private final String information;

  /**
   * Instantiates a new Info tile.
   *
   * @param gameObject  the game object
   * @param position    the position
   * @param information the information
   */
  public InfoTile(GameObject gameObject, Position position, String information) {
    if (position == null) {
      throw new IllegalArgumentException();
    }
    if (information == null) {
      throw new IllegalArgumentException();
    }

    this.position = position;
    this.gameObject = gameObject;
    this.information = information;
  }

  /**
   * Returns the information that we want the user to see when stepped on of the class.
   *
   * @return String .
   */
  public String getInformation() {
    return information;
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
    return "InfoTile";
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
