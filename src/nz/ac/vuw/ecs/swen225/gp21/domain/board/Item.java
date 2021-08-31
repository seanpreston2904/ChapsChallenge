package nz.ac.vuw.ecs.swen225.gp21.domain.board;

public abstract class Item {

    public enum ItemType {
        KEY, TREASURE, INFO, EXIT_LOCK
    }

    ItemType type;

    /**
     * Constructor
     */
    public Item(ItemType type) {
        this.type = type;
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
     * set type
     */
    public void setType(ItemType type) {
        this.type = type;
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
