package nz.ac.vuw.ecs.swen225.gp21.domain;

import nz.ac.vuw.ecs.swen225.gp21.domain.actor.Actor;
import nz.ac.vuw.ecs.swen225.gp21.domain.actor.Player;
import nz.ac.vuw.ecs.swen225.gp21.domain.board.Board;
import nz.ac.vuw.ecs.swen225.gp21.domain.board.Tile;
import nz.ac.vuw.ecs.swen225.gp21.domain.utils.Coordinate;
import nz.ac.vuw.ecs.swen225.gp21.domain.utils.Direction;

import java.util.Random;

/**
 * The effective game class.
 */
public class Domain {


    //   BOARD   //
    // Board object
    Board board;
    // Player object
    Player hero;

    /**
     * The Domain initializes with a board from Persistence, this is the only req. for the Domain to launch.
     */
    public Domain(Board board) {
        this.board = board;
        hero = new Player(board.getPlayerStartPosition());
    }

    //  GAME LOGIC //
    // what events can and cannot happen is controlled by the following methods

    /**
     * Core method that handles the actor movement and checks for exceptions.
     *
     * @param actor
     * @param direction
     */
    public void moveActor(Actor actor, Direction direction) {

        // precondition checks
        if (direction == null) {
            throw new NullPointerException("Move Actor Direction is Null");
        }

        Coordinate moveToCoordinate = actor.getResultingLocation(direction);

        // checks on the move to location
        if (board.validMove(moveToCoordinate, actor)) {
            throw new IllegalArgumentException("chap cannot be moved into this position");
        }

        // if we get here then we move the actor
        actor.setPosition(moveToCoordinate);

        // call the item's interact event
        interactWithItem(actor);
    }

    /**
     * Side method that handles random movement.
     *
     * This is called by a tick event in app for other actors/enemies.
     *
     * @param actor
     */
    public void randomlyMoveActor(Actor actor) {

        moveActor(actor,
                Direction.values()[
                                    new Random().nextInt(Direction.values().length) // randomly choose a direction
                                ]);

    }



    // INVENTORY //
    // - treasure
    // - keys
    // - other ... ?

    /**
     * When you step on an item you interact with the item.
     */
    public void interactWithItem(Actor actor) {
        Coordinate current = actor.getPosition();

        // boundary checks on Coordinate
        if (current == null) {
            throw new NullPointerException("Illegal Hero State");
        }

        Tile currentTile = board.getTile(current);

        // boundary checks on Tile
        if (currentTile == null) {
            throw new NullPointerException("Illegal Tile");
        }
        // boundary checks on Item
        if (currentTile.getItem() == null) {
            throw new IllegalStateException("Invalid Retrieve Item State");
        }

        // the hero uses the item
        currentTile.getItem().interact(actor);

        // if it has no repeat uses
        if (currentTile.getItem().isOneTimeUse()) {
            // remove item
            currentTile.setItem(null);
        }
    }


}
