package nz.ac.vuw.ecs.swen225.gp21.domain.board;

import nz.ac.vuw.ecs.swen225.gp21.domain.actor.Actor;
import nz.ac.vuw.ecs.swen225.gp21.domain.actor.Player;

import java.awt.*;

public class Item_Key extends Item {

    private String color;

    /**
     * Constructor initializes type KEY and impassable = false
     */
    public Item_Key(String color) {
        super(ItemType.KEY, false);
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    /**
     * Interact void fires upon player entering the cell
     *
     * Key is added to inventory
     */
    @Override
    public void interact(Actor actor) {

        // try to cast Actor to Player
        if (actor instanceof Player) {

            Player player = (Player)actor;

            // add to inventory
            player.addToInventory(this);

            // remove from board
            // TODO
        }

    }
}