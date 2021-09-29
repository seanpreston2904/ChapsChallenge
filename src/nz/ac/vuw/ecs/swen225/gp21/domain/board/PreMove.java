package nz.ac.vuw.ecs.swen225.gp21.domain.board;

import nz.ac.vuw.ecs.swen225.gp21.domain.actor.Actor;

import java.util.ArrayList;

public interface PreMove {
    /**
     * A method which is used to fire events before the actor enters a space.
     *
     * @param board - the game board.
     *
     * @param enemies - the enemies on the game board.
     *
     * @param actor - the person doing the pre-move.
     *
     * @return - true means continue to space, false means do not.
     *
     */
    boolean preInteract(Board board, ArrayList<Actor> enemies, Actor actor);
}
