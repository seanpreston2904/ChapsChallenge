package nz.ac.vuw.ecs.swen225.gp21.domain.board;

import nz.ac.vuw.ecs.swen225.gp21.domain.actor.Actor;
import nz.ac.vuw.ecs.swen225.gp21.domain.actor.Player;
import nz.ac.vuw.ecs.swen225.gp21.domain.utils.ItemType;

public class Item_Info extends Item {

    private String info;

    /**
     * Constructor initializes type TREASURE and impassable = false.
     *
     * We also initialize with a special criteria "oneTimeUse = false" which means it is not removed.
     */
    public Item_Info(String info) {
        super(ItemType.INFO, false, false);
        this.info = info;
    }

    /**
     * Getter for info string.
     */
    public String getInfo() {
        return info;
    }


    /**
     * Interact void fires upon player entering the cell.
     *
     * Info blurb pops up.
     */
    @Override
    public void interact(Actor actor) {

        // try to cast Actor to Player
        if (actor instanceof Player) {

            Player player = (Player)actor;

            // display the info as a popup
            player.setInfoMessage(info);
        }

    }

    /**
     * Return a string for the console based version of this board.
     *
     * @return
     */
    public String consoleString() {
        return "i|";
    }
}
