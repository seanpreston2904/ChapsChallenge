package nz.ac.vuw.ecs.swen225.gp21.domain.utils;

public class Coordinate {

    private int x;
    private int y;

    /**
     * A coordinate is a simple metric containing and x and y coordinate
     * @param x
     * @param y
     */
    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Getter for X
     */
    public int getX() {
        return x;
    }

    /**
     * Setter for X
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Getter for Y
     */
    public int getY() {
        return y;
    }

    /**
     * Setter for Y
     */
    public void setY(int y) {
        this.y = y;
    }


    /**
     * to String method including x and y
     */
    @Override
    public String toString() {
        return "{" + x +
                ", " + y + '}';
    }
}
