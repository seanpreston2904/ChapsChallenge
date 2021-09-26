package nz.ac.vuw.ecs.swen225.gp21.domain.utils;

public class Coordinate {

    private int x;
    private int y;

    /**
     * A coordinate is a simple metric containing and x and y coordinate.
     * @param x
     * @param y
     */
    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Getter for X.
     */
    public int getX() {
        return x;
    }

    /**
     * Setter for X.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Getter for Y.
     */
    public int getY() {
        return y;
    }

    /**
     * Setter for Y.
     */
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "{" + x +
                ", " + y + '}';
    }

    /**
     * Utility method to move the actor a certain direction and return its resulting position.
     *
     * @param direction the direction to move to
     *
     * @return
     *
     */
    public Coordinate getResultingLocation(Direction direction) {

        // in favor of the traditional and confusing Swing system (south is +1)
        switch (direction) {
            case EAST:
                return new Coordinate(getX() + 1, getY());
            case SOUTH:
                return new Coordinate(getX(), getY() + 1);
            case WEST:
                return new Coordinate(getX() - 1, getY());
            case NORTH:
                return new Coordinate(getX(), getY() - 1);
        }

        return null;
    }
}
