package nz.ac.vuw.ecs.swen225.gp21.domain.board;

import java.awt.*;

public abstract class Tile {

    public enum TileType {
        WALL, FREE, EXIT
    }

    Dimension location;
    TileType type;

    /**
     * Constructor
     */
    public Tile(Dimension location, TileType type) {
        this.location = location;
        this.type = type;
    }

    /**
     * @return - location and type
     */
    @Override
    public String toString() {
        return "Tile{" + location +
                ", " + type + '}';
    }
}
