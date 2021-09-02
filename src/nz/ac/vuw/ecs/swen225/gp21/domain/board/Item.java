package nz.ac.vuw.ecs.swen225.gp21.domain.board;

public abstract class Item {

    public enum ItemType {
        KEY, TREASURE, INFO, EXIT_LOCK, DOOR_LOCK
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
    public abstract void interact();

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
