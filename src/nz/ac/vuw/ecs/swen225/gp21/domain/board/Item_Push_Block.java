package nz.ac.vuw.ecs.swen225.gp21.domain.board;

import nz.ac.vuw.ecs.swen225.gp21.domain.actor.Actor;
import nz.ac.vuw.ecs.swen225.gp21.domain.actor.Player;
import nz.ac.vuw.ecs.swen225.gp21.domain.utils.Coordinate;
import nz.ac.vuw.ecs.swen225.gp21.domain.utils.Direction;
import nz.ac.vuw.ecs.swen225.gp21.domain.utils.ItemType;
import nz.ac.vuw.ecs.swen225.gp21.domain.utils.TileType;

import java.util.ArrayList;

public class Item_Push_Block extends Item implements PreMove {

    /**
     * Constructor initializes type PUSH_BLOCK and impassable = true.
     */
    public Item_Push_Block() {
        super(ItemType.PUSH_BLOCK, true);
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

            Player player = new Player(actor.getPosition());

            // NOTE: there is an expectation that actor.getFacing actually points towards this item. If this is not true it will err.
            Direction currentPath = actor.getFacing();

            // load the coordinates for the item, the actor and the final tile
            Coordinate startingCoordinate = actor.getPosition();
            Coordinate itemCoordinate = startingCoordinate.getResultingLocation(currentPath);
            Coordinate finalCoordinate = itemCoordinate.getResultingLocation(currentPath);

            player.setPosition(player.getResultingLocation(currentPath)); // simulate moving into the square

            Tile finalTile = board.getTile(finalCoordinate);

            // now check if the place the block ends up is free
            if (board.validMove(enemies, finalCoordinate, new Actor(itemCoordinate) {}))
            {
                //System.out.println("Apparently its legal to go into: " + finalCoordinate + " from " + itemCoordinate + " from " + actor.getPosition());

                // it is free so lets move it
                board.getTile(itemCoordinate).setItem(null);
                finalTile.setItem(this);

                // and we can move
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Item{" +
                "type=" + ItemType.PUSH_BLOCK +
                '}';
    }

    /**
     * Return a string for the console based version of this board.
     *
     * @return
     */
    public String consoleString() {
        return "❒|";
    }
}
