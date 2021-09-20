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
}

