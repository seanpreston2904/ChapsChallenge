package nz.ac.vuw.ecs.swen225.gp21.domain.board;

import nz.ac.vuw.ecs.swen225.gp21.domain.actor.Actor;
import nz.ac.vuw.ecs.swen225.gp21.domain.actor.Player;
import nz.ac.vuw.ecs.swen225.gp21.domain.utils.ItemType;

import java.util.ArrayList;

public class Item_Exit extends Item implements PreMove {

    private int treasure;

    /**
     * Constructor initializes type LOCK_EXIT and impassable = true.
     */
    public Item_Exit(int treasure) {
        super(ItemType.LOCK_EXIT, true);

        this.treasure = treasure;
    }


    @Override
    public void interact(Actor actor) {}

    /**
     * PreInteract void fires upon player attempting to enter the cell.
     * @return
     */
    @Override
    public boolean preInteract(Board board, ArrayList<Actor> enemies, Actor actor) {

        // check if it is a player
        if (actor instanceof Player) {

            Player player = (Player)actor;

            // check the treasure count
            if (player.getTreasure() == treasure) {

                // remove door from board
                return true;
            }
        }

        return false;
    }

    /**
     * Getter method for treasure.
     *
     * @return
     */
    public int getTreasure() {
        return treasure;
    }

    /**
     * Return a string for the console based version of this board.
     *
     * @return
     */
    public String consoleString() {
        return "x|";
    }
}
