package nz.ac.vuw.ecs.swen225.gp21.domain.board;

import nz.ac.vuw.ecs.swen225.gp21.domain.actor.Actor;

public interface PreMove {
    /**
     * A method which is used to fire events before the actor enters a space.
     *
     * @return - true means continue to space, false means do not.
     */
    boolean preInteract(Actor actor);
}
