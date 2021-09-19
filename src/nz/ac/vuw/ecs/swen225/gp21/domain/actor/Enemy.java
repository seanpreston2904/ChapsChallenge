package nz.ac.vuw.ecs.swen225.gp21.domain.actor;

import nz.ac.vuw.ecs.swen225.gp21.domain.utils.Coordinate;

import java.util.ArrayList;
import java.util.Random;

public class Enemy extends Actor {

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

}
