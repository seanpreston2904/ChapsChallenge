package nz.ac.vuw.ecs.swen225.gp21.domain.actor;

import nz.ac.vuw.ecs.swen225.gp21.domain.Domain;
import nz.ac.vuw.ecs.swen225.gp21.domain.board.Board;
import nz.ac.vuw.ecs.swen225.gp21.domain.utils.Coordinate;
import nz.ac.vuw.ecs.swen225.gp21.domain.utils.Direction;

import java.util.ArrayList;
import java.util.Random;

public abstract class Enemy extends Actor {

    /**
     * Default constructor with spawn location.
     *
     * @param initial
     */
    public Enemy(Coordinate initial) {
        super(initial);
    }

    /**
     * Alternative constructor which randomly generates.
     *
     * @param options - one of this list is choosen as spawn location.
     */
    public Enemy(ArrayList<Coordinate> options) {
        super(options.get(new Random().nextInt(options.size())));
    }

    /**
     * This method handles the AI movement mode of the enemy.
     *
     * NOTE: Default mode is none.
     *
     * @return - resulting direction.
     *
     */
    public Direction movementModeAIDirection(Board board, Player hero) {
        return null;
    }

    /**
     * Getter for the actor's name.
     */
    @Override
    public String getName() {
        return "abstract_enemy";
    }

}
