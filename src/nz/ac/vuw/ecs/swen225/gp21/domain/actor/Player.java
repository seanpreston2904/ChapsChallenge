package nz.ac.vuw.ecs.swen225.gp21.domain.actor;

import nz.ac.vuw.ecs.swen225.gp21.domain.board.Item;
import nz.ac.vuw.ecs.swen225.gp21.domain.utils.Coordinate;

import java.util.ArrayList;

/**
 * Player class is a type of actor which represents the players pawn.
 */
public class Player extends Actor {

    private ArrayList<Item> inventory;
    private int treasure;

    public Player(Coordinate initial) {
        super(initial);
        inventory = new ArrayList<>();
    }

    /**
     * Utility method for setting items.
     *
     * @param item - the item.
     */
    public void addToInventory(Item item) {
        inventory.add(item);
    }

    /**
     * Getter method items.
     *
     * @return all items
     */
    public ArrayList<Item> getInventory() {
        return inventory;
    }

    /**
     * Utility method for removing items.
     *
     * @param itemID - the item's id to be found.
     *
     * @return - true ONLY if it was removed.
     *
     */
    public boolean removeFromInventory(String itemID) {

        // loop through to find and remove
        for (Item item : inventory) {
            if (item.getId().equals(itemID)) {
                return inventory.remove(item);
            }
        }

        return false;
    }

    /**
     * Getter for the actor's name.
     */
    @Override
    public String getName() {
        return "hero";
    }

    /**
     * Getter for treasure count.
     */
    public int getTreasure() {
        return treasure;
    }

    /**
     * Setter for treasure count.
     */
    public void setTreasure(int treasure) {
        this.treasure = treasure;
    }
}
