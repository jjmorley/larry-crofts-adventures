package nz.ac.wgtn.swen225.lc.domain;

import java.util.Objects;

/**
 * The type Position.
 * Position is an object that holds a fixed X and Y value for our board.
 * New Position object must be made to change X and Y value.
 *
 * @author Alexander_Galloway.
 */
public class Position {
    private final int x;
    private final int y;

    /**
     * Instantiates a new Position.
     *
     * @param x the x.
     * @param y the y.
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Position position)) return false;
        return getX() == position.getX() && getY() == position.getY();
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
        return Objects.hash(getX(), getY());
    }

    /**
     * Gets x.
     *
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * Gets y.
     *
     * @return the y
     */
    public int getY() {
        return y;
    }
}

