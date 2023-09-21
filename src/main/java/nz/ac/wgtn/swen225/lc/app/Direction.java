package nz.ac.wgtn.swen225.lc.app;

import javafx.scene.input.KeyCode;

/**
 * A Direction.
 */
public enum Direction {
  DOWN,
  LEFT,
  RIGHT,
  UP,
  NONE;

  /**
   * Convert an arrow key to a direction.
   *
   * @param key the key pressed.
   * */
  public static Direction keyToDirection(KeyCode key) {
    Direction out;
    switch (key) {
      case UP -> out = UP;
      case DOWN -> out = DOWN;
      case LEFT -> out = LEFT;
      case RIGHT -> out = RIGHT;
      default -> out = NONE;
    }
    return out;
  }
}

