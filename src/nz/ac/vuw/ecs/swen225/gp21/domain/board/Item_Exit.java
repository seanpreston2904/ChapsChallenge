package nz.ac.vuw.ecs.swen225.gp21.domain.board;

import nz.ac.vuw.ecs.swen225.gp21.domain.actor.Actor;
import nz.ac.vuw.ecs.swen225.gp21.domain.actor.Player;
import nz.ac.vuw.ecs.swen225.gp21.domain.utils.ItemType;

public class Item_Exit extends Item implements PreMove {

    /**
     * Constructor initializes type LOCK_EXIT and impassable = true.
     */
    public Item_Exit() {
        super(ItemType.LOCK_EXIT, true);
    }


    @Override
    public void interact(Actor actor) {}

    /**
     * PreInteract void fires upon player attempting to enter the cell.
     * @return
     */
    @Override
    public boolean preInteract(Actor actor) {

        // check if it is a player
        if (actor instanceof Player) {

            Player player = (Player)actor;

            // check the treasure count
            // TODO
            if (true) {

                // remove door from board
                // TODO
                return true;
            }
        }

        return false;
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
