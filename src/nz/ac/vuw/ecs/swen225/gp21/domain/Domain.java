package nz.ac.vuw.ecs.swen225.gp21.domain;

import nz.ac.vuw.ecs.swen225.gp21.domain.actor.Player;
import nz.ac.vuw.ecs.swen225.gp21.domain.board.Board;
import nz.ac.vuw.ecs.swen225.gp21.domain.board.Tile;
import nz.ac.vuw.ecs.swen225.gp21.domain.utils.Coordinate;
import nz.ac.vuw.ecs.swen225.gp21.domain.utils.Direction;

public class Domain {


    //   BOARD   //
    // Board object
    Board board;
    // Player object
    Player hero;

    /**
     * The Domain initializes with a board from Persistence, this is the only req. for the Domain to launch
     */
    public Domain(Board board) {
        this.board = board;
        hero = new Player(board.getPlayerStartPosition());
    }

    //  GAME LOGIC //
    // what events can and cannot happen is controlled by the following methods

    /**
     * Core method that handles the player movement and checks for exceptions
     *
     * @param direction
     */
    public void moveActor(Direction direction) {

        // precondition checks
        if (direction == null) {
            throw new NullPointerException("Move Actor Direction is Null");
        }

        Coordinate moveToCoordinate = hero.getResultingLocation(direction);

        // checks on the moveto location
        if (board.validMove(moveToCoordinate, hero)) {
            throw new IllegalArgumentException("chap cannot be moved into this position");
        }

        // if we get here then we move the actor
        hero.setPosition(moveToCoordinate);

        interactWithItem();
    }



    // INVENTORY //
    // - treasure
    // - keys
    // - other ... ?

    /**
     * When you step on an item you interact with the item
     */
    public void interactWithItem() {
        Coordinate current = hero.getPosition();

        if (current == null) {
            throw new NullPointerException("Illegal Hero State");
        }

        Tile currentTile = board.getTile(current);

        if (currentTile == null) {
            throw new NullPointerException("Illegal Tile");
        }
        if (currentTile.getItem() == null) {
            throw new IllegalStateException("Invalid Retrieve Item State");
        }

        // the hero uses the item
        currentTile.getItem().interact(hero);

        // if it has no repeat uses
        if (currentTile.getItem().isOneTimeUse()) {
            // remove item
            currentTile.setItem(null);
        }
    }


}
