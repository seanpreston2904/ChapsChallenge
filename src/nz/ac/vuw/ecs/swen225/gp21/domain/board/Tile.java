package nz.ac.vuw.ecs.swen225.gp21.domain.board;

import nz.ac.vuw.ecs.swen225.gp21.domain.utils.Coordinate;

import java.awt.*;

public class Tile {

    public enum TileType {
        WALL, FREE, EXIT
    }

    private Coordinate location;
    private TileType type;
    private Item item;

    /**
     * Constructor
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
     * getter for location
     */
    public Coordinate getLocation() {
        return location;
    }

    /**
     * setter for location
     */
    public void setLocation(Coordinate location) {
        this.location = location;
    }

    /**
     * getter for type
     */
    public TileType getType() {
        return type;
    }

    /**
     * setter for type
     */
    public void setType(TileType type) {
        this.type = type;
    }

    /**
     * getter for item
     */
    public Item getItem() {
        return item;
    }

    /**
     * setter for item
     */
    public void setItem(Item item) {
        this.item = item;
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
