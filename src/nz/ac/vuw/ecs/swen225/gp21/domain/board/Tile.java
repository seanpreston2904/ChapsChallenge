package nz.ac.vuw.ecs.swen225.gp21.domain.board;

import nz.ac.vuw.ecs.swen225.gp21.domain.utils.TileType;
import nz.ac.vuw.ecs.swen225.gp21.domain.utils.Coordinate;

public class Tile {

    private Coordinate location;
    private TileType type;
    private Item item;

    /**
     * Constructor.
     */
    public Tile(Coordinate location, TileType type, Item item) {
        this.location = location;
        this.type = type;
        this.item = item;
    }
    public Tile(Coordinate location, TileType type) {
        this.location = location;
        this.type = type;
    }

    /**
     * Getter for location.
     */
    public Coordinate getLocation() {
        return location;
    }

    /**
     * Setter for location.
     */
    public void setLocation(Coordinate location) {
        this.location = location;
    }

    /**
     * Getter for type.
     */
    public TileType getType() {
        return type;
    }

    /**
     * Setter for type.
     */
    public void setType(TileType type) {
        this.type = type;
    }

    /**
     * Getter for item.
     */
    public Item getItem() {
        return item;
    }

    /**
     * Setter for item.
     */
    public void setItem(Item item) {
        this.item = item;
    }

    @Override
    public String toString() {
        return "Tile{" + location +
                ", " + type + '}';
    }
}
