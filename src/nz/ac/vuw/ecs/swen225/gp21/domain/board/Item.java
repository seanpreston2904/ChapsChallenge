package nz.ac.vuw.ecs.swen225.gp21.domain.board;

import nz.ac.vuw.ecs.swen225.gp21.domain.actor.Actor;

public abstract class Item {

    public enum ItemType {
        KEY, TREASURE, INFO, LOCK_DOOR, LOCK_EXIT
    }

    private ItemType type;
    private boolean impassable;

    /**
     * Constructor
     */
    public Item(ItemType type, boolean impassable) {
        this.type = type;
        this.impassable = impassable;
    }

    /**
     * The key method of the item class is what happens when you interact. This is declared in the objects
     */
    public abstract void interact(Actor actor);

    /**
     * In order to locate items in the inventory we need n ID
     */
    public String getId() {
        return "ID: " + type;
    }

    /**
     * get type
     */
    public ItemType getType() {
        return type;
    }

    /**
     * get whether you can move through
     * @return
     */
    public boolean isImpassable() {
        return impassable;
    }


    /**
     * @return type
     */
    @Override
    public String toString() {
        return "Item{" +
                "type=" + type +
                '}';
    }
}
