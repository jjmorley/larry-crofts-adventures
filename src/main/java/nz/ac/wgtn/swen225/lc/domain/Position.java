package nz.ac.wgtn.swen225.lc.domain;

import java.util.Objects;

/**
 * The type Position.
 * Position is an object that holds a fixed X and Y value for our board.
 * New Position object must be made to change X and Y value.
 *
 * @author Alexander_Galloway 300611406.
 */
public record Position(int x, int y) {
  /**
   * Instantiates a new Position.
   *
   * @param x the x.
   * @param y the y.
   */
  public Position {
    if (x < 0) throw new IllegalArgumentException();
    if (y < 0) throw new IllegalArgumentException();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Position position)) return false;
    return x() == position.x() && y() == position.y();
  }

  @Override
  public String toString() {
    return "Position{" +
        "x=" + x +
        ", y=" + y +
        '}';
  }

  @Override
  public int hashCode() {
    return Objects.hash(x(), y());
  }

  /**
   * Gets x.
   *
   * @return the x
   */
  @Override
  public int x() {
    return x;
  }

  /**
   * Gets y.
   *
   * @return the y
   */
  @Override
  public int y() {
    return y;
  }
}

