package nz.ac.vuw.ecs.swen225.gp21.domain.utils;

public enum Direction {
    /**
     * Using base Swing coordinate design.
     *
     * North is -1 y
     * East is +1 x
     * South is +1 y
     * West is -1 x
     */
    NORTH, EAST, SOUTH, WEST;

    /**
     * Simple parsing method from string to direction.
     *
     * @param direction
     *
     * @return
     *
     */
    public static Direction parseDirection(String direction) {
        switch (direction) {
            case "north":
                return NORTH;
            case "east":
                return EAST;
            case "south":
                return SOUTH;
            case "west":
                return WEST;
            default:
                return null;
        }
    }


    /**
     * Determine a direction from the difference of two coordinates.
     *
     * @param origin - origin
     *
     * @param ending - final
     *
     * @return
     *
     */
    public static Direction facingFromPositionChange(Coordinate origin, Coordinate ending) {

        if (origin == ending) return null;

        // handle no change
        if (origin.getY() == ending.getY() && origin.getX() == ending.getX()) {
            return null;
        }
        // handle X axis motion left
        else if (origin.getY() == ending.getY() && origin.getX() > ending.getX()) {
            return WEST;
        }
        // handle X axis motion right
        else if (origin.getY() == ending.getY() && origin.getX() < ending.getX()) {
            return EAST;
        }
        // handle Y axis motion down
        else if (origin.getY() > ending.getY() && origin.getX() == ending.getX()) {
            return NORTH;
        }
        // handle Y axis motion up
        else if (origin.getY() < ending.getY() && origin.getX() == ending.getX()) {
            return SOUTH;
        }
        // handle two axis motion
        else if (origin.getX() != ending.getX() && origin.getY() != ending.getY()) {

            // calculate the greater difference to determine the facing
            if (Math.abs(origin.getX() - ending.getX()) > Math.abs(origin.getY() - ending.getY())) {
                // X axis facing
                origin.setY(ending.getY());
            } else {
                // Y axis facing
                origin.setX(ending.getX());
            }
            return facingFromPositionChange(origin , ending);
        }

        return null;
    }
}

