package nz.ac.vuw.ecs.swen225.gp21.domain.board;

import nz.ac.vuw.ecs.swen225.gp21.domain.actor.Actor;
import nz.ac.vuw.ecs.swen225.gp21.domain.actor.Player;
import nz.ac.vuw.ecs.swen225.gp21.domain.utils.ItemType;

import java.util.ArrayList;

public class Item_Door extends Item implements PreMove {

    private String color;

    /**
     * Constructor initializes type LOCK_DOOR and impassable = true.
     */
    public Item_Door(String color) {
        super(ItemType.LOCK_DOOR, true);
        this.color = color;
    }

    /**
     * Get color.
     */
    public String getColor() {
        return color;
    }


    @Override
    public void interact(Actor actor) {}

    /**
     * PreInteract void fires upon player attempting to enter the cell.
     * @return
     */
    @Override
    public boolean preInteract(Board board, ArrayList<Actor> enemies, Actor actor) {

        // try to cast Actor to Player
        if (actor instanceof Player) {

            Player player = (Player)actor;

            // try to get a key
            if (player.removeFromInventory(new Item_Key(color).getId())) {

                // remove door from board
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Item{" +
                "type=" + ItemType.LOCK_DOOR + "," + color +
                '}';
    }

    /**
     * Return a string for the console based version of this board.
     *
     * @return
     */
    public String consoleString() {
        return "\uD83D\uDEAA|";
    }
}
