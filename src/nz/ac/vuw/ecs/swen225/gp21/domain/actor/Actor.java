package nz.ac.vuw.ecs.swen225.gp21.domain.actor;

import nz.ac.vuw.ecs.swen225.gp21.domain.utils.Coordinate;
import nz.ac.vuw.ecs.swen225.gp21.domain.utils.Direction;

/**
 * Actor is the interface that movable actors in the levels will inherit
 */
public abstract class Actor {

    private Coordinate position;

    public Actor(Coordinate initial) {
        this.position = initial;
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
                return new Coordinate(position.getX() + 1, position.getY());
            case SOUTH:
                return new Coordinate(position.getX(), position.getY() + 1);
            case WEST:
                return new Coordinate(position.getX() - 1, position.getY());
            case NORTH:
                return new Coordinate(position.getX(), position.getY() - 1);
        }

        return null;
    }

    /**
     * setter for the object's location.
     *
     * @param position
     */
    public void setPosition(Coordinate position) {
        this.position = position;
    }

    /**
     * getter for the object's location.
     *
     * @return position
     */
    public Coordinate getPosition() {
        return this.position;
    }


    /**
     * Return a string for the console based version of this board.
     *
     * @return
     */
    public String consoleString() {
        return "\uD83D\uDC64";
    }
}
