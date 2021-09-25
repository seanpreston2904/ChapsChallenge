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
     * @param a - origin
     *
     * @param b - final
     *
     * @return
     *
     */
    public static Direction facingFromPositionChange(Coordinate a, Coordinate b) {

        if (a == b) return null;

        // handle no change
        if (a.getY() == b.getY() && a.getX() == b.getX()) {
            return null;
        }
        // handle Y axis motion left
        else if (a.getY() == b.getY() && a.getX() > b.getX()) {
            return WEST;
        }
        // handle Y axis motion right
        else if (a.getY() == b.getY() && a.getX() < b.getX()) {
            return EAST;
        }
        // handle X axis motion down
        else if (a.getY() > b.getY() && a.getX() == b.getX()) {
            return SOUTH;
        }
        // handle X axis motion up
        else if (a.getY() < b.getY() && a.getX() == b.getX()) {
            return NORTH;
        }
        // handle two axis motion
        else if (a.getX() != b.getX() && a.getY() != b.getY()) {

            // calculate the greater difference to determine the facing
            if (Math.abs(a.getX() - b.getX()) > Math.abs(a.getY() - b.getY())) {
                // X axis facing
                a.setY(b.getY());
            } else {
                // Y axis facing
                a.setX(b.getX());
            }
            return facingFromPositionChange(a , b);
        }

        return null;
    }
}

