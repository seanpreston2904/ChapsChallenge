package nz.ac.vuw.ecs.swen225.gp21.domain.actor;

import nz.ac.vuw.ecs.swen225.gp21.domain.Domain;
import nz.ac.vuw.ecs.swen225.gp21.domain.board.Board;
import nz.ac.vuw.ecs.swen225.gp21.domain.utils.Coordinate;
import nz.ac.vuw.ecs.swen225.gp21.domain.utils.Direction;

public class Bug extends Enemy {

    /**
     * Entirely standard constructor.
     *
     * @param initial
     *
     */
    public Bug(Coordinate initial) {
        super(initial);
    }

    /**
     * This method handles the AI movement mode of the enemy.
     *
     * NOTE: Mode is random movement.
     *
     * @return - resulting direction.
     *
     */
    @Override
    public Direction movementModeAIDirection(Board board, Player hero) {
        return Domain.randomlyMoveActor(this);
    }

    /**
     * The name is bug, this will load the image.
     *
     * @return
     *
     */
    @Override
    public String getName() {
        return "bug";
    }

}
