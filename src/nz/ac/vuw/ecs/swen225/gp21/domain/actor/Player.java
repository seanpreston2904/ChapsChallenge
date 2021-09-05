package nz.ac.vuw.ecs.swen225.gp21.domain.actor;

import nz.ac.vuw.ecs.swen225.gp21.domain.board.Item;
import nz.ac.vuw.ecs.swen225.gp21.domain.utils.Coordinate;

import java.util.ArrayList;

public class Player extends Actor {

    private ArrayList<Item> inventory;

    public Player(Coordinate initial) {
        super(initial);
    }

    /**
     * Utility method for setting items
     *
     * @param item
     */
    public void addToInventory(Item item) {
        inventory.add(item);
    }

    /**
     * Utility method for removing items
     *
     * @param itemID
     * @return - returns true ONLY if it was removed
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
}
