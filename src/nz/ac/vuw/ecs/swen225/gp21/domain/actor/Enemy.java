package nz.ac.vuw.ecs.swen225.gp21.domain.actor;

import nz.ac.vuw.ecs.swen225.gp21.domain.board.Board;
import nz.ac.vuw.ecs.swen225.gp21.domain.utils.Coordinate;
import nz.ac.vuw.ecs.swen225.gp21.domain.utils.Direction;

import java.util.ArrayList;
import java.util.Random;

/**
 * Enemy is the abstract class that enemies in the levels will inherit.
 *
 * Note: Enemy is part of the domain. I understand this is not exactly what is anticipated but spoke with Jens
 * and he said it was fine as long as sub-actors where loaded by the Persistence for plugin features.
 *
 * See Persistency/levels/level2.jar for plugin features.
 *
 */
public abstract class Enemy extends Actor {

    // static variable to ID the enemies
    public static int enemyCount = 0;

    /**
     * Constructor with spawn location.
     *
     * @param initial
     */
    public Enemy(Coordinate initial) {
        super(initial);
        enemyCount++;
    }

    /**
     * Default constructor no parameters.
     */
    public Enemy() {
        super();
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

    /**
     * Getter for the actor's ID.
     */
    @Override
    public String getID() {
        return "enemy" + enemyCount;
    }

    /**
     * Return a string for the console based version of this board.
     *
     * @return
     */
    public String consoleString() {
        return "\uD83D\uDC1B";
    }
}
