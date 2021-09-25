package nz.ac.vuw.ecs.swen225.gp21.domain.board;

import nz.ac.vuw.ecs.swen225.gp21.domain.actor.Actor;
import nz.ac.vuw.ecs.swen225.gp21.domain.actor.Player;
import nz.ac.vuw.ecs.swen225.gp21.domain.utils.Direction;
import nz.ac.vuw.ecs.swen225.gp21.domain.utils.ItemType;
import nz.ac.vuw.ecs.swen225.gp21.domain.utils.TileType;

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
    public boolean preInteract(Board board, Actor actor) {

        // try to cast Actor to Player
        if (actor instanceof Player) {


            Player player = (Player)actor;

            Direction currentPath = player.getFacing(); // the way the block is being pushed

            player.setPosition(player.getResultingLocation(currentPath)); // simulate moving into the square

            Tile initialTile = board.getTile(player.getPosition());
            Tile finalTile = board.getTile(player.getResultingLocation(currentPath));

            // now check if the place the block ends up is free
            if (finalTile.getType().equals(TileType.FREE) && !finalTile.getItem().isImpassable()) {
                // it is free so lets move it
                initialTile.setItem(null);
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
