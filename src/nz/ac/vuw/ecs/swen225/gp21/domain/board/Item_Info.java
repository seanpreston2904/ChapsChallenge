package nz.ac.vuw.ecs.swen225.gp21.domain.board;

import nz.ac.vuw.ecs.swen225.gp21.domain.actor.Actor;
import nz.ac.vuw.ecs.swen225.gp21.domain.actor.Player;

public class Item_Info extends Item {

    private String info;

    /**
     * Constructor initializes type TREASURE and impassable = false
     */
    public Item_Info(String info) {
        super(ItemType.INFO, false);
        this.info = info;
    }

    /**
     * Interact void fires upon player entering the cell
     *
     * Treasure is added to treasure stash
     */
    @Override
    public void interact(Actor actor) {

        // try to cast Actor to Player
        if (actor instanceof Player) {

            Player player = (Player)actor;

            // display the info as a popup
            // TODO
        }

    }
}