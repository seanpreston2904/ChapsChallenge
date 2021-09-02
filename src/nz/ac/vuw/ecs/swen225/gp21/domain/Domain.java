package nz.ac.vuw.ecs.swen225.gp21.domain;

import nz.ac.vuw.ecs.swen225.gp21.domain.actor.Player;
import nz.ac.vuw.ecs.swen225.gp21.domain.board.Board;
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

    }

    //  GAME LOGIC //
    // what events may and may not happen is controlled by the following methods

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
        if (board.validMove(moveToCoordinate)) {
            throw new IllegalArgumentException("chap cannot be moved into this position");
        }

        // if we get here then we move the actor
        hero.setPosition(moveToCoordinate);
    }



    // INVENTORY //
    // - treasure
    // - keys
    // - other ... ?


}
