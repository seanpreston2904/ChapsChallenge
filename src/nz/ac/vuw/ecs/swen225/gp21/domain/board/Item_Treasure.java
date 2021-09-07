package nz.ac.vuw.ecs.swen225.gp21.domain.board;

import nz.ac.vuw.ecs.swen225.gp21.domain.actor.Actor;
import nz.ac.vuw.ecs.swen225.gp21.domain.actor.Player;
import nz.ac.vuw.ecs.swen225.gp21.domain.utils.ItemType;

public class Item_Treasure extends Item {

    /**
     * Constructor initializes type TREASURE and impassable = false
     */
    public Item_Treasure() {
        super(ItemType.TREASURE, false);
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

            // add to treasure
            player.setTreasure(player.getTreasure()+1);

        }

    }
}
